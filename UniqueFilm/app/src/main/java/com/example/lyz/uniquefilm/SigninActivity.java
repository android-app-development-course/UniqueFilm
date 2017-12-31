package com.example.lyz.uniquefilm;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lyz.uniquefilm.Database.userinformation;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SigninActivity extends AppCompatActivity {

    private EditText edusername;
    private EditText edpassword;
    private EditText edphone;
    private Button btnsignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        edusername=(EditText)findViewById(R.id.et_username);
        edpassword=(EditText)findViewById(R.id.et_password);
        edphone=(EditText)findViewById(R.id.et_phone);
        btnsignin=(Button)findViewById(R.id.btn_signin);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=edusername.getText().toString();
                final String password=edpassword.getText().toString();
                BmobQuery<userinformation> query=new BmobQuery<userinformation>();
                query.addWhereEqualTo("username",username);
                query.findObjects(new FindListener<userinformation>() {
                    @Override
                    public void done(List<userinformation> list, BmobException e) {
                        if(e==null){
                            if(list.size()!=0){
                                if(list.get(0).getUsername().equals(username)&&list.get(0).getUserpassword().equals(password)){
                                    SharedPreferences myPreference=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=myPreference.edit();
                                    editor.putBoolean("userstate",true);
                                    editor.putString("username",username);
                                    editor.commit();
                                    Toast.makeText(SigninActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else{
                                    Toast.makeText(SigninActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(SigninActivity.this,"用户名不存在",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SigninActivity.this,"登录失败！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


}
