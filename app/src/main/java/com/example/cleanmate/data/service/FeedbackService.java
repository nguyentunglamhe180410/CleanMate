package com.example.cleanmate.data.service;


import com.example.cleanmate.common.utils.DateTimeVN;
import com.example.cleanmate.data.model.Booking;
import com.example.cleanmate.data.model.Feedback;
import com.example.cleanmate.data.repository.BookingRepository;
import com.example.cleanmate.data.repository.FeedbackRepository;

import java.sql.SQLException;
import java.sql.Timestamp;

public class FeedbackService implements AutoCloseable {
    private final FeedbackRepository repo;
    private final BookingRepository bookingRepo;

    public FeedbackService() throws SQLException, ClassNotFoundException {
        this.repo = new FeedbackRepository();
        this.bookingRepo = new BookingRepository();
    }


    public void addFeedback(int bookingId,
                            String userId,
                            String cleanerId,
                            Double rating,
                            String content) throws SQLException {
        Booking b = bookingRepo.getBookingById(bookingId);
        if (b == null) {
            throw new IllegalArgumentException("Booking không tồn tại.");
        }
        if (b.getUserId() != userId) {
            throw new IllegalArgumentException("Bạn chỉ có thể để lại feedback cho booking của chính mình.");
        }
        if (cleanerId != null && !cleanerId.isEmpty() && b.getCleanerId() != cleanerId) {
            throw new IllegalArgumentException("Cleaner này không được gán cho booking.");
        }
        // 2) Validate rating
        if (rating != null && (rating < 1 || rating > 5)) {
            throw new IllegalArgumentException("Rating phải từ 1 đến 5.");
        }
        // 3) Build entity and persist
        Feedback f = new Feedback();
        f.setBookingId(bookingId);
        f.setUserId(userId);
        f.setCleanerId(cleanerId);
        f.setRating(rating);
        f.setContent(content);
        f.setCreatedAt(Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
        f.setUpdatedAt(Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
        repo.addFeedback(f);
    }

    /**
     * Update an existing feedback. Throws if not found or unauthorized.
     */
    public void updateFeedback(int feedbackId,
                               String userId,
                               Double rating,
                               String content) throws SQLException {
        Feedback existing = repo.getFeedbackById(feedbackId);
        if (existing == null) {
            throw new IllegalArgumentException("Feedback không tồn tại.");
        }
        if (!existing.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Bạn chỉ có thể cập nhật feedback của chính mình.");
        }
        if (rating != null && (rating < 1 || rating > 5)) {
            throw new IllegalArgumentException("Rating phải từ 1 đến 5.");
        }
        if (rating != null)  existing.setRating(rating);
        if (content != null) existing.setContent(content);
        existing.setUpdatedAt(Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
        repo.updateFeedback(existing);
    }

    /**
     * Delete a feedback. Throws if not found or unauthorized.
     */
    public void deleteFeedback(int feedbackId,
                               String userId) throws SQLException {
        Feedback existing = repo.getFeedbackById(feedbackId);
        if (existing == null) {
            throw new IllegalArgumentException("Feedback không tồn tại.");
        }
        if (!existing.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Bạn chỉ có thể xóa feedback của chính mình.");
        }
        repo.deleteFeedback(feedbackId);
    }

    @Override
    public void close() throws SQLException {
        repo.close();
    }
}

