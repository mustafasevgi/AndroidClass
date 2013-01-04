package me.almalkawi.DynamicFragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button mShowButton;
    private Button mHideButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mShowButton = (Button) findViewById(R.id.show_city);
        mHideButton = (Button) findViewById(R.id.hide_city);
    }

    public void showCity(View view) {
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Add fragment
        fragmentTransaction.add(R.id.image_container, new CityImageFragment());

        // Apply a transition animation
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        // Add the fragment transaction to the back stack
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
        mShowButton.setEnabled(false);
        mHideButton.setEnabled(true);
    }

    public void hideCity(View view) {
        final FragmentManager fragmentManager = getFragmentManager();

        // Remove fragment
        final Fragment fragment = fragmentManager.findFragmentById(R.id.image_container);
        if (fragment != null) {
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
        mHideButton.setEnabled(false);
        mShowButton.setEnabled(true);
    }
}
