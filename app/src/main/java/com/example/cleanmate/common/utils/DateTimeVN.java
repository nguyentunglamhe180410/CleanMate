package com.example.cleanmate.common.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public final class DateTimeVN {
    private DateTimeVN() {}

    /**
     * Trả về thời điểm hiện tại theo múi giờ Việt Nam.
     */
    public static ZonedDateTime getNow() {
        // Trên Windows ID là "SE Asia Standard Time", trên Linux/macOS dùng "Asia/Ho_Chi_Minh"
        String tzId = TimeZone.getDefault().getID().equals("Asia/Ho_Chi_Minh")
                ? "Asia/Ho_Chi_Minh"
                : (System.getProperty("os.name").toLowerCase().contains("win")
                ? "SE Asia Standard Time"
                : "Asia/Ho_Chi_Minh");

        ZoneId zone = ZoneId.of(tzId);
        return ZonedDateTime.now(zone);
    }
}
