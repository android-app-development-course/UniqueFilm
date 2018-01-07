package com.example.lyz.uniquefilm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.lyz.uniquefilm.Adapter.KwSearchRvAdapter;
import com.example.lyz.uniquefilm.Adapter.NpmRvAdapter;
import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.Database.newmovies;
import com.example.lyz.uniquefilm.Database.userinformation;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class NowplayingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private NpmRvAdapter mAdapter;
    private List<newmovies> mMoviesList=new ArrayList<newmovies>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowplaying);
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_now);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new NpmRvAdapter(this);
        for(int i=0;i<22;i++){
            //if(i==0){
             //   mMoviesList.removeAll(mMoviesList);
            //}
            new Getcollection(i,mRecyclerView,mAdapter).start();

        }

    }


    public class Getcollection extends Thread {

        private RecyclerView mRecyclerView;
        private NpmRvAdapter mAdapter;
        private movies mMovies;
        private int movieid;


        public Getcollection(int movieid,RecyclerView rv,NpmRvAdapter adapter){
            this.movieid=movieid;
            this.mRecyclerView=rv;
            this.mAdapter=adapter;
        }

        @Override
        public void run() {
            //user.setUsercollection( user.getUsercollection()+Integer.toString(movieid) + ",");
            BmobQuery<newmovies> query=new BmobQuery<newmovies>();
            query.addWhereEqualTo("movieid",movieid);
            query.findObjects(new FindListener<newmovies>() {
                @Override
                public void done(List<newmovies> list, BmobException e) {
                    if(e==null){
                        Log.i("result", "获取成功");
                        //setcollection(list.get(0));
                        mMoviesList.add(list.get(0));
                        mAdapter.setData(mMoviesList);
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
