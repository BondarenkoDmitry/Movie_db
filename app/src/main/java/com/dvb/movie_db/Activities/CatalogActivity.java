package com.dvb.movie_db.Activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.dvb.movie_db.Data.MovieContract;
import com.dvb.movie_db.Data.MovieCursorAdapter;
import com.dvb.movie_db.R;

/**
 * Created by dmitrybondarenko on 11.01.18.
 */

public class CatalogActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final int MOVIE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql_catalog_activity);

        // Initialize the loader
        getSupportLoaderManager().initLoader(1, null, this);
    }

    private void insertDummyMovie(){

        ContentValues values = new ContentValues();

            values.put(MovieContract.MovieEntry.COLUMN_FILM_NAME, "WOW Movie");
            values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, "find_this_poster");
            values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, "That's a nice WOW movie");
            values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, "12.07.1986");
            values.put(MovieContract.MovieEntry.COLUMN_RATING, 9);

        Uri newUri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
    }

    private void deleteAllMovies(){
        int rowsDeleted = getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity ", rowsDeleted + " rows deleted from body database");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        String[] projection = {
                MovieContract.MovieEntry._ID,
                MovieContract.MovieEntry.COLUMN_FILM_NAME,
                MovieContract.MovieEntry.COLUMN_POSTER_PATH,
                MovieContract.MovieEntry.COLUMN_OVERVIEW,
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
                MovieContract.MovieEntry.COLUMN_RATING
        };
        return new CursorLoader(this,
                MovieContract.MovieEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Initialize the ListView
        ListView lvItems = (ListView) findViewById(R.id.sqList);
        MovieCursorAdapter adapter = new MovieCursorAdapter(this, data);
        lvItems.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            case R.id.action_insert_dummy_data:
                insertDummyMovie();
                return true;

            case R.id.action_delete_all_entries:
                deleteAllMovies();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}





























