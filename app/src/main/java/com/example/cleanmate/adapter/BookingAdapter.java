package com.example.cleanmate.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanmate.R;
import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.data.model.dto.dto;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    public interface OnConfirmListener {
        void onConfirmComplete(dto.BookingDTO booking);
    }

    private final Context context;
    private final OnConfirmListener listener;
    private List<dto.BookingDTO> data;

    public BookingAdapter(Context ctx, OnConfirmListener l) {
        this.context = ctx;
        this.listener = l;
    }

    public void setData(List<dto.BookingDTO> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    @NonNull @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.ViewHolder h, int pos) {
        dto.BookingDTO w = data.get(pos);
        h.txtServiceName.setText(w.getServiceName());
        h.txtServiceDescription.setText(w.getServiceDescription());
        h.txtDateTime.setText("Ngày: " + w.getDate() + "  |  Giờ: " + w.getStartTime());
        h.txtPriceCommission.setText("Giá: " + w.getPrice()
                + "  |  Hoa hồng: " + w.getCommission());
        h.txtAddress.setText("Địa chỉ: " + w.getAddress() + " (số: " + w.getAddressNo() + ")");
        h.txtCustomer.setText("Khách: " + w.getCustomerFullName()
                + " (" + w.getCustomerPhoneNumber() + ")");

        if (w.getBookingStatusId() != CommonConstants.BookingStatus.DONE) {
            h.btnConfirmDone.setVisibility(View.VISIBLE);
            h.btnConfirmDone.setOnClickListener(v -> listener.onConfirmComplete(w));
        } else {
            h.btnConfirmDone.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtServiceName, txtServiceDescription, txtDateTime,
                txtPriceCommission, txtAddress, txtCustomer;
        Button btnConfirmDone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtServiceName        = itemView.findViewById(R.id.txtServiceName);
            txtServiceDescription = itemView.findViewById(R.id.txtServiceDescription);
            txtDateTime           = itemView.findViewById(R.id.txtDateTime);
            txtPriceCommission    = itemView.findViewById(R.id.txtPriceCommission);
            txtAddress            = itemView.findViewById(R.id.txtAddress);
            txtCustomer           = itemView.findViewById(R.id.txtCustomer);
            btnConfirmDone        = itemView.findViewById(R.id.btnConfirmDone);
        }
    }
}

