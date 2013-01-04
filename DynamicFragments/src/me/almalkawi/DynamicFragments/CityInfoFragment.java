package me.almalkawi.DynamicFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CityInfoFragment extends Fragment { // TODO: don't add if it is already added
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View ui = inflater.inflate(R.layout.city_info_fragment, container, false);
        final TextView cityName = (TextView) ui.findViewById(R.id.city_name);
        cityName.setText("Chicago");
        return ui; // If the fragment has no UI, we return null.
    }
}
