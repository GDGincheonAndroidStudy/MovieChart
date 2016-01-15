package com.seunghyo.moviechart;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MovieData data = new MovieData();
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        MovieNetwork network = new MovieNetwork();
        network.getChart("430156241533f1d058c603178cc3ca0e", "20160114");

        setContentView(R.layout.main);
        list = (ListView) findViewById(R.id.list);
        CustomList adapter = new CustomList(MainActivity.this);
        list.setAdapter(adapter);

         /*class moviesync extends AsyncTask<Void,Void,Void> {

            여기에 리턴받을 놈 =    network.getChart("430156241533f1d058c603178cc3ca0e","20160114");
            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }
        }*/
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
            String title_str = MovieData.getInstance().getMovie_name().get(position);
            String date_str = MovieData.getInstance().getOpen_date().get(position);
            String cnt_str = MovieData.getInstance().getCnt().get(position);

            title.setTextSize(20);
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

}
