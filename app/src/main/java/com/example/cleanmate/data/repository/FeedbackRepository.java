package com.example.cleanmate.data.repository;

import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.common.utils.DateTimeVN;
import com.example.cleanmate.data.model.Feedback;

import java.sql.*;

public class FeedbackRepository implements AutoCloseable {
    private final Connection conn;

    public FeedbackRepository() throws SQLException, ClassNotFoundException {
        try {
            System.out.println("FeedbackRepository: Đang load JDBC driver...");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            System.out.println("FeedbackRepository: Đã load JDBC driver thành công");
            
            System.out.println("FeedbackRepository: Đang kết nối database...");
            System.out.println("FeedbackRepository: JDBC_URL = " + CommonConstants.JDBC_URL);
            this.conn = DriverManager.getConnection(CommonConstants.JDBC_URL);
            System.out.println("FeedbackRepository: Đã kết nối database thành công");
            
        } catch (ClassNotFoundException e) {
            System.err.println("FeedbackRepository: Lỗi load JDBC driver: " + e.getMessage());
            throw e;
        } catch (SQLException e) {
            System.err.println("FeedbackRepository: Lỗi kết nối database: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /** Debug: Check table structure */
    public void debugTableStructure() throws SQLException {
        System.out.println("=== DEBUG: Checking Feedbacks table structure ===");
        String sql = "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Feedbacks' ORDER BY ORDINAL_POSITION";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("DATA_TYPE");
                System.out.println("Column: " + columnName + " (" + dataType + ")");
            }
        }
    }

    /** 1) Get one Feedback by its ID */
    public Feedback getFeedbackById(int feedbackid) throws SQLException {
        // Debug: Kiểm tra cấu trúc bảng trước
        debugTableStructure();
        
        String sql = "SELECT FeedbackId, BookingId, Rating, Content, CreatedAt " +
                "FROM Feedbacks WHERE FeedbackId = ?";
        System.out.println("DEBUG: getFeedbackById SQL = " + sql);
        System.out.println("DEBUG: FeedbackId = " + feedbackid);
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

