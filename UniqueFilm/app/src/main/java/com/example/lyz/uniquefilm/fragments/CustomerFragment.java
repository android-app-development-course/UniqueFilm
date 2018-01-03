package com.example.lyz.uniquefilm.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lyz.uniquefilm.R;
import com.example.lyz.uniquefilm.SigninActivity;
import com.example.lyz.uniquefilm.SignupActivity;
import com.example.lyz.uniquefilm.UserComActivity;
import com.example.lyz.uniquefilm.UsercolActivity;
import com.example.lyz.uniquefilm.UserrecommendActivity;

/**
 * Created by lyz on 17-11-19.
 */

public class CustomerFragment extends Fragment {

    private ImageButton imusericon;
    private Button btcollect;
    private Button bthmarked;
    private Button btrecommend;
    private Button btncom;
    private Button btsetting;
    private TextView tvusername;
    boolean state;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_customer, container,false);
        imusericon=(ImageButton)v.findViewById(R.id.ibuserpic);
        btcollect=(Button)v.findViewById(R.id.btncollections);
        bthmarked=(Button)v.findViewById(R.id.btnhavemarked);
        btrecommend=(Button)v.findViewById(R.id.btnrecommend);
        btncom=(Button)v.findViewById(R.id.btncommends);
        btsetting=(Button)v.findViewById(R.id.btnsettings);
        tvusername=(TextView)v.findViewById(R.id.tvshowusername);
        SharedPreferences myPreference=getActivity().getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        state=myPreference.getBoolean("userstate",false);
        if(state){
            final String username=myPreference.getString("username","");
            tvusername.setText(username);
        }

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imusericon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!state){
                    Intent intent=new Intent(getActivity(), SigninActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.in_from_bottom,R.anim.out_to_top);
                }
            }
        });

        btcollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), UsercolActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            }
        });

        bthmarked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btrecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), UserrecommendActivity.class);
                startActivity(intent);
            }
        });

        btncom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), UserComActivity.class);
                startActivity(intent);
            }
        });

        btsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
