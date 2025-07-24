package com.example.MovieInABox.data.service;


import com.example.MovieInABox.common.enums.PaymentType;
import com.example.MovieInABox.data.model.Payment;
import com.example.MovieInABox.data.model.dto.dto;
import com.example.MovieInABox.data.repository.PaymentRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class PaymentService implements AutoCloseable {
    private final PaymentRepository repo;

    public PaymentService() throws SQLException, ClassNotFoundException {
        this.repo = new PaymentRepository();
    }

    /**
     * Add a new Payment record.
     */
    public Payment addNewPayment(Payment newPayment) throws SQLException {
        return repo.addNewPayment(newPayment);
    }

    /**
     * Fetch the Payment by its bookingId (first match).
     */
    public Payment getPaymentByBookingId(int bookingId) throws SQLException {
        return repo.getPaymentByBookingId(bookingId);
    }

    /**
     * Mark an existing payment as paid, optionally setting a transactionId.
     * Returns a DTO or null if not found.
     */
    public dto.PaymentDTO markBookingAsPaid(int paymentId, String transactionId) throws SQLException {
        Payment payment = repo.getPaymentByBookingId(paymentId);
        if (payment == null) {
            return null;
        }

        payment.setPaymentStatus("Paid");
        if (transactionId != null) {
            payment.setTransactionId(transactionId);
        }
        // UpdatedAt could be set here if you track it on Payment
        payment = repo.updatePayment(payment);

        // Build DTO
        return new dto.PaymentDTO(
                payment.getPaymentId(),
                payment.getBookingId(),
                payment.getAmount(),
                PaymentType.valueOf(payment.getPaymentMethod()),
                payment.getPaymentStatus(),
                payment.getTransactionId(),
                LocalDateTime.ofInstant(payment.getCreatedAt().toInstant(), ZoneId.systemDefault())
        );
    }

    @Override
    public void close() throws SQLException {
        repo.close();
    }
}

