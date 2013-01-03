package me.almalkawi.FragmentsBasics;

import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class HeadlinesFragment extends ListFragment {
    OnHeadlineSelectedListener mCallback;

    // The container Activity must implement this interface so the fragment can deliver messages.
    public interface OnHeadlineSelectedListener {
        // Called by HeadlineFragment when a list item is selected.
        public void onArticleSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, Ipsum.Headlines));
    }
}
