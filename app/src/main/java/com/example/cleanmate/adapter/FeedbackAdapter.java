package com.example.cleanmate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanmate.R;
import com.example.cleanmate.data.model.Feedback;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {
    
    private List<Feedback> feedbacks;
    private OnFeedbackClickListener listener;

    public interface OnFeedbackClickListener {
        void onFeedbackClick(Feedback feedback);
        void onEditClick(Feedback feedback);
        void onDeleteClick(Feedback feedback);
    }

    public FeedbackAdapter(List<Feedback> feedbacks, OnFeedbackClickListener listener) {
        this.feedbacks = feedbacks;
        this.listener = listener;
    }

    public void updateFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feedback, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        holder.bindFeedback(feedbacks.get(position));
    }

    @Override
    public int getItemCount() {
        return feedbacks != null ? feedbacks.size() : 0;
    }

    class FeedbackViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvBookingId;
        private final TextView tvCleanerId;
        private final TextView tvRatingText;
        private final RatingBar ratingBar;
        private final TextView tvContent;
        private final TextView tvCreatedAt;
        private final Button btnEdit;
        private final Button btnDelete;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookingId = itemView.findViewById(R.id.tv_booking_id);
            tvCleanerId = itemView.findViewById(R.id.tv_cleaner_id);
            tvRatingText = itemView.findViewById(R.id.tv_rating_text);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvCreatedAt = itemView.findViewById(R.id.tv_created_at);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }

        public void bindFeedback(Feedback feedback) {
            // Format Booking ID
            tvBookingId.setText("Booking #" + feedback.getBookingId());
            tvCleanerId.setText("Cleaner: " + feedback.getCleanerId());
            
            // Set rating
            float rating = feedback.getRating().floatValue();
            ratingBar.setRating(rating);
            tvRatingText.setText(String.format("%.1f", rating));
            
            tvContent.setText(feedback.getContent());
            tvCreatedAt.setText("📅 " + formatTimestamp(feedback.getCreatedAt()));

            // Set click listeners
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onFeedbackClick(feedback);
                }
            });

            btnEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditClick(feedback);
                }
            });

            btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(feedback);
                }
            });
        }

        private String formatTimestamp(java.sql.Timestamp timestamp) {
            if (timestamp == null) return "N/A";
            String timestampStr = timestamp.toString();
            // Format: YYYY-MM-DD HH:MM:SS -> DD/MM/YYYY HH:MM
            if (timestampStr.length() >= 19) {
                String date = timestampStr.substring(0, 10); // YYYY-MM-DD
                String time = timestampStr.substring(11, 16); // HH:MM
                String[] dateParts = date.split("-");
                if (dateParts.length == 3) {
                    return dateParts[2] + "/" + dateParts[1] + "/" + dateParts[0] + " " + time;
                }
            }
            return timestampStr;
        }
    }
} 