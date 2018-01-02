package com.example.lyz.uniquefilm.Adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lyz.uniquefilm.Analysis.Httpimage;
import com.example.lyz.uniquefilm.Information.SearchInfo;
import com.example.lyz.uniquefilm.R;
import com.example.lyz.uniquefilm.Refresh.ProgressWheel;

import java.util.ArrayList;

/**
 * Created by lyz on 18-1-1.
 */

public class SearchRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
                        implements View.OnClickListener{

    public ArrayList<SearchInfo> list;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private Handler handler;
    private OnItemClickListener mOnItemClickListener=null;

    public static final int PULLUP_LOAD_MORE=0;
    public static final int LOADING_MORE=1;
    private int load_more_status=0;
    private static final int TYPE_FOOTER=1;
    private static final int TYPE_NORMAL=0;

    private boolean loading=false;

    public SearchRvAdapter(Context context){
        this.mContext=context;
        this.mLayoutInflater=LayoutInflater.from(context);
    }

    public void setData(ArrayList<SearchInfo> list){

        this.list=list;
    }

    public SearchInfo getData(int position){
        return list.get(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return new SRvHolder(mLayoutInflater.inflate(R.layout.item_card_search, parent, false));

        if(viewType==TYPE_NORMAL){
            View view=mLayoutInflater.inflate(R.layout.item_card_search,parent,false);
            SRvHolder holder=new SRvHolder(view);
            view.setOnClickListener(this);
            return holder;
        }
        else if(viewType==TYPE_FOOTER){
            View footview=mLayoutInflater.inflate(R.layout.uprefresh,parent,false);
            FooterViewHolder holder=new FooterViewHolder(footview);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof SRvHolder){
            holder.itemView.setTag(position);
            //Log.i("position",Integer.toString(position));
            SearchInfo result=list.get(position);
            //Log.i("list",list.get(position).getTitle());
            ((SRvHolder)holder).title.setText((String)result.getTitle());

            //holder.cover.setImageBitmap(getbitmap(result.getCover()));
            new Httpimage(result.getCover(),((SRvHolder)holder).cover).execute();

            String []director=result.getDirectors();
            String dir="";
            for(int i=0;i<director.length;i++){
                dir=director[i]+" ";
            }
            ((SRvHolder)holder).director.setText("导演："+dir);
            ((SRvHolder)holder).rate.setText("评分："+Double.toString(result.getRate()));
        }
        else if(holder instanceof FooterViewHolder){
            FooterViewHolder footholder=(FooterViewHolder)holder;
            footholder.loadmore.setText("正在加载");
            footholder.loadmore.setSpinSpeed(5);
            footholder.loadmore.startSpinning();
            return;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public int getItemViewType(int position) {
        //SearchInfo info=list.get(position);

        if(position==list.size()-1){
            return TYPE_FOOTER;
        }
        else{
            return TYPE_NORMAL;
        }
    }

    public class SRvHolder extends RecyclerView.ViewHolder{

        private ImageView cover;
        private TextView title;
        private TextView rate;
        private TextView director;


        public SRvHolder(View view){
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

    public class FooterViewHolder extends RecyclerView.ViewHolder{

        private ProgressWheel loadmore;

        public FooterViewHolder(View view){
            super(view);
            loadmore=(ProgressWheel)view.findViewById(R.id.rcv_load_more);
        }
    }
}
