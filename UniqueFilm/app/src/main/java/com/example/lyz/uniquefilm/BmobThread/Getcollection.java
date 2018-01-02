package com.example.lyz.uniquefilm.BmobThread;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.lyz.uniquefilm.Adapter.KwSearchRvAdapter;
import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.Database.userinformation;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by lyz on 18-1-2.
 */

public class Getcollection extends Thread {

    private userinformation user;
    private RecyclerView mRecyclerView;
    private KwSearchRvAdapter mAdapter;
    private String movieid;
    private movies mMovies;
    private List<movies> mlist;


    public Getcollection(String movieid,RecyclerView rv,KwSearchRvAdapter adapter){
        this.movieid=movieid;
        this.mRecyclerView=rv;
        this.mAdapter=adapter;
        this.mlist=adapter.list;
    }

    @Override
    public void run() {
        //user.setUsercollection( user.getUsercollection()+Integer.toString(movieid) + ",");
        BmobQuery<movies> query=new BmobQuery<movies>();
        query.addWhereEqualTo("movieid",Integer.parseInt(movieid));
        query.findObjects(new FindListener<movies>() {
            @Override
            public void done(List<movies> list, BmobException e) {
                if(e==null){
                    Log.i("result", "获取成功");
                    //setcollection(list.get(0));
                    if(mAdapter.list.size()>0){
                        mAdapter.list.add(list.get(0));
                    }

                    mAdapter.setData(mAdapter.list);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else{
                    e.printStackTrace();
                }
            }
        });
    }

    private void setcollection(movies movie){
        mMovies=movie;
    }

    public movies getMovies(){
        return mMovies;
    }
}
