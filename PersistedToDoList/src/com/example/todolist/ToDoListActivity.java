package com.example.todolist;

import java.util.ArrayList;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoListActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ArrayList<ToDoItem> mTodoItemsList;
    private ToDoItemAdapter mToDoItemsAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final EditText newItemEditText = (EditText) findViewById(R.id.new_item);

        newItemEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER) {
                    onNewItemAdded(newItemEditText.getText().toString());
                    newItemEditText.setText("");
                    return true;
                }
                return false;
            }
        });

        // Get references to the ListView
        final ListView todoListView = (ListView) findViewById(R.id.todos);

        // Create the array list of to do items
        mTodoItemsList = new ArrayList<ToDoItem>();

        // Create the array adapter to bind the array to the ListView
        mToDoItemsAdapter = new ToDoItemAdapter(this, R.layout.todolist_item, mTodoItemsList);

        // Bind the array adapter to the ListView.
        todoListView.setAdapter(mToDoItemsAdapter);

        // CusrorLoader ensure queries are performed asynchronously.
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }

    public void onNewItemAdded(String newItem) {
        //  Each ContentValue represents a single table row
        //  as a map of column names to values.
        final ContentValues values = new ContentValues();
        values.put(ToDoContentProvider.KEY_TASK, newItem);

        final ContentResolver cr = getContentResolver();
        cr.insert(ToDoContentProvider.CONTENT_URI, values);
        getLoaderManager().restartLoader(0, null, this);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ToDoContentProvider.CONTENT_URI,
                null, null, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mTodoItemsList.clear();

        // Gets index of the column given a name.
        final int keyTaskIndex = cursor.getColumnIndexOrThrow(ToDoContentProvider.KEY_TASK);

        // Database queries are returned as Cursor objects.
        // Cursors are pointers to the result set within the underlying data.
        while (cursor.moveToNext()) { // Moves cursor to next row
            final ToDoItem newItem = new ToDoItem(cursor.getString(keyTaskIndex)); // Extract column data from cursor
            mTodoItemsList.add(newItem);
        }
        mToDoItemsAdapter.notifyDataSetChanged();
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
