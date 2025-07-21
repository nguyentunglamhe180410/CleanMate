package com.example.cleanmate.data.repository;

import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.common.utils.DateTimeVN;
import com.example.cleanmate.data.model.Feedback;

import java.sql.*;

public class FeedbackRepository implements AutoCloseable {
    private final Connection conn;

    public FeedbackRepository() throws SQLException, ClassNotFoundException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        this.conn = DriverManager.getConnection(CommonConstants.JDBC_URL);
    }

    /** 1) Get one Feedback by its ID */
    public Feedback getFeedbackById(int feedbackid) throws SQLException {
        String sql = "SELECT FeedbackId, BookingId, Rating, Content, CreatedAt " +
                "FROM Feedbacks WHERE FeedbackId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, feedbackid);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Feedback f = new Feedback();
                f.setFeedbackId(rs.getInt("FeedbackId"));
                f.setBookingId(rs.getInt("BookingId"));
                f.setRating(rs.getDouble("Rating"));
                f.setContent(rs.getString("Content"));
                f.setCreatedAt(rs.getTimestamp("CreatedAt"));
                return f;
            }
        }
    }

    /** 2) Insert a new Feedback */
    public void addFeedback(Feedback feedback) throws SQLException {
        String sql = "INSERT INTO Feedbacks (BookingId, Rating, Content, CreatedAt) " +
                "VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, feedback.getBookingId());
            ps.setDouble(2, feedback.getRating());
            ps.setString(3, feedback.getContent());
            ps.setTimestamp(4, Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    feedback.setFeedbackId(keys.getInt(1));
                }
            }
        }
    }

    /** 3) Update an existing Feedback */
    public void updateFeedback(Feedback feedback) throws SQLException {
        String sql = "UPDATE Feedbacks SET Rating = ?, Content = ? " +
                "WHERE FeedbackId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, feedback.getRating());
            ps.setString(2, feedback.getContent());
            ps.setInt(3, feedback.getFeedbackId());
            ps.executeUpdate();
        }
    }

    /** 4) Delete a Feedback by its ID */
    public void deleteFeedback(int feedbackid) throws SQLException {
        String sql = "DELETE FROM Feedbacks WHERE FeedbackId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, feedbackid);
            ps.executeUpdate();
        }
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}

