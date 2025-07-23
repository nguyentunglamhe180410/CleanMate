package com.example.cleanmate.activity.cleanerActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cleanmate.R;
import com.example.cleanmate.data.model.Feedback;
import com.example.cleanmate.adapter.FeedbackAdapter;
import java.util.ArrayList;
import java.util.List;

public class CleanerViewFeedbackActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FeedbackAdapter adapter;
    private List<Feedback> allFeedback = new ArrayList<>();
    private List<Feedback> displayedFeedback = new ArrayList<>();
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

        adapter = new FeedbackAdapter(displayedFeedback, new FeedbackAdapter.OnFeedbackClickListener() {
            @Override
            public void onFeedbackClick(Feedback feedback) {
                // Show feedback details
                showFeedbackDetails(feedback);
            }

            @Override
            public void onEditClick(Feedback feedback) {
                // Not applicable for cleaner view
            }

            @Override
            public void onDeleteClick(Feedback feedback) {
                // Not applicable for cleaner view
            }
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
            Feedback feedback = new Feedback();
            feedback.setFeedbackId(i);
            feedback.setBookingId(100 + i);
            feedback.setUserId("customer-" + i);
            feedback.setCleanerId("cleaner-001");
            feedback.setRating((double) ((i % 5) + 1));
            feedback.setContent("Dịch vụ rất tốt! Khách hàng " + i + " rất hài lòng.");
            feedback.setCreatedAt(java.sql.Timestamp.valueOf("2024-01-15 10:30:00"));
            allFeedback.add(feedback);
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
        List<Feedback> filtered = new ArrayList<>();
        for (Feedback feedback : allFeedback) {
            if (feedback.getContent().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(feedback);
            }
        }
        allFeedback = filtered; // Reassign filtered list for simplicity
        page = 1;
        paginate();
    }

    private void showFeedbackDetails(Feedback feedback) {
        // Create a simple dialog to show feedback details
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Chi tiết Feedback");
        
        String details = "Booking ID: " + feedback.getBookingId() + "\n" +
                "Rating: " + feedback.getRating() + "/5.0\n" +
                "Nội dung: " + feedback.getContent() + "\n" +
                "Ngày tạo: " + feedback.getCreatedAt();
        
        builder.setMessage(details);
        builder.setPositiveButton("Đóng", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
