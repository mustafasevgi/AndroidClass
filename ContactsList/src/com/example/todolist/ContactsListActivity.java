package com.example.todolist;

import java.util.ArrayList;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;

public class ContactsListActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ArrayList<ContactItem> mContactsList;
    private ContactsListAdapter mContactsListAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        // Get references to the ListView
        final ListView contactsListView = (ListView) findViewById(R.id.contacts);

        // Create the array list of contact items
        mContactsList = new ArrayList<ContactItem>();

        // Create the array adapter to bind the array to the ListView
        mContactsListAdapter = new ContactsListAdapter(this, R.layout.contactlist_item, mContactsList);

        // Bind the array adapter to the ListView.
        contactsListView.setAdapter(mContactsListAdapter);

        // CusrorLoader ensure queries are performed asynchronously.
        getLoaderManager().initLoader(0, null, this); // Third parameter is reference to callbacks
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this); // Third parameter is reference to callbacks
    }

    // Called when the loader is initliazed.
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
    }

    // Called when the Loader Manager has completed the async query.
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mContactsList.clear();

        // Gets index of the column given a name.
        final int columnIndex = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);

        // Database queries are returned as Cursor objects.
        // Cursors are pointers to the result set within the underlying data.
        // Here is how we iterate over the cursor rows.
        while (cursor.moveToNext()) { // Moves cursor to next row, cursor is initialized at before first.
            final ContactItem newItem = new ContactItem(cursor.getString(columnIndex)); // Extract column data from cursor
            mContactsList.add(newItem);
        }
        mContactsListAdapter.notifyDataSetChanged();
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
