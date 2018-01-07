package com.example.lyz.uniquefilm.Analysis;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.lyz.uniquefilm.Adapter.CRvAdapter;
import com.example.lyz.uniquefilm.Adapter.UFRvAdapter;
import com.example.lyz.uniquefilm.Information.BoxInfo;
import com.example.lyz.uniquefilm.Information.CinemaInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lyz on 18-1-3.
 */

public class Cinemajson extends Thread{

    private String url;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private CRvAdapter adapter;
    private Handler handler;

    public Cinemajson(String url, RecyclerView recyclerView, CRvAdapter adapter, Handler handler){
        super();
        this.url=url;
        this.mRecyclerView=recyclerView;
        this.adapter=adapter;
        this.handler=handler;
    }

    @Override
    public void run() {
        URL httpurl;
        try{
            httpurl=new URL(url);
            HttpURLConnection connection=(HttpURLConnection)httpurl.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            int resonseCode=connection.getResponseCode();
            String json=null;
            if(resonseCode== HttpURLConnection.HTTP_OK){
                InputStream inputStream=connection.getInputStream();
                json=StreamUtils.getString(inputStream);
                System.out.println(json);
            }
            final ArrayList<CinemaInfo> jsonParse=jsonParse(json);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.setData(jsonParse);
                    mRecyclerView.setAdapter(adapter);
                }
            });
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<CinemaInfo> jsonParse(String json){
        ArrayList<BoxInfo> movieList=new ArrayList<BoxInfo>();
        Type type = new TypeToken<BoxGsonBean>() {
        }.getType();
        Gson gson=new Gson();
        CinemaGsonBean cinemagson=gson.fromJson(json,CinemaGsonBean.class);
        ArrayList<CinemaInfo> cinema=cinemagson.result;
        //Log.i("here",cinema.get(0).getName());
        return cinema;
    }
}
