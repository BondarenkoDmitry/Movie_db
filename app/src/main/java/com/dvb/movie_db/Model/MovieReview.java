package com.dvb.movie_db.Model;

import android.util.Log;
import android.view.View;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dmitrybondarenko on 22.11.17.
 */

public class MovieReview {

    private String original_title;
    private String poster_path;
    private String overview;
    private Number vote_average;
    private Date date;

    public MovieReview(){
        this.setOriginal_title(original_title);
        this.setPoster_path(poster_path);
    };

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Number getVote_average() {
        return vote_average;
    }

    public void setVote_average(Number vote_average) {
        this.vote_average = vote_average;
    }




    public Date getDate() {
        return date;
    }

    public void setDate(String release_date) {
        try {
            SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.date = serverFormat.parse(release_date);
        } catch (ParseException e) {
            Log.w("MovieReview", "Failed to parse the JSON date: " + release_date);
        }
    }


}
