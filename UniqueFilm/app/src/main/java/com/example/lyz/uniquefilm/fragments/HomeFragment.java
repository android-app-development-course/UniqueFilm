package com.example.lyz.uniquefilm.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.example.lyz.uniquefilm.Adapter.ComRvAdapter;
import com.example.lyz.uniquefilm.Adapter.NpRvAdapter;
import com.example.lyz.uniquefilm.CitychooseActivity;
import com.example.lyz.uniquefilm.Database.commendmovies;
import com.example.lyz.uniquefilm.Database.newmovies;
import com.example.lyz.uniquefilm.NowplayingActivity;
import com.example.lyz.uniquefilm.R;


import java.util.ArrayList;
import java.util.List;

import List.DemoAdapter;
import List.ImageUtil;
import List.SpacesItemDecoration;
import Refresh.CircleRefreshLayout;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import com.youth.banner.Banner;


/**
 * Created by lyz on 17-11-19.
 */

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView mRecyclerView;
    //private DemoAdapter adapter;
    private CircleRefreshLayout mRefreshLayout;
    private Button btnchosecity;
    private Banner banner;
    private List<Integer> images;
    private List<newmovies> movieslist=new ArrayList<newmovies>();
    private List<commendmovies> comlist=new ArrayList<commendmovies>();
    private NpRvAdapter mAdapter;
    private ComRvAdapter mComRvAdapter;
    private Button nowmore;
    //private ArrayList<testdata> datalist;
    //public LocationClient mLocationClient=null;
    //private MyLocationListener myListener=new MyLocationListener();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.tab_home,container,false);
        mRefreshLayout=(CircleRefreshLayout)v.findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView)v.findViewById(R.id.recylerview);
        mRecyclerView=(RecyclerView)v.findViewById(R.id.rv_recommend);
        nowmore=(Button)v.findViewById(R.id.homeFilmseeAll);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter=new NpRvAdapter(getActivity());
        mComRvAdapter=new ComRvAdapter(getActivity());
        //btnchosecity=(Button)v.findViewById(R.id.btncity);

        //btnchosecity.setText("北京");
        //btnchosecity.setTextColor(Color.WHITE);

        banner = (Banner) v.findViewById(R.id.banner);
        images = new ArrayList<>();
        int[] img = getImages();
        for (int i = 0; i < img.length; i++) {
            //
            images.add(img[i]);
        }
        String[] titles=new String[]{"title1","title2","title3","title4"};
        // banner.setImages(images);
        banner.setBannerTitle(titles);
        banner.setBannerStyle(Banner.CIRCLE_INDICATOR_TITLE);
        banner.setIndicatorGravity(Banner.CENTER);
        banner.setDelayTime(5000);
        banner.setImages(images, new Banner.OnLoadImageListener() {
            @Override
            public void OnLoadImage(ImageView view, Object url) {
                Glide.with(getActivity()).load(url).into(view);
            }
        });
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {

            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //SpacesItemDecoration decoration=new SpacesItemDecoration(32);
        //recyclerView.setAdapter(adapter = new DemoAdapter());
        //adapter.replaceAll(getData());


        for(int i=0;i<6;i++){
            if(i==0)
                movieslist.removeAll(movieslist);
            new Getnewmovies(Integer.toString(i),recyclerView,mAdapter).start();
        }



        for(int i=0;i<1;i++){
            if(i==0)
                comlist.removeAll(comlist);
            new Getrecommend(0,mRecyclerView,mComRvAdapter).start();
        }

        nowmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), NowplayingActivity.class);
                startActivity(intent);
            }
        });
        //判断权限够不够，不够就给
       /* if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1);
        } else {
            //够了就设置路径等，准备播放
            mLocationClient=new LocationClient(getActivity());
            mLocationClient.registerLocationListener(myListener);
            setLocationOption();
            mLocationClient.start();
            //Toast.makeText(getActivity(),"已定位",Toast.LENGTH_SHORT).show();
            mLocationClient.requestLocation();
        }



        btnchosecity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CitychooseActivity.class);
                startActivityForResult(intent, 1);// 1 唯一标识选择城市页面
            }
        });
        */
        mRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            Handler handler=new Handler();
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    mRefreshLayout.finishRefreshing();
                    handler.postDelayed(this, 2000);
                }
            };
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {
                handler.postDelayed(runnable, 2000);
            }
        });

        return v;
    }

    private int[] getImages() {
        int[] list = {R.mipmap.banner1,R.mipmap.banner2,R.mipmap.banner3,R.mipmap.banner4};

        return list;

    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //bindService(MediaServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
                    mLocationClient=new LocationClient(getActivity());
                    mLocationClient.registerLocationListener(myListener);
                    setLocationOption();
                    mLocationClient.start();
                    //Toast.makeText(getActivity(),"已定位",Toast.LENGTH_SHORT).show();
                    mLocationClient.requestLocation();
                } else {
                    Toast.makeText(getActivity(), "权限不够无法定位，程序将退出", Toast.LENGTH_SHORT).show();

                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
            {
                if(resultCode==1){
                String resultstr=data.getStringExtra("result");
                btnchosecity.setText(resultstr);}
            }
            break;
        }
    }

    private void setLocationOption(){
        LocationClientOption option=new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd0911");
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5*60*1000);
        option.setEnableSimulateGps(false);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }*/

    public ArrayList<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        for (String url : ImageUtil.imageUrls) {
            list.add(url);
        }
        return list;
    }


    /*

    public class MyLocationListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            double latitude=bdLocation.getLatitude();
            double longitude=bdLocation.getLongitude();
            float radius=bdLocation.getRadius();

            String coorType=bdLocation.getCoorType();
            int errorCode=bdLocation.getLocType();
            String city=bdLocation.getCity();
            if(errorCode==61||errorCode==161||errorCode==65||errorCode==66||errorCode==68){
                if(!city.equals(""))
                    Toast.makeText(getActivity(),"已定位到当前城市",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(),"定位失败！",Toast.LENGTH_LONG).show();
            }

            else
                Toast.makeText(getActivity(),"定位失败，错误码为"+Integer.toString(errorCode),Toast.LENGTH_LONG).show();
            SharedPreferences myPreference=getActivity().getSharedPreferences("myPreference", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=myPreference.edit();
            if(!city.equals("")){
                editor.putString("city",city);

                btnchosecity.setText(city);
            }
            else{
                editor.putString("city","北京市");
            }
            editor.commit();
        }


    }*/


    public class Getnewmovies extends Thread {

        private RecyclerView mRecyclerView;
        private NpRvAdapter mAdapter;
        private String movieid;
        private newmovies mMovies;
        private List<newmovies> mlist;


        public Getnewmovies(String movieid,RecyclerView rv,NpRvAdapter adapter){
            this.movieid=movieid;
            this.mRecyclerView=rv;
            this.mAdapter=adapter;
            this.mlist=adapter.list;
        }

        @Override
        public void run() {
            //user.setUsercollection( user.getUsercollection()+Integer.toString(movieid) + ",");
            BmobQuery<newmovies> query=new BmobQuery<newmovies>();
            query.addWhereEqualTo("movieid",Integer.parseInt(movieid));
            query.findObjects(new FindListener<newmovies>() {
                @Override
                public void done(List<newmovies> list, BmobException e) {
                    if(e==null){
                        Log.i("result", "获取成功");
                        //setcollection(list.get(0));
                        movieslist.add(list.get(0));
                        mAdapter.setData(movieslist);
                        mRecyclerView.setAdapter(mAdapter);
                        Log.i("setdate","here");
                    }
                    else{
                        e.printStackTrace();
                    }
                }
            });
        }

        private void setcollection(newmovies movie){
            mMovies=movie;
        }

        public newmovies getMovies(){
            return mMovies;
        }
    }


    public class Getrecommend extends Thread {

        private RecyclerView mRecyclerView;
        private ComRvAdapter mAdapter;
        private int id;
        private commendmovies mrecommend;
        private List<commendmovies> mlist;


        public Getrecommend(int comid,RecyclerView rv,ComRvAdapter adapter){
            this.id=comid;
            this.mRecyclerView=rv;
            this.mAdapter=adapter;
            this.mlist=adapter.list;
        }

        @Override
        public void run() {
            //user.setUsercollection( user.getUsercollection()+Integer.toString(movieid) + ",");
            BmobQuery<commendmovies> query=new BmobQuery<commendmovies>();
            query.addWhereEqualTo("id",id);
            query.findObjects(new FindListener<commendmovies>() {
                @Override
                public void done(List<commendmovies> list, BmobException e) {
                    if(e==null){
                        Log.i("result1", "获取成功");
                        Log.i("list",list.get(0).getTitle());
                        //setcollection(list.get(0));
                        comlist.add(list.get(0));
                        comlist.add(list.get(1));
                        mAdapter.setData(comlist);
                        mRecyclerView.setAdapter(mAdapter);
                        Log.i("setdate","here");
                    }
                    else{
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
