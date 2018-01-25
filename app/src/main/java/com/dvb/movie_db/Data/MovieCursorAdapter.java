package com.dvb.movie_db.Data;

import android.content.Context;
import android.database.Cursor;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dvb.movie_db.Helpers.RoundedTransformation;
import com.dvb.movie_db.Models.Movie;
import com.dvb.movie_db.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;

import butterknife.OnClick;

import static android.R.attr.rating;

/**
 * Created by dmitrybondarenko on 10.01.18.
 */

public class MovieCursorAdapter extends CursorAdapter {

    public MovieCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.sql_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView aTitle = (TextView) view.findViewById(R.id.sqTitle);
        ImageView aPosterPath = (ImageView) view.findViewById(R.id.sqPoster);
        TextView aReleaseDate = (TextView) view.findViewById(R.id.sqReleaseDate);
        TextView aOverview = (TextView) view.findViewById(R.id.sqOverview);
        RatingBar aRating = (RatingBar) view.findViewById(R.id.sqRating);
        Button dButton = (Button) view.findViewById(R.id.sqDelete);

        int titleColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_FILM_NAME);
        int posterColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
        int releaseColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);
        int overviewColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW);
        int ratingColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING);


        String movieTitle = cursor.getString(titleColumnIndex);
        String moviePoster = cursor.getString(posterColumnIndex);
        String movieRelease = cursor.getString(releaseColumnIndex);
        String movieOverview = cursor.getString(overviewColumnIndex);
        Number movieRating = cursor.getInt(ratingColumnIndex);


        aTitle.setText(movieTitle);

        Picasso.with(aPosterPath.getContext())
                .load("https://image.tmdb.org/t/p/w185" + moviePoster)
                .transform(new RoundedTransformation(5, 0))
                .error(R.mipmap.ic_launcher)
                .into(aPosterPath);


        // Don't know how to convert it into normal date.
//        The code below breaks the app.

//        if (movieRelease != null){
//            String formattedDate = DateFormat.getDateInstance().format(movieRelease);
//            aReleaseDate.setText(formattedDate);
//        }

        aReleaseDate.setText(movieRelease);
        aOverview.setText(movieOverview);
        aRating.setRating(movieRating.floatValue());

        
        dButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSQLMovie();
            }
        });
    }

    private void deleteSQLMovie() {

    }
}








































