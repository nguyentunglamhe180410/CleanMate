package com.example.MovieInABox.data.repository;

import com.example.MovieInABox.common.CommonConstants;
import com.example.MovieInABox.data.model.dto.*;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CleanPerHourRepository implements AutoCloseable {
    private final Connection conn;

    public CleanPerHourRepository() throws SQLException, ClassNotFoundException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        this.conn = DriverManager.getConnection(CommonConstants.JDBC_URL);
    }

    public List<dto.CleanerDTO> getAllCleaners() throws SQLException {
        String sql =
                "SELECT c.UserId, u.FullName " +
                        "FROM CleanerProfiles c " +
                        "JOIN Users u ON c.UserId = u.Id " +
                        "WHERE c.Available = 1";

        List<dto.CleanerDTO> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                dto.CleanerDTO dto = new dto.CleanerDTO();
                dto.setCleanerId(rs.getString("UserId"));
                dto.setName(rs.getString("FullName"));
                list.add(dto);
            }
        }
        return list;
    }


    public List<String> getBookedCleaners(
            String requestDate,
            String requestStartTime,
            String requestEndTime
    ) throws SQLException {

        String sql =
                "SELECT b.CleanerId, b.StartTime, d.DurationTime " +
                        "FROM Booking  b " +
                        "JOIN ServicePrices sp ON b.ServicePriceId = sp.PriceId " +
                        "JOIN Durations d      ON sp.DurationId     = d.DurationId " +
                        "WHERE b.BookingStatusId <> ? " +
                        "  AND b.Date = ? " +
                        "  AND b.StartTime < ?";

        List<String> booked = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, CommonConstants.BookingStatus.CANCEL);
            ps.setString(2, requestDate);
            ps.setString(3, requestEndTime);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String cleanerId = rs.getString("CleanerId");
                    String startTimeStr = rs.getString("StartTime");
                    int durationHours = rs.getInt("DurationTime");

                    // parse times
                    LocalTime start = LocalTime.parse(startTimeStr);
                    LocalTime end   = start.plusHours(durationHours);

                    LocalTime reqStart = LocalTime.parse(requestStartTime);
                    // if booking end after requested start → conflict
                    if (end.isAfter(reqStart)) {
                        if (!booked.contains(cleanerId)) {
                            booked.add(cleanerId);
                        }
                    }
                }
            }
        }
        return booked;
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}

