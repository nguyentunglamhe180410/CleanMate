package com.example.cleanmate.data.repository;

import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.common.utils.DateTimeVN;
import com.example.cleanmate.data.model.WalletTransaction;
import com.example.cleanmate.data.model.*;
import com.example.cleanmate.common.utils.*;
import com.example.cleanmate.common.enums.*;


import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements AutoCloseable {
    private final Connection conn;

    public TransactionRepository() throws SQLException, ClassNotFoundException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        this.conn = DriverManager.getConnection(CommonConstants.JDBC_URL);
    }

    /** 1) Add a new WalletTransaction, return its generated ID */
    public int addTransaction(WalletTransaction tx) throws SQLException {
        String sql = "INSERT INTO WalletTransactions "
                + "(WalletId, Amount, TransactionType, Description, CreatedAt) "
                + "VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, tx.getWalletid());
            ps.setBigDecimal(2, tx.getAmount());
            ps.setString(3, tx.getTransactiontype());
            ps.setString(4, tx.getDescription());
            ps.setTimestamp(5, Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1;
            }
        }
    }

    /** 2) Get all withdraw requests (joined with user/admin/transaction if needed) */
    public List<Withdrawrequest> getAllWithdrawRequests() throws SQLException {
        String sql = "SELECT RequestId, UserId, ProcessedBy, TransactionId, Amount, RequestedAt, "
                + "ProcessedAt, Status, AdminNote "
                + "FROM WithdrawRequests";
        List<Withdrawrequest> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Withdrawrequest r = mapWithdrawRequest(rs);
                list.add(r);
            }
        }
        return list;
    }

    /** 3) Get a single withdraw request by its ID */
    public Withdrawrequest getWithdrawRequestById(int requestId) throws SQLException {
        String sql = "SELECT RequestId, UserId, ProcessedBy, TransactionId, Amount, RequestedAt, "
                + "ProcessedAt, Status, AdminNote "
                + "FROM WithdrawRequests WHERE RequestId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapWithdrawRequest(rs) : null;
            }
        }
    }

    /** 4) Create a new withdraw request, return its generated ID */
    public int createWithdrawRequest(Withdrawrequest req) throws SQLException {
        String sql = "INSERT INTO WithdrawRequests "
                + "(UserId, Amount, RequestedAt, Status) "
                + "VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, req.getUserid());
            ps.setBigDecimal(2, req.getAmount());
            ps.setTimestamp(3, Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
            ps.setString(4, req.getStatus());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1;
            }
        }
    }
    /** 5) Update the fields of an existing withdraw request */
    public boolean updateWithdrawRequest(int requestId, Withdrawrequest updated) throws SQLException {
        String sql = "UPDATE WithdrawRequests SET "
                + "Status=?, ProcessedAt=?, AdminNote=?, TransactionId=?, ProcessedBy=? "
                + "WHERE RequestId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, updated.getStatus());
            ps.setTimestamp(2, updated.getProcessedat() == null
                    ? null
                    : Timestamp.valueOf(updated.getProcessedat().toLowerCase()));
            ps.setString(3, updated.getAdminnote());
            ps.setInt(4, updated.getTransactionid());
            ps.setString(5, updated.getProcessedby());
            ps.setInt(6, requestId);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean executeWithdrawTransaction(
            int requestId,
            String ProcessedBy,
            BigDecimal amount,
            String userId
    ) throws SQLException {
        try {
            conn.setAutoCommit(false);

            // a) Update wallet balance
            String sqlW = "UPDATE User_Wallet SET Balance = Balance - ?, UpdatedAt = ? WHERE UserId=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlW)) {
                ps.setBigDecimal(1, amount);
                ps.setTimestamp(2, Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
                ps.setString(3, userId);
                ps.executeUpdate();
            }

            // b) Record negative transaction
            WalletTransaction wt = new WalletTransaction();
            wt.setWalletid(getWalletIdByUser(userId));
            wt.setAmount(amount.negate());
            wt.setTransactiontype(TransactionType.DEBIT.name());
            wt.setDescription("Withdraw request #" + requestId);
            int txId = addTransaction(wt);

            // c) Update withdraw request
            String sqlR = "UPDATE WithdrawRequests SET ProcessedAt=?, TransactionId=?, ProcessedBy=?, Status=? "
                    + "WHERE RequestId=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlR)) {
                ps.setTimestamp(1, Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
                ps.setInt(2, txId);
                ps.setString(3, ProcessedBy);
                ps.setString(4, WithdrawStatus.APPROVED.name());
                ps.setInt(5, requestId);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    /** 7) Sum pending withdraw amounts for a user */
    public BigDecimal getPendingWithdrawAmount(String userId) throws SQLException {
        String sql = "SELECT SUM(Amount) FROM WithdrawRequests "
                + "WHERE UserId=? AND Status = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, WithdrawStatus.PENDING.name());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getBigDecimal(1) != null
                        ? rs.getBigDecimal(1)
                        : BigDecimal.ZERO;
            }
        }
    }

    private int getWalletIdByUser(String userId) throws SQLException {
        String sql = "SELECT WalletId FROM User_Wallet WHERE UserId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("WalletId");
                throw new SQLException("Wallet not found for user " + userId);
            }
        }
    }

    private Withdrawrequest mapWithdrawRequest(ResultSet rs) throws SQLException {
        Withdrawrequest r = new Withdrawrequest();
        r.setRequestid(rs.getInt("RequestId"));
        r.setUserid(rs.getString("UserId"));
        r.setUserid(rs.getString("ProcessedBy"));
        r.setTransactionid(rs.getInt("TransactionId"));
        r.setAmount(rs.getBigDecimal("Amount"));
        r.setRequestedat(rs.getTimestamp("RequestedAt").toLocaleString());
        Timestamp p = rs.getTimestamp("ProcessedAt");
        r.setProcessedat(p == null ? null : p.toLocaleString());
        r.setStatus(String.valueOf(WithdrawStatus.valueOf(rs.getString("Status"))));
        r.setAdminnote(rs.getString("AdminNote"));
        return r;
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}

