package com.quit;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class SilenceProvider extends ContentProvider {
    // Implement necessary methods of the ContentProvider class
    @Override
    public boolean onCreate() {
        // Initialize your provider here
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Perform query operation and return the cursor
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Perform insert operation and return the inserted item's Uri
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // Perform update operation and return the number of rows affected
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Perform delete operation and return the number of rows deleted
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // Return the MIME type of the data
        return null;
    }
}
