package com.example.cleanmate.data.repository;

import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.common.utils.DateTimeVN;
import com.example.cleanmate.data.model.Payment;

import java.sql.*;

public class PaymentRepository implements AutoCloseable {
    private final Connection conn;

    public PaymentRepository() throws SQLException, ClassNotFoundException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        this.conn = DriverManager.getConnection(CommonConstants.JDBC_URL);
    }

    /**
     * Inserts a new Payment and returns it with paymentid filled.
     */
    public Payment addNewPayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO Payment "
                + "(BookingId, Amount, PaymentMethod, PaymentStatus, TransactionId, CreatedAt) "
                + "VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, payment.getBookingId());
            ps.setBigDecimal(2, payment.getAmount());
            ps.setString(3, payment.getPaymentMethod());
            ps.setString(4, payment.getPaymentStatus());
            ps.setString(5, payment.getTransactionId());
            ps.setTimestamp(6, Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    payment.setPaymentId(keys.getInt(1));
                }
            }
            return payment;
        }
    }

    /**
     * Updates an existing Payment (matched by bookingid).
     * Returns updated String or null if none found.
     */
    public Payment updatePayment(Payment newPayment) throws SQLException {
        // First, find existing by bookingid
        Payment existing = getPaymentByBookingId(newPayment.getBookingId());
        if (existing == null) return null;

        String sql = "UPDATE Payment SET "
                + "Amount = ?, PaymentMethod = ?, PaymentStatus = ?, "
                + "TransactionId = ? "
                + "WHERE BookingId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, newPayment.getAmount());
            ps.setString(2, newPayment.getPaymentMethod());
            ps.setString(3, newPayment.getPaymentStatus());
            ps.setString(4, newPayment.getTransactionId());
            ps.setInt(5, newPayment.getBookingId());
            ps.executeUpdate();
            // refresh fields on existing
            existing.setAmount(newPayment.getAmount());
            existing.setPaymentMethod(newPayment.getPaymentMethod());
            existing.setPaymentStatus(newPayment.getPaymentStatus());
            existing.setTransactionId(newPayment.getTransactionId());
            return existing;
        }
    }

    /**
     * Finds a Payment by its primary key paymentid.
     */
    public Payment findPaymentById(int paymentid) throws SQLException {
        String sql = "SELECT PaymentId, BookingId, Amount, PaymentMethod, PaymentStatus, TransactionId, CreatedAt "
                + "FROM Payment WHERE PaymentId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paymentid);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return mapRow(rs);
            }
        }
    }

    /**
     * Returns the first Payment matching a given bookingid, or null.
     */
    public Payment getPaymentByBookingId(int bookingid) throws SQLException {
        String sql = "SELECT PaymentId, BookingId, Amount, PaymentMethod, PaymentStatus, TransactionId, CreatedAt "
                + "FROM Payment WHERE BookingId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingid);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return mapRow(rs);
            }
        }
    }

    /** Maps a ResultSet row into your Payment model. */
    private Payment mapRow(ResultSet rs) throws SQLException {
        Payment p = new Payment();
        p.setPaymentId(rs.getInt("PaymentId"));
        p.setBookingId(rs.getInt("BookingId"));
        p.setAmount(rs.getBigDecimal("Amount"));
        p.setPaymentMethod(rs.getString("PaymentMethod"));
        p.setPaymentStatus(rs.getString("PaymentStatus"));
        p.setTransactionId(rs.getString("TransactionId"));
        p.setCreatedAt(rs.getTimestamp("CreatedAt"));
        return p;
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}

