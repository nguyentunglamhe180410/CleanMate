package com.example.cleanmate.common.utils;

import java.text.NumberFormat;
import java.time.LocalTime;
import java.util.Locale;

public final class ChangeType {
    private ChangeType() {}

    /**
     * Định dạng số tiền (VND) với phân nhóm nghìn
     */
    public static String changeMoneyType(double amount) {
        NumberFormat fmt = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        return fmt.format(amount) + " VND";
    }

    /**
     * Định dạng thời gian (LocalTime) thành "X giờ Y phút" hoặc "X giờ"
     */
    public static String changeTimeType(LocalTime time) {
        if (time.getMinute() == 0) {
            return time.getHour() + " giờ";
        } else {
            return time.getHour() + " giờ " + time.getMinute() + " phút";
        }
    }
}