package com.example.lyz.uniquefilm.Analysis;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.lyz.uniquefilm.Adapter.UFRvAdapter;
import com.example.lyz.uniquefilm.Information.BoxInfo;
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
 * Created by lyz on 17-12-28.
 */

public class Boxjson extends Thread {

    private String url;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private UFRvAdapter adapter;
    private Handler handler;

    public Boxjson(String url,RecyclerView recyclerView,UFRvAdapter adapter,Handler handler){
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
            final ArrayList<BoxInfo> jsonParse=jsonParse(json);
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

    private ArrayList<BoxInfo> jsonParse(String json){

        ArrayList<BoxInfo> movieList=new ArrayList<BoxInfo>();
        Type type = new TypeToken<BoxGsonBean>() {
        }.getType();
        Gson gson=new Gson();
        BoxGsonBean boxgson=gson.fromJson(json,BoxGsonBean.class);
        ArrayList<BoxInfo> box=boxgson.data.list;
        Log.i("here",box.get(0).getMovieName());
        return box;
    }
}
