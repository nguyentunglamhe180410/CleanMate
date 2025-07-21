package com.example.cleanmate.common.utils;

import static android.icu.text.DateTimePatternGenerator.ZONE;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public final class DateTimeVN {
    private DateTimeVN() {}

    /**
     * Trả về thời điểm hiện tại theo múi giờ Việt Nam.
     */
    public static ZonedDateTime getNow() {
        String tzId = TimeZone.getDefault().getID().equals("Asia/Ho_Chi_Minh")
                ? "Asia/Ho_Chi_Minh"
                : (System.getProperty("os.name").toLowerCase().contains("win")
                ? "SE Asia Standard Time"
                : "Asia/Ho_Chi_Minh");

        ZoneId zone = ZoneId.of(tzId);
        return ZonedDateTime.now(zone);
    }
    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    public static LocalTime convertToLocalTime(Time timeToConvert) {
        Instant instant = Instant.ofEpochMilli(timeToConvert.getTime());
        return instant.atZone(ZoneId.systemDefault()).toLocalTime();
    }
}
