package com.dvb.movie_db.Model;

/**
 * Created by dmitrybondarenko on 25.12.17.
 */

public class Video {

    private String site;
    private String key;

    public Video(String site, String key){
        this.setSite(site);
        this.setKey(key);
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
