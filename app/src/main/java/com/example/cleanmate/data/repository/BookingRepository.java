package com.example.cleanmate.data.repository;

import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.common.utils.DateTimeVN;
import com.example.cleanmate.data.model.Booking;
import com.example.cleanmate.data.model.dto.dto;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class BookingRepository implements AutoCloseable {
    private final Connection conn;

    public BookingRepository() throws SQLException, ClassNotFoundException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        this.conn = DriverManager.getConnection(CommonConstants.JDBC_URL);
    }

    /** Thêm mới booking và trả về đối tượng với bookingid gán tự động */
    public Booking addBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO Booking "
                + "(ServicePriceId, CleanerId, UserId, BookingStatusId, Note, AddressId, Date, StartTime, TotalPrice, CreatedAt) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, booking.getServicePriceId());
            ps.setString(2, booking.getCleanerId());
            ps.setString(3, booking.getUserId());
            ps.setInt(4, booking.getBookingStatusId());
            ps.setString(5, booking.getNote());
            ps.setInt(6, booking.getAddressId());
            ps.setDate(7, Date.valueOf(String.valueOf(booking.getDate())));
            ps.setTime(8, Time.valueOf(String.valueOf(booking.getStartTime())));
            ps.setBigDecimal(9, booking.getTotalPrice());
            ps.setTimestamp(10, Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    booking.setBookingId(keys.getInt(1));
                }
            }
            return booking;
        }
    }

    /** Lấy một booking theo ID */
    public Booking getBookingById(int bookingid) throws SQLException {
        String sql = "SELECT * FROM Booking WHERE BookingId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingid);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return mapRow(rs);
            }
        }
    }
    public dto.BookingDTO getBookingDtoById(int bookingId) throws SQLException {
        String sql =
                "SELECT " +
                        "  b.BookingId, " +
                        "  s.Name AS ServiceName, " +
                        "  s.Description AS ServiceDescription, " +
                        "  b.Date, " +
                        "  b.StartTime, " +
                        "  sp.Price, " +
                        "  sp.Commission, " +
                        "  a.GG_FormattedAddress AS Address, " +
                        "  a.AddressNo, " +
                        "  u.FullName AS CustomerFullName, " +
                        "  u.PhoneNumber AS CustomerPhoneNumber, " +
                        "  b.BookingStatusId, " +
                        "  b.UserId, " +
                        "  b.CleanerId " +
                        "FROM Service s " +
                        "  JOIN Service_Price sp ON s.ServiceId = sp.ServiceId " +
                        "  JOIN Booking b       ON b.Service_PriceId = sp.PriceId " +
                        "  JOIN Customer_Address a ON a.UserId = b.UserId " +
                        "  JOIN AspNetUsers u   ON u.Id = b.UserId " +
                        "WHERE b.BookingId = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                // parse date/time strings
                String dateStr = rs.getString("Date");
                LocalDate date = dateStr == null
                        ? null
                        : LocalDate.parse(dateStr);                // ISO_LOCAL_DATE

                String timeStr = rs.getString("StartTime");
                LocalTime time = timeStr == null
                        ? null
                        : LocalTime.parse(timeStr);                // ISO_LOCAL_TIME

                return new dto.BookingDTO(
                        rs.getInt       ("BookingId"),
                        rs.getString    ("ServiceName"),
                        rs.getString    ("ServiceDescription"),
                        date,
                        time,
                        rs.getBigDecimal("Price"),
                        rs.getBigDecimal("Commission"),
                        rs.getString    ("Address"),
                        rs.getString    ("AddressNo"),
                        rs.getString    ("CustomerFullName"),
                        rs.getString    ("CustomerPhoneNumber"),
                        rs.getInt       ("BookingStatusId"),
                        rs.getString    ("UserId"),
                        rs.getString    ("CleanerId")
                );
            }
        }
    }

    public List<dto.BookingDTO> getBookingByUserIdDto(String userId,
                                                   Integer statusId) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT " +
                        "  b.BookingId, " +
                        "  s.Name AS ServiceName, " +
                        "  s.Description AS ServiceDescription, " +
                        "  b.Date, " +
                        "  b.StartTime, " +
                        "  sp.Price, " +
                        "  sp.Commission, " +
                        "  a.GG_FormattedAddress AS Address, " +
                        "  a.AddressNo, " +
                        "  u.FullName AS CustomerFullName, " +
                        "  u.PhoneNumber AS CustomerPhoneNumber, " +
                        "  b.BookingStatusId, " +
                        "  b.UserId, " +
                        "  b.CleanerId " +
                        "FROM Service s " +
                        "  JOIN Service_Price sp ON s.ServiceId = sp.ServiceId " +
                        "  JOIN Booking b       ON b.Service_PriceId = sp.PriceId " +
                        "  JOIN Customer_Address a ON a.UserId = b.UserId " +
                        "  JOIN AspNetUsers u   ON u.Id = b.UserId " +
                        "WHERE b.UserId = ?"
        );
        if (statusId != null) {
            sql.append(" AND b.BookingStatusId = ?");
        }
        sql.append(" ORDER BY b.CreatedAt DESC");

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            // bind parameters
            ps.setString(1, userId);
            if (statusId != null) {
                ps.setInt(2, statusId);
            }

            List<dto.BookingDTO> results = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    java.sql.Date sqlDate = rs.getDate("Date");
                    String dateStr = rs.getString("Date");          // e.g. "2025-06-13"
                    LocalDate date = dateStr == null
                            ? null
                            : LocalDate.parse(dateStr);                // ISO_LOCAL_DATE

                    String timeStr = rs.getString("StartTime");     // e.g. "17:00:00"
                    LocalTime time = timeStr == null
                            ? null
                            : LocalTime.parse(timeStr);                        results.add(new dto.BookingDTO(
                            rs.getInt   ("BookingId"),
                            rs.getString("ServiceName"),
                            rs.getString("ServiceDescription"),
                            date,time,
                            rs.getBigDecimal("Price"),
                            rs.getBigDecimal("Commission"),
                            rs.getString("Address"),
                            rs.getString("AddressNo"),
                            rs.getString("CustomerFullName"),
                            rs.getString("CustomerPhoneNumber"),
                            rs.getInt   ("BookingStatusId"),
                            rs.getString("UserId"),
                            rs.getString("CleanerId")
                    ));
                }
            }
            return results;
        }
    }

    /** Lấy danh sách booking của user, có thể filter statusid */
    public List<Booking> getBookingByUserId(String userid, Integer statusid) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM Booking WHERE UserId = ?");
        if (statusid != null) sql.append(" AND BookingStatusId = ").append(statusid);
        sql.append(" ORDER BY CreatedAt DESC");

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            ps.setString(1, userid);
            try (ResultSet rs = ps.executeQuery()) {
                List<Booking> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
                return list;
            }
        }
    }


    /** Lấy tất cả booking cho admin, có thể filter statusid */
    public List<Booking> getBookingForAdmin(Integer statusid) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM Booking");
        if (statusid != null) sql.append(" WHERE BookingStatusId = ").append(statusid);
        sql.append(" ORDER BY CreatedAt");

        try (PreparedStatement ps = conn.prepareStatement(sql.toString());
             ResultSet rs = ps.executeQuery()) {
            List<Booking> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        }
    }

    /** Gán cleaner cho booking; chỉ thành công nếu trạng thái phù hợp */
    public boolean processBookingAfterAssigningCleaner(int bookingid, String cleanerid) throws SQLException {
        Booking booking = getBookingById(bookingid);
        if (booking == null) throw new SQLException("Booking not found");
        int st = booking.getBookingStatusId();
        if (st != CommonConstants.BookingStatus.NEW && st != CommonConstants.BookingStatus.ACCEPT) {
            throw new SQLException("Invalid status for assignment");
        }
        String sql = "UPDATE Booking SET CleanerId=?, BookingStatusId=? WHERE BookingId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cleanerid);
            ps.setInt(2, CommonConstants.BookingStatus.ACCEPT);
            ps.setInt(3, bookingid);
            return ps.executeUpdate() == 1;
        }
    }

    /** Hủy booking; chỉ cho phép nếu hiện tại NEW hoặc ACCEPT */
    public boolean changeBookingStatus(int bookingid, int status) throws SQLException {
        Booking booking = getBookingById(bookingid);
        if (booking == null) throw new SQLException("Booking not found");
        int st = booking.getBookingStatusId();
        if (st != CommonConstants.BookingStatus.NEW && st != CommonConstants.BookingStatus.ACCEPT) {
            throw new SQLException("Cannot cancel booking in this state");
        }
        String sql = "UPDATE Booking SET BookingStatusId=?, UpdatedAt=? WHERE BookingId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setTimestamp(10, Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
            ps.setInt(3, bookingid);
            return ps.executeUpdate() == 1;
        }
    }


    private Booking mapRow(ResultSet rs) throws SQLException {
        Booking b = new Booking();

        b.setBookingId(rs.getInt("BookingId"));
        b.setServicePriceId(rs.getInt("ServicePriceId"));
        b.setCleanerId(rs.getString("CleanerId"));
        b.setUserId(rs.getString("UserId"));
        b.setBookingStatusId(rs.getInt("BookingStatusId"));
        b.setNote(rs.getString("Note"));
        b.setAddressId(rs.getInt("AddressId"));

        // Map DATE → String via java.sql.Date.toString()
        java.sql.Date sqlDate = rs.getDate("Date");
        b.setDate(sqlDate != null ? sqlDate.toString() : null);

        // Map TIME → String via java.sql.Time.toString()
        java.sql.Time sqlTime = rs.getTime("StartTime");
        b.setStartTime(sqlTime != null ? sqlTime.toString() : null);

        // DECIMAL → BigDecimal
        b.setTotalPrice(rs.getBigDecimal("TotalPrice"));

        // TIMESTAMP → java.sql.Timestamp (matches your field type)
        b.setCreatedAt(rs.getTimestamp("CreatedAt"));
        b.setUpdatedAt(rs.getTimestamp("UpdatedAt"));

        return b;
    }




    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}

