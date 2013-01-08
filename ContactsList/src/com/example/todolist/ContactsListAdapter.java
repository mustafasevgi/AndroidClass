package com.example.todolist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactsListAdapter extends ArrayAdapter<ContactItem> {
    private int mResource;
    private LayoutInflater mInflator;

    public ContactsListAdapter(Context context, int resource, List<ContactItem> items) {
        super(context, resource, items);
        mResource = resource;
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = (LinearLayout) mInflator.inflate(mResource, null);
        }
        final LinearLayout contactsListLayout = (LinearLayout) convertView;

        final ContactItem item = getItem(position);
        final TextView taskView = (TextView) contactsListLayout.findViewById(R.id.row);
        taskView.setText(item.getDisplayName());

        return contactsListLayout;
    }
}
