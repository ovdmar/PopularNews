package com.android.popular_news.popularnews.model;

/**
 * Created by ovidiu on 12.05.2017.
 */

public class ApiUtils {
    public static final String BASE_URL = "https://api.nytimes.com/";

    public static NYTService getNYTService() {
        return RetrofitClient.getClient(BASE_URL).create(NYTService.class);
    }

    public static final String CONTENT_BASE_URL = "www.nytimes.com/";

}
