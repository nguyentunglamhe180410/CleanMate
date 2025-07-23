package com.example.cleanmate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanmate.R;
import com.example.cleanmate.data.model.Voucher;
import com.example.cleanmate.data.model.UserVoucherDisplay;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
    
    private List<Voucher> vouchers;
    private List<UserVoucherDisplay> userVouchers;
    private boolean isShowingUserVouchers = false;
    private OnVoucherClickListener listener;

    public interface OnVoucherClickListener {
        void onVoucherClick(Voucher voucher);
        void onUserVoucherClick(UserVoucherDisplay userVoucher);
        void onUseNowClick(Voucher voucher);
        void onUseNowClick(UserVoucherDisplay userVoucher);
    }

    public VoucherAdapter(List<Voucher> vouchers, OnVoucherClickListener listener) {
        this.vouchers = vouchers;
        this.listener = listener;
        this.isShowingUserVouchers = false;
    }

    public void updateVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
        this.userVouchers = null;
        this.isShowingUserVouchers = false;
        notifyDataSetChanged();
    }

    public void updateUserVouchers(List<UserVoucherDisplay> userVouchers) {
        this.userVouchers = userVouchers;
        this.vouchers = null;
        this.isShowingUserVouchers = true;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_voucher, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        try {
            if (isShowingUserVouchers) {
                if (userVouchers != null && position < userVouchers.size()) {
                    holder.bindUserVoucher(userVouchers.get(position));
                }
            } else {
                if (vouchers != null && position < vouchers.size()) {
                    holder.bindVoucher(vouchers.get(position));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (isShowingUserVouchers) {
            return userVouchers != null ? userVouchers.size() : 0;
        } else {
            return vouchers != null ? vouchers.size() : 0;
        }
    }

    class VoucherViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvVoucherCode;
        private final TextView tvDescription;
        private final TextView tvExpireDate;
        private final TextView tvStatus;
        private final TextView tvQuantity;
        private final TextView tvUseNow;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVoucherCode = itemView.findViewById(R.id.tv_voucher_code);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvExpireDate = itemView.findViewById(R.id.tv_expire_date);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvUseNow = itemView.findViewById(R.id.tv_use_now);
        }

        public void bindVoucher(Voucher voucher) {
            if (voucher == null) {
                return;
            }
            
            tvVoucherCode.setText(voucher.getVoucherCode() != null ? voucher.getVoucherCode() : "N/A");
            tvDescription.setText(voucher.getDescription() != null ? voucher.getDescription() : "N/A");
            tvExpireDate.setText("Ngày hết hạn: " + formatDate(voucher.getExpireDate()));
            tvStatus.setText("Trạng thái: " + (voucher.getStatus() != null ? voucher.getStatus() : "N/A"));
            tvQuantity.setVisibility(View.GONE);
            tvUseNow.setVisibility(View.VISIBLE);
            
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onVoucherClick(voucher);
                }
            });
            
            tvUseNow.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onUseNowClick(voucher);
                }
            });
        }

        public void bindUserVoucher(UserVoucherDisplay userVoucher) {
            if (userVoucher == null) {
                return;
            }
            
            tvVoucherCode.setText(userVoucher.getVoucherCode() != null ? userVoucher.getVoucherCode() : "N/A");
            tvDescription.setText(userVoucher.getDescription() != null ? userVoucher.getDescription() : "N/A");
            tvExpireDate.setText("Ngày hết hạn: " + formatDate(userVoucher.getExpireDate()));
            tvStatus.setText("Trạng thái: " + (userVoucher.getStatus() != null ? userVoucher.getStatus() : "N/A"));
            tvQuantity.setText("Số lượng: " + (userVoucher.getQuantity() != null ? userVoucher.getQuantity() : 0));
            tvQuantity.setVisibility(View.VISIBLE);
            
            if (userVoucher.getQuantity() != null && userVoucher.getQuantity() > 0) {
                tvUseNow.setVisibility(View.VISIBLE);
                tvUseNow.setText("Sử dụng ngay");
            } else {
                tvUseNow.setVisibility(View.GONE);
            }
            
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onUserVoucherClick(userVoucher);
                }
            });
            
            tvUseNow.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onUseNowClick(userVoucher);
                }
            });
        }

        private String formatDate(String dateString) {
            if (dateString == null || dateString.isEmpty()) {
                return "N/A";
            }
            try {
                // Giả sử dateString có format "YYYY-MM-DD"
                String[] parts = dateString.split("-");
                if (parts.length >= 3) {
                    return parts[2] + "/" + parts[1] + "/" + parts[0];
                }
                return dateString;
            } catch (Exception e) {
                return dateString;
            }
        }
    }
} 