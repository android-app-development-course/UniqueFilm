package com.example.lyz.uniquefilm.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.lyz.uniquefilm.Adapter.WriteRvAdapter;
import com.example.lyz.uniquefilm.AddmovieActivity;
import com.example.lyz.uniquefilm.Database.commendmovies;
import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.R;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by douhua on 2018/1/1.
 */

public class WritingFragment extends Fragment {
    //选择添加图片、连接 的按钮
    com.nightonke.boommenu.BoomMenuButton add_btn;

    //保存并发表按钮
    Button SaveSent;
    //标题和正文的edittext
    EditText WritingTitle;
    EditText WritingContent;
    private RecyclerView mRecyclerView;
    private WriteRvAdapter mAdapter;
    private HashMap<Integer,String> map;
    private List<movies> movieslist=new ArrayList<movies>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View viewWriting = inflater.inflate(R.layout.tab_writing, container,false);

        add_btn=(com.nightonke.boommenu.BoomMenuButton)viewWriting.findViewById(R.id.bmb);
        SaveSent=(Button) viewWriting.findViewById(R.id.writingsent_btn);
        WritingTitle=(EditText)viewWriting.findViewById(R.id.writingtitle_et);
        WritingContent=(EditText)viewWriting.findViewById(R.id.writingcontent_et);
        mRecyclerView=(RecyclerView)viewWriting.findViewById(R.id.rv_addmovie);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter=new WriteRvAdapter(getActivity());


        //add_btn按钮相关
        for(int i=0;i<add_btn.getPiecePlaceEnum().pieceNumber();i++)
        {


            SimpleCircleButton.Builder builder=new SimpleCircleButton.Builder();
            if(i==0)
            {
               builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        //点击添加图片按钮之后的事件
                    }
                });
                builder.normalImageRes(R.mipmap.img);
                builder.normalColor(Color.parseColor("#CD96CD"));

            }
            if(i==1)
            {
                 builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                            //点击添加链接按钮之后的事件
                        Intent intent=new Intent(getActivity(), AddmovieActivity.class);
                        startActivityForResult(intent,1);
                    }
                });
                builder.normalImageRes(R.mipmap.links);
                builder.normalColor(Color.parseColor("#EE9A49"));

            }
            builder.rotateImage(false);
            builder.imagePadding(new Rect(20,20,20,20));
            add_btn.addBuilder(builder);
        }

        //获取标题和内容

        String title=WritingTitle.getText().toString();
        String content=WritingContent.getText().toString();


        //发表按钮的点击事件：将title和content存数据库
        SaveSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //存数据库操作
                String title=WritingTitle.getText().toString();
                String content=WritingContent.getText().toString();
                String movieandintro="";
                String cover="";
                map=new HashMap<Integer, String>();
                map=mAdapter.getMap();

                for(int i=0;i<movieslist.size();i++){
                    if(map.get(i)!=null)
                        movieandintro=movieandintro+movieslist.get(i).getMoviename()+":"+map.get(i)+"|";
                    else
                        movieandintro=movieandintro+movieslist.get(i).getMoviename()+"|";
                    if(i<3){
                        cover=cover+movieslist.get(i).getCover()+" ";
                    }
                }
                SharedPreferences myPreference=getActivity().getSharedPreferences(
                        "myPreference", Context.MODE_PRIVATE);
                String username=myPreference.getString("username","");
                BmobQuery<commendmovies> query=new BmobQuery<commendmovies>();
                commendmovies commend=new commendmovies();
                commend.setId(0);
                commend.setTitle(title);
                commend.setContent(content);
                commend.setCover(cover);
                commend.setMovies(movieandintro);
                commend.setAuthorname(username);
                commend.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(getActivity(),"影单发表成功！",Toast.LENGTH_SHORT).show();
                            updatefragment();
                        }
                        else{
                            Toast.makeText(getActivity(),"发表失败！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        return viewWriting;

    }

    void updatefragment(){
        WritingTitle.setText("");
        WritingContent.setText("");
        movieslist.removeAll(movieslist);
        mAdapter.setData(movieslist);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            Log.i("requestCode",Integer.toString(requestCode));
            Log.i("resultCode",Integer.toString(resultCode));
            if(requestCode==1) {
                if (resultCode == 1) {
                    Bundle bundle = data.getExtras();

                    String moviename = bundle.getString("moviename");
                    BmobQuery<movies> query = new BmobQuery<movies>();
                    query.addWhereEqualTo("moviename", moviename);
                    Log.i("moviename", moviename);
                    query.findObjects(new FindListener<movies>() {
                        @Override
                        public void done(List<movies> list, BmobException e) {
                            if (e == null) {
                                movieslist.add(list.get(0));
                                mAdapter.setData(movieslist);
                                mRecyclerView.setAdapter(mAdapter);
                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }
}
