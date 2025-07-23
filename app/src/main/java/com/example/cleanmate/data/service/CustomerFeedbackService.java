package com.example.cleanmate.data.service;

import com.example.cleanmate.data.model.Feedback;
import com.example.cleanmate.data.repository.FeedbackRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class CustomerFeedbackService {
    private final FeedbackRepository feedbackRepository;

    public CustomerFeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Thêm feedback mới
     * Tương tự như POST / trong ASP.NET FeedbackController
     */
    public boolean addFeedback(int bookingId, String userId, String cleanerId, double rating, String content) throws SQLException {
        Objects.requireNonNull(userId, "UserId không được null");
        Objects.requireNonNull(cleanerId, "CleanerId không được null");
        
        if (bookingId <= 0) {
            throw new IllegalArgumentException("BookingId phải lớn hơn 0");
        }
        
        if (rating < 1.0 || rating > 5.0) {
            throw new IllegalArgumentException("Rating phải từ 1.0 đến 5.0");
        }
        
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content không được để trống");
        }

        Feedback feedback = new Feedback();
        feedback.setBookingId(bookingId);
        feedback.setUserId(userId);
        feedback.setCleanerId(cleanerId);
        feedback.setRating(rating);
        feedback.setContent(content.trim());

        feedbackRepository.addFeedback(feedback);
        return true;
    }

    /**
     * Cập nhật feedback
     * Tương tự như PUT /{feedbackId} trong ASP.NET FeedbackController
     */
    public boolean updateFeedback(int feedbackId, String userId, double rating, String content) throws SQLException {
        if (feedbackId <= 0) {
            throw new IllegalArgumentException("FeedbackId phải lớn hơn 0");
        }
        
        Objects.requireNonNull(userId, "UserId không được null");
        
        if (rating < 1.0 || rating > 5.0) {
            throw new IllegalArgumentException("Rating phải từ 1.0 đến 5.0");
        }
        
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content không được để trống");
        }

        // Kiểm tra feedback có tồn tại không
        Feedback existingFeedback = feedbackRepository.getFeedbackById(feedbackId);
        if (existingFeedback == null) {
            throw new IllegalArgumentException("Không tìm thấy feedback với ID: " + feedbackId);
        }

        // Kiểm tra feedback có thuộc về user này không
        if (!userId.equals(existingFeedback.getUserId())) {
            throw new IllegalArgumentException("Bạn không có quyền cập nhật feedback này");
        }

        existingFeedback.setRating(rating);
        existingFeedback.setContent(content.trim());

        feedbackRepository.updateFeedback(existingFeedback);
        return true;
    }

    /**
     * Xóa feedback
     * Tương tự như DELETE /{feedbackId} trong ASP.NET FeedbackController
     */
    public boolean deleteFeedback(int feedbackId, String userId) throws SQLException {
        if (feedbackId <= 0) {
            throw new IllegalArgumentException("FeedbackId phải lớn hơn 0");
        }
        
        Objects.requireNonNull(userId, "UserId không được null");

        // Kiểm tra feedback có tồn tại không
        Feedback existingFeedback = feedbackRepository.getFeedbackById(feedbackId);
        if (existingFeedback == null) {
            throw new IllegalArgumentException("Không tìm thấy feedback với ID: " + feedbackId);
        }

        // Kiểm tra feedback có thuộc về user này không
        if (!userId.equals(existingFeedback.getUserId())) {
            throw new IllegalArgumentException("Bạn không có quyền xóa feedback này");
        }

        feedbackRepository.deleteFeedback(feedbackId);
        return true;
    }

    /**
     * Lấy feedback theo ID
     * Tương tự như GET /{feedbackId} trong ASP.NET FeedbackController
     */
    public Feedback getFeedbackById(int feedbackId) throws SQLException {
        if (feedbackId <= 0) {
            throw new IllegalArgumentException("FeedbackId phải lớn hơn 0");
        }

        Feedback feedback = feedbackRepository.getFeedbackById(feedbackId);
        if (feedback == null) {
            throw new IllegalArgumentException("Không tìm thấy feedback với ID: " + feedbackId);
        }

        return feedback;
    }

    /**
     * DTO cho Feedback Display
     */
    public static class FeedbackDisplay {
        private final Integer feedbackId;
        private final Integer bookingId;
        private final String cleanerId;
        private final Double rating;
        private final String content;
        private final String createdAt;
        private final String updatedAt;

        public FeedbackDisplay(Integer feedbackId, Integer bookingId, String cleanerId, 
                             Double rating, String content, String createdAt, String updatedAt) {
            this.feedbackId = feedbackId;
            this.bookingId = bookingId;
            this.cleanerId = cleanerId;
            this.rating = rating;
            this.content = content;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        // Getters
        public Integer getFeedbackId() { return feedbackId; }
        public Integer getBookingId() { return bookingId; }
        public String getCleanerId() { return cleanerId; }
        public Double getRating() { return rating; }
        public String getContent() { return content; }
        public String getCreatedAt() { return createdAt; }
        public String getUpdatedAt() { return updatedAt; }

        // Helper method để tạo từ Feedback entity
        public static FeedbackDisplay fromFeedback(Feedback feedback) {
            return new FeedbackDisplay(
                feedback.getFeedbackId(),
                feedback.getBookingId(),
                feedback.getCleanerId(),
                feedback.getRating(),
                feedback.getContent(),
                formatTimestamp(feedback.getCreatedAt()),
                formatTimestamp(feedback.getUpdatedAt())
            );
        }

        private static String formatTimestamp(java.sql.Timestamp timestamp) {
            if (timestamp == null) return "N/A";
            return timestamp.toString().substring(0, 19); // Format: YYYY-MM-DD HH:MM:SS
        }
    }

    /**
     * DTO cho Add Feedback
     */
    public static class AddFeedbackRequest {
        private final int bookingId;
        private final String cleanerId;
        private final double rating;
        private final String content;

        public AddFeedbackRequest(int bookingId, String cleanerId, double rating, String content) {
            this.bookingId = bookingId;
            this.cleanerId = cleanerId;
            this.rating = rating;
            this.content = content;
        }

        // Getters
        public int getBookingId() { return bookingId; }
        public String getCleanerId() { return cleanerId; }
        public double getRating() { return rating; }
        public String getContent() { return content; }
    }

    /**
     * DTO cho Update Feedback
     */
    public static class UpdateFeedbackRequest {
        private final double rating;
        private final String content;

        public UpdateFeedbackRequest(double rating, String content) {
            this.rating = rating;
            this.content = content;
        }

        // Getters
        public double getRating() { return rating; }
        public String getContent() { return content; }
    }
} 