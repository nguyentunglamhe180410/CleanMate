package com.example.cleanmate.data.repository;


import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.common.enums.TransactionType;
import com.example.cleanmate.common.utils.DateTimeVN;
import com.example.cleanmate.data.model.dto.dto;
import com.example.cleanmate.data.model.viewmodels.employee.EarningsSummaryViewModel;
import com.example.cleanmate.data.model.viewmodels.employee.MonthlyEarningViewModel;
import com.example.cleanmate.data.model.viewmodels.employee.WorkDetailsViewModel;
import com.example.cleanmate.data.model.viewmodels.employee.WorkHistoryViewModel;
import com.example.cleanmate.data.model.viewmodels.employee.WorkListViewModel;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


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
    public List<WorkListViewModel> findWorkByEmployeeId(String employeeId) throws SQLException {
        return findAllWork(null, employeeId);
    }

    //---- 4. changeWorkStatus ----
    public boolean changeWorkStatus(int bookingId, int newStatus, String cleanerId) throws SQLException {
        String sql = "UPDATE Booking SET BookingStatusId=?, CleanerId=? WHERE BookingId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newStatus);
            if (cleanerId!=null) ps.setString(2, cleanerId);
            else               ps.setNull(2, Types.VARCHAR);
            ps.setInt(3, bookingId);
            return ps.executeUpdate()==1;
        }
    }

    //---- 5. countByStatus ----
    public int countByStatus(int status) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Booking WHERE BookingStatusId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    //---- 6. canCleanerAcceptWork ----
    public boolean canCleanerAcceptWork(int bookingId, String employeeId) throws SQLException {
        LocalDateTime now = DateTimeVN.getNow().toLocalDateTime();

        // get target booking
        WorkDetailsViewModel target = findWorkById(bookingId);
        if (target==null || (target.getEmployeeId()!=null && !target.getEmployeeId().equals(employeeId)))
            return false;

        LocalDateTime start = LocalDate.parse(target.getDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .atTime(LocalTime.parse(target.getStartTime()));
        long duration = Integer.parseInt(target.getDuration().replaceAll("\\D",""));
        LocalDateTime end = start.plusHours(duration);

        // fetch existing
        String sql =
                "SELECT b.Date, b.StartTime, d.DurationTime, b.CleanerId " +
                        "FROM Booking b " +
                        "JOIN ServicePrices sp ON b.ServicePriceId=sp.PriceId " +
                        "JOIN Durations d ON sp.DurationId=d.DurationId " +
                        "WHERE b.CleanerId=? AND b.BookingStatusId IN (?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employeeId);
            ps.setInt(2, CommonConstants.BookingStatus.NEW);
            ps.setInt(3, CommonConstants.BookingStatus.ACCEPT);
            ps.setInt(4, CommonConstants.BookingStatus.IN_PROGRESS);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LocalDate d = LocalDate.parse(rs.getDate("Date").toLocaleString());
                    LocalTime t = LocalTime.parse(rs.getTime("StartTime").toLocaleString());
                    long dur = rs.getInt("DurationTime");
                    LocalDateTime s2 = d.atTime(t);
                    LocalDateTime e2 = s2.plusHours(dur);
                    if (start.isBefore(e2) && end.isAfter(s2)) return false;
                }
            }
        }
        return true;
    }

    //---- 7. checkAndCancelPastDueWork ----
    public int checkAndCancelPastDueWork() throws SQLException {
        LocalDateTime now = DateTimeVN.getNow().toLocalDateTime();
        String select = "SELECT BookingId, Date, StartTime FROM Booking WHERE BookingStatusId=?";
        List<Integer> toCancel = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(select)) {
            ps.setInt(1, CommonConstants.BookingStatus.NEW);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LocalDate d = LocalDate.parse(rs.getDate("Date").toLocaleString());
                    LocalTime t = LocalTime.parse(rs.getTime("StartTime").toLocaleString());
                    if (d.atTime(t).isBefore(now)) {
                        toCancel.add(rs.getInt("BookingId"));
                    }
                }
            }
        }
        int count = 0;
        for (int id : toCancel) {
            if (changeWorkStatus(id, CommonConstants.BookingStatus.CANCEL, null)) count++;
        }
        return count;
    }

    //---- 8. createCleanerProfile ----
    public void createCleanerProfile(String userId) throws SQLException {
        String check = "SELECT 1 FROM CleanerProfiles WHERE UserId=?";
        try (PreparedStatement ps = conn.prepareStatement(check)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return;
            }
        }
        String insert = "INSERT INTO CleanerProfiles(UserId, Rating, ExperienceYear, Available, Area) VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(insert)) {
            ps.setString(1, userId);
            ps.setBigDecimal(2, BigDecimal.ZERO);
            ps.setInt(3, 0);
            ps.setBoolean(4, true);
            ps.setString(5, "Hòa Lạc");
            ps.executeUpdate();
        }
    }
    public List<WorkHistoryViewModel> getWorkHistory(String employeeId) throws SQLException {
        String sql =
                "SELECT b.BookingId, s.Name AS ServiceName, u.FullName AS CustomerFullName, " +
                        "       b.Date, b.StartTime, d.DurationTime AS Duration, " +
                        "       a.GG_DispalyName + ' ' + a.AddressNo AS Address, b.Note, " +
                        "       b.TotalPrice, f.Rating, f.Content, bs.Status " +
                        "FROM Booking b " +
                        "JOIN ServicePrices sp ON b.ServicePriceId=sp.PriceId " +
                        "JOIN Services s ON sp.ServiceId=s.ServiceId " +
                        "JOIN Durations d ON sp.DurationId=d.DurationId " +
                        "JOIN Users u ON b.UserId=u.Id " +
                        "JOIN CustomerAddresses a ON b.AddressId=a.AddressId " +
                        "LEFT JOIN Feedbacks f ON b.BookingId=f.BookingId " +
                        "JOIN BookingStatuses bs ON b.BookingStatusId=bs.BookingStatusId " +
                        "WHERE b.CleanerId=? AND b.BookingStatusId IN (?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employeeId);
            ps.setInt(2, CommonConstants.BookingStatus.DONE);
            ps.setInt(3, CommonConstants.BookingStatus.CANCEL);
            ps.setInt(4, CommonConstants.BookingStatus.PENDING_DONE);

            List<WorkHistoryViewModel> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    WorkHistoryViewModel w = new WorkHistoryViewModel();
                    w.setBookingId(rs.getInt("BookingId"));
                    w.setServiceName(rs.getString("ServiceName"));
                    w.setCustomerFullName(rs.getString("CustomerFullName"));
                    w.setDate(LocalDate.parse(rs.getDate("Date").toString()));
                    w.setStartTime(LocalTime.parse(rs.getTime("StartTime").toString()));
                    w.setDuration(BigDecimal.valueOf(rs.getInt("Duration")));
                    w.setAddress(rs.getString("Address"));
                    w.setNote(rs.getString("Note"));
                    w.setEarnings(rs.getBigDecimal("TotalPrice")
                            .multiply(BigDecimal.valueOf(1 - CommonConstants.COMMISSION_PERCENTAGE / 100.0))
                            .setScale(0, BigDecimal.ROUND_FLOOR)
                            .divide(BigDecimal.valueOf(1000)).multiply(BigDecimal.valueOf(1000))
                    );
                    w.setRating(rs.getObject("Rating") != null ? rs.getDouble("Rating") : null);
                    w.setComment(rs.getString("Content"));
                    w.setStatus(rs.getString("Status"));
                    list.add(w);
                }
            }
            return list;
        }
    }



    public BigDecimal getMonthlyEarnings(String employeeId) throws SQLException {
        LocalDate now = DateTimeVN.getNow().toLocalDateTime().toLocalDate();
        String sql =
                "SELECT SUM(FLOOR(b.TotalPrice * (1-?/100) / 1000) * 1000) AS MonthEarn " +
                        "FROM Booking b WHERE b.CleanerId=? AND b.BookingStatusId=? " +
                        " AND b.Date BETWEEN ? AND ?";
        LocalDate start = now.withDayOfMonth(1);
        LocalDate end   = now.withDayOfMonth(now.lengthOfMonth());
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, BigDecimal.valueOf(CommonConstants.COMMISSION_PERCENTAGE));
            ps.setString(2, employeeId);
            ps.setInt(3, CommonConstants.BookingStatus.DONE);
            ps.setDate(4, Date.valueOf(String.valueOf(start)));
            ps.setDate(5, Date.valueOf(String.valueOf(end)));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getBigDecimal("MonthEarn");
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * 12. getEarningsByMonth
     */
    public List<MonthlyEarningViewModel> getEarningsByMonth(String employeeId) throws SQLException {
        LocalDateTime now = DateTimeVN.getNow().toLocalDateTime();
        int year = now.getYear(), month = now.getMonthValue();
        List<MonthlyEarningViewModel> list = new ArrayList<>();

        String sql =
                "SELECT MONTH(b.Date) AS Mon, SUM(FLOOR(b.TotalPrice * ? /1000)*1000) AS Earn " +
                        "FROM Booking b WHERE b.CleanerId=? AND b.BookingStatusId=? AND YEAR(b.Date)=? " +
                        "GROUP BY MONTH(b.Date)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, BigDecimal.valueOf(1 - CommonConstants.COMMISSION_PERCENTAGE/100.0));
            ps.setString(2, employeeId);
            ps.setInt(3, CommonConstants.BookingStatus.DONE);
            ps.setInt(4, year);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int m = rs.getInt("Mon");
                    BigDecimal earn = rs.getBigDecimal("Earn");
                    list.add(new MonthlyEarningViewModel(m, earn));
                }
            }
        }
        return list;
    }

    /**
     * 13. getAvailableCleaners
     */
    public List<dto.CleanerDTO> getAvailableCleaners() throws SQLException {
        String sql =
                "SELECT cp.UserId, u.FullName FROM CleanerProfiles cp " +
                        "JOIN Users u ON cp.UserId=u.Id WHERE cp.Available=1";
        List<dto.CleanerDTO> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new dto.CleanerDTO(rs.getString("UserId"), rs.getString("FullName")));
            }
        }
        return out;
    }

    /**
     * 14. getCleanerList
     */
    public List<dto.CleanerListItemDTO> getCleanerList() throws SQLException {
        String sql =
                "SELECT cp.UserId, u.FullName, u.Email, u.PhoneNumber, cp.Area, cp.Available, cp.ExperienceYear " +
                        "FROM CleanerProfiles cp JOIN Users u ON cp.UserId=u.Id";
        List<dto.CleanerListItemDTO> out = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new dto.CleanerListItemDTO(
                        rs.getString("UserId"),
                        rs.getString("FullName"),
                        rs.getString("Email"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Area"),
                        rs.getBoolean("Available"),
                        rs.getInt("ExperienceYear")
                ));
            }
        }
        return out;
    }

    /**
     * 15. getCleanerDetail
     */
    public dto.CleanerDetailDTO getCleanerDetail(String cleanerId) throws SQLException {
        // basic profile + wallet
        String profSql =
                "SELECT u.Id,u.FullName,u.Email,u.PhoneNumber,u.ProfileImage,w.Balance " +
                        "FROM CleanerProfiles cp " +
                        "JOIN Users u ON cp.UserId=u.Id " +
                        "LEFT JOIN UserWallets w ON u.Id=w.UserId " +
                        "WHERE u.Id=?";
        dto.CleanerDetailDTO cd = null;
        try (PreparedStatement ps = conn.prepareStatement(profSql)) {
            ps.setString(1, cleanerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                cd = new dto.CleanerDetailDTO(
                        rs.getString("Id"),
                        rs.getString("FullName"),
                        rs.getString("Email"),
                        rs.getString("PhoneNumber"),
                        rs.getBigDecimal("Balance")
                );
            }
        }
        // bookings
        String bkSql =
                "SELECT BookingId, ServicePriceId, UserId, AddressId, TotalPrice, Note, CreatedAt, UpdatedAt, BookingStatusId " +
                        "FROM Booking WHERE CleanerId=?";
        try (PreparedStatement ps = conn.prepareStatement(bkSql)) {
            ps.setString(1, cleanerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dto.BookingDTO b = new dto.BookingDTO(
                            rs.getInt("BookingId"),
                            rs.getInt("ServicePriceId"),
                            rs.getString("ServiceName"),
                            rs.getInt("DurationTime"),
                            rs.getString("SquareMeterSpecific"),
                            rs.getBigDecimal("Price"),
                            rs.getString("CleanerId"),
                            rs.getString("CleanerName"),
                            rs.getString("UserId"),
                            rs.getString("UserName"),
                            rs.getInt("BookingStatusId"),
                            rs.getString("Status"),
                            rs.getString("StatusDescription"),
                            rs.getString("Note"),
                            rs.getObject("AddressId") != null ? rs.getInt("AddressId") : null,
                            rs.getString("AddressFormatted"),
                            rs.getString("AddressNo"),
                            rs.getString("PaymentMethod"),
                            rs.getString("PaymentStatus"),
                            DateTimeVN.convertToLocalDate(rs.getDate("Date")) ,
                            DateTimeVN.convertToLocalTime(rs.getTime("StartTime")),
                            rs.getBigDecimal("TotalPrice"),
                            rs.getTimestamp("CreatedAt"),
                            rs.getTimestamp("UpdatedAt"),
                            rs.getInt("HasFeedback") == 1
                    );
                    cd.getBooking().add(b);                }
            }
        }
        return cd;
    }

    /**
     * 16. toggleCleanerAvailability
     */
    public boolean toggleCleanerAvailability(String cleanerId, boolean isAvailable) throws SQLException {
        String sql = "UPDATE CleanerProfiles SET Available=? WHERE UserId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, isAvailable);
            ps.setString(2, cleanerId);
            return ps.executeUpdate()==1;
        }
    }
    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}

