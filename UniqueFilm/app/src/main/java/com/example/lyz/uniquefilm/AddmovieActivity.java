package com.example.lyz.uniquefilm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lyz.uniquefilm.Adapter.KwSearchRvAdapter;
import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.Database.userinformation;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class AddmovieActivity extends AppCompatActivity {

    private SearchView svsearch;
    private RecyclerView mRecyclerView;
    private KwSearchRvAdapter mAdapter;
    MyTextWathcer myTextWathcer;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmovie);
        svsearch = (SearchView) findViewById(R.id.sv_search);
        message=(TextView)findViewById(R.id.tv_message);
        svsearch.setIconifiedByDefault(false);
        svsearch.setSubmitButtonEnabled(true);
        svsearch.setQueryHint("请输入要查找的电影名");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_searchresult);
        mAdapter=new KwSearchRvAdapter(AddmovieActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myTextWathcer = new MyTextWathcer();

        svsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String content=query;
                new Getmovies(content).start();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String content=newText;
                new Getmovies(content).start();
                return false;
            }
        });
        //svsearch.addTextChangedListener(myTextWathcer);

        mAdapter.setOnItemClickListener(new KwSearchRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                movies movie=mAdapter.getDate(position);
                Log.i("movie:",movie.getMoviename());
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("movieid",movie.getObjectId());
                bundle.putString("moviename",movie.getMoviename());
                bundle.putString("rate",Double.toString(movie.getRate()));
                intent.putExtras(bundle);
                setResult(1,intent);
                finish();
            }
        });
    }



    private class MyTextWathcer implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String content=s.toString();
            new Getmovies(content).start();
        }
    }



    public class Getmovies extends Thread {

        private String mtitle;

        public Getmovies(String title){
            this.mtitle=title;
        }

        @Override
        public void run() {
            BmobQuery<movies> query=new BmobQuery<movies>();
            query.addWhereEqualTo("moviename",mtitle);
            query.findObjects(new FindListener<movies>() {
                @Override
                public void done(List<movies> list, BmobException e) {
                    if(e==null){
                        Log.i("set","here");
                        if(list.size()>0){
                            message.setText("没有更多搜索结果");
                        }
                        else{
                            message.setText("没有找到匹配项");
                        }
                        mAdapter.setData(list);
                        mRecyclerView.setAdapter(mAdapter);
                        //Log.i("set","here");
                    }
                    else{
                        e.printStackTrace();
                        message.setText("没有找到匹配项");
                    }
                }
            });
        }
    }
}
