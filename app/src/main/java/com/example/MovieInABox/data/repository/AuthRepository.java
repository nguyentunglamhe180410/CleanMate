package com.example.MovieInABox.data.repository;


import com.example.MovieInABox.common.CommonConstants;
import com.example.MovieInABox.common.utils.DateTimeVN;
import com.example.MovieInABox.common.utils.HashPassword;
import com.example.MovieInABox.data.model.User;
import com.example.MovieInABox.data.model.viewmodels.authen.LoginModel;
import com.example.MovieInABox.data.model.viewmodels.authen.RegisterModel;
import java.sql.*;

public class AuthRepository implements AutoCloseable {
    private final Connection conn;

    public AuthRepository() throws SQLException, ClassNotFoundException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        this.conn = DriverManager.getConnection(CommonConstants.JDBC_URL);
    }

    /**
     * Registers a new user, hashing their password with BCrypt.
     */
    public boolean register(RegisterModel model) throws SQLException {
        String sql = ""
                + "INSERT INTO AspNetUsers "
                + "(Id, UserName, Email, PasswordHash, PhoneNumber, FullName, EmailConfirmed, LockoutEnabled, CreatedDate) "
                + "VALUES (NEWID(), ?,?,?,?,?,?,1,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, model.getEmail());                               // UserName
            ps.setString(2, model.getEmail());                               // Email
            String hashed = HashPassword.hashPassword(model.getPassword());
            ps.setString(3, hashed);                                         // PasswordHash
            ps.setString(4, model.getPhoneNumber());                         // PhoneNumber
            ps.setString(5, model.getFullName());                            // FullName
            ps.setTimestamp(6, Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * Attempts to log in; verifies the password using BCrypt.
     */
    public User login(LoginModel model) throws SQLException {
        String sql = ""
                + "SELECT Id, Email, PasswordHash, FullName, PhoneNumber, EmailConfirmed, LockoutEnabled "
                + "FROM AspNetUsers WHERE Email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, model.getEmail());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                String storedHash = rs.getString("PasswordHash");
                if (!HashPassword.verifyPassword(model.getPassword(), storedHash)) {
                    return null;
                }

                if (!rs.getBoolean("EmailConfirmed") || rs.getBoolean("LockoutEnabled")) {
                    return null;
                }

                User u = new User();
                u.setUserId(rs.getString("Id"));
                u.setEmail(rs.getString("Email"));
                u.setFullName(rs.getString("FullName"));
                u.setPhoneNumber(rs.getString("PhoneNumber"));
                return u;
            }
        }
    }
    public boolean existsByIdentification(String cccd) throws SQLException {
        String sql = "SELECT 1 FROM AspNetUsers WHERE CCCD = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cccd);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    public boolean registerEmployee(RegisterModel m) throws SQLException {
        String sqlUser = ""
                + "INSERT INTO AspNetUsers "
                + "(Id, UserName, Email, PasswordHash, PhoneNumber, FullName, CCCD, BankName, BankNo, EmailConfirmed, LockoutEnabled, CreatedDate) "
                + "VALUES (NEWID(),?,?,?,?,?,?,?,?,1,0,?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlUser)) {
            ps.setString(1, m.getEmail());                              // UserName
            ps.setString(2, m.getEmail());                              // Email
            ps.setString(3, HashPassword.hashPassword(m.getPassword())); // PasswordHash
            ps.setString(4, m.getPhoneNumber());                        // PhoneNumber
            ps.setString(5, m.getFullName());                           // FullName
            ps.setString(6, m.getIdentification());                      // CCCD
            ps.setString(7, m.getBank());                               // BankName
            ps.setString(8, m.getBankAccount());                        // BankNo
            ps.setTimestamp(9, Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
            if (ps.executeUpdate() != 1) {
                return false;
            }
        }

        String sqlRole = ""
                + "INSERT INTO AspNetUserRoles (UserId, RoleId) "
                + "VALUES ( "
                + "  (SELECT Id FROM AspNetUsers WHERE Email = ?), "
                + "  (SELECT Id FROM AspNetRoles WHERE Name = 'Cleaner')"
                + ")";
        try (PreparedStatement ps2 = conn.prepareStatement(sqlRole)) {
            ps2.setString(1, m.getEmail());
            return ps2.executeUpdate() == 1;
        }
    }
    public boolean existsByEmail(String email) throws SQLException {
        String sql = "SELECT 1 FROM AspNetUsers WHERE Email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean existsByPhone(String phone) throws SQLException {
        String sql = "SELECT 1 FROM AspNetUsers WHERE PhoneNumber = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}

