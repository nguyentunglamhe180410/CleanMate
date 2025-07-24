package com.example.MovieInABox.data.repository;

import com.example.MovieInABox.common.CommonConstants;
import com.example.MovieInABox.data.model.Service;
import com.example.MovieInABox.data.model.ServicePrice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AllServiceRepository implements AutoCloseable {
    private final Connection conn;

    public AllServiceRepository() throws SQLException, ClassNotFoundException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        this.conn = DriverManager.getConnection(CommonConstants.JDBC_URL);
    }

    /** Lấy toàn bộ Service */
    public List<Service> getAllService() throws SQLException {
        String sql = "SELECT ServiceId, Name, Description FROM Services";
        List<Service> list = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Service s = new Service();
                s.setServiceId(rs.getInt("ServiceId"));
                s.setName(rs.getString("Name"));
                s.setDescription(rs.getString("Description"));
                list.add(s);
            }
        }

        return list;
    }

    /** Lấy ServicePrice thuần túy theo serviceId */
    public List<ServicePrice> getServicePriceByServiceId(int serviceid) throws SQLException {
        String sql =
                "SELECT PriceId, ServiceId, DurationId, Price " +
                        "FROM ServicePrices " +
                        "WHERE ServiceId = ?";
        List<ServicePrice> list = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, serviceid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ServicePrice sp = new ServicePrice();
                    sp.setPriceId(rs.getInt("PriceId"));
                    sp.setServiceId(rs.getInt("ServiceId"));
                    sp.setDurationId(rs.getInt("DurationId"));
                    sp.setPrice(rs.getBigDecimal("Price"));
                    list.add(sp);
                }
            }
        }

        return list;
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) conn.close();
    }
}


