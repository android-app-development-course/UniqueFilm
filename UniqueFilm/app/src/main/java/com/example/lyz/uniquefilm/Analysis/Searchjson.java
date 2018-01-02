package com.example.lyz.uniquefilm.Analysis;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.lyz.uniquefilm.Adapter.SearchRvAdapter;
import com.example.lyz.uniquefilm.Information.SearchInfo;
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
 * Created by lyz on 18-1-1.
 */

public class Searchjson extends Thread{

    private String url;
    private Context context;
    private RecyclerView mRecyclerView;
    private SearchRvAdapter adapter;
    private Handler handler;

    public Searchjson(String url,RecyclerView recyclerView,SearchRvAdapter adapter,Handler handler){
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
            int responseCode=connection.getResponseCode();
            String json=null;
            if(responseCode==HttpURLConnection.HTTP_OK){
                InputStream inputStream=connection.getInputStream();
                json=StreamUtils.getString(inputStream);
                System.out.println(json);
            }
            final ArrayList<SearchInfo> jsonParse=jsonParse(json);
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

    public static ArrayList<SearchInfo> jsonParse(String json){
        Type type = new TypeToken<SearchGsonBean>() {
        }.getType();
        Gson gson=new Gson();
        SearchGsonBean searchgson=gson.fromJson(json,SearchGsonBean.class);
        ArrayList<SearchInfo> searchresult=searchgson.data;
        Log.i("here",searchresult.get(0).getTitle());
        return searchresult;
    }
}
