package com.example.cleanmate.data.repository;

import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.common.utils.DateTimeVN;
import com.example.cleanmate.data.model.UserWallet;

import java.math.BigDecimal;
import java.sql.*;

public class UserWalletRepository implements AutoCloseable {
    private final Connection conn;

    public UserWalletRepository() throws SQLException, ClassNotFoundException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        this.conn = DriverManager.getConnection(CommonConstants.JDBC_URL);
    }


    public UserWallet addNewWallet(UserWallet wallet) throws SQLException {
        String sql = "INSERT INTO User_Wallet (UserId, Balance, UpdatedAt) VALUES (?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, wallet.getUserId());
            ps.setBigDecimal(2, wallet.getBalance());
            ps.setTimestamp(3, Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    wallet.setWalletId(keys.getInt(1));
                }
            }
        }
        return wallet;
    }

    /**
     * Fetches a UserWallet by userId, or throws if none found.
     */
    public UserWallet getWalletByUserId(String userId) throws SQLException {
        String sql = "SELECT WalletId, UserId, Balance, UpdatedAt " +
                "FROM User_Wallet WHERE UserId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Không tìm thấy ví cho người dùng này.");
                }
                UserWallet w = new UserWallet();
                w.setWalletId(rs.getInt("WalletId"));
                w.setUserId(rs.getString("UserId"));
                w.setBalance(rs.getBigDecimal("Balance"));
                w.setUpdatedAt(rs.getTimestamp("UpdatedAt").toLocaleString());
                return w;
            }
        }
    }

    /**
     * Updates the wallet's balance by adding 'amount', sets UpdatedAt, returns true on success.
     */
    public boolean updateWalletBalance(String userId, BigDecimal amount, String transactionDescription) throws SQLException {
        UserWallet w = getWalletByUserId(userId);
        BigDecimal newBalance = w.getBalance().add(amount);

        String sql = "UPDATE User_Wallet SET Balance = ?, UpdatedAt = ? WHERE UserId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, newBalance);
            ps.setTimestamp(2, Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
            ps.setString(3, userId);
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}
