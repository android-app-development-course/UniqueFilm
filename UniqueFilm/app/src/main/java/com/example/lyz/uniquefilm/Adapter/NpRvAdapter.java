package com.example.lyz.uniquefilm.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lyz.uniquefilm.Analysis.Httpimage;
import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.Database.newmovies;
import com.example.lyz.uniquefilm.R;

import java.util.List;

/**
 * Created by lyz on 18-1-4.
 */

public class NpRvAdapter extends RecyclerView.Adapter<NpRvAdapter.NpRvHolder>
                    implements View.OnClickListener{

    public List<newmovies> list;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private OnItemClickListener mOnItemClickListener=null;

    public NpRvAdapter(Context context){
        this.mContext=context;
        mLayoutInflater= LayoutInflater.from(mContext);
    }

    public void setData(List<newmovies> list){
        Log.i("info",list.get(0).getMoviename());
        this.list=list;
    }

    @Override
    public NpRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mLayoutInflater.inflate(R.layout.item_now_playing,parent,false);
        NpRvHolder nh=new NpRvHolder(view);
        view.setOnClickListener(this);
        return nh;
    }

    @Override
    public void onBindViewHolder(NpRvHolder holder, int position) {
        holder.itemView.setTag(position);
        newmovies result=list.get(position);
        holder.title.setText(result.getMoviename());
        holder.rate.setText(Double.toString(result.getRate()));
        new Httpimage(result.getCover(),holder.cover).execute();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public static interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public class NpRvHolder extends RecyclerView.ViewHolder{

        private ImageView cover;
        private TextView title;
        private TextView rate;

        public NpRvHolder(View v){
            super(v);
            cover=(ImageView)v.findViewById(R.id.iv_cover);
            title=(TextView)v.findViewById(R.id.tv_title);
            rate=(TextView)v.findViewById(R.id.tv_rate);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


    }
}
