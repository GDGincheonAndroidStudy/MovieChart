package com.seunghyo.moviechart;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by SeungHyo on 2016-01-15.
 */
public interface NetworkInterface {

    public static final String API_URL = "http://www.kobis.or.kr";


    @GET("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json")
    Call<String> movieInfo(
            @Query("key") String key,
            @Query("targetDt") String date
    );

}
