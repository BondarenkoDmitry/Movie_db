package com.dvb.movie_db.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.dvb.movie_db.Data.MovieCursorAdapter;
import com.dvb.movie_db.R;

import static android.R.attr.data;

/**
 * Created by dmitrybondarenko on 11.01.18.
 */

public class CatalogActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final int MOVIE_LOADER = 0;
    MovieCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql_catalog_activity);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
