package com.example.cleanmate.data.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cleanmate.R;
import com.example.cleanmate.data.ui.FeedbackWorkUI;
import com.example.cleanmate.data.ui.FeedbackAdapter;
import java.util.ArrayList;
import java.util.List;

public class CleanerViewFeedbackActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FeedbackAdapter adapter;
    private List<FeedbackWorkUI> allFeedback = new ArrayList<>();
    private List<FeedbackWorkUI> displayedFeedback = new ArrayList<>();
    private EditText searchInput;
    private TextView pageIndicator;
    private int page = 1;
    private final int WORKS_PER_PAGE = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_feedback);

        recyclerView = findViewById(R.id.recyclerFeedback);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        searchInput = findViewById(R.id.searchInput);
        pageIndicator = findViewById(R.id.pageIndicator);

        loadDummyData(); // Replace with API later
        paginate();

        adapter = new FeedbackAdapter(displayedFeedback, work -> {
            // TODO: Open FeedbackDetailsDialog with "work"
        });

        recyclerView.setAdapter(adapter);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        findViewById(R.id.btnPrevPage).setOnClickListener(v -> changePage(-1));
        findViewById(R.id.btnNextPage).setOnClickListener(v -> changePage(1));
    }

    private void loadDummyData() {
        for (int i = 1; i <= 20; i++) {
            allFeedback.add(new FeedbackWorkUI(
                    "Khách hàng " + i,
                    "09:0" + (i % 5),
                    "2025-07-21",
                    (i % 5) + 1,
                    "Dịch vụ rất tốt!",
                    i * 50000
            ));
        }
    }

    private void paginate() {
        int start = (page - 1) * WORKS_PER_PAGE;
        int end = Math.min(start + WORKS_PER_PAGE, allFeedback.size());
        displayedFeedback.clear();
        displayedFeedback.addAll(allFeedback.subList(start, end));
        pageIndicator.setText(page + " / " + (int) Math.ceil(allFeedback.size() / (double) WORKS_PER_PAGE));
        adapter.notifyDataSetChanged();
    }

    private void changePage(int delta) {
        int totalPages = (int) Math.ceil(allFeedback.size() / (double) WORKS_PER_PAGE);
        page = Math.max(1, Math.min(page + delta, totalPages));
        paginate();
    }

    private void filter(String query) {
        List<FeedbackWorkUI> filtered = new ArrayList<>();
        for (FeedbackWorkUI work : allFeedback) {
            if (work.getCustomerFullName().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(work);
            }
        }
        allFeedback = filtered; // Reassign filtered list for simplicity
        page = 1;
        paginate();
    }
}
