package com.example.lyz.uniquefilm.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.lyz.uniquefilm.Adapter.KwSearchRvAdapter;
import com.example.lyz.uniquefilm.Database.movies;
import com.example.lyz.uniquefilm.FilmDetailActivity;
import com.example.lyz.uniquefilm.R;
import com.example.lyz.uniquefilm.SearchResultActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyz on 18-1-2.
 */

public class SearchFragment extends Fragment {

    //private EditText etsearch;
    //private Button btngo;
    private RecyclerView mRecyclerView;
    private KwSearchRvAdapter hotsearch_adapter;
    private Handler handler=new Handler();

    SearchView searchView;
    SearchView.SearchAutoComplete mSearchAutoComplete;
    Toolbar toolbar;
    //搜索类型:1全部、2片名、3类型
    int type=1;
    //popup下拉选择框
    private ArrayAdapter adapter;
    ArrayList<String> popup_list;
    Button popup_button;

    //评分搜索选择按钮
    at.markushi.ui.CircleButton eight;
    at.markushi.ui.CircleButton nine;
    at.markushi.ui.CircleButton ten;

    //用户选择的评分数字
    int score;

    View v_all;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v_all=inflater.inflate(R.layout.tab_search,container,false);
        //btngo=(Button)v_all.findViewById(R.id.btn_search);
        //etsearch=(EditText)v.findViewById(R.id.et_search);

        mRecyclerView=(RecyclerView)v_all.findViewById(R.id.rv_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        hotsearch_adapter=new KwSearchRvAdapter(getActivity());

        eight=(at.markushi.ui.CircleButton)v_all.findViewById(R.id.eight);
        nine=(at.markushi.ui.CircleButton)v_all.findViewById(R.id.nine);
        ten=(at.markushi.ui.CircleButton)v_all.findViewById(R.id.ten);

        toolbar = (Toolbar)v_all.findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }


        });



        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=8;
                Intent intent=new Intent(getActivity(),SearchResultActivity.class);
                intent.putExtra("content","");
                intent.putExtra("type",3);
                intent.putExtra("score",8);
                startActivity(intent);
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=8;
                Intent intent=new Intent(getActivity(),SearchResultActivity.class);
                intent.putExtra("content","");
                intent.putExtra("type",3);
                intent.putExtra("score",0);
                startActivity(intent);
            }
        });

        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=8;
                Intent intent=new Intent(getActivity(),SearchResultActivity.class);
                intent.putExtra("content","");
                intent.putExtra("type",3);
                intent.putExtra("score",10);
                startActivity(intent);
            }
        });

        //热门搜索的adpter item点击函数
        hotsearch_adapter.setOnItemClickListener(new KwSearchRvAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                movies info=hotsearch_adapter.getDate(position);
                Log.i("info",Integer.toString(position)+info.getMoviename());
                Intent intent=new Intent(getActivity(),FilmDetailActivity.class);
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
        });
        //测试数据
        List<movies> lists=new ArrayList<>();
        movies m;
        m=new movies();
        m.setDescription("description");
        m.setMoviename("111");
        for(int i=0;i<30;i++)
        {
            lists.add(m);
        }
        hotsearch_adapter.setData(lists);
        mRecyclerView.setAdapter(hotsearch_adapter);

        setHasOptionsMenu(true);

        return v_all;
    }
    //@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);

        final View popupView = getActivity().getLayoutInflater().inflate(R.layout.popuwindows, null);
        final ListView lsvMore = (ListView) popupView.findViewById(R.id.lsvMore);


        //通过MenuItem得到SearchView
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        popup_button=(Button)v_all.findViewById(R.id.popupwindows_btn);
        mSearchAutoComplete=(SearchView.SearchAutoComplete)searchView.findViewById(R.id.search_src_text);
        final PopupWindow window = new PopupWindow(popupView,280, LinearLayout.LayoutParams.WRAP_CONTENT);

        popup_list=new ArrayList<>();



        popup_list=data();
        popup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,popup_list);
                lsvMore.setAdapter(adapter);



                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));


                window.setFocusable(true);

                window.setOutsideTouchable(true);

                window.update();

                window.showAsDropDown(popup_button, 0, 0);


            }
        });
        lsvMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type=position;
                window.dismiss();
            }
        });

        WindowManager wm = getActivity().getWindowManager();
        int width=wm.getDefaultDisplay().getWidth();
        int width_btn=popup_button.getWidth();
        //int width_nav= toolbar.getNavigationIcon().getMinimumWidth();
        int width_final=width-width_btn-100;
        searchView.setMaxWidth(width_final);
        searchView.setIconifiedByDefault(true);

        searchView.setQueryHint("电影/类型/评分...");


        mSearchAutoComplete.setTextColor(getResources().getColor(android.R.color.background_light));
        mSearchAutoComplete.setHintTextColor(Color.parseColor("#FFBBFF"));
        mSearchAutoComplete.setTextSize(18);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //提交监听事件，比对数据库，跳转到搜索结果
            public boolean onQueryTextSubmit(String query) {
                String content=searchView.getQuery().toString();
                if(content.length()>0){
                    Intent intent=new Intent(getActivity(),SearchResultActivity.class);
                    intent.putExtra("content",content);
                    intent.putExtra("type",type);
                    intent.putExtra("score",8);
                    startActivity(intent);}


                //如果用户是搜索所有结果
                if(type==1){

                }

                //如果用户搜索时选择的是影片名：
                if(type==2){

                }
                //如果用户搜索时选择的是影片类型
                if(type==3){

                }

                return  true;
            }
            @Override
            //检测文字变化，搜索提示功能
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });




        //有文字时显示x

        //searchView.setIconified(true);


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        searchView.getQuery();

        super.onCreateOptionsMenu(menu,inflater);
    }
    private ArrayList<String> data(){
        ArrayList<String> list=new ArrayList<>();
        list.add("全部");
        list.add("按影片名");
        list.add("按类型");

        return list;
    }



}

