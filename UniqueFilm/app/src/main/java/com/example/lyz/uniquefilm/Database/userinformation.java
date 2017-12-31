package com.example.lyz.uniquefilm.Database;

import cn.bmob.v3.BmobObject;

/**
 * Created by lyz on 17-12-29.
 */

public class userinformation extends BmobObject {

    private int userid;
    private String username;
    private String userphoto;
    private String userpassword;
    private String userphone;
    private String usercollection;
    private String userscored;

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphoto() {
        return userphoto;
    }

    public void setUserphoto(String userphoto) {
        this.userphoto = userphoto;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUsercollection() {
        return usercollection;
    }

    public void setUsercollection(String usercollection) {
        this.usercollection = usercollection;
    }

    public String getUserscored() {
        return userscored;
    }

    public void setUserscored(String userscored) {
        this.userscored = userscored;
    }
}
