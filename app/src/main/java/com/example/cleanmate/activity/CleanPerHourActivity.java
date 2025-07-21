package com.example.cleanmate.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cleanmate.data.model.dto.dto;
import com.example.cleanmate.data.repository.CleanPerHourRepository;
import com.example.cleanmate.data.service.CleanPerHourService;

import java.time.LocalDateTime;
import java.util.List;

public class CleanPerHourActivity extends AppCompatActivity {

    private CleanPerHourService cleanPerHourService;
    private CleanPerHourRepository cleanPerHourRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set your layout file here (e.g., activity_clean_per_hour.xml)
        // setContentView(R.layout.activity_clean_per_hour);

        cleanPerHourService = new CleanPerHourService(cleanPerHourRepository); // You will need to implement this

        // Example usage: load available cleaners
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2);

        try {
            List<dto.CleanerDTO> cleaners = cleanPerHourService.getAvailableCleanersForTimeSlot(startTime, endTime);
            // Display cleaners in a RecyclerView or ListView
            // For now, just show a toast with how many were found
            Toast.makeText(this, "Found " + cleaners.size() + " cleaners", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
