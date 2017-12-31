package com.example.lyz.uniquefilm.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Handler;

import com.example.lyz.uniquefilm.Information.BoxInfo;
import com.example.lyz.uniquefilm.R;

import java.util.ArrayList;

/**
 * Created by lyz on 17-12-28.
 */

public class UFRvAdapter extends RecyclerView.Adapter<UFRvAdapter.UFRvHolder>{

    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<String> mPiaofang=new ArrayList<>();
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ArrayList<BoxInfo> list;
    private Handler handler=new Handler();

    public UFRvAdapter(Context context){
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
    }

    public void setData(ArrayList<BoxInfo> list){
        this.list=list;
    }

    @Override
    public UFRvAdapter.UFRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UFRvHolder(mLayoutInflater.inflate(R.layout.item_card_paihang, parent, false));
    }

    @Override
    public void onBindViewHolder(UFRvAdapter.UFRvHolder holder, int position) {

        BoxInfo box=list.get(position);
        holder.mtvnumber.setText(Integer.toString(position));
        holder.mtvfilmname.setText((String)box.getMovieName());
        holder.mtvfilmpiaofang.setText(Double.toString(box.getBoxInfo()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class UFRvHolder extends RecyclerView.ViewHolder{

        private TextView mtvnumber;
        private TextView mtvfilmname;
        private TextView mtvfilmpiaofang;

        public UFRvHolder(View view){
            super(view);
            mtvnumber=(TextView)view.findViewById(R.id.tv_number);
            mtvfilmname=(TextView)view.findViewById(R.id.tv_filmname);
            mtvfilmpiaofang=(TextView)view.findViewById(R.id.tv_filmpiaofang);
        }
    }
}
