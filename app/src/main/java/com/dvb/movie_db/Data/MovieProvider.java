package com.dvb.movie_db.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by dmitrybondarenko on 09.01.18.
 */

public class MovieProvider extends ContentProvider {

    public static final String LOG_TAG = MovieProvider.class.getSimpleName();
    private static final int MOVIES = 100;
    private static final int MOVIE_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY,
                MovieContract.PATH_MOVIE, MOVIES);
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY,
                MovieContract.PATH_MOVIE + "/#", MOVIE_ID);
    }

    private MovieDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new MovieDbHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match){
            case MOVIES:

                cursor = database.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIE_ID:

                selection = MovieContract.MovieEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can't query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }





    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case MOVIES:
                return insertMovie(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }



    private Uri insertMovie(Uri uri, ContentValues values){
        String name = values.getAsString(MovieContract.MovieEntry.COLUMN_FILM_NAME);
        if (name == null){
            throw new IllegalArgumentException("This movie should have a NAME");
        }

        String poster_path = values.getAsString(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
        if (poster_path == null){
            throw new IllegalArgumentException("This movie has no POSTER");
        }

        String overview = values.getAsString(MovieContract.MovieEntry.COLUMN_OVERVIEW);
        if (overview == null){
            throw new IllegalArgumentException("There's no OVERVIEW");
        }

        String release_date = values.getAsString(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);
        if (release_date == null){
            throw new IllegalArgumentException("This movie has no RELEASE_DATE");
        }

        Integer rating = values.getAsInteger(MovieContract.MovieEntry.COLUMN_RATING);
        if (rating != 0 && rating < 0){
            throw new IllegalArgumentException("This movie has negative RATING");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
        if (id == - 1){
            Log.e(LOG_TAG, "Failed to insert for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }



    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;
        final int match = sUriMatcher.match(uri);

        switch (match){
            case MOVIES:
//                Delete all movies
                return database.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
            case MOVIE_ID:
                // Delete a single movie
                selection = MovieContract.MovieEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }


        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }


    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case MOVIES:
                return MovieContract.MovieEntry.CONTENT_LIST_TYPE;
            case MOVIE_ID:
                return MovieContract.MovieEntry.CONTENT_LIST_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}





































