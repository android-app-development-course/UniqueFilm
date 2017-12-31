package com.example.lyz.uniquefilm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lyz.uniquefilm.Analysis.TimeUtils;
import com.example.lyz.uniquefilm.Database.userinformation;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CheckphoneActivity extends AppCompatActivity {

    private EditText etcheck;
    private Button btnsure;
    private Button btntryagain;
    private TextView tvtime;
    private Handler handler=new Handler();
    MyCountTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkphone);
        etcheck=(EditText)findViewById(R.id.et_checknum);
        btnsure=(Button)findViewById(R.id.btn_sure);
        btntryagain=(Button)findViewById(R.id.btn_tryagain);
        tvtime=(TextView)findViewById(R.id.tv_lefttime);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("user");
        final String phone=bundle.getString("userphone");
        final String name=bundle.getString("username");
        final String password=bundle.getString("userpassword");
        String str="重新获取";
        //new TimeUtils((Button)findViewById(R.id.btn_tryagain),str).RunTimer();
        requestSMSCode(phone);

        btntryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSMSCode(phone);
            }
        });

        btnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOrBind(name,password,phone);
            }
        });
    }

    private void requestSMSCode(String phone) {

        if (!TextUtils.isEmpty(phone)) {
            timer = new MyCountTimer(60000, 1000);
            timer.start();
            BmobSMS.requestSMSCode(phone, "短信模板", new QueryListener<Integer>() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if(e==null){
                        Toast.makeText(CheckphoneActivity.this,"验证码发送成功",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        timer.cancel();
                    }
                }
            });
        }
    }

    private void verifyOrBind(final String username, final String userpassword, final String phone){
        String code=etcheck.getText().toString();
        if(TextUtils.isEmpty(code)){
            Toast.makeText(CheckphoneActivity.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog progress=new ProgressDialog(this);
        progress.setMessage("正在验证短信验证码...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    progress.dismiss();
                    userinformation user=new userinformation();
                    user.setUsername(username);
                    user.setUserpassword(userpassword);
                    user.setUserphone(phone);
                    user.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(CheckphoneActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(CheckphoneActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    class MyCountTimer extends CountDownTimer{

        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            btntryagain.setText((millisUntilFinished / 1000) +"秒后重发");
        }
        @Override
        public void onFinish() {
            btntryagain.setText("重新发送验证码");
        }
    }
}
