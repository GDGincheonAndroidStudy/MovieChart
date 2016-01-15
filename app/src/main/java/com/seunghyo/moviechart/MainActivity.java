package com.seunghyo.moviechart;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MovieTask().execute();

        final View rootView = (View) getLayoutInflater().inflate(R.layout.main, null);
        rootView.setBackgroundColor(Color.WHITE);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        CustomList customList = new CustomList(MainActivity.this);
        listView.setAdapter(customList);

        setContentView(rootView);
    }



    public class CustomList extends ArrayAdapter<String> {

        private final Activity context;

        public CustomList(Activity context ) {
            super(context, R.layout.listitem, data.getInstance().getMovie_name());
            this.context = context;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.listitem, null, true);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
            TextView title = (TextView) rowView.findViewById(R.id.title);
            TextView opendate = (TextView) rowView.findViewById(R.id.opendate);
            TextView cnt = (TextView) rowView.findViewById(R.id.cnt);

            imageView.setImageResource(R.mipmap.ic_launcher);
            String title_str = data.getInstance().getMovie_name().get(position);
            String date_str = data.getInstance().getOpen_date().get(position);
            String cnt_str = data.getInstance().getCnt().get(position);

            title.setTextSize(30);
            title.setText(title_str);

            opendate.setTextSize(10);
            opendate.setText(date_str);

            cnt.setTextSize(15);
            cnt.setText(cnt_str + "명");

            return rowView;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MovieTask extends AsyncTask<String, String, String> {

        private final String LOG_TAG = "Movie Task";
        private ArrayList<String> movie_name = new ArrayList<String>();
        private ArrayList<String> open_date = new ArrayList<String>();
        private ArrayList<String> rank = new ArrayList<String>();
        private ArrayList<String> cnt = new ArrayList<String>();

        private String getMovieDataFromJson(String MovieJsonStr)
                throws JSONException {

            JSONObject movieJson = new JSONObject(MovieJsonStr);
            JSONArray MovieArray = movieJson.getJSONArray("dailyBoxOfficeList");

            for (int i = 0; i < MovieArray.length(); i++) {

                JSONObject movieObj = MovieArray.getJSONObject(i);

                rank.add(i, movieObj.getString("rank"));
                movie_name.add(i, movieObj.getString("movieNm"));
                open_date.add(i, movieObj.getString("openDt"));
                cnt.add(i, movieObj.getString("audiCnt"));
            }

            data.getInstance().setRank(rank);
            data.getInstance().setMovie_name(movie_name);
            data.getInstance().setCnt(cnt);
            data.getInstance().setOpen_date(open_date);

            return null;
        }

        @Override
        protected String doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String movie_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?";
            HttpsURLConnection urlConnection = null;
            BufferedReader reader = null;
            String key = "430156241533f1d058c603178cc3ca0e";
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String date2 = "20160114";
            String movieJsonStr = null;

            try {
                final String KEY_PARAM = "key";
                final String DATE_PARAM = "targetDt";

                Uri builtUri = Uri.parse(movie_url).buildUpon()
                        .appendQueryParameter(KEY_PARAM, key)
                        .appendQueryParameter(DATE_PARAM, date2).build();

                URL url = new URL(builtUri.toString());
                Log.e(LOG_TAG, "Built URI " + builtUri.toString());

                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }

                movieJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Movie string " + movieJsonStr);
            }
             catch (IOException e) {
                 Log.e(LOG_TAG, "Error ", e);
                 return  null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            } try {
                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }
    }
}
