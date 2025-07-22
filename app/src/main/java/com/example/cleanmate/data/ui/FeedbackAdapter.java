package com.example.cleanmate.data.ui.;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cleanmate.R;
import com.example.cleanmate.data.ui.FeedbackWorkUI;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(FeedbackWorkUI work);
    }

    private List<FeedbackWorkUI> feedbackList;
    private OnItemClickListener listener;

    public FeedbackAdapter(List<FeedbackWorkUI> feedbackList, OnItemClickListener listener) {
        this.feedbackList = feedbackList;
        this.listener = listener;
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
        FeedbackWorkUI work = feedbackList.get(position);
        holder.bind(work, listener);
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateTimeText;
        private final TextView customerName;
        private final TextView ratingText;
        private final TextView contentText;
        private final TextView priceText;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTimeText = itemView.findViewById(R.id.dateTimeText);
            customerName = itemView.findViewById(R.id.customerName);
            ratingText = itemView.findViewById(R.id.ratingText);
            contentText = itemView.findViewById(R.id.contentText);
            priceText = itemView.findViewById(R.id.priceText);
        }

        public void bind(final FeedbackWorkUI work, final OnItemClickListener listener) {
            dateTimeText.setText("Bắt đầu lúc " + formatTime(work.getStartTime()) +
                    " giờ ngày " + formatDate(work.getDate()));
            customerName.setText(work.getCustomerFullName());
            ratingText.setText("Đánh giá: " + work.getRating() + "★");
            contentText.setText("Phản hồi: " + work.getContent());
            priceText.setText("Số tiền: " + formatPrice(work.getDecimalPrice()));

            itemView.setOnClickListener(v -> listener.onItemClick(work));
        }

        private String formatPrice(double price) {
            return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(price);
        }

        private String formatTime(String time) {
            if (time == null) return "";
            String[] parts = time.split(":");
            return parts.length >= 2 ? parts[0] + ":" + parts[1] : time;
        }

        private String formatDate(String input) {
            return input; // Keep as string or parse to format properly if needed
        }
    }
}
