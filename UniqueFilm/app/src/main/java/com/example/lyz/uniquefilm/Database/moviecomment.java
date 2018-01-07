package com.example.lyz.uniquefilm.Database;

import cn.bmob.v3.BmobObject;

/**
 * Created by lyz on 18-1-4.
 */

public class moviecomment extends BmobObject {

    private int movieid;
    private String username;
    private String comment;

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
