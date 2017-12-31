package com.example.lyz.uniquefilm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.Database.userinformation;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.b.I;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ScoreActivity extends AppCompatActivity {

    private ImageButton ibfirst;
    private ImageButton ibsecond;
    private ImageButton ibthird;
    private ImageButton ibforth;
    private ImageButton ibfifth;
    private EditText etcomment;
    private Button btnsure;
    private int score;
    private String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent intent=getIntent();
        final int id=intent.getIntExtra("id",-1);
        init();

        ibfirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=2;
                ibfirst.setBackgroundResource(R.mipmap.starfull);
            }
        });

        ibsecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=4;
                ibsecond.setBackgroundResource(R.mipmap.starfull);
            }
        });

        ibthird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=6;
                ibthird.setBackgroundResource(R.mipmap.starfull);
            }
        });

        ibforth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=8;
                ibforth.setBackgroundResource(R.mipmap.starfull);
            }
        });

        ibfifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=10;
                ibfifth.setBackgroundResource(R.mipmap.starfull);
            }
        });

        btnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences myPreference=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
                String username=myPreference.getString("username","");
                final BmobQuery<userinformation> query=new BmobQuery<userinformation>();
                query.addWhereEqualTo("username",username);
                query.findObjects(new FindListener<userinformation>() {
                    @Override
                    public void done(List<userinformation> list, BmobException e) {
                        if(e==null){

                            if(id>0){
                                list.get(0).setUserscored(Integer.toString(id)+",");
                                BmobQuery<movies> query1=new BmobQuery<movies>();
                                query1.addWhereEqualTo("movieid",id);
                                query1.findObjects(new FindListener<movies>() {
                                    @Override
                                    public void done(List<movies> list, BmobException e) {
                                        if(e==null){
                                            if(list.size()>0){
                                                String comment=etcomment.getText().toString();
                                                if(!comment.equals("")){
                                                    list.get(0).setComment(comment+" "+Integer.toString(id));
                                                }

                                            }
                                        }
                                        else {
                                            Toast.makeText(ScoreActivity.this,"评论失败！",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                        }
                        else {
                            Toast.makeText(ScoreActivity.this,"评分失败！",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                });
            }
        });
    }

    private void init(){
        ibfirst=(ImageButton)findViewById(R.id.ib_first);
        ibsecond=(ImageButton)findViewById(R.id.ib_second);
        ibthird=(ImageButton)findViewById(R.id.ib_third);
        ibforth=(ImageButton)findViewById(R.id.ib_forth);
        ibfifth=(ImageButton)findViewById(R.id.ib_fifth);
        etcomment=(EditText)findViewById(R.id.etcomment);
        btnsure=(Button)findViewById(R.id.btnsure);
    }
}
