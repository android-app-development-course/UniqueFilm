package com.example.lyz.uniquefilm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lyz.uniquefilm.Analysis.Httpimage;
import com.example.lyz.uniquefilm.Database.userinformation;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class FilmDetailActivity extends AppCompatActivity {

    private ImageView iv_poster;
    private TextView tv_title;
    private TextView tv_genres;
    private TextView tv_language;
    private TextView tv_runtime;
    private TextView tv_director;
    private TextView tv_actor;
    private TextView tv_rate;
    private TextView tv_rate_count;
    private TextView tv_post;
    private TextView tv_release;
    private TextView tv_also;
    private TableRow trcover;
    private Button btncollect;
    private Button btnscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        init();
        Intent intent=getIntent();
        final Bundle bundle=intent.getBundleExtra("info");
        //Log.i("bundle",bundle.toString());
        final int movieid=bundle.getInt("id");
        Log.i("bundle",bundle.getString("poster"));
        new Httpimage(bundle.getString("poster"),(ImageView)findViewById(R.id.iv_poster),(TableRow)findViewById(R.id.tr_cover)).execute();
        tv_title.setText(bundle.getString("title"));
        tv_genres.setText(bundle.getString("genres"));
        tv_language.setText(bundle.getString("film_location")+"/"+bundle.getString("language"));
        tv_runtime.setText("片长："+bundle.getString("runtime"));
        tv_director.setText("导演："+bundle.getString("directors"));
        tv_actor.setText("演员："+bundle.getString("actors"));
        tv_release.setText("上映时间："+bundle.getString("releasedate"));
        tv_also.setText("又名："+bundle.getString("alsoknowas"));
        tv_post.setText(bundle.getString("plotsimple"));
        tv_rate.setText(bundle.getString("rating"));
        //tv_rate_count.setText(bundle.getString("ratingcount"));


        btncollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences myPreference=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
                boolean state=myPreference.getBoolean("userstate",false);
                if(state){
                    String username=myPreference.getString("username","");
                    BmobQuery<userinformation> query=new BmobQuery<userinformation>();
                    query.addWhereEqualTo("username",username);
                    query.findObjects(new FindListener<userinformation>() {
                        @Override
                        public void done(List<userinformation> list, BmobException e) {
                            if(e==null) {
                                userinformation user = list.get(0);
                                user.setUsercollection(list.get(0).getUsercollection() + Integer.toString(movieid) + ",");
                                user.update(Integer.toString(user.getUserid()), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(FilmDetailActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(FilmDetailActivity.this, "收藏失败！", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else {
                                Toast.makeText(FilmDetailActivity.this, "收藏失败！", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                else{
                    Intent intent=new Intent(FilmDetailActivity.this,SigninActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences myPreference=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
                boolean state=myPreference.getBoolean("userstate",false);
                if(state){
                    Intent intent=new Intent(FilmDetailActivity.this,ScoreActivity.class);
                    intent.putExtra("id",movieid);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(FilmDetailActivity.this,SigninActivity.class);
                    startActivity(intent);
                }
            }
        });
    }



    void init(){
        iv_poster=(ImageView)findViewById(R.id.iv_poster);
        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_genres=(TextView)findViewById(R.id.tv_yeargenres);
        tv_language=(TextView)findViewById(R.id.tv_countrylanguage);
        tv_runtime=(TextView)findViewById(R.id.tv_runtime);
        tv_director=(TextView)findViewById(R.id.tv_director);
        tv_actor=(TextView)findViewById(R.id.tv_actors);
        tv_rate=(TextView)findViewById(R.id.tv_rate);
        tv_rate_count=(TextView)findViewById(R.id.tv_rate_count);
        tv_post=(TextView)findViewById(R.id.tv_post);
        tv_release=(TextView)findViewById(R.id.tv_releasetime);
        tv_also=(TextView)findViewById(R.id.tv_alsoknown);
        trcover=(TableRow)findViewById(R.id.tr_cover);
        btncollect=(Button)findViewById(R.id.filmdetail_collect);
        btnscore=(Button)findViewById(R.id.filmdetail_setscore);
    }


}
