package com.example.lyz.uniquefilm.Database;

import cn.bmob.v3.BmobObject;

/**
 * Created by lyz on 17-12-28.
 */

public class movies extends BmobObject {

    private int movieid;
    private String doubanid;
    private String moviename;
    private String doubanurl;
    private String cover;
    private double rate;
    private String directors;
    private String composer;
    private String actors;
    private String category;
    private String district;
    private String language;
    private String showtime;
    private String length;
    private String othername;
    private String description;
    private String comment;


    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getDoubanid() {
        return doubanid;
    }

    public String getDoubanurl() {
        return doubanurl;
    }

    public String getCover() {
        return cover;
    }


    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }


    public String getDirectors() {
        return directors;
    }

    public String getComposer() {
        return composer;
    }

    public String getActors() {
        return actors;
    }

    public String getCategory() {
        return category;
    }

    public String getDistrict() {
        return district;
    }

    public String getLanguage() {
        return language;
    }

    public String getShowtime() {
        return showtime;
    }

    public String getLength() {
        return length;
    }

    public String getOthername() {
        return othername;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
