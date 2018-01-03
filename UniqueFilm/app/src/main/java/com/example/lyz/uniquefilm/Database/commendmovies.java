package com.example.lyz.uniquefilm.Database;

import cn.bmob.v3.BmobObject;

/**
 * Created by lyz on 18-1-2.
 */

public class commendmovies extends BmobObject {

    private int id;
    private String cover;
    private String title;
    private String content;
    private String movies;
    private String authorname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getMovies() {
        return movies;
    }

    public void setMovies(String movies) {
        this.movies = movies;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }
}
