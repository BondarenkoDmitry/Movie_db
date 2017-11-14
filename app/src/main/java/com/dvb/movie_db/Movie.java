package com.dvb.movie_db;

/**
 * Created by dmitrybondarenko on 14.11.17.
 */


public class Movie {

    private String title;
    private String poster_path;


    public Movie(String title, String poster_path) {

        this.setTitle(title);
        this.setPoster_path(poster_path);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

}


