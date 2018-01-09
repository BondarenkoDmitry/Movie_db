package com.dvb.movie_db.Models;

/**
 * Created by dmitrybondarenko on 25.12.17.
 */


public class Review {

    private String author;
    private String content;


    public Review(String author,  String content){
        this.setAuthor(author);
        this.setContent(content);
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}