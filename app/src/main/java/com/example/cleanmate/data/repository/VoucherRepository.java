package com.example.cleanmate.data.repository;

import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.common.utils.DateTimeVN;
import com.example.cleanmate.data.model.UserVoucher;
import com.example.cleanmate.data.model.Voucher;
import com.example.cleanmate.data.model.UserVoucherDisplay;
import com.example.cleanmate.data.model.viewmodels.employee.UserVoucherViewModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VoucherRepository implements AutoCloseable {
    private final Connection conn;

    public VoucherRepository() throws SQLException, ClassNotFoundException {
        try {
            System.out.println("VoucherRepository: Đang load JDBC driver...");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            System.out.println("VoucherRepository: Đã load JDBC driver thành công");
            
            System.out.println("VoucherRepository: Đang kết nối database...");
            System.out.println("VoucherRepository: Host = " + CommonConstants.SQL_SERVER_HOST);
            System.out.println("VoucherRepository: Database = " + CommonConstants.SQL_SERVER_DATABASE);
            System.out.println("VoucherRepository: User = " + CommonConstants.SQL_SERVER_USER);
            System.out.println("VoucherRepository: JDBC_URL = " + CommonConstants.JDBC_URL);
            this.conn = DriverManager.getConnection(CommonConstants.JDBC_URL);
            System.out.println("VoucherRepository: Đã kết nối database thành công");
            
        } catch (ClassNotFoundException e) {
            System.err.println("VoucherRepository: Lỗi load JDBC driver: " + e.getMessage());
            throw e;
        } catch (SQLException e) {
            System.err.println("VoucherRepository: Lỗi kết nối database: " + e.getMessage());
            System.err.println("VoucherRepository: Error Code: " + e.getErrorCode());
            System.err.println("VoucherRepository: SQL State: " + e.getSQLState());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("VoucherRepository: Lỗi không xác định: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /** 1) Get all vouchers */
    public List<Voucher> getAllVoucher() throws SQLException {
        // Debug: Kiểm tra cấu trúc bảng trước
        debugTableStructure();
        
        String discountColumn = getDiscountColumnName();
        String sql = "SELECT VoucherId, Description, " + discountColumn + ", CreatedAt, ExpireDate, "
                + "VoucherCode, IsEventVoucher, CreatedBy, Status "
                + "FROM Voucher";
        System.out.println("DEBUG: getAllVoucher SQL = " + sql);
        List<Voucher> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapVoucher(rs, discountColumn));
            }
        }
        return list;
    }

    /** 2) Get one voucher by ID */
    public Voucher getVoucherById(int voucherid) throws SQLException {
        String discountColumn = getDiscountColumnName();
        String sql = "SELECT VoucherId, Description, " + discountColumn + ", CreatedAt, ExpireDate, "
                + "VoucherCode, IsEventVoucher, CreatedBy, Status "
                + "FROM Voucher WHERE VoucherId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, voucherid);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapVoucher(rs, discountColumn) : null;
            }
        }
    }

    /** 3) Add a new voucher */
    public void addVoucher(Voucher v) throws SQLException {
        String sql = "INSERT INTO Voucher "
                + "(Description, DiscountPercentage, CreatedAt, ExpireDate, VoucherCode, IsEventVoucher, CreatedBy, Status) "
                + "VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getDescription());
            ps.setBigDecimal(2, v.getDiscountPercentage());
            ps.setTimestamp(3, Timestamp.valueOf(String.valueOf(DateTimeVN.getNow().toLocalDateTime())));
            ps.setDate(4, Date.valueOf(String.valueOf(LocalDate.parse(v.getExpireDate())))); // expect "YYYY-MM-DD"
            ps.setString(5, v.getVoucherCode());
            ps.setBoolean(6, v.getIsEventVoucher());
            ps.setString(7, v.getCreatedBy());
            ps.setString(8, v.getStatus());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) v.setVoucherId(keys.getInt(1));
            }
        }
    }

    /** 4) Update existing voucher */
    public void updateVoucher(Voucher v) throws SQLException {
        String sql = "UPDATE Voucher SET "
                + "Description=?, DiscountPercentage=?, ExpireDate=?, VoucherCode=?, "
                + "IsEventVoucher=?, CreatedBy=?, Status=? "
                + "WHERE VoucherId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, v.getDescription());
            ps.setBigDecimal(2, v.getDiscountPercentage());
            ps.setDate(3, Date.valueOf(String.valueOf(LocalDate.parse(v.getExpireDate()))));
            ps.setString(4, v.getVoucherCode());
            ps.setBoolean(5, v.getIsEventVoucher());
            ps.setString(6, v.getCreatedBy());
            ps.setString(7, v.getStatus());
            ps.setInt(8, v.getVoucherId());
            ps.executeUpdate();
        }
    }

    /** 5) Delete a voucher */
    public void deleteVoucher(int voucherid) throws SQLException {
        String sql = "DELETE FROM Voucher WHERE VoucherId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, voucherid);
            ps.executeUpdate();
        }
    }

    /** 6) Get user vouchers by userId (including voucher info) */
    public List<UserVoucherViewModel> getUserVoucherByUserId(String userid) throws SQLException {
        String sql = "SELECT uv.UserVoucherId, uv.UserId, uv.VoucherId, uv.Quantity, uv.IsUsed, "
                + "v.Description, v.DiscountPercentage, v.ExpireDate, v.Status as VoucherStatus "
                + "FROM UserVoucher uv "
                + "JOIN Voucher v ON uv.VoucherId = v.VoucherId "
                + "WHERE uv.UserId = ?";
        List<UserVoucherViewModel> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapUserVoucherViewModel(rs));
                }
            }
        }
        return list;
    }

    /** 6b) Get user vouchers display by userId (including voucher code) */
    public List<UserVoucherDisplay> getUserVoucherDisplayByUserId(String userid) throws SQLException {
        // Debug: In ra SQL query
        String discountColumn = getDiscountColumnName();
        String sql = "SELECT uv.UserVoucherId, uv.UserId, uv.VoucherId, uv.Quantity, uv.IsUsed, uv.UsedAt, "
                + "v.VoucherCode, v.Description, v." + discountColumn + ", v.ExpireDate, v.Status "
                + "FROM UserVoucher uv "
                + "JOIN Voucher v ON uv.VoucherId = v.VoucherId "
                + "WHERE uv.UserId = ?";
        System.out.println("DEBUG: SQL Query = " + sql);
        System.out.println("DEBUG: UserId = " + userid);
        List<UserVoucherDisplay> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapUserVoucherDisplay(rs, discountColumn));
                }
            }
        }
        return list;
    }

    /** 7) Assign voucher to user */
    public void assignVoucherToUser(String userid, int voucherid, int quantity) throws SQLException {
        String sql = "INSERT INTO UserVoucher (UserId, VoucherId, Quantity, IsUsed) VALUES (?,?,?,0)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userid);
            ps.setInt(2, voucherid);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        }
    }

    /** 8) Check if a voucher exists */
    public boolean voucherExists(int voucherid) throws SQLException {
        String sql = "SELECT 1 FROM Voucher WHERE VoucherId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, voucherid);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /** 9) Check if a voucher code exists */
    public boolean voucherCodeExists(String code) throws SQLException {
        String sql = "SELECT 1 FROM Voucher WHERE VoucherCode = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /** 10) Get available user vouchers (not used, not expired) */
    public List<UserVoucher> getAvailableUserVoucher(String userid) throws SQLException {
        String today = LocalDate.now(DateTimeVN.getNow().getZone()).toString();
        String sql = "SELECT UserVoucherId, UserId, VoucherId, Quantity, IsUsed "
                + "FROM UserVoucher uv "
                + "JOIN Voucher v ON uv.VoucherId = v.VoucherId "
                + "WHERE uv.UserId = ? AND uv.IsUsed = 0 AND v.ExpireDate >= ?";
        List<UserVoucher> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userid);
            ps.setString(2, today);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserVoucher uv = new UserVoucher();
                    uv.setUserVoucherId(rs.getInt("UserVoucherId"));
                    uv.setUserId(rs.getString("UserId"));
                    uv.setVoucherId(rs.getInt("VoucherId"));
                    uv.setQuantity(rs.getInt("Quantity"));
                    uv.setIsUsed(rs.getBoolean("IsUsed"));
                    list.add(uv);
                }
            }
        }
        return list;
    }

    /** 11) Get one user voucher by code */
    public UserVoucher getUserVoucherByCode(String userid, String code) throws SQLException {
        String today = LocalDate.now(DateTimeVN.getNow().getZone()).toString();
        String sql = "SELECT UserVoucherId, UserId, VoucherId, Quantity, IsUsed "
                + "FROM UserVoucher uv "
                + "JOIN Voucher v ON uv.VoucherId = v.VoucherId "
                + "WHERE uv.UserId = ? AND v.VoucherCode = ? AND uv.IsUsed = 0 AND v.ExpireDate >= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userid);
            ps.setString(2, code);
            ps.setString(3, today);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                UserVoucher uv = new UserVoucher();
                uv.setUserVoucherId(rs.getInt("UserVoucherId"));
                uv.setUserId(rs.getString("UserId"));
                uv.setVoucherId(rs.getInt("VoucherId"));
                uv.setQuantity(rs.getInt("Quantity"));
                uv.setIsUsed(rs.getBoolean("IsUsed"));
                return uv;
            }
        }
    }

    /** 12) Update a user voucher */
    public void updateUserVoucher(UserVoucher uv) throws SQLException {
        String sql = "UPDATE UserVoucher SET Quantity = ?, IsUsed = ? WHERE UserVoucherId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, uv.getQuantity());
            ps.setBoolean(2, uv.getIsUsed());
            ps.setInt(3, uv.getUserVoucherId());
            ps.executeUpdate();
        }
    }

    /** Debug: Check table structure */
    public void debugTableStructure() throws SQLException {
        System.out.println("=== DEBUG: Checking Voucher table structure ===");
        String sql = "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Voucher' ORDER BY ORDINAL_POSITION";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("DATA_TYPE");
                System.out.println("Column: " + columnName + " (" + dataType + ")");
            }
        }
        
        System.out.println("=== DEBUG: Checking UserVoucher table structure ===");
        sql = "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'UserVoucher' ORDER BY ORDINAL_POSITION";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("DATA_TYPE");
                System.out.println("Column: " + columnName + " (" + dataType + ")");
            }
        }
    }
    
    /** Get correct column name for discount */
    private String getDiscountColumnName() throws SQLException {
        String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Voucher' AND COLUMN_NAME LIKE '%discount%'";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                System.out.println("DEBUG: Found discount column: " + columnName);
                return columnName;
            }
        }
        
        // Thử các tên column khác có thể có
        String[] possibleNames = {"Discount", "DiscountAmount", "DiscountValue", "DiscountPercent"};
        for (String name : possibleNames) {
            sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Voucher' AND COLUMN_NAME = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("DEBUG: Found discount column: " + name);
                        return name;
                    }
                }
            }
        }
        
        System.out.println("DEBUG: No discount column found, using default: DiscountPercentage");
        return "DiscountPercentage";
    }

    private Voucher mapVoucher(ResultSet rs, String discountColumn) throws SQLException {
        Voucher v = new Voucher();
        v.setVoucherId(rs.getInt("VoucherId"));
        v.setDescription(rs.getString("Description"));
        v.setDiscountPercentage(rs.getBigDecimal(discountColumn));
        v.setCreatedAt(rs.getTimestamp("CreatedAt"));
        v.setExpireDate(rs.getDate("ExpireDate").toString());
        v.setVoucherCode(rs.getString("VoucherCode"));
        v.setIsEventVoucher(rs.getBoolean("IsEventVoucher"));
        v.setCreatedBy(rs.getString("CreatedBy"));
        v.setStatus(rs.getString("Status"));
        return v;
    }
    
    // Overload method for backward compatibility
    private Voucher mapVoucher(ResultSet rs) throws SQLException {
        return mapVoucher(rs, "DiscountPercentage");
    }

    private UserVoucherViewModel mapUserVoucherViewModel(ResultSet rs) throws SQLException {
        UserVoucherViewModel uv = new UserVoucherViewModel();
        uv.setUservoucherid(rs.getInt("UserVoucherId"));
        uv.setUserid(rs.getString("UserId"));
        uv.setVoucherid(rs.getInt("VoucherId"));
        uv.setQuantity(rs.getInt("Quantity"));
        uv.setUsed(rs.getBoolean("IsUsed"));
        uv.setDescription(rs.getString("Description"));
        uv.setDiscountPercentage(rs.getDouble("DiscountPercentage"));
        uv.setExpiredate(Date.valueOf(rs.getDate("ExpireDate").toString()));
        uv.setVoucherStatus(rs.getString("VoucherStatus"));
        return uv;
    }

    private UserVoucherDisplay mapUserVoucherDisplay(ResultSet rs, String discountColumn) throws SQLException {
        UserVoucherDisplay uv = new UserVoucherDisplay();
        uv.setUserVoucherId(rs.getInt("UserVoucherId"));
        uv.setUserId(rs.getString("UserId"));
        uv.setVoucherId(rs.getInt("VoucherId"));
        uv.setQuantity(rs.getInt("Quantity"));
        uv.setIsUsed(rs.getBoolean("IsUsed"));
        uv.setUsedAt(rs.getTimestamp("UsedAt"));
        uv.setVoucherCode(rs.getString("VoucherCode"));
        uv.setDescription(rs.getString("Description"));
        uv.setDiscountPercentage(rs.getDouble(discountColumn));
        uv.setExpireDate(rs.getDate("ExpireDate").toString());
        uv.setStatus(rs.getString("Status"));
        return uv;
    }
    
    // Overload method for backward compatibility
    private UserVoucherDisplay mapUserVoucherDisplay(ResultSet rs) throws SQLException {
        return mapUserVoucherDisplay(rs, "DiscountPercentage");
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}

