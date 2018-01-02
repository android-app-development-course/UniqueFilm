package com.example.lyz.uniquefilm;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.lyz.uniquefilm.Adapter.KwSearchRvAdapter;
import com.example.lyz.uniquefilm.Adapter.SearchRvAdapter;
import com.example.lyz.uniquefilm.Analysis.Searchjson;
import com.example.lyz.uniquefilm.Analysis.StreamUtils;
import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.Information.SearchInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SearchRvAdapter adapter;
    private KwSearchRvAdapter adapter1;
    Toolbar toolbar;
    private Handler handler=new Handler();

    private boolean loading=false;
    private String url;
    int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mRecyclerView=(RecyclerView)findViewById(R.id.rv_search);
        toolbar=(Toolbar)findViewById(R.id.toolbarResult);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new SearchRvAdapter(SearchResultActivity.this);
        adapter1=new KwSearchRvAdapter(SearchResultActivity.this);
        toolbar.setTitle("搜索结果");

        Intent intent=getIntent();
        final String content=intent.getStringExtra("content");
        final int type=intent.getIntExtra("type",1);
        final int score=intent.getIntExtra("score",8);
        Log.i("content:",content);
        Log.i("type:",Integer.toString(type));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultActivity.this.finish();
            }
        });

        adapter1.setOnItemClickListener(new KwSearchRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                movies info=adapter1.getDate(position);
                Log.i("info",Integer.toString(position)+info.getMoviename());
                filmdetail(info);
            }
        });

        adapter.setOnItemClickListener(new SearchRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SearchInfo sinfo=adapter.getData(position);
                String moviename=sinfo.getTitle();
                BmobQuery<movies> bmobQuery=new BmobQuery<movies>();
                bmobQuery.addWhereEqualTo("moviename", moviename);
                bmobQuery.findObjects(new FindListener<movies>() {
                    @Override
                    public void done(List<movies> list, BmobException e) {
                        if(e==null){
                            if(list.size()>0) {
                                movies info = list.get(0);
                                filmdetail(info);
                            }
                        }
                        else{
                            Toast.makeText(SearchResultActivity.this,"出现错误，请稍后重试",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        if(type==0){
            //adapter1=new KwSearchRvAdapter(SearchResultActivity.this);
            //String content=etsearch.getText().toString();
            BmobQuery<movies> bmobQuery=new BmobQuery<movies>();
            bmobQuery.addWhereEqualTo("moviename", content);
            bmobQuery.findObjects(new FindListener<movies>() {
                @Override
                public void done(List<movies> list, BmobException e) {
                    if(e==null){
                        Log.i("listlength",Integer.toString(list.size()));
                        adapter1.setData(list);
                        mRecyclerView.setAdapter(adapter1);
                    }
                    else{
                        e.printStackTrace();
                    }
                }
            });
        }
        else if(type==1){
            //adapter1=new KwSearchRvAdapter(SearchResultActivity.this);
            //String content=etsearch.getText().toString();
            BmobQuery<movies> bmobQuery=new BmobQuery<movies>();
            bmobQuery.addWhereEqualTo("moviename", content);
            bmobQuery.findObjects(new FindListener<movies>() {
                @Override
                public void done(List<movies> list, BmobException e) {
                    if(e==null){
                        Log.i("listlength",Integer.toString(list.size()));
                        adapter1.setData(list);
                        mRecyclerView.setAdapter(adapter1);
                    }
                    else{
                        e.printStackTrace();
                    }
                }
            });
        }
        else if(type==2){

            url="https://movie.douban.com/j/new_search_subjects?sort=S&range=0,10&tags=电影,"+content+"&start=0";
            new Searchjson(url,mRecyclerView,adapter,handler).start();
        }
        else if(type==3){
            //adapter=new SearchRvAdapter(SearchResultActivity.this);
            url="https://movie.douban.com/j/new_search_subjects?sort=S&range="+Integer.toString(score-1)+","+Integer.toString(score)+"&tags=电影&start=0";
            new Searchjson(url,mRecyclerView,adapter,handler).start();
        }
        //SimpleAdapter adapter=new SimpleAdapter(this,getData(),R.layout.listviewitem,new String[]{"title","img"},new int[]{R.id.searchResultTitle,R.id.searchResultImg});


        /*searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击搜索结果中每个item的响应事件
            }
        });*/

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
                int lastVisibleItem=layoutManager.findLastVisibleItemPosition();
                int totalItemCount=layoutManager.getItemCount();
                if(!loading&&totalItemCount==lastVisibleItem+2){
                    String start=Integer.toString(20*i);
                    i=i+1;
                    Log.i("start",start);
                    //String content;
                    if(type==2)
                        url="https://movie.douban.com/j/new_search_subjects?sort=S&range=0,10&tags=电影,"+content+"&start="+start;
                    else if(type==3)
                        url="https://movie.douban.com/j/new_search_subjects?sort=S&range="+Integer.toString(score-1)+","+Integer.toString(score)+"&tags=电影&start="+start;

                    new SearchTask(SearchResultActivity.this,adapter.list,adapter,url).execute();
                    loading=true;
                }

            }
        });

    }

    private void filmdetail(movies info){
        Intent intent=new Intent(SearchResultActivity.this,FilmDetailActivity.class);
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

    //测试数据
    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();

        Map<String,Object> map=new HashMap<String,Object>();
        map.put("title","111");
        map.put("img",R.mipmap.result1);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title","222");
        map.put("img",R.mipmap.result2);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title","333");
        map.put("img",R.mipmap.result3);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title","444");
        map.put("img",R.mipmap.result4);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title","555");
        map.put("img",R.mipmap.result5);
        list.add(map);

        return  list;
    }


    public class SearchTask extends AsyncTask<Integer,Void,ArrayList<SearchInfo>> {

        private Context mContext;
        private ArrayList<SearchInfo> infolist=new ArrayList<SearchInfo>();
        private SearchRvAdapter madapter;
        String murl;
        //boolean loading;

        public SearchTask(Context context, ArrayList<SearchInfo> list, SearchRvAdapter adapter,String url){
            this.mContext=context;
            this.infolist=list;
            this.madapter=adapter;
            this.murl=url;
            // this.loading=loading;
        }

       /* @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (infolist!=null&&infolist.size()>0){
                infolist.add(null);
                madapter.notifyItemInserted(infolist.size()-1);
            }
        }*/

        @Override
        protected ArrayList<SearchInfo> doInBackground(Integer... params) {
            try{
                Thread.sleep(1500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return getList();
        }

        public ArrayList<SearchInfo> getList(){
            URL httpurl;
            try {
                httpurl = new URL(murl);
                HttpURLConnection connection = (HttpURLConnection) httpurl.openConnection();
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                String json = null;
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    json = StreamUtils.getString(inputStream);
                    System.out.println(json);
                }
                final ArrayList<SearchInfo> jsonParse = Searchjson.jsonParse(json);
                return jsonParse;
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<SearchInfo> searchInfos) {
            super.onPostExecute(searchInfos);
            if(infolist.size()==0){
                infolist.addAll(searchInfos);
                madapter.notifyDataSetChanged();
            }
            else {
                infolist.remove(infolist.size()-1);
                infolist.addAll(searchInfos);
                madapter.notifyDataSetChanged();
                loading=false;
            }
        }
    }
}
