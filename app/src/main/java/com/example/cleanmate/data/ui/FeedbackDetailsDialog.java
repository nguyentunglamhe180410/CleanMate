package com.example.cleanmate.data.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.cleanmate.R;

public class FeedbackDetailsDialog extends DialogFragment {

    private static final String ARG_FEEDBACK = "arg_feedback";
    private FeedbackWorkUI feedback;

    public static FeedbackDetailsDialog newInstance(FeedbackWorkUI feedback) {
        FeedbackDetailsDialog dialog = new FeedbackDetailsDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FEEDBACK, feedback);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            feedback = (FeedbackWorkUI) getArguments().getSerializable(ARG_FEEDBACK);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_feedback_details, null);

        TextView customerName = view.findViewById(R.id.dialogCustomerName);
        TextView dateTime = view.findViewById(R.id.dialogDateTime);
        TextView rating = view.findViewById(R.id.dialogRating);
        TextView content = view.findViewById(R.id.dialogContent);
        TextView price = view.findViewById(R.id.dialogPrice);
        ImageView closeButton = view.findViewById(R.id.dialogClose);

        if (feedback != null) {
            customerName.setText(feedback.getCustomerFullName());
            dateTime.setText("Ngày: " + feedback.getDate() + " - Giờ: " + feedback.getStartTime());
            rating.setText("Đánh giá: " + feedback.getRating() + "★");
            content.setText(feedback.getContent());
            price.setText("Số tiền: " + feedback.getDecimalPrice() + "₫");
        }

        closeButton.setOnClickListener(v -> dismiss());

        builder.setView(view);
        return builder.create();
    }
}
