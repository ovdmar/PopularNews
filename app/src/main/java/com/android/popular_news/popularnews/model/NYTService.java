package com.android.popular_news.popularnews.model;


import com.android.popular_news.popularnews.data.remote.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by ovidiu on 12.05.2017.
 */

public interface NYTService {
    @GET("/svc/mostpopular/v2/mostemailed/all-sections/7.json")
    Call<ResponseModel> getMostEmailedNews(@Header("api-key") String key);
    @GET("/svc/mostpopular/v2//mostshared/all-sections/7.json")
    Call<ResponseModel> getMostSharedNews(@Header("api-key") String key);
    @GET("/svc/mostpopular/v2/mostviewed/all-sections/7.json")
    Call<ResponseModel> getMostViewedNews(@Header("api-key") String key);

}

