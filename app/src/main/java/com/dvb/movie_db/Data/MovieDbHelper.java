package com.dvb.movie_db.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dvb.movie_db.Models.Movie;
import com.dvb.movie_db.Data.MovieContract.MovieEntry;
/**
 * Created by dmitrybondarenko on 09.01.18.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie_db.db";
    public static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " ("
           + MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
           + MovieEntry.COLUMN_FILM_NAME + " TEXT NOT NULL, "
           + MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, "
           + MovieEntry.COLUMN_OVERVIEW + " TEXT, "
           + MovieEntry.COLUMN_RELEASE_DATE + " TEXT, "
           + MovieEntry.COLUMN_RATING + " INTEGER DEFAULT 0);";

                db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
