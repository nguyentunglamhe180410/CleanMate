package com.example.cleanmate.data.service;


import com.example.cleanmate.data.model.dto.dto;
import com.example.cleanmate.data.repository.CleanPerHourRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class CleanPerHourService {
    private final CleanPerHourRepository cleanPerHourRepo;

    public CleanPerHourService(CleanPerHourRepository cleanPerHourRepo) {
        this.cleanPerHourRepo = cleanPerHourRepo;
    }


    public List<dto.CleanerDTO> getAvailableCleanersForTimeSlot(LocalDateTime startDateTime,
                                                                LocalDateTime endDateTime)
            throws Exception {
        String requestDate      = String.valueOf(startDateTime.toLocalDate());
        String requestStartTime = String.valueOf(startDateTime.toLocalTime());
        String requestEndTime   = String.valueOf(endDateTime.toLocalTime());

        List<dto.CleanerDTO> allCleaners    = cleanPerHourRepo.getAllCleaners();
        List<String> bookedCleanerIds = cleanPerHourRepo.getBookedCleaners(requestDate, requestStartTime, requestEndTime);

        return allCleaners.stream()
                .filter(c -> !bookedCleanerIds.contains(c.getCleanerId()))
                .collect(Collectors.toList());
    }
}

