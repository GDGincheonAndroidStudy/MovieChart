package com.seunghyo.moviechart;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by SeungHyo on 2016-01-15.
 */
public interface NetworkInterface {

    public static final String API_URL = "http://www.kobis.or.kr";

    @GET("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.jsonkey={key}&targetDt={date}")
    Call<String> movieInfo(
            @Query("key1") String key,
            @Query("date") String date
    );


}
