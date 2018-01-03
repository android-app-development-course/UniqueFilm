package com.example.lyz.uniquefilm.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lyz.uniquefilm.Information.CinemaInfo;
import com.example.lyz.uniquefilm.R;

import java.util.ArrayList;

/**
 * Created by lyz on 18-1-3.
 */

public class CRvAdapter extends RecyclerView.Adapter<CRvAdapter.CRvHolder> {

    private ArrayList<CinemaInfo> list;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public CRvAdapter(Context context){
        mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
    }

    public void setData(ArrayList<CinemaInfo> list){
        this.list=list;
    }

    @Override
    public CRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CRvHolder(mLayoutInflater.inflate(R.layout.item_cinema, parent, false));
    }

    @Override
    public void onBindViewHolder(CRvHolder holder, int position) {
        CinemaInfo info=list.get(position);
        holder.cinema.setText(info.getCinemaName());
        holder.address.setText(info.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class CRvHolder extends RecyclerView.ViewHolder{

        private TextView cinema;
        private TextView address;

        public CRvHolder(View view){
            super(view);
            cinema=(TextView)view.findViewById(R.id.tv_cinema);
            address=(TextView)view.findViewById(R.id.tv_address);

        }
    }
}
