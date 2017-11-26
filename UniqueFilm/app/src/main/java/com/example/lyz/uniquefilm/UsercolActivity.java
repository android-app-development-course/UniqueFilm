package com.example.lyz.uniquefilm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.lyz.uniquefilm.fragments.CustomerFragment;

import java.util.List;

import List.ViewHolderAdapter;
import Refresh.CircleRefreshLayout;

public class UsercolActivity extends AppCompatActivity {

    private ListView mlvcol;
    private String[] names={"1","2","3","4","5","6","7","8","9","10","11","12","13"};

    private ImageButton ibret;

    private CircleRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercol);
        ibret=(ImageButton)findViewById(R.id.ib_col_ret);
        mlvcol=(ListView)findViewById(R.id.lvmovie);
        mRefreshLayout=(CircleRefreshLayout)findViewById(R.id.refresh_layout);
        ViewHolderAdapter mAdapter=new ViewHolderAdapter(UsercolActivity.this,names);
        mlvcol.setAdapter(mAdapter);

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
}
