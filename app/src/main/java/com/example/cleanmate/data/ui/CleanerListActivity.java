package com.example.cleanmate.data.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanmate.R;
import com.example.cleanmate.data.ui.CleanerUI;

import java.util.ArrayList;
import java.util.List;

public class CleanerListActivity extends AppCompatActivity implements CleanerAdapter.CleanerClickListener {

    private RecyclerView recyclerView;
    private CleanerAdapter adapter;
    private EditText searchInput;
    private TextView pageIndicator;
    private Button btnPrev, btnNext;

    private List<CleanerUI> allCleaners = new ArrayList<>();
    private List<CleanerUI> filteredCleaners = new ArrayList<>();

    private int currentPage = 1;
    private final int rowsPerPage = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_list);

        recyclerView = findViewById(R.id.recyclerEmployees);
        searchInput = findViewById(R.id.searchInput);
        pageIndicator = findViewById(R.id.pageIndicator);
        btnPrev = findViewById(R.id.btnPrevPage);
        btnNext = findViewById(R.id.btnNextPage);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Temporary dummy data (replace with API later)
        for (int i = 1; i <= 25; i++) {
            allCleaners.add(new CleanerUI(
                    String.valueOf(i),
                    "Nhân viên " + i,
                    "employee" + i + "@gmail.com",
                    "090000000" + i,
                    "Khu vực " + i,
                    i % 5,
                    i % 2 == 0,
                    ""
            ));
        }

        setupPagination();
        setupSearch();
    }

    private void setupPagination() {
        btnPrev.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                refreshList();
            }
        });

        btnNext.setOnClickListener(v -> {
            int totalPages = (int) Math.ceil(filteredCleaners.size() / (double) rowsPerPage);
            if (currentPage < totalPages) {
                currentPage++;
                refreshList();
            }
        });

        refreshList();
    }

    private void setupSearch() {
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentPage = 1;
                refreshList();
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void refreshList() {
        String query = searchInput.getText().toString().toLowerCase();
        filteredCleaners.clear();
        for (CleanerUI c : allCleaners) {
            if (c.getFullName().toLowerCase().contains(query)) {
                filteredCleaners.add(c);
            }
        }

        int totalPages = (int) Math.ceil(filteredCleaners.size() / (double) rowsPerPage);
        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, filteredCleaners.size());
        List<CleanerUI> pageItems = filteredCleaners.subList(start, end);

        adapter = new CleanerAdapter(this, pageItems, this);
        recyclerView.setAdapter(adapter);

        pageIndicator.setText(currentPage + " / " + (totalPages == 0 ? 1 : totalPages));
    }

    @Override
    public void onDetailsClicked(CleanerUI cleaner) {
        FragmentManager fm = getSupportFragmentManager();
        new CleanerDetailsDialog(cleaner).show(fm, "DetailsDialog");
    }

    @Override
    public void onToggleClicked(CleanerUI cleaner) {
        cleaner.setAvailable(!cleaner.isAvailable());
        refreshList();
    }
}

