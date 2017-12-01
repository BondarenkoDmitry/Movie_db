package com.dvb.movie_db.Model;

import static android.R.attr.id;

/**
 * Created by dmitrybondarenko on 14.11.17.
 */


public class Movie {

    private int id;
    private String title;
    private String poster_path;



    public Movie(int id, String title, String poster_path) {

        this.setId(id);
        this.setTitle(title);
        this.setPoster_path(poster_path);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


