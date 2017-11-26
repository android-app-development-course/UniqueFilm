package com.example.lyz.uniquefilm.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lyz.uniquefilm.R;
import com.example.lyz.uniquefilm.UsercolActivity;

import List.ViewHolderAdapter;
import Refresh.CircleRefreshLayout;

/**
 * Created by lyz on 17-11-19.
 */

public class SortFragment extends Fragment {



    private ListView mlvcol;
    private String[] names={"1","2","3","4","5","6","7","8","9","10","11","12","13"};

    private CircleRefreshLayout mRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.tab_sorting,container,false);
        mlvcol=(ListView)v.findViewById(R.id.lvmovie);
        mRefreshLayout=(CircleRefreshLayout)v.findViewById(R.id.refresh_layout);
        ViewHolderAdapter mAdapter=new ViewHolderAdapter(getActivity(),names);
        mlvcol.setAdapter(mAdapter);

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
