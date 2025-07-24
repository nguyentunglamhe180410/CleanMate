package com.example.MovieInABox.common;

import java.time.Duration;

public final class CommonConstants {
    private CommonConstants() {} // utility class

    // Key movie
    public static final String MOVIE_FAVORITE ="favorite";
    public static final String MOVIE_COMMENT ="comment";
    public static final String MOVIE_STAR ="star";
    public static final String MOVIE_LATEST ="latest";
    public static final String MOVIE_GENRE ="genre";
    public static final String MOVIE_COUNTRY ="country";

    public static final String ACCESS_TOKEN ="access_token";

    public static final int DEFAULT_PAGE_SIZE = 9;

    public static final Duration TIME_INTERVAL = Duration.ofMinutes(20);


    public static final String DEFAULT_ADMIN    = "";
    public static final String DEFAULT_CUSTOMER = "";

}
