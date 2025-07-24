package com.example.MovieInABox.common;

import java.time.Duration;

public final class CommonConstants {
    private CommonConstants() {} // utility class

    // Key movie
    String MOVIE_FAVORITE ="favorite";
    String MOVIE_COMMENT ="comment";
    String MOVIE_STAR ="star";
    String MOVIE_LATEST ="latest";
    String MOVIE_GENRE ="genre";
    String MOVIE_COUNTRY ="country";

    // Key authen
    String ACCESS_TOKEN ="access_token";

    public static final int DEFAULT_PAGE_SIZE = 9;

    /** Khoảng thời gian giữa mỗi ca (20 phút). */
    public static final Duration TIME_INTERVAL = Duration.ofMinutes(20);

    /** Phần trăm hoa hồng (0.80 tương ứng 80%). */
    public static final double COMMISSION_PERCENTAGE = 0.80;

    /** Số tiền tối thiểu (VND). */
    public static final long MINIMUM_DEBIT_AMOUNT       = 200_000L;
    public static final long MINIMUM_DEPOSIT_AMOUNT     = 200_000L;
    public static final long MINIMUM_COINS_TO_ACCEPT_WORK = 200_000L;

    public static final String DEFAULT_ADMIN    = "";
    public static final String DEFAULT_CUSTOMER = "";
    public static final String DEFAULT_CLEANER  = "";
    public static final String SQL_SERVER_HOST     = "cleanmate-server.database.windows.net";
    public static final String SQL_SERVER_PORT     = "1433";
    public static final String SQL_SERVER_DATABASE = "CleanMateDB_PRM";
    public static final String SQL_SERVER_USER     = "cleanmateadmin@cleanmate-server";
    public static final String SQL_SERVER_PASSWORD = "CleanmateServer@";


    public static final String JDBC_URL = String.format(
            "jdbc:jtds:sqlserver://%s:%s/%s;ssl=require;user=%s;password=%s;",
            SQL_SERVER_HOST,
            SQL_SERVER_PORT,
            SQL_SERVER_DATABASE,
            SQL_SERVER_USER,
            SQL_SERVER_PASSWORD
    );
    /** Các trạng thái booking. */
    public static final class BookingStatus {
        private BookingStatus() {}

        public static final int NEW          = 1;
        public static final int CANCEL       = 2;
        public static final int ACCEPT       = 3;
        public static final int IN_PROGRESS  = 4;
        public static final int PENDING_DONE = 5;
        public static final int DONE         = 6;
    }

    /**
     * Chuyển mã trạng thái thành chuỗi
     */
    public static String getStatusString(int statusId) {
        switch (statusId) {
            case BookingStatus.NEW:          return "Việc mới";
            case BookingStatus.CANCEL:       return "Đã huỷ";
            case BookingStatus.ACCEPT:       return "Đã nhận";
            case BookingStatus.IN_PROGRESS:  return "Đang thực hiện";
            case BookingStatus.PENDING_DONE: return "Chờ khách hàng xác nhận";
            case BookingStatus.DONE:         return "Hoàn thành";
            default:                         return "Không xác định";
        }
    }
}
