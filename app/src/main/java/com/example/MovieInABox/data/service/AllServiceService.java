package com.example.MovieInABox.data.service;


import com.example.MovieInABox.data.model.Service;
import com.example.MovieInABox.data.model.ServicePrice;
import com.example.MovieInABox.data.repository.AllServiceRepository;

import java.util.List;

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
