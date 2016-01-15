package com.seunghyo.moviechart;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

        ArrayList<String> rank = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> open_date = new ArrayList<>();
        ArrayList<String> cnt = new ArrayList<>();

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
                JSONObject totalObj = new JSONObject(res_str);
                String first_obj = totalObj.getString("boxOfficeResult");

                JSONObject boxOfficeObj =new JSONObject(first_obj);
                String last_obj = boxOfficeObj.getString("dailyBoxOfficeList");

                JSONArray movie_array = new JSONArray(last_obj);

                for(int i = 0 ; i < movie_array.length() ; i++) {
                    JSONObject movie_obj = movie_array.getJSONObject(i);
                    rank.add(i,  movie_obj.getString("rank"));
                    title.add(i,  movie_obj.getString("movieNm"));
                    open_date.add(i, movie_obj.getString("openDt"));
                    cnt.add(i,  movie_obj.getString("audiCnt"));

                    Log.e("내용", rank.get(i)+"위 "+title.get(i)+" 개봉일 : "+ open_date.get(i) + " 관람객 : "+cnt.get(i));
                }

                if(rank.size()>0) {
                    MovieData.getInstance().setRank(rank);
                    MovieData.getInstance().setMovie_name(title);
                    MovieData.getInstance().setOpen_date(open_date);
                    MovieData.getInstance().setCnt(cnt);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

}
