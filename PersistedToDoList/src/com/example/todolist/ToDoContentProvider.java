package com.example.todolist;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;

public class ToDoContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI =
            Uri.parse("content://com.example.todoprovider/todoitems");

    public static final String KEY_ID = "_id";
    public static final String KEY_TASK = "task";

    private MySQLiteOpenHelper mDBOpenHelper;

    @Override
    public boolean onCreate() {
        // Construct the underlying database.
        mDBOpenHelper = new MySQLiteOpenHelper(getContext(),
                MySQLiteOpenHelper.DATABASE_NAME, null,
                MySQLiteOpenHelper.DATABASE_VERSION);
        return true;
    }

    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;

    private static final UriMatcher uriMatcher;

    // URI ending in 'todoitems' corresponds to a request for all items
    // URI ending in 'todoitems/[rowID]' corresponds to a single row.
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.todoprovider", "todoitems", ALLROWS);
        uriMatcher.addURI("com.example.todoprovider", "todoitems/#", SINGLE_ROW);
    }

    @Override
    public String getType(Uri uri) {
        // Return a string that identifies the MIME type for a Content Provider URI
        switch (uriMatcher.match(uri)) {
            case ALLROWS: {
                return "vnd.android.cursor.dir/vnd.example.todos";
            }
            case SINGLE_ROW: {
                return "vnd.android.cursor.item/vnd.example.todos";
            }
            default: {
                throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();

        final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MySQLiteOpenHelper.TABLE_NAME);

        // If this is a row query, limit the result set to the passed in row.
        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW: {
                String rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(KEY_ID + "=" + rowID);
                break;
            }
            default: {
                break;
            }
        }

        final Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);

        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();

        // If this is a row URI, limit the deletion to the specified row.
        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW: {
                final String rowID = uri.getPathSegments().get(1);
                selection = KEY_ID + "=" + rowID
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            }
            default: {
                break;
            }
        }

        // To return the number of deleted items, you must specify a where
        // clause. To delete all rows and return a value, pass in "1".
        if (selection == null) {
            selection = "1";
        }

        // Execute the deletion.
        int deleteCount = db.delete(MySQLiteOpenHelper.TABLE_NAME, selection, selectionArgs);

        // Notify any observers of the change in the data set.
        getContext().getContentResolver().notifyChange(uri, null);

        return deleteCount;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Get write database instance.
        final SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();

        // Insert the values into the table
        long id = db.insert(MySQLiteOpenHelper.TABLE_NAME, null, values);

        if (id > -1) {
            // Construct and return the URI of the newly inserted row.
            final Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);

            // Notify any observers of the change in the data set.
            getContext().getContentResolver().notifyChange(insertedId, null);

            return insertedId;
        } else {
            return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();

        // If this is a row URI, limit the deletion to the specified row.
        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW: {
                String rowID = uri.getPathSegments().get(1);
                selection = KEY_ID + "=" + rowID
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            }
            default: {
                break;
            }
        }

        // Perform the update.
        final int updateCount = db.update(MySQLiteOpenHelper.TABLE_NAME,
                values, selection, selectionArgs);

        // Notify any observers of the change in the data set.
        getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;
    }

    private static class MySQLiteOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "todo.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME = "todoItemTable";

        public MySQLiteOpenHelper(Context context, String name,
                                  CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // SQL statement to create a new database.
        private static final String CREATE_TABLE = "create table " +
                TABLE_NAME + " (" + KEY_ID + " integer primary key autoincrement, " +
                KEY_TASK + " text not null);";

        // Called when no database exists in disk and the helper class needs to create a new one.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        // Called when there is a database version mismatch, meaning that the version
        // of the database on disk needs to be upgraded to the current version.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // The simplest case is to drop the old table and create a new one.
            db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_NAME);
            // Create a new one.
            onCreate(db);
        }
    }
}
