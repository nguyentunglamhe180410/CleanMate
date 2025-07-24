package com.example.MovieInABox.data.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MovieInABox.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CleanerAdapter extends RecyclerView.Adapter<CleanerAdapter.CleanerViewHolder> {

    public interface CleanerClickListener {
        void onDetailsClicked(CleanerUI cleaner);
        void onToggleClicked(CleanerUI cleaner);
    }

    private List<CleanerUI> cleaners;
    private Context context;
    private CleanerClickListener listener;

    public CleanerAdapter(Context context, List<CleanerUI> cleaners, CleanerClickListener listener) {
        this.context = context;
        this.cleaners = cleaners;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CleanerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cleaner, parent, false);
        return new CleanerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CleanerViewHolder holder, int position) {
        CleanerUI cleaner = cleaners.get(position);
        holder.name.setText(cleaner.getFullName());
        holder.email.setText(cleaner.getEmail());
        holder.status.setText(cleaner.isAvailable() ? "Khả dụng" : "Không khả dụng");
        holder.status.setTextColor(cleaner.isAvailable() ? 0xFF4CAF50 : 0xFFF44336);

        holder.avatar.setImageResource(R.drawable.user_image);

// If avatar URL exists, load it on a background thread
        if (cleaner.getAvatar() != null && !cleaner.getAvatar().isEmpty()) {
            new Thread(() -> {
                try {
                    URL url = new URL(cleaner.getAvatar());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);

                    // Update ImageView on the main thread
                    new Handler(Looper.getMainLooper()).post(() -> {
                        holder.avatar.setImageBitmap(bitmap);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    // Fallback to default avatar if load fails
                    new Handler(Looper.getMainLooper()).post(() -> {
                        holder.avatar.setImageResource(R.drawable.user_image);
                    });
                }
            }).start();
        }

        holder.detailsButton.setOnClickListener(v -> listener.onDetailsClicked(cleaner));
        holder.toggleButton.setOnClickListener(v -> listener.onToggleClicked(cleaner));
    }

    @Override
    public int getItemCount() {
        return cleaners.size();
    }

    static class CleanerViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name, email, status;
        ImageButton detailsButton, toggleButton;

        CleanerViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.cleanerAvatar);
            name = itemView.findViewById(R.id.cleanerName);
            email = itemView.findViewById(R.id.cleanerEmail);
            status = itemView.findViewById(R.id.cleanerStatus);
            detailsButton = itemView.findViewById(R.id.btnDetails);
            toggleButton = itemView.findViewById(R.id.btnToggle);
        }
    }
}

