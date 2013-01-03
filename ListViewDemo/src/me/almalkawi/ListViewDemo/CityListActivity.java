package me.almalkawi.ListViewDemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CityListActivity extends Activity {
    private ListView mListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list);

        // Data
        final ArrayList<City> cities = new ArrayList<City>();
        cities.add(new City("San Francisco", "sf", "http://en.wikipedia.org/wiki/San_Francisco"));
        cities.add(new City("Seattle", "seattle", "http://en.wikipedia.org/wiki/Seattle"));
        cities.add(new City("Chicago", "chicago", "http://en.wikipedia.org/wiki/Chicago"));
        cities.add(new City("London", "london", "http://en.wikipedia.org/wiki/London"));
        cities.add(new City("Mumbai", "mumbai", "http://en.wikipedia.org/wiki/Mumbai"));
        cities.add(new City("Moscow", "moscow", "http://en.wikipedia.org/wiki/Moscow"));
        cities.add(new City("Sydney", "sydney", "http://en.wikipedia.org/wiki/Sydney"));
        cities.add(new City("Tokyo", "tokyo", "http://en.wikipedia.org/wiki/Tokyo"));

        mListView = (ListView) findViewById(R.id.city_list);
        mListView.setAdapter(new CityArrayAdapter(this, R.layout.city_row_item, cities));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final City city = (City) parent.getItemAtPosition(position);
                final Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(city.getUrl()));
                startActivity(intent);
            }
        });
    }
}
