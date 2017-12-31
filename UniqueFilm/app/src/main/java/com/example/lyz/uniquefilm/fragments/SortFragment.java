package com.example.lyz.uniquefilm.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lyz.uniquefilm.Adapter.UFRvAdapter;
import com.example.lyz.uniquefilm.Analysis.Boxjson;
import com.example.lyz.uniquefilm.Information.BoxInfo;
import com.example.lyz.uniquefilm.R;
import com.example.lyz.uniquefilm.UsercolActivity;

import java.util.ArrayList;

import List.ViewHolderAdapter;
import Refresh.CircleRefreshLayout;

/**
 * Created by lyz on 17-11-19.
 */

public class SortFragment extends Fragment {


    private String url="https://box.maoyan.com/promovie/api/box/second.json";
    private RecyclerView mRecyclerView;
    private ArrayList<BoxInfo> box;
    private UFRvAdapter adapter;
    private Handler handler=new Handler();

    private CircleRefreshLayout mRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.tab_sorting,container,false);
        mRefreshLayout=(CircleRefreshLayout)v.findViewById(R.id.refresh_layout);
        mRecyclerView=(RecyclerView)v.findViewById(R.id.rv_paihang);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new UFRvAdapter(getActivity());
        Log.i("adapter","this");

        new Boxjson(url,mRecyclerView,adapter,handler).start();


        mRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {

            Handler handler=new Handler();
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    mRefreshLayout.finishRefreshing();
                    handler.postDelayed(this, 2000);
                }
            };

            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {
                handler.postDelayed(runnable, 2000);
            }
        });

        return v;
    }


}
