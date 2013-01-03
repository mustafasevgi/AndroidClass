package me.almalkawi.ListViewDemo;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CityArrayAdapter extends ArrayAdapter {
    private int mResource;
    private LayoutInflater mInflater;
    private Context mContext;

    public CityArrayAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);

        mInflater = LayoutInflater.from(context); // used to create a view of the layout
        mContext = context;   // need it to access resources
        mResource = resource; // the custom layout ID
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // Create a new view and inflate it in the row.
            convertView = mInflater.inflate(mResource, null);
        }

        // Extract the city object
        final City city = (City) getItem(position);

        // Set the city name
        final TextView cityName = (TextView) convertView.findViewById(R.id.city_name);
        cityName.setText(city.getName());

        // Set the city drawable
        final Resources res = mContext.getResources();
        final int id = res.getIdentifier(city.getImage(), "drawable", mContext.getPackageName());
        final ImageView cityImage = (ImageView) convertView.findViewById(R.id.city_image);
        cityImage.setImageDrawable(res.getDrawable(id));

        return convertView;
    }
}
