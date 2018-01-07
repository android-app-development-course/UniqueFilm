package com.example.lyz.uniquefilm;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

public class CitychooseActivity extends AppCompatActivity {

    private Button btnret;
    private Button CurrentBtn;
    private Button HotcityBtn1;
    private Button HotcityBtn2;
    private Button HotcityBtn3;
    private String city;
    private SearchView citySearch;
    //ListView cityList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citychoose);
        btnret=(Button)findViewById(R.id.btnret);
        CurrentBtn=(Button)findViewById(R.id.currentcity);
        HotcityBtn1=(Button)findViewById(R.id.hotcity1);
        HotcityBtn2=(Button)findViewById(R.id.hotcity2);
        HotcityBtn3=(Button)findViewById(R.id.hotcity3);
        citySearch=(SearchView)findViewById(R.id.svcity);
        city="无";





       // ArrayAdapter<String> adapter=new ArrayAdapter(CitychooseActivity.this,android.R.layout.simple_list_item_1,getData());
       // cityList.setAdapter(adapter);

       /* cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //city=item的值
            }
        });*/

        btnret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backMainAct();
            }
        });
        CurrentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city=CurrentBtn.getText().toString();
                backMainAct();
            }
        });
        HotcityBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city=HotcityBtn1.getText().toString();
                backMainAct();
            }
        });
        HotcityBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city=HotcityBtn2.getText().toString();
                backMainAct();
            }
        });
        HotcityBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city=HotcityBtn3.getText().toString();
                backMainAct();
            }
        });
        citySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //处理搜索结果
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //搜索提示功能
                return false;
            }
        });

    }

    //绑定数据并回传
    private void backMainAct(){
        Intent intent=getIntent();
        intent.putExtra("result",city);
        CitychooseActivity.this.setResult(1,intent);
        CitychooseActivity.this.finish();
    }
    //测试数据
    private ArrayList<String> getData(){
        ArrayList<String> list=new ArrayList<>();
        for(int i=0;i<20;i++)
        {
            list.add("city"+i);
        }
        return list;
    }
}
