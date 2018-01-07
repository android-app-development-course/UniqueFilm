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
 * Created by lyz on 18-1-5.
 */

public class NpmRvAdapter extends RecyclerView.Adapter<NpmRvAdapter.NpmRvHolder>
        implements View.OnClickListener {

    public List<newmovies> list;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private OnItemClickListener mOnItemClickListener=null;

    public NpmRvAdapter(Context context){
        this.mContext=context;
        mLayoutInflater= LayoutInflater.from(mContext);
    }

    public void setData(List<newmovies> list){
        Log.i("info",list.get(0).getMoviename());
        this.list=list;
    }


    public newmovies getDate(int position){
        newmovies info=list.get(position);
        return info;
    }

    @Override
    public NpmRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mLayoutInflater.inflate(R.layout.item_card_search,parent,false);
        NpmRvHolder kh=new NpmRvHolder(view);
        view.setOnClickListener(this);
        //return new KwSRvHolder(mLayoutInflater.inflate(R.layout.item_card_search,parent,false));
        return kh;
    }

    @Override
    public void onBindViewHolder(NpmRvHolder holder, int position) {

        holder.itemView.setTag(position);
        newmovies result=list.get(position);
        Log.i("result",result.getMoviename());
        holder.title.setText(result.getMoviename());
        new Httpimage(result.getCover(),holder.cover).execute();

        holder.director.setText(result.getDirectors());
        holder.rate.setText(Double.toString(result.getRate()));
    }

    @Override
    public void onClick(View v) {

        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }

    public class NpmRvHolder extends RecyclerView.ViewHolder{

        private ImageView cover;
        private TextView title;
        private TextView rate;
        private TextView director;

        public NpmRvHolder(View view){
            super(view);
            cover=(ImageView)view.findViewById(R.id.iv_cover);
            title=(TextView)view.findViewById(R.id.tv_moviename);
            rate=(TextView)view.findViewById(R.id.tv_rate);
            director=(TextView)view.findViewById(R.id.tv_director);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
