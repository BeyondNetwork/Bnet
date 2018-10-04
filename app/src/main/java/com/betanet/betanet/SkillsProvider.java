package com.betanet.betanet;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class SkillsProvider extends ContentProvider {
    private static final String AUTHORITY = "com.betanet.betanet";
    static final String PROVIDER_NAME = "com.betanet.betanet.SkillsProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/skills";
    static final Uri CONTENT_URI = Uri.parse(URL);
    private static final String TAG = "SkillsProvider";
    static final UriMatcher uriMatcher = buildUriMatcher();
    private static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        return uriMatcher;
    }
    String[] skillsList = new String[]{"Lion", "Tiger", "Dog",
            "Cat", "Tortoise", "Rat", "Elephant", "Fox",
            "Cow", "Donkey", "Monkey"};

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String query = uri.getLastPathSegment().toLowerCase();
        Log.e(TAG, "query: "+query );
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"_ID", SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_TEXT_2, SearchManager.SUGGEST_COLUMN_INTENT_DATA});
        int i=0;
        for(String skill:skillsList) {
            if(skill.toLowerCase().contains(query.toLowerCase())) {
                Log.e(TAG, "query: match = "+ skill);
                matrixCursor.addRow(new Object[]{i++, skill, "", skill});
            }
        }
        Log.e(TAG, "query: count = " + matrixCursor.getCount());
        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
