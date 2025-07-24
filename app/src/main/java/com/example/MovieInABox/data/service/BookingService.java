package com.example.MovieInABox.data.service;

import com.example.MovieInABox.common.CommonConstants;
import com.example.MovieInABox.common.utils.DateTimeVN;
import com.example.MovieInABox.data.model.Booking;
import com.example.MovieInABox.data.model.User;
import com.example.MovieInABox.data.model.dto.*;
import com.example.MovieInABox.data.repository.BookingRepository;
import com.example.MovieInABox.data.repository.CustomerRepository;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class BookingService {
    private final BookingRepository repo;
    private final CustomerRepository crepo;

    public BookingService(BookingRepository repo, CustomerRepository crepo) {
        this.repo = repo;
        this.crepo = crepo;
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

    public List<dto.BookingDTO> getBookingsByUserIdDto(String userId, Integer statusId) throws SQLException {
        return repo.getBookingByUserIdDto(userId,statusId);
    }



    public boolean assignCleaner(int bookingId, String cleanerId) throws SQLException {
        return repo.processBookingAfterAssigningCleaner(bookingId, cleanerId);
    }

    public boolean cancelBooking(int bookingId) throws SQLException {
        return repo.changeBookingStatus(bookingId, CommonConstants.BookingStatus.CANCEL);
    }
    public boolean acceptWork(int bookingId) throws SQLException {
        return repo.changeBookingStatus(bookingId, CommonConstants.BookingStatus.ACCEPT);
    }
    public boolean doneWork(int bookingId) throws SQLException {
        return repo.changeBookingStatus(bookingId, CommonConstants.BookingStatus.PENDING_DONE);
    }
    public boolean doneCustomerAccept(int bookingId) throws SQLException {
        return repo.changeBookingStatus(bookingId, CommonConstants.BookingStatus.DONE);
    }

    // --- helper to map Booking → BookingDTO ---
    private dto.BookingDTO toDto(Booking b) throws SQLException {
        User u = crepo.getUserById(b.getUserId());
        dto.BookingDTO d = new dto.BookingDTO( b.getBookingId(), "Dọn nhà theo giờ", "Dịch vụ dọn nhà theo giờ chuyên nghiệp, linh hoạt theo nhu cầu của bạn. Nhân viên được đào tạo kỹ lưỡng, đảm bảo sạch sẽ, gọn gàng và tiết kiệm thời gian. Phù hợp cho nhà ở, căn hộ, văn phòng nhỏ hoặc phòng trọ",b.getDate(),b.getStartTime(),b.getTotalPrice(),b.getTotalPrice(),"Hoa lac","No 1",u.getFullName(),u.getPhoneNumber(),b.getBookingStatusId(),b.getUserId(),b.getCleanerId());
            return d;
    }

}

