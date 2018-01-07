package com.example.lyz.uniquefilm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lyz.uniquefilm.Adapter.CRvAdapter;
import com.example.lyz.uniquefilm.Analysis.Cinemajson;
import com.example.lyz.uniquefilm.Information.CinemaInfo;

import java.util.ArrayList;

public class GetCinemaActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CRvAdapter mAdapter;
    private ArrayList<CinemaInfo> list;
    private String url;
    private Handler handler=new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_cinema);
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_cinema);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new CRvAdapter(this);
        SharedPreferences myPreference=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        String city=myPreference.getString("city","北京");
        Intent intent=getIntent();
        String moviename=intent.getStringExtra("moviename");
        url="http://api.avatardata.cn/Movies/Query?key=cb9a59de14a749b28ad385b6fd30debb&wd=芳华"+"&location="+city;
        new Cinemajson(url,mRecyclerView,mAdapter,handler).start();
    }
}
