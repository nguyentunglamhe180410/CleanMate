package com.example.cleanmate.data.ui;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.cleanmate.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class CleanerDetailsDialog extends DialogFragment {

    private final CleanerUI cleaner;

    public CleanerDetailsDialog(CleanerUI cleaner) {
        this.cleaner = cleaner;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_cleaner_details, null);

        ImageView avatar = view.findViewById(R.id.dialogAvatar);
        TextView name = view.findViewById(R.id.dialogName);
        TextView email = view.findViewById(R.id.dialogEmail);
        TextView phone = view.findViewById(R.id.dialogPhone);
        TextView experience = view.findViewById(R.id.dialogExperience);
        TextView area = view.findViewById(R.id.dialogArea);
        TextView availability = view.findViewById(R.id.dialogAvailability);

        name.setText(cleaner.getFullName());
        email.setText(cleaner.getEmail());
        phone.setText(cleaner.getPhoneNumber());
        experience.setText(cleaner.getExperienceYear() + " năm");
        area.setText(cleaner.getArea());
        availability.setText(cleaner.isAvailable() ? "Khả dụng" : "Không khả dụng");
        availability.setTextColor(cleaner.isAvailable() ? 0xFF4CAF50 : 0xFFF44336);

        avatar.setImageResource(R.drawable.user_image); // default placeholder

        if (cleaner.getAvatar() != null && !cleaner.getAvatar().isEmpty()) {
            new Thread(() -> {
                try {
                    URL url = new URL(cleaner.getAvatar());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);

                    new Handler(Looper.getMainLooper()).post(() -> {
                        avatar.setImageBitmap(bitmap);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(() -> {
                        avatar.setImageResource(R.drawable.user_image);
                    });
                }
            }).start();
        } else {
            avatar.setImageResource(R.drawable.user_image);
        }

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .create();
    }
}
