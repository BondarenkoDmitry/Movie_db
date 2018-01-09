package com.dvb.movie_db.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dmitrybondarenko on 09.01.18.
 */

public class MovieContract {

    private MovieContract(){}

    public static final String CONTENT_AUTHORITY = "com.dvb.movie_db";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIE);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public final static String TABLE_NAME = "movie";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_POSTER_PATH = "poster_path";
        public final static String COLUMN_FILM_NAME = "name";
        public final static String COLUMN_OVERVIEW = "overview";
        public final static String COLUMN_RATING = "rating";
        public final static String COLUMN_RELEASE_DATE = "release_date";
    }
}
