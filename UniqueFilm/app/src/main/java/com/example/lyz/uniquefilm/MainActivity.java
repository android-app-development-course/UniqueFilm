package com.example.lyz.uniquefilm;

import android.content.Intent;
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

import com.example.lyz.uniquefilm.fragments.CustomerFragment;
import com.example.lyz.uniquefilm.fragments.HomeFragment;
import com.example.lyz.uniquefilm.fragments.MyFragmentAdapter;
import com.example.lyz.uniquefilm.fragments.SortFragment;

import java.util.ArrayList;
import java.util.List;

import Animator.LoadingDialog;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener{

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mViews=new ArrayList<Fragment>();

    private LinearLayout mTabhome;
    private LinearLayout mTabsorting;
    private LinearLayout mTabcustomer;

    private ImageButton mhomeImg;
    private ImageButton msortingImg;
    private ImageButton mcustomerImg;

    LoadingDialog loading;

    Handler handler=new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentItem=mViewPager.getCurrentItem();
                switch (currentItem){
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

        mhomeImg=(ImageButton)findViewById(R.id.id_tab_home_img);
        msortingImg=(ImageButton)findViewById(R.id.id_tab_sorting_img);
        mcustomerImg=(ImageButton)findViewById(R.id.id_tab_customer_img);
    }

    private void initViewPage(){
        LayoutInflater mLayoutInflater=LayoutInflater.from(this);
        //View home=mLayoutInflater.inflate(R.layout.tab_home,null);
        //View sorting=mLayoutInflater.inflate(R.layout.tab_sorting,null);
        //View customer=mLayoutInflater.inflate(R.layout.tab_customer,null);
        //View loading=mLayoutInflater.inflate(R.layout.loadingdialog,null);

        mViews.add(new HomeFragment());
        mViews.add(new SortFragment());
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
                loading.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                    }
                }, 2000);
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
            case R.id.id_tab_customer:
                mViewPager.setCurrentItem(2);
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
