package com.example.lyz.uniquefilm.fragments;


import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lyz.uniquefilm.R;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;

/**
 * Created by douhua on 2018/1/1.
 */

public class WritingFragment extends Fragment {
    //选择添加图片、连接 的按钮
    com.nightonke.boommenu.BoomMenuButton add_btn;

    //保存并发表按钮
    Button SaveSent;
    //标题和正文的edittext
    EditText WritingTitle;
    EditText WritingContent;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View viewWriting = inflater.inflate(R.layout.tab_writing, container,false);

        add_btn=(com.nightonke.boommenu.BoomMenuButton)viewWriting.findViewById(R.id.bmb);
        SaveSent=(Button) viewWriting.findViewById(R.id.writingsent_btn);
        WritingTitle=(EditText)viewWriting.findViewById(R.id.writingtitle_et);
        WritingContent=(EditText)viewWriting.findViewById(R.id.writingcontent_et);

        //add_btn按钮相关
        for(int i=0;i<add_btn.getPiecePlaceEnum().pieceNumber();i++)
        {


            SimpleCircleButton.Builder builder=new SimpleCircleButton.Builder();
            if(i==0)
            {
               builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        //点击添加图片按钮之后的事件
                    }
                });
                builder.normalImageRes(R.mipmap.img);
                builder.normalColor(Color.parseColor("#CD96CD"));

            }
            if(i==1)
            {
                 builder.listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                            //点击添加链接按钮之后的事件
                    }
                });
                builder.normalImageRes(R.mipmap.links);
                builder.normalColor(Color.parseColor("#EE9A49"));

            }
            builder.rotateImage(false);
            builder.imagePadding(new Rect(20,20,20,20));
            add_btn.addBuilder(builder);
        }

        //获取标题和内容

        String title=WritingTitle.getText().toString();
        String content=WritingContent.getText().toString();


        //发表按钮的点击事件：将title和content存数据库
        SaveSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //存数据库操作
            }
        });

        return viewWriting;

    }
}
