package com.example.lyz.uniquefilm.BmobThread;

import android.util.Log;

import com.example.lyz.uniquefilm.Database.userinformation;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by lyz on 18-1-2.
 */

public class Addcollection extends Thread {

    private userinformation user;
    private int movieid;

    public Addcollection(userinformation user,int movieid){
        this.user=user;
        this.movieid=movieid;
    }

    @Override
    public void run() {
        user.setUsercollection( user.getUsercollection()+Integer.toString(movieid) + ",");
        user.update(user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("result", "收藏成功");
                    //Toast.makeText(, "收藏成功！", Toast.LENGTH_SHORT).show();
                } else {
                    e.printStackTrace();
                    //Toast.makeText(FilmDetailActivity.this, "1收藏失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
