package me.almalkawi.FragmentsXML;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CityImageFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View ui = inflater.inflate(R.layout.city_image_fragment, container, false);
        final ImageView cityImage = (ImageView) ui.findViewById(R.id.city_image);
        cityImage.setImageResource(R.drawable.chicago);

        return ui; // If the fragment has no UI, we return null.
    }
}
