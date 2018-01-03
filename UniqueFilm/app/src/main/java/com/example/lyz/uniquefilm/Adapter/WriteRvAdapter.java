package com.example.lyz.uniquefilm.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lyz.uniquefilm.AddMovieintroActivity;
import com.example.lyz.uniquefilm.Analysis.Httpimage;
import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lyz on 18-1-2.
 */

public class WriteRvAdapter extends RecyclerView.Adapter<WriteRvAdapter.WriteRvHolder> {

    private List<movies> list;
    private Activity mContext;
    private LayoutInflater mLayoutInflater;
    private HashMap<Integer,String> map=new HashMap<Integer, String>();

    public WriteRvAdapter(Activity context){
        this.mContext=context;
        mLayoutInflater= LayoutInflater.from(mContext);
    }

    public void setData(List<movies> list){
        Log.i("info",list.get(0).getMoviename());
        this.list=list;
    }

    public movies getDate(int position){
        movies info=list.get(position);
        return info;
    }

    public HashMap<Integer,String> getMap(){
        return map;
    }

    @Override
    public WriteRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mLayoutInflater.inflate(R.layout.writing_add_movie,parent,false);
        WriteRvHolder wh=new WriteRvHolder(view);
        return wh;
    }

    @Override
    public void onBindViewHolder(final WriteRvHolder holder, final int position) {
        movies result=list.get(position);
        Log.i("result",result.getMoviename());
        holder.title.setText(result.getMoviename());
        new Httpimage(result.getCover(),holder.cover).execute();
        holder.rate.setText(Double.toString(result.getRate()));

        holder.intro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content=holder.intro.getText().toString();
                //if(map.get(position)==null)
                map.put(position,content);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public class WriteRvHolder extends RecyclerView.ViewHolder{

        private ImageView cover;
        private TextView title;
        private TextView rate;
        private EditText intro;

        public WriteRvHolder(View v){
            super(v);

            cover=(ImageView)v.findViewById(R.id.iv_cover);
            title=(TextView)v.findViewById(R.id.tv_title);
            rate=(TextView)v.findViewById(R.id.tv_rate);
            intro=(EditText)v.findViewById(R.id.et_intro);


        }
    }


}
