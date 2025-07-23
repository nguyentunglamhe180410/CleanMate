package com.example.cleanmate.data.repository;

import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.data.model.User;
import com.example.cleanmate.data.model.CleanerProfile;

import java.sql.*;

public class CustomerProfileRepository implements AutoCloseable {
    private final Connection conn;

    public CustomerProfileRepository() throws SQLException, ClassNotFoundException {
        try {
            System.out.println("CustomerProfileRepository: Đang load JDBC driver...");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            System.out.println("CustomerProfileRepository: Đã load JDBC driver thành công");
            
            System.out.println("CustomerProfileRepository: Đang kết nối database...");
            System.out.println("CustomerProfileRepository: JDBC_URL = " + CommonConstants.JDBC_URL);
            this.conn = DriverManager.getConnection(CommonConstants.JDBC_URL);
            System.out.println("CustomerProfileRepository: Đã kết nối database thành công");
            
        } catch (ClassNotFoundException e) {
            System.err.println("CustomerProfileRepository: Lỗi load JDBC driver: " + e.getMessage());
            throw e;
        } catch (SQLException e) {
            System.err.println("CustomerProfileRepository: Lỗi kết nối database: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Lấy thông tin user theo ID
     * Tương tự như trong ASP.NET CustomerService
     */
    public User getUserById(String userId) throws SQLException {
        System.out.println("DEBUG: CustomerProfileRepository.getUserById() - userId = " + userId);
        
        String sql = ""
                + "SELECT Id, Email, FullName, PhoneNumber, ProfileImage, DOB, Gender, "
                + "CreatedDate, BankName, BankNo, CCCD, UserName, EmailConfirmed, LockoutEnabled "
                + "FROM AspNetUsers WHERE Id = ?";
        
        System.out.println("DEBUG: SQL Query = " + sql);
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            System.out.println("DEBUG: Executing query with userId = " + userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("DEBUG: No user found with userId = " + userId);
                    return null;
                }

                System.out.println("DEBUG: User found, creating User object");
                User user = new User();
                user.setUserId(rs.getString("Id"));
                user.setEmail(rs.getString("Email"));
                user.setFullName(rs.getString("FullName"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setProfileImage(rs.getString("ProfileImage"));
                user.setDob(rs.getString("DOB"));
                user.setGender(rs.getBoolean("Gender"));
                user.setCreatedDate(rs.getString("CreatedDate"));
                user.setBankName(rs.getString("BankName"));
                user.setBankNo(rs.getString("BankNo"));
                user.setCccd(rs.getString("CCCD"));
                user.setUserName(rs.getString("UserName"));
                user.setEmailConfirmed(rs.getBoolean("EmailConfirmed"));
                
                System.out.println("DEBUG: User object created successfully");
                System.out.println("DEBUG: - Id: " + user.getUserId());
                System.out.println("DEBUG: - FullName: " + user.getFullName());
                System.out.println("DEBUG: - Email: " + user.getEmail());
                
                return user;
            }
        } catch (SQLException e) {
            System.err.println("DEBUG: SQL Exception in getUserById: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Cập nhật thông tin user
     * Tương tự như trong ASP.NET CustomerService
     */
    public boolean updateUser(User user) throws SQLException {
        String sql = ""
                + "UPDATE AspNetUsers SET "
                + "FullName = ?, Email = ?, PhoneNumber = ?, ProfileImage = ?, "
                + "DOB = ?, Gender = ? "
                + "WHERE Id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getProfileImage());
            ps.setString(5, user.getDob());
            ps.setBoolean(6, user.getGender());
            ps.setString(7, user.getUserId());
            
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * Lấy profile của cleaner
     * Tương tự như trong ASP.NET EmployeeService
     */
    public CleanerProfile getCleanerProfile(String cleanerId) throws SQLException {
        String sql = ""
                + "SELECT u.Id, u.FullName, u.Email, u.PhoneNumber, u.ProfileImage, "
                + "u.CCCD, u.BankName, u.BankNo, u.CreatedDate, "
                + "COUNT(f.Id) as FeedbackCount, "
                + "AVG(CAST(f.Rating AS FLOAT)) as AverageRating "
                + "FROM AspNetUsers u "
                + "LEFT JOIN Feedbacks f ON u.Id = f.CleanerId "
                + "WHERE u.Id = ? AND EXISTS (SELECT 1 FROM AspNetUserRoles ur WHERE ur.UserId = u.Id AND ur.RoleId = 'Employee') "
                + "GROUP BY u.Id, u.FullName, u.Email, u.PhoneNumber, u.ProfileImage, u.CCCD, u.BankName, u.BankNo, u.CreatedDate";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cleanerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                CleanerProfile profile = new CleanerProfile();
                profile.setCleanerId(rs.getString("Id"));
                profile.setFullName(rs.getString("FullName"));
                profile.setEmail(rs.getString("Email"));
                profile.setPhoneNumber(rs.getString("PhoneNumber"));
                profile.setAddress("Chưa cập nhật"); // Tạm thời set default
                profile.setExperienceYears(2); // Tạm thời set default
                profile.setActiveAreas("Hà Nội"); // Tạm thời set default
                profile.setBankName(rs.getString("BankName"));
                profile.setBankNo(rs.getString("BankNo"));
                profile.setBalance(0.0); // Tạm thời set default
                profile.setStatus("Active"); // Tạm thời set default
                profile.setCreatedAt(rs.getTimestamp("CreatedDate"));
                profile.setUpdatedAt(rs.getTimestamp("CreatedDate")); // Tạm thời set bằng CreatedDate
                
                return profile;
            }
        }
    }

    /**
     * Kiểm tra user có tồn tại không
     */
    public boolean userExists(String userId) throws SQLException {
        String sql = "SELECT 1 FROM AspNetUsers WHERE Id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Kiểm tra email đã được sử dụng chưa (trừ user hiện tại)
     */
    public boolean emailExists(String email, String excludeUserId) throws SQLException {
        String sql = "SELECT 1 FROM AspNetUsers WHERE Email = ? AND Id != ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, excludeUserId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Kiểm tra phone number đã được sử dụng chưa (trừ user hiện tại)
     */
    public boolean phoneExists(String phoneNumber, String excludeUserId) throws SQLException {
        String sql = "SELECT 1 FROM AspNetUsers WHERE PhoneNumber = ? AND Id != ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phoneNumber);
            ps.setString(2, excludeUserId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
} 