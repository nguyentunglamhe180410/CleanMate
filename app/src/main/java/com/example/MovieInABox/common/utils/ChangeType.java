package com.example.MovieInABox.common.utils;

import java.text.NumberFormat;
import java.time.LocalTime;
import java.util.Locale;

public final class ChangeType {
    private ChangeType() {}

    public static String changeMoneyType(double amount) {
        NumberFormat fmt = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        return fmt.format(amount) + " VND";
    }


    public static String changeTimeType(LocalTime time) {
        if (time.getMinute() == 0) {
            return time.getHour() + " giờ";
        } else {
            return time.getHour() + " giờ " + time.getMinute() + " phút";
        }
    }
}