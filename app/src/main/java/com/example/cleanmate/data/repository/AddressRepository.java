package com.example.cleanmate.data.repository;

import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.data.model.CustomerAddress;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressRepository {
    private static final String URL = CommonConstants.JDBC_URL;
    private Connection conn;

    public AddressRepository() throws SQLException, ClassNotFoundException {
        // Load JTDS driver
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        conn = DriverManager.getConnection(URL);
    }

    /** Inserts a new CustomerAddress, returns it with generated ID filled. */
    public CustomerAddress addAddress(CustomerAddress addr) throws SQLException {
        String sql = "INSERT INTO CustomerAddresses"
                + " (UserId, GG_FormattedAddress, GG_DispalyName, GG_PlaceId,"
                + "  AddressNo, Latitude, Longitude, IsInUse, IsDefault)"
                + " VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, addr.getUserId());
            ps.setString(2, addr.getGgFormattAdaddress());
            ps.setString(3, addr.getGgDispalyName());
            ps.setString(4, addr.getGgPlaceId());
            ps.setString(5, addr.getAddressNo());
            ps.setBigDecimal(6, addr.getLatitude());
            ps.setBigDecimal(7, addr.getLongitude());
            ps.setBoolean(8, addr.getIsInUse());
            ps.setBoolean(9, addr.getIsDefault());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    addr.setAddressId(keys.getInt(1));
                }
            }
            return addr;
        }
    }

    /** Updates an existing address; returns updated String or null if not found */
    public CustomerAddress editAddress(CustomerAddress addr) throws SQLException {
        String sql = "UPDATE CustomerAddresses SET"
                + " GG_FormattedAddress=?, GG_DispalyName=?, GG_PlaceId=?,"
                + " AddressNo=?, Latitude=?, Longitude=?, IsInUse=?, IsDefault=?"
                + " WHERE AddressId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, addr.getGgFormattAdaddress());
            ps.setString(2, addr.getGgDispalyName());
            ps.setString(3, addr.getGgPlaceId());
            ps.setString(4, addr.getAddressNo());
            ps.setBigDecimal(5, addr.getLatitude());
            ps.setBigDecimal(6, addr.getLongitude());
            ps.setBoolean(7, addr.getIsInUse());
            ps.setBoolean(8, addr.getIsDefault());
            ps.setInt(9, addr.getAddressId());
            int updated = ps.executeUpdate();
            return (updated == 1) ? addr : null;
        }
    }

    /** Retrieves all addresses for a user where IsInUse = true */
    public List<CustomerAddress> getAddressesInUseByCustomerId(String userId) throws SQLException {
        String sql = "SELECT * FROM CustomerAddresses WHERE UserId=? AND IsInUse=1";
        List<CustomerAddress> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    /** Retrieves a single address by its ID (or null) */
    public CustomerAddress getAddressByAddressId(int addressId) throws SQLException {
        String sql = "SELECT * FROM CustomerAddresses WHERE AddressId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, addressId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }

    /** Helper: map a ResultSet row into your model */
    private CustomerAddress mapRow(ResultSet rs) throws SQLException {
        CustomerAddress a = new CustomerAddress();
        a.setAddressId(rs.getInt("AddressId"));
        a.setUserId(rs.getString("UserId"));
        a.setGgFormattAdaddress(rs.getString("GG_FormattedAddress"));
        a.setGgDispalyName(rs.getString("GG_DispalyName"));
        a.setGgPlaceId(rs.getString("GG_PlaceId"));
        a.setAddressNo(rs.getString("AddressNo"));
        a.setLatitude(rs.getBigDecimal("Latitude"));
        a.setLongitude(rs.getBigDecimal("Longitude"));
        a.setIsInUse(rs.getBoolean("IsInUse"));
        a.setIsDefault(rs.getBoolean("IsDefault"));
        return a;
    }

    /** Be sure to close the connection when done: */
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}

