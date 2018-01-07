package com.example.lyz.uniquefilm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.lyz.uniquefilm.Adapter.KwSearchRvAdapter;
import com.example.lyz.uniquefilm.Adapter.SComRvAdapter;
import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.Database.userinformation;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ShowrecomActivity extends AppCompatActivity {

    private TextView tvtitle;
    private TextView tvcontent;
    private RecyclerView mRecyclerView;
    private SComRvAdapter mAdapter;
    private List<String> introlist=new ArrayList<String>();
    private List<movies> mMoviesList=new ArrayList<movies>();
    private List<String> moviename=new ArrayList<String>();
    private List<String> intro=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showrecom);
        tvtitle=(TextView)findViewById(R.id.tv_title);
        tvcontent=(TextView)findViewById(R.id.tv_content);
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_movies);
        mAdapter=new SComRvAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String title=bundle.getString("title");
        String content=bundle.getString("content");
        String movies=bundle.getString("movies");
        Log.i("movies",movies);
        String authorname=bundle.getString("authorname");

        tvtitle.setText(title);
        tvcontent.setText(content);
        String movie[]=movies.split("\\|");
        Log.i("movielength",Integer.toString(movie.length));
        for(int i=0;i<movie.length;i++){
            String subitem[]=movie[i].split(":");
            moviename.add(subitem[0]);
            if(subitem.length>1)
                introlist.add(subitem[1]);
            else
                introlist.add("");
        }
        for(int i=0;i<moviename.size();i++){
            Log.i("moviename",moviename.get(i));
            Log.i("introlist",introlist.get(i));
            new Getmovies(introlist.get(i),moviename.get(i),mRecyclerView,mAdapter).start();
        }
    }

    public class Getmovies extends Thread {

        private RecyclerView mRecyclerView;
        private SComRvAdapter mAdapter;
        private String moviename;
        private movies mMovies;
        private String movieintro;


        public Getmovies(String movieintro,String moviename,RecyclerView rv,SComRvAdapter adapter){
            this.movieintro=movieintro;
            this.moviename=moviename;
            this.mRecyclerView=rv;
            this.mAdapter=adapter;
        }

        @Override
        public void run() {
            //user.setUsercollection( user.getUsercollection()+Integer.toString(movieid) + ",");
            BmobQuery<movies> query=new BmobQuery<movies>();
            query.addWhereEqualTo("moviename",moviename);
            query.findObjects(new FindListener<movies>() {
                @Override
                public void done(List<movies> list, BmobException e) {
                    if(e==null){
                        Log.i("result", "获取成功");
                        //setcollection(list.get(0));
                        mMoviesList.add(list.get(0));
                        intro.add(movieintro);
                        Log.i("intro",movieintro);
                        mAdapter.setData(mMoviesList,intro);
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
