package com.example.lyz.uniquefilm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lyz.uniquefilm.Database.moviecomment;
import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.Database.userinformation;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.b.I;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

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
                ibfirst.setBackgroundResource(R.mipmap.starfull);
                ibsecond.setBackgroundResource(R.mipmap.starfull);
            }
        });

        ibthird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=6;
                ibfirst.setBackgroundResource(R.mipmap.starfull);
                ibsecond.setBackgroundResource(R.mipmap.starfull);
                ibthird.setBackgroundResource(R.mipmap.starfull);
            }
        });

        ibforth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=8;
                ibfirst.setBackgroundResource(R.mipmap.starfull);
                ibsecond.setBackgroundResource(R.mipmap.starfull);
                ibthird.setBackgroundResource(R.mipmap.starfull);
                ibforth.setBackgroundResource(R.mipmap.starfull);
            }
        });

        ibfifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=10;
                ibfirst.setBackgroundResource(R.mipmap.starfull);
                ibsecond.setBackgroundResource(R.mipmap.starfull);
                ibthird.setBackgroundResource(R.mipmap.starfull);
                ibforth.setBackgroundResource(R.mipmap.starfull);
                ibfifth.setBackgroundResource(R.mipmap.starfull);
            }
        });

        btnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences myPreference=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
                final String username=myPreference.getString("username","");
                final BmobQuery<userinformation> query=new BmobQuery<userinformation>();
                query.addWhereEqualTo("username",username);
                query.findObjects(new FindListener<userinformation>() {
                    @Override
                    public void done(List<userinformation> list, BmobException e) {
                        if(e==null){

                            if(id>0){
                                userinformation user=new userinformation();
                                list.get(0).setUserscored(Integer.toString(id)+" "+Integer.toString(score)+"|");
                                user.update(list.get(0).getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            Toast.makeText(ScoreActivity.this,"评分成功！",Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(ScoreActivity.this,"评分失败！",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                new Setmovierate(id).start();
                                if(!comment.equals(""))
                                    new Addmoviecomment(id,username,comment).start();
                                /*BmobQuery<movies> query1=new BmobQuery<movies>();
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
                                });*/
                            }

                        }
                        else {
                            //Toast.makeText(ScoreActivity.this,"评分失败！",Toast.LENGTH_SHORT).show();
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

    public class Setmovierate extends Thread {

        private int movieid;


        public Setmovierate(int movieid){
            this.movieid=movieid;
        }

        @Override
        public void run() {
            //user.setUsercollection( user.getUsercollection()+Integer.toString(movieid) + ",");
            BmobQuery<movies> query=new BmobQuery<movies>();
            query.addWhereEqualTo("movieid",movieid);
            query.findObjects(new FindListener<movies>() {
                @Override
                public void done(List<movies> list, BmobException e) {
                    if(e==null){
                        Log.i("result", "获取成功");
                        //setcollection(list.get(0));
                        movies movies=new movies();
                        movies.setUniquerate(score);
                        movies.setRatecount(1);
                        movies.update(list.get(0).getObjectId(),new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(ScoreActivity.this,"评分成功！",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(ScoreActivity.this,"评分成功！",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Log.i("setdate","here");
                    }
                    else{
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public class Addmoviecomment extends Thread {

        private int movieid;
        private String username;
        private String mcomment;


        public Addmoviecomment(int movieid,String username,String comment){
            this.movieid=movieid;
            this.username=username;
            this.mcomment=comment;
        }

        @Override
        public void run() {
            //user.setUsercollection( user.getUsercollection()+Integer.toString(movieid) + ",");
            moviecomment comment=new moviecomment();
            comment.setMovieid(movieid);
            comment.setUsername(username);
            comment.setComment(mcomment);
            comment.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null){
                        Toast.makeText(ScoreActivity.this,"评论发表成功！",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ScoreActivity.this,"评论发表失败！",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
