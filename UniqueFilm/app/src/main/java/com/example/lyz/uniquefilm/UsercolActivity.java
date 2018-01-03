package com.example.lyz.uniquefilm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.lyz.uniquefilm.Adapter.KwSearchRvAdapter;
import com.example.lyz.uniquefilm.Adapter.SearchRvAdapter;
import com.example.lyz.uniquefilm.BmobThread.Getcollection;
import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.Database.userinformation;
import com.example.lyz.uniquefilm.fragments.CustomerFragment;

import java.util.ArrayList;
import java.util.List;

import List.ViewHolderAdapter;
import Refresh.CircleRefreshLayout;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class UsercolActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private KwSearchRvAdapter adapter;

    private ImageButton ibret;
    private CircleRefreshLayout mRefreshLayout;
    List<movies> movieslist=new ArrayList<movies>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercol);
        ibret=(ImageButton)findViewById(R.id.ib_col_ret);
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_colmovies);
        mRefreshLayout=(CircleRefreshLayout)findViewById(R.id.refresh_layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new KwSearchRvAdapter(UsercolActivity.this);
        final SharedPreferences myPreference=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        String username=myPreference.getString("username","");
        BmobQuery<userinformation> query=new BmobQuery<userinformation>();
        query.addWhereEqualTo("username",username);
        query.findObjects(new FindListener<userinformation>() {
            @Override
            public void done(List<userinformation> list, BmobException e) {
                if(e==null){
                    userinformation user=list.get(0);
                    //new Getcollection(user,adapter,mRecyclerView).start();
                    String collections=user.getUsercollection();
                    String collection[]=collections.split(",");
                    Log.i("lenght",Integer.toString(collection.length));
                    List<movies> moviesList=new ArrayList<movies>() ;
                    for(int i=0;i<collection.length;i++){

                        new Getcollection(collection[i],mRecyclerView,adapter).start();
                    }

                }
            }
        });


        ibret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {

            }
        });
    }

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
                        movieslist.add(list.get(0));
                        mAdapter.setData(movieslist);
                        mRecyclerView.setAdapter(mAdapter);
                        Log.i("setdate","here");
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

}
