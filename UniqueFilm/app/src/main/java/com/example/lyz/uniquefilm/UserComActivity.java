package com.example.lyz.uniquefilm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lyz.uniquefilm.Adapter.ComRvAdapter;
import com.example.lyz.uniquefilm.Database.commendmovies;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class UserComActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ComRvAdapter mAdapter;
    private List<commendmovies> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_com);
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_commend);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new ComRvAdapter(this);
        final SharedPreferences myPreference=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        String username=myPreference.getString("username","");
        BmobQuery<commendmovies> query=new BmobQuery<commendmovies>();
        query.addWhereEqualTo("authorname",username);
        query.findObjects(new FindListener<commendmovies>() {
            @Override
            public void done(List<commendmovies> list, BmobException e) {
                if(e==null){
                    mlist=list;
                    mAdapter.setData(list);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else{
                    e.printStackTrace();
                }
            }
        });

        mAdapter.setOnItemClickListener(new ComRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(UserComActivity.this,ShowrecomActivity.class);
                commendmovies item=mlist.get(position);
                Bundle bundle=new Bundle();
                bundle.putString("title",item.getTitle());
                bundle.putString("content",item.getContent());
                bundle.putString("movies",item.getMovies());
                bundle.putString("authorname",item.getAuthorname());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
