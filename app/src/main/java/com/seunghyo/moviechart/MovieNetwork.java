package com.seunghyo.moviechart;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by SeungHyo on 2016-01-15.
 */
public class MovieNetwork {

    public MovieNetwork() {
        return;
    }

    public void getChart(String key, String date) {

        Retrofit retrofit_movie = new Retrofit.Builder()
                .baseUrl(NetworkInterface.API_URL)
                .client(new OkHttpClient())
                .addConverterFactory(new StringConverter())
                .build();

        NetworkInterface networkInterface = retrofit_movie.create(NetworkInterface.class);

        Call<String> callBack = networkInterface.movieInfo(key,date);


        try {
            Response<String> res = callBack.execute();
            String res_str = res.body();

            if(res_str.isEmpty()) {
                Log.e("에러", "API 받아오기 에러");
            } else {
                Log.e("내용", res_str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
