package com.example.lyz.uniquefilm.Adapter;

import android.app.Activity;
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

import com.example.lyz.uniquefilm.Analysis.Httpimage;
import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lyz on 18-1-3.
 */

public class SComRvAdapter extends RecyclerView.Adapter<SComRvAdapter.SComRvHolder>{

    private List<movies> list;
    private List<String> introlist;
    private Activity mContext;
    private LayoutInflater mLayoutInflater;
    private HashMap<Integer,String> map=new HashMap<Integer, String>();

    public SComRvAdapter(Activity context){
        this.mContext=context;
        mLayoutInflater= LayoutInflater.from(mContext);
    }

    public void setData(List<movies> list,List<String> introlist){
        Log.i("info",list.get(0).getMoviename());
        this.list=list;
        this.introlist=introlist;
    }

    public movies getDate(int position){
        movies info=list.get(position);
        return info;
    }

    public HashMap<Integer,String> getMap(){
        return map;
    }


    @Override
    public SComRvAdapter.SComRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mLayoutInflater.inflate(R.layout.showrecom,parent,false);
        SComRvAdapter.SComRvHolder wh=new SComRvAdapter.SComRvHolder(view);
        return wh;
    }

    @Override
    public void onBindViewHolder(final SComRvAdapter.SComRvHolder holder, final int position) {
        movies result=list.get(position);
        Log.i("result",result.getMoviename());
        holder.title.setText(result.getMoviename());
        new Httpimage(result.getCover(),holder.cover).execute();
        holder.rate.setText(Double.toString(result.getRate()));
        holder.intro.setText(introlist.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public class SComRvHolder extends RecyclerView.ViewHolder{

        private ImageView cover;
        private TextView title;
        private TextView rate;
        private TextView intro;

        public SComRvHolder(View v){
            super(v);

            cover=(ImageView)v.findViewById(R.id.iv_cover);
            title=(TextView)v.findViewById(R.id.tv_title);
            rate=(TextView)v.findViewById(R.id.tv_rate);
            intro=(TextView) v.findViewById(R.id.tv_intro);


        }
    }

}
