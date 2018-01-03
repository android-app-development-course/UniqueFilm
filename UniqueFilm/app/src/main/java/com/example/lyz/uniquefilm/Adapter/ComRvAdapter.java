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
import com.example.lyz.uniquefilm.Database.commendmovies;
import com.example.lyz.uniquefilm.R;

import java.util.List;

/**
 * Created by lyz on 18-1-3.
 */

public class ComRvAdapter extends RecyclerView.Adapter<ComRvAdapter.ComRvHolder>
                          implements View.OnClickListener{

    public List<commendmovies> list;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private OnItemClickListener mOnItemClickListener=null;

    public ComRvAdapter(Context context){
        this.mContext=context;
        mLayoutInflater= LayoutInflater.from(mContext);
    }

    public void setData(List<commendmovies> list){
        Log.i("info",list.get(0).getTitle());
        this.list=list;
    }

    public commendmovies getDate(int position){
        commendmovies info=list.get(position);
        return info;
    }

    @Override
    public ComRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mLayoutInflater.inflate(R.layout.item_commend,parent,false);
        ComRvHolder ch=new ComRvHolder(view);
        view.setOnClickListener(this);
        return ch;
    }

    @Override
    public void onBindViewHolder(ComRvHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.title.setText(list.get(position).getTitle());
        holder.content.setText(list.get(0).getContent());
        String cover[]=list.get(position).getCover().split(" ");
        if(cover[0]!=null){
            new Httpimage(cover[0],holder.cover1).execute();
        }
        if(cover[1]!=null){
            new Httpimage(cover[1],holder.cover2).execute();
        }
        if(cover[2]!=null){
            new Httpimage(cover[2],holder.cover3).execute();
        }
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

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ComRvHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView content;
        private ImageView cover1;
        private ImageView cover2;
        private ImageView cover3;

        public ComRvHolder(View v){
            super(v);
            title=(TextView)v.findViewById(R.id.tv_title);
            content=(TextView)v.findViewById(R.id.tv_content);
            cover1=(ImageView)v.findViewById(R.id.iv_cover1);
            cover2=(ImageView)v.findViewById(R.id.iv_cover2);
            cover3=(ImageView)v.findViewById(R.id.iv_cover3);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
