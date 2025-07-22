package com.example.cleanmate;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanmate.R;
import com.example.cleanmate.data.model.Booking;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookingList;
    private Context context;
    private OnBookingActionListener listener;

    public interface OnBookingActionListener {
        void onConfirmComplete(Booking booking);
    }

    public BookingAdapter(Context context, OnBookingActionListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<Booking> bookings) {
        this.bookingList = bookings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.txtService.setText("Dịch vụ ID: " + booking.getServicePriceId());
        holder.txtDate.setText("Ngày: " + booking.getDate());
        holder.txtStatus.setText("Trạng thái: " + getStatusName(booking.getBookingStatusId()));

        // Nếu trạng thái chưa hoàn thành (ví dụ: ID 3 là "Chờ xác nhận hoàn thành")
        if (booking.getBookingStatusId() != 4) {
            holder.btnConfirmDone.setVisibility(View.VISIBLE);
            holder.btnConfirmDone.setOnClickListener(v -> {
                listener.onConfirmComplete(booking);
            });
        } else {
            holder.btnConfirmDone.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return bookingList != null ? bookingList.size() : 0;
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView txtService, txtDate, txtStatus;
        Button btnConfirmDone;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            txtService = itemView.findViewById(R.id.txtService);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            btnConfirmDone = itemView.findViewById(R.id.btnConfirmDone);
        }
    }

    private String getStatusName(int statusId) {
        switch (statusId) {
            case 1: return "Đang chờ xử lý";
            case 2: return "Đã xác nhận";
            case 3: return "Chờ xác nhận hoàn thành";
            case 4: return "Hoàn thành";
            default: return "Không xác định";
        }
    }
}

