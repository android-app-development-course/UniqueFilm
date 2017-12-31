package com.example.lyz.uniquefilm.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lyz.uniquefilm.Adapter.KwSearchRvAdapter;
import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.FilmDetailActivity;
import com.example.lyz.uniquefilm.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by lyz on 17-12-28.
 */

public class SearchFragment extends Fragment {

    private EditText etsearch;
    private Button btngo;
    private RecyclerView mRecyclerView;
    private KwSearchRvAdapter adapter;
    private Handler handler=new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.tab_search,container,false);
        btngo=(Button)v.findViewById(R.id.btn_search);
        etsearch=(EditText)v.findViewById(R.id.et_search);
        mRecyclerView=(RecyclerView)v.findViewById(R.id.rv_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new KwSearchRvAdapter(getActivity());


        adapter.setOnItemClickListener(new KwSearchRvAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                movies info=adapter.getDate(position);
                Log.i("info",Integer.toString(position)+info.getMoviename());
                Intent intent=new Intent(getActivity(),FilmDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id",info.getMovieid());
                bundle.putString("title",info.getMoviename());
                bundle.putString("rating",Double.toString(info.getRate()));
                bundle.putString("genres",info.getCategory());
                bundle.putString("runtime",info.getLength());
                bundle.putString("language",info.getLanguage());
                bundle.putString("poster",info.getCover());
                bundle.putString("film_location",info.getDistrict());
                bundle.putString("directors",info.getDirectors());
                //bundle.putString("ratingcount",Integer.toString(info.getRating_count()));
                bundle.putString("actors",info.getActors());
                bundle.putString("plotsimple",info.getDescription());
                bundle.putString("releasedate",info.getShowtime());;
                bundle.putString("alsoknowas",info.getOthername());
                intent.putExtra("info",bundle);
                startActivity(intent);
            }
        });

        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=etsearch.getText().toString();
                BmobQuery<movies> bmobQuery=new BmobQuery<movies>();
                bmobQuery.addWhereEqualTo("moviename", content);
                bmobQuery.findObjects(new FindListener<movies>() {
                    @Override
                    public void done(List<movies> list, BmobException e) {
                        if(e==null){
                            Log.i("listlength",Integer.toString(list.size()));
                            adapter.setData(list);
                            mRecyclerView.setAdapter(adapter);
                        }
                        else{
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        return v;
    }
}
