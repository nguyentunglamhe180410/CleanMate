package com.example.cleanmate.data.service;


import com.example.cleanmate.data.model.dto.*;
import com.example.cleanmate.data.model.Service;
import com.example.cleanmate.data.model.ServicePrice;
import com.example.cleanmate.data.repository.AllServiceRepository;
import com.example.cleanmate.data.repository.CleanPerHourRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class AllServiceService {
    private final AllServiceRepository allServiceRepo;

    public AllServiceService(AllServiceRepository allServiceRepo) {
        this.allServiceRepo = allServiceRepo;
    }

    /** Returns all services. */
    public List<Service> getAllService() throws Exception {
        return allServiceRepo.getAllService();
    }

    public List<ServicePrice> getServicePriceByServiceId(int serviceId) throws Exception {
        return allServiceRepo.getServicePriceByServiceId(serviceId);
    }
}
