package com.example.cleanmate.data.service;

import com.example.cleanmate.data.model.Service;
import com.example.cleanmate.data.model.ServicePrice;

import java.util.ArrayList;
import java.util.List;

public class CleanMateService {
    private final AllServiceService allService;

    public CleanMateService(AllServiceService allService) {
        this.allService = allService;
    }

    // Get all cleaning services
    public List<Service> getAllServiceClean() throws Exception {
        List<Service> result = allService.getAllService();
        if (result == null || result.isEmpty()) {
            throw new Exception("Không tìm thấy dịch vụ dọn dẹp theo giờ.");
        }
        return result;
    }

    // Get service prices by service ID
    public List<ServicePrice> getServicePriceByServiceId(int serviceId) throws Exception {
        List<ServicePrice> result = allService.getServicePriceByServiceId(serviceId);
        if (result == null || result.isEmpty()) {
            throw new Exception("Không tìm thấy mức giá cho dịch vụ có ID = " + serviceId + ".");
        }

        // In ASP.NET, it converted to DTO, but we assume result is already DTO format in Android.
        return new ArrayList<>(result);
    }
}
