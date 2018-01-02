package com.example.lyz.uniquefilm.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.lyz.uniquefilm.CitychooseActivity;
import com.example.lyz.uniquefilm.R;


import java.util.ArrayList;

import List.DemoAdapter;
import List.ImageUtil;
import List.SpacesItemDecoration;
import Refresh.CircleRefreshLayout;


/**
 * Created by lyz on 17-11-19.
 */

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private DemoAdapter adapter;
    private CircleRefreshLayout mRefreshLayout;
    private Button btnchosecity;
    public LocationClient mLocationClient=null;
    private MyLocationListener myListener=new MyLocationListener();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.tab_home,container,false);
        mRefreshLayout=(CircleRefreshLayout)v.findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView)v.findViewById(R.id.recylerview);
        btnchosecity=(Button)v.findViewById(R.id.btncity);

        btnchosecity.setText("北京");
        btnchosecity.setTextColor(Color.WHITE);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration=new SpacesItemDecoration(32);
        recyclerView.setAdapter(adapter = new DemoAdapter());
        adapter.replaceAll(getData());


        //判断权限够不够，不够就给
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
    }

    public ArrayList<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        for (String url : ImageUtil.imageUrls) {
            list.add(url);
        }
        return list;
    }



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
            btnchosecity.setText(city);
        }


    }
}
