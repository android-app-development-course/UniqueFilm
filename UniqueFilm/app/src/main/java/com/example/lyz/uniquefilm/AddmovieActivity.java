package com.example.lyz.uniquefilm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.lyz.uniquefilm.Adapter.KwSearchRvAdapter;
import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.Database.userinformation;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class AddmovieActivity extends AppCompatActivity {

    private EditText etsearch;
    private RecyclerView mRecyclerView;
    private KwSearchRvAdapter mAdapter;
    MyTextWathcer myTextWathcer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmovie);
        etsearch = (EditText) findViewById(R.id.et_search);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_searchresult);
        mAdapter=new KwSearchRvAdapter(AddmovieActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myTextWathcer = new MyTextWathcer();
        etsearch.addTextChangedListener(myTextWathcer);

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

    private void setMyText() {
        etsearch.setText("11111new");
        etsearch.addTextChangedListener(myTextWathcer);
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
                        mAdapter.setData(list);
                        mRecyclerView.setAdapter(mAdapter);
                        //Log.i("set","here");
                    }
                    else{
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
