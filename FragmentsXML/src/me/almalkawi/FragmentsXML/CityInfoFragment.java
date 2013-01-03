package me.almalkawi.FragmentsXML;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CityInfoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View ui = inflater.inflate(R.layout.city_info_fragment, container, false);

        final TextView cityName = (TextView) ui.findViewById(R.id.city_name);
        cityName.setText("Chicago");

        // If the fragment has no UI, we return null.
        return ui;
    }
}
