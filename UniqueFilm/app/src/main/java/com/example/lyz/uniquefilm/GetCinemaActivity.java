package com.example.lyz.uniquefilm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lyz.uniquefilm.Adapter.CRvAdapter;
import com.example.lyz.uniquefilm.Information.CinemaInfo;

import java.util.ArrayList;

public class GetCinemaActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CRvAdapter mAdapter;
    private ArrayList<CinemaInfo> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_cinema);
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_cinema);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new CRvAdapter(this);

    }
}
