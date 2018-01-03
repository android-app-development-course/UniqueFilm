package com.example.lyz.uniquefilm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.lyz.uniquefilm.Adapter.SearchRvAdapter;
import com.example.lyz.uniquefilm.Analysis.Searchjson;
import com.example.lyz.uniquefilm.Database.movies;

import java.util.Random;

import cn.bmob.v3.BmobQuery;

public class UserrecommendActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SearchRvAdapter mAdapter;
    private Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userrecommend);
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_recommend);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new SearchRvAdapter(this);
        SharedPreferences myPreference=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        String types=myPreference.getString("type","剧情");
        Log.i("types",types);
        String type[]=types.split("/");
        int which=new Random().nextInt(type.length);
        Log.i("type",Integer.toString(which));
        String url="https://movie.douban.com/j/new_search_subjects?sort=S&range=0,10&tags=电影,"+type[which]+"&start=0";
        new Searchjson(url,mRecyclerView,mAdapter,handler).start();
    }
}
