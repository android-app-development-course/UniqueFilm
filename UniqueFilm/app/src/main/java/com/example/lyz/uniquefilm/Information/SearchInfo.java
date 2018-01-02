package com.example.lyz.uniquefilm.Information;

/**
 * Created by lyz on 18-1-1.
 */

public class SearchInfo {

    public String[] directors;
    public double rate;
    public int cover_x;
    public int star;
    public String title;
    public String url;
    public String[] casts;
    public String cover;
    public String id;
    public int cover_y;

    public String[] getDirectors() {
        return directors;
    }

    public void setDirectors(String[] directors) {
        this.directors = directors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }





    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public class director{

        public String name;
    }

    public class cast{

        public String name;
    }
}
