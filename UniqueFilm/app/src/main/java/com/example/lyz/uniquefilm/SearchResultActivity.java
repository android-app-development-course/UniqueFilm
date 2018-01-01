package com.example.lyz.uniquefilm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity {

    private ListView searchResult;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchResult=(ListView)findViewById(R.id.searchResultlist);
        toolbar=(Toolbar)findViewById(R.id.toolbarResult);



        toolbar.setTitle("搜索结果");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultActivity.this.finish();
            }
        });

        SimpleAdapter adapter=new SimpleAdapter(this,getData(),R.layout.listviewitem,new String[]{"title","img"},new int[]{R.id.searchResultTitle,R.id.searchResultImg});
        searchResult.setAdapter(adapter);
        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击搜索结果中每个item的响应事件
            }
        });

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
}
