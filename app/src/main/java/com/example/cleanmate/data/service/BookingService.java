package com.example.cleanmate.data.service;

import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.common.utils.DateTimeVN;
import com.example.cleanmate.data.model.Booking;
import com.example.cleanmate.data.model.dto.*;
import com.example.cleanmate.data.repository.BookingRepository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
public class BookingService {
    private final BookingRepository repo;

    public BookingService(BookingRepository repo) {
        this.repo = repo;
    }

    public Booking addNewBooking(dto.BookingCreateDTO dto) throws SQLException {
        Booking b = new Booking();
        b.setServicePriceId(dto.getServicePriceId());
        b.setCleanerId(dto.getCleanerId());
        b.setUserId(dto.getUserId());
        b.setBookingStatusId(CommonConstants.BookingStatus.NEW);
        b.setNote(dto.getNote());
        b.setAddressId(dto.getAddressId());
        b.setDate(String.valueOf(dto.getDate()));
        b.setStartTime(String.valueOf(dto.getStartTime()));
        b.setTotalPrice(dto.getTotalPrice());
        b.setCreatedAt(Timestamp.valueOf(String.valueOf(DateTimeVN.getNow())));
        b.setUpdatedAt(null);
        return repo.addBooking(b);
    }

    public Booking getBookingById(int bookingId) throws SQLException {
        return repo.getBookingById(bookingId);
    }

    public List<dto.BookingDTO> getBookingsByUserId(String userId, Integer statusId) throws SQLException {
        return repo.getBookingByUserId(userId, statusId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<dto.BookingDTO> getBookingsForAdmin(Integer statusId) throws SQLException {
        return repo.getBookingForAdmin(statusId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public boolean assignCleaner(int bookingId, String cleanerId) throws SQLException {
        return repo.processBookingAfterAssigningCleaner(bookingId, cleanerId);
    }

    public boolean cancelBooking(int bookingId) throws SQLException {
        return repo.cancelBooking(bookingId);
    }

    // --- helper to map Booking → BookingDTO ---
    private dto.BookingDTO toDto(Booking b) {
        dto.BookingDTO d = new dto.BookingDTO();
        d.setBookingId(b.getBookingId());
        d.setServicePriceId(b.getServicePriceId());
        d.setCleanerId(b.getCleanerId());
        d.setUserId(b.getUserId());
        d.setBookingStatusId(b.getBookingStatusId());
        d.setNote(b.getNote());
        d.setAddressId(b.getAddressId());
        d.setDate(b.getDate());
        d.setStartTime(b.getStartTime());
        d.setTotalPrice(b.getTotalPrice());
        d.setCreatedAt(b.getCreatedAt());
        d.setUpdatedAt(b.getUpdatedAt());
        // derived fields
        d.setStatus(CommonConstants.getStatusString(b.getBookingStatusId()));
        d.setHasFeedback(false);
        return d;
    }
}

