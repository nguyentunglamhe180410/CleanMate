package com.example.cleanmate.data.repository;

import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.common.enums.TransactionType;
import com.example.cleanmate.data.model.User;
import com.example.cleanmate.data.model.dto.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements AutoCloseable {
    private final Connection conn;

    public CustomerRepository() throws SQLException, ClassNotFoundException {
        try {
            System.out.println("CustomerRepository: Đang load JDBC driver...");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            System.out.println("CustomerRepository: Đã load JDBC driver thành công");
            
            System.out.println("CustomerRepository: Đang kết nối database...");
            System.out.println("CustomerRepository: JDBC_URL = " + CommonConstants.JDBC_URL);
            this.conn = DriverManager.getConnection(CommonConstants.JDBC_URL);
            System.out.println("CustomerRepository: Đã kết nối database thành công");
            
        } catch (ClassNotFoundException e) {
            System.err.println("CustomerRepository: Lỗi load JDBC driver: " + e.getMessage());
            throw e;
        } catch (SQLException e) {
            System.err.println("CustomerRepository: Lỗi kết nối database: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /** Debug: Check table structure */
    public void debugTableStructure() throws SQLException {
        System.out.println("=== DEBUG: Checking AspNetUsers table structure ===");
        String sql = "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'AspNetUsers' ORDER BY ORDINAL_POSITION";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("DATA_TYPE");
                System.out.println("Column: " + columnName + " (" + dataType + ")");
            }
        }
        
        System.out.println("=== DEBUG: Checking AspNetRoles table structure ===");
        sql = "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'AspNetRoles' ORDER BY ORDINAL_POSITION";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("DATA_TYPE");
                System.out.println("Column: " + columnName + " (" + dataType + ")");
            }
        }
        
        System.out.println("=== DEBUG: Checking AspNetUserRoles table structure ===");
        sql = "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'AspNetUserRoles' ORDER BY ORDINAL_POSITION";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("DATA_TYPE");
                System.out.println("Column: " + columnName + " (" + dataType + ")");
            }
        }
    }

    /** 1) Get all customers in role 'Customer' */
    public List<dto.CustomerListItemDTO> getCustomerList() throws SQLException {
        // Debug: Kiểm tra cấu trúc bảng trước
        debugTableStructure();
        
        String sql =
                "SELECT Id, FullName, Email, PhoneNumber, CreatedDate, LockoutEnabled " +
                        "FROM AspNetUsers u " +
                        "JOIN AspNetUserRoles ur ON u.Id = ur.UserId " +
                        "JOIN AspNetRoles r     ON ur.RoleId = r.Id " +
                        "WHERE r.Name = 'Customer' " +
                        "ORDER BY u.UserName";
        System.out.println("DEBUG: getCustomerList SQL = " + sql);

        List<dto.CustomerListItemDTO> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                dto.CustomerListItemDTO dto = new dto.CustomerListItemDTO();
                dto.setId(rs.getString("Id"));
                dto.setFullName(rs.getString("FullName"));
                dto.setEmail(rs.getString("Email"));
                dto.setPhoneNumber(rs.getString("PhoneNumber"));
                dto.setCreatedDate(LocalDateTime.parse(rs.getTimestamp("CreatedDate").toLocaleString()));
                dto.setActive(!rs.getBoolean("LockoutEnabled"));
                list.add(dto);
            }
        }
        return list;
    }

    /** 2) Lock a user account */
    public boolean lockUserAccount(String userId) throws SQLException {
        String sql = "UPDATE AspNetUsers SET LockoutEnabled = 1 WHERE Id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.executeUpdate();
            return true;
        }
        catch(SQLException e){
            return false;
        }
    }

    /** 3) Unlock a user account */
    public boolean unlockUserAccount(String userId) throws SQLException {
        String sql = "UPDATE AspNetUsers SET LockoutEnabled = 0 WHERE Id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.executeUpdate();
            return true;
        }
        catch(SQLException e){
            return false;
        }
    }

    /** 4) Get a single user (minimal) */
    public User getUserById(String userId) throws SQLException {
        String sql = "SELECT Id, FullName, Email, PhoneNumber, CreatedDate, LockoutEnabled " +
                "FROM AspNetUsers WHERE Id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                User u = new User();
                u.setUserId(rs.getString("Id"));
                u.setFullName(rs.getString("FullName"));
                u.setEmail(rs.getString("Email"));
                u.setPhoneNumber(rs.getString("PhoneNumber"));
                u.setCreatedDate(rs.getTimestamp("CreatedDate").toLocaleString());
                return u;
            }
        }
    }

    /** 5) Get detailed customer info */
    public dto.CustomerDetailDTO getCustomerDetail(String userId) throws SQLException {
        // a) Basic user + addresses + wallet balance
        String sqlUser =
                "SELECT u.Id, u.FullName, u.Email, u.PhoneNumber, u.CreatedDate, u.LockoutEnabled, " +
                        "       w.WalletId, w.Balance " +
                        "FROM AspNetUsers u " +
                        "LEFT JOIN User_Wallet w ON u.Id = w.UserId " +
                        "WHERE u.Id = ?";
        dto.CustomerDetailDTO detail = null;
        try (PreparedStatement ps = conn.prepareStatement(sqlUser)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                detail = new dto.CustomerDetailDTO();
                detail.setId(rs.getInt("Id"));
                detail.setFullName(rs.getString("FullName"));
                detail.setEmail(rs.getString("Email"));
                detail.setPhoneNumber(rs.getString("PhoneNumber"));
                detail.setCreatedDate(LocalDateTime.parse(rs.getTimestamp("CreatedDate").toLocaleString()));
                detail.setActive(!rs.getBoolean("LockoutEnabled"));
                detail.setWalletBalance(rs.getBigDecimal("Balance"));
            }
        }

        // b) Addresses
        List<dto.CustomerAddressDTO> addrs = new ArrayList<>();
        String sqlAddr =
                "SELECT AddressId, GG_FormattedAddress, GG_DispalyName, GG_PlaceId, AddressNo, " +
                        "       IsInUse, IsDefault, Latitude, Longitude " +
                        "FROM CustomerAddresses WHERE UserId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlAddr)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dto.CustomerAddressDTO a = new dto.CustomerAddressDTO();
                    a.setAddressId(rs.getInt("AddressId"));
                    a.setGgFormattedAddress(rs.getString("GG_FormattedAddress"));
                    a.setGgDispalyName(rs.getString("GG_DispalyName"));
                    a.setGgPlaceId(rs.getString("GG_PlaceId"));
                    a.setAddressNo(rs.getString("AddressNo"));
                    a.setInUse(rs.getBoolean("IsInUse"));
                    a.setDefault(rs.getBoolean("IsDefault"));
                    a.setLatitude(rs.getBigDecimal("Latitude"));
                    a.setLongitude(rs.getBigDecimal("Longitude"));
                    addrs.add(a);
                }
            }
        }
        detail.setAddresses(addrs);

        // c) Transactions
        List<dto.WalletTransactionDTO> txns = new ArrayList<>();
        String sqlTxn =
                "SELECT TransactionId, Amount, TransactionType, Description, CreatedAt " +
                        "FROM WalletTransactions WHERE WalletId = ?";
        Integer walletId = detail.getWalletBalance() != null ? detail.getId() : null;
        if (walletId != null) {
            try (PreparedStatement ps = conn.prepareStatement(sqlTxn)) {
                ps.setInt(1, walletId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        dto.WalletTransactionDTO t = new dto.WalletTransactionDTO();
                        t.setTransactionId(rs.getInt("TransactionId"));
                        t.setAmount(rs.getBigDecimal("Amount"));
                        t.setTransactionType(TransactionType.valueOf(rs.getString("TransactionType")));
                        t.setDescription(rs.getString("Description"));
                        t.setCreatedAt(LocalDateTime.parse(rs.getTimestamp("CreatedAt").toLocaleString()));
                        txns.add(t);
                    }
                }
            }
        }
        detail.setTransactions(txns);

        // d) Booking 
        List<dto.BookingDTO> books = new ArrayList<>();
        String sqlBook =
                "SELECT BookingId, ServicePriceId, CleanerId, AddressId, TotalPrice, Note, CreatedAt, UpdatedAt, BookingStatusId " +
                        "FROM Booking  WHERE UserId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlBook)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dto.BookingDTO b = new dto.BookingDTO();
                    b.setBookingid(rs.getInt("BookingId"));
                    b.setServicePriceId(rs.getInt("ServicePriceId"));
                    b.setCleanerId(rs.getString("CleanerId"));
                    b.setAddressId(rs.getInt("AddressId"));
                    b.setTotalPrice(rs.getBigDecimal("TotalPrice"));
                    b.setNote(rs.getString("Note"));
                    b.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    b.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                    b.setBookingStatusId(rs.getInt("BookingStatusId"));
                    books.add(b);
                }
            }
        }
        detail.setBooking (books);

        return detail;
    }
    public boolean updateUser(User user) throws SQLException {
        String sql = ""
                + "UPDATE AspNetUsers SET "
                + " FullName     = ?,"
                + " Email        = ?,"
                + " PhoneNumber  = ?,"
                + " ProfileImage = ?"
                + " WHERE Id     = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getProfileImage());
            ps.setString(5, user.getUserId());
            return ps.executeUpdate() == 1;
        }
    }

    /** Get all customer IDs for spinner */
    public List<String> getAllCustomerIds() throws SQLException {
        String sql = "SELECT u.Id FROM AspNetUsers u " +
                    "JOIN AspNetUserRoles ur ON u.Id = ur.UserId " +
                    "JOIN AspNetRoles r ON ur.RoleId = r.Id " +
                    "WHERE r.Name = 'Customer' " +
                    "ORDER BY u.Id";
        
        System.out.println("DEBUG: getAllCustomerIds SQL = " + sql);
        
        List<String> userIds = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String userId = rs.getString("Id");
                userIds.add(userId);
                System.out.println("DEBUG: Found user ID: " + userId);
            }
        }
        
        System.out.println("DEBUG: Total customer IDs found: " + userIds.size());
        return userIds;
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}

