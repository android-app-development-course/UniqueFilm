package com.example.lyz.uniquefilm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lyz.uniquefilm.fragments.CustomerFragment;
import com.example.lyz.uniquefilm.fragments.HomeFragment;
import com.example.lyz.uniquefilm.fragments.MyFragmentAdapter;
import com.example.lyz.uniquefilm.fragments.SearchFragment;
import com.example.lyz.uniquefilm.fragments.SortFragment;
import com.example.lyz.uniquefilm.fragments.WritingFragment;

import java.util.ArrayList;
import java.util.List;

import Animator.LoadingDialog;
import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener{

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mViews=new ArrayList<Fragment>();

    private LinearLayout mTabhome;
    private LinearLayout mTabsorting;
    private LinearLayout mTabcustomer;
    private LinearLayout mTabsearch;
    private LinearLayout mTabWrite;

    private ImageButton mhomeImg;
    private ImageButton msortingImg;
    private ImageButton mcustomerImg;
    private ImageButton msearchImg;
    private ImageButton mwriteImg;

    LoadingDialog loading;

    Handler handler=new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this,"486138ea611fa2a2a8728ef9f8f836b5");
        SharedPreferences myPreference=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        boolean iscontain=myPreference.contains("userstate");
        if(!iscontain){
            SharedPreferences.Editor editor=myPreference.edit();
            editor.putBoolean("userstate",false);
        }
        setContentView(R.layout.activity_main);
        loading=new LoadingDialog(this,R.drawable.img_loading2);
        initView();
        initViewPage();
        initEvent();
    }

    private void initEvent(){
        mTabhome.setOnClickListener(this);
        mTabsorting.setOnClickListener(this);
        mTabcustomer.setOnClickListener(this);
        mTabsearch.setOnClickListener(this);
        mTabWrite.setOnClickListener(this);
        mwriteImg.setOnClickListener(new View.OnClickListener() {//set button click
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "12", Toast.LENGTH_LONG).show();
                mViewPager.setCurrentItem(2);
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        int currentItem = mViewPager.getCurrentItem();
                        switch (currentItem) {
                            case 0:
                                resetImg();
                                mhomeImg.setImageResource(R.mipmap.home_w);
                                break;
                            case 1:
                                resetImg();
                                msortingImg.setImageResource(R.mipmap.sorting_w);
                                break;
                            case 2:
                                resetImg();
                                mwriteImg.setImageResource(R.mipmap.add_w);
                                break;
                            case 3:
                                resetImg();
                                msearchImg.setImageResource(R.mipmap.search_w);
                                break;
                            case 4:
                                resetImg();
                                mcustomerImg.setImageResource(R.mipmap.customer_w);
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
    }

    private void initView(){
        mViewPager=(ViewPager)findViewById(R.id.id_viewpage);
        mTabhome=(LinearLayout)findViewById(R.id.id_tab_home);
        mTabsorting=(LinearLayout)findViewById(R.id.id_tab_sorting);
        mTabcustomer=(LinearLayout)findViewById(R.id.id_tab_customer);
        mTabsearch=(LinearLayout)findViewById(R.id.id_tab_search);
        mTabWrite=(LinearLayout)findViewById(R.id.id_tab_write);

        mhomeImg=(ImageButton)findViewById(R.id.id_tab_home_img);
        msortingImg=(ImageButton)findViewById(R.id.id_tab_sorting_img);
        mcustomerImg=(ImageButton)findViewById(R.id.id_tab_customer_img);
        msearchImg=(ImageButton)findViewById(R.id.id_tab_search_img);
        mwriteImg=(ImageButton)findViewById(R.id.id_tab_write_img);
    }

    private void initViewPage(){
        LayoutInflater mLayoutInflater=LayoutInflater.from(this);
        //View home=mLayoutInflater.inflate(R.layout.tab_home,null);
        //View sorting=mLayoutInflater.inflate(R.layout.tab_sorting,null);
        //View customer=mLayoutInflater.inflate(R.layout.tab_customer,null);
        //View loading=mLayoutInflater.inflate(R.layout.loadingdialog,null);

        mViews.add(new HomeFragment());
        mViews.add(new SortFragment());
        mViews.add(new WritingFragment());
        mViews.add(new SearchFragment());
        mViews.add(new CustomerFragment());



        MyFragmentAdapter adapter=new MyFragmentAdapter(getSupportFragmentManager(),mViews);
        mViewPager.setAdapter(adapter);

        /*mPagerAdapter=new PagerAdapter() {
            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view=mViews.get(position);
                container.addView(view);
                return view;
            }
        };
        mViewPager.setAdapter(mPagerAdapter);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_tab_home:
               /* loading.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                    }
                }, 2000);*/
                //handler.removeCallbacks(runnable);
                mViewPager.setCurrentItem(0);
                resetImg();
                mhomeImg.setImageResource(R.mipmap.home_w);
                break;
            case R.id.id_tab_sorting:
                mViewPager.setCurrentItem(1);
                resetImg();
                msortingImg.setImageResource(R.mipmap.sorting_w);
                break;
            case R.id.id_tab_write:
                mViewPager.setCurrentItem(2);
                resetImg();
                msearchImg.setImageResource(R.mipmap.add_w);
                break;
            case R.id.id_tab_search:
                mViewPager.setCurrentItem(3);
                resetImg();
                msearchImg.setImageResource(R.mipmap.search_w);
                break;
            case R.id.id_tab_customer:
                mViewPager.setCurrentItem(4);
                resetImg();
                mcustomerImg.setImageResource(R.mipmap.customer_w);
                break;
            default:
                break;
        }
    }

    private void resetImg(){
        mhomeImg.setImageResource(R.mipmap.home_y);
        msortingImg.setImageResource(R.mipmap.sorting_y);
        mcustomerImg.setImageResource(R.mipmap.customer_y);
        msearchImg.setImageResource(R.mipmap.search_y);
        mwriteImg.setImageResource(R.mipmap.add_y);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loading.dismiss();
    }

    @Override
    public void onBackPressed() {
        if(loading.isShowing())
            loading.dismiss();
        else
            finish();
    }
}
