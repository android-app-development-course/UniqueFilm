package com.example.lyz.uniquefilm.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lyz.uniquefilm.CitychooseActivity;
import com.example.lyz.uniquefilm.R;

import java.util.ArrayList;

import List.DemoAdapter;
import List.ImageUtil;
import List.SpacesItemDecoration;
import Refresh.CircleRefreshLayout;

/**
 * Created by lyz on 17-11-19.
 */

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private DemoAdapter adapter;
    private CircleRefreshLayout mRefreshLayout;
    private Button btnchosecity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.tab_home,container,false);
        mRefreshLayout=(CircleRefreshLayout)v.findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView)v.findViewById(R.id.recylerview);
        btnchosecity=(Button)v.findViewById(R.id.btncity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration=new SpacesItemDecoration(32);
        recyclerView.setAdapter(adapter = new DemoAdapter());
        adapter.replaceAll(getData());

        btnchosecity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CitychooseActivity.class);
                startActivity(intent);
            }
        });

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

    public ArrayList<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        for (String url : ImageUtil.imageUrls) {
            list.add(url);
        }
        return list;
    }


}
