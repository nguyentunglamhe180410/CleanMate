package com.example.cleanmate.data.repository;


import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.common.utils.DateTimeVN;
import com.example.cleanmate.data.model.viewmodels.employee.WorkDetailsViewModel;
import com.example.cleanmate.data.model.viewmodels.employee.WorkListViewModel;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements AutoCloseable {
    private final Connection conn;

    public EmployeeRepository() throws SQLException, ClassNotFoundException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        this.conn = DriverManager.getConnection(CommonConstants.JDBC_URL);
    }


    public List<WorkListViewModel> findAllWork(Integer status, String employeeId) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT b.BookingId, s.Name AS ServiceName, u.FullName AS CustomerFullName, ")
                .append("       b.Date, b.StartTime, d.DurationTime AS Duration, ")
                .append("       a.GG_DispalyName AS Address, b.Note, ")
                .append("       ISNULL(b.TotalPrice, 0) AS TotalPrice, ")
                .append("       bs.Status AS Status, a.AddressNo, ")
                .append("       b.CreatedAt, b.UpdatedAt ")
                .append("FROM Booking  b ")
                .append("JOIN ServicePrices sp ON b.ServicePriceId = sp.PriceId ")
                .append("JOIN Services s      ON sp.ServiceId     = s.ServiceId ")
                .append("JOIN Durations d     ON sp.DurationId    = d.DurationId ")
                .append("JOIN Users u         ON b.UserId         = u.Id ")
                .append("JOIN CustomerAddresses a ON b.AddressId    = a.AddressId ")
                .append("JOIN BookingStatuses bs ON b.BookingStatusId = bs.BookingStatusId ")
                .append("WHERE 1=1 ");
        if (status != null)       sql.append(" AND b.BookingStatusId = ").append(status);
        if (employeeId != null)   sql.append(" AND b.CleanerId = '").append(employeeId).append("'");
        sql.append(" ORDER BY b.CreatedAt DESC");

        List<WorkListViewModel> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql.toString());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                WorkListViewModel w = new WorkListViewModel();
                w.setBookingId(rs.getInt("BookingId"));
                w.setServiceName(rs.getString("ServiceName"));
                w.setCustomerFullName(rs.getString("CustomerFullName"));
                w.setDate(LocalDate.parse(rs.getDate("Date").toString()));
                w.setStartTime(LocalTime.parse(rs.getTime("StartTime").toString()));
                w.setDuration(rs.getInt("Duration"));
                w.setAddress(rs.getString("Address"));
                w.setNote(rs.getString("Note"));
                w.setTotalPrice(rs.getBigDecimal("TotalPrice"));
                w.setStatus(rs.getString("Status"));
                w.setAddressNo(rs.getString("AddressNo"));
                w.setCreatedAt(rs.getTimestamp("CreatedAt"));
                w.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                list.add(w);
            }
        }
        return list;
    }

    /**
     * Equivalent to EF's FindWorkByIdAsync.
     */
    public WorkDetailsViewModel findWorkById(int bookingId) throws SQLException {
        String sql =
                "SELECT b.BookingId, s.Name AS ServiceName, " +
                        "       d.SquareMeterSpecific + 'm² làm trong ' + CAST(d.DurationTime AS VARCHAR) + ' giờ' AS ServiceDescription, " +
                        "       CAST(d.DurationTime AS VARCHAR) + ' giờ' AS Duration, " +
                        "       b.TotalPrice AS Price, " +
                        "       FLOOR(b.TotalPrice * ? / 1000) * 1000 AS Commission, " +
                        "       CONVERT(VARCHAR, b.Date, 23) AS Date, " +
                        "       CONVERT(VARCHAR, b.StartTime, 8) AS StartTime, " +
                        "       a.GG_DispalyName AS Address, a.AddressNo, b.Note, " +
                        "       bs.Status AS Status, b.BookingStatusId AS StatusId, 0 AS IsRead, " +
                        "       u.FullName AS CustomerFullName, u.PhoneNumber AS CustomerPhoneNumber, " +
                        "       b.CleanerId AS EmployeeId, a.GG_PlaceId AS PlaceID, " +
                        "       CAST(a.Latitude AS VARCHAR) AS Latitude, " +
                        "       CAST(a.Longitude AS VARCHAR) AS Longitude, " +
                        "       sp.Price AS DecimalPrice, " +
                        "       FLOOR(sp.Price * ? / 1000) * 1000 AS DecimalCommission " +
                        "FROM Booking  b " +
                        "JOIN ServicePrices sp ON b.ServicePriceId = sp.PriceId " +
                        "JOIN Services s      ON sp.ServiceId     = s.ServiceId " +
                        "JOIN Durations d     ON sp.DurationId    = d.DurationId " +
                        "JOIN CustomerAddresses a ON b.AddressId  = a.AddressId " +
                        "JOIN Users u         ON b.UserId        = u.Id " +
                        "JOIN BookingStatuses bs ON b.BookingStatusId = bs.BookingStatusId " +
                        "WHERE b.BookingId = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // commission percentage from CommonConstants
            ps.setBigDecimal(1, BigDecimal.valueOf(CommonConstants.COMMISSION_PERCENTAGE));
            ps.setBigDecimal(2, BigDecimal.valueOf(CommonConstants.COMMISSION_PERCENTAGE));
            ps.setInt(3, bookingId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                WorkDetailsViewModel w = new WorkDetailsViewModel();
                w.setBookingId(rs.getInt("BookingId"));
                w.setServiceName(rs.getString("ServiceName"));
                w.setServiceDescription(rs.getString("ServiceDescription"));
                w.setDuration(rs.getString("Duration"));
                w.setPrice(rs.getString("Price"));
                w.setCommission(rs.getString("Commission"));
                w.setDate(rs.getString("Date"));
                w.setStartTime(rs.getString("StartTime"));
                w.setAddress(rs.getString("Address"));
                w.setAddressNo(rs.getString("AddressNo"));
                w.setNote(rs.getString("Note"));
                w.setStatus(rs.getString("Status"));
                w.setStatusId(rs.getInt("StatusId"));
                w.setRead(false);
                w.setCustomerFullName(rs.getString("CustomerFullName"));
                w.setCustomerPhoneNumber(rs.getString("CustomerPhoneNumber"));
                w.setEmployeeId(rs.getString("EmployeeId"));
                w.setPlaceID(rs.getString("PlaceID"));
                w.setLatitude(rs.getString("Latitude"));
                w.setLongitude(rs.getString("Longitude"));
                w.setDecimalPrice(rs.getBigDecimal("DecimalPrice"));
                w.setDecimalCommission(rs.getBigDecimal("DecimalCommission"));
                return w;
            }
        }
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}

