package com.example.lyz.uniquefilm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lyz.uniquefilm.Database.userinformation;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SignupActivity extends AppCompatActivity {

    private EditText etname;
    private EditText etpassword;
    private EditText etphone;
    private Button btnsignup;
    private TextView tverror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //BmobSMS.initialize(this,"486138ea611fa2a2a8728ef9f8f836b5");
        etname=(EditText)findViewById(R.id.et_username);
        etpassword=(EditText)findViewById(R.id.et_password);
        etphone=(EditText)findViewById(R.id.et_phone);
        btnsignup=(Button)findViewById(R.id.btn_signup);
        tverror=(TextView)findViewById(R.id.tv_error);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean error=false;
                final String username=etname.getText().toString();
                final String userpassword=etpassword.getText().toString();
                final String userphone=etphone.getText().toString();
                BmobQuery<userinformation> query=new BmobQuery<userinformation>();
                query.addWhereEqualTo("username",username);
                query.findObjects(new FindListener<userinformation>() {
                    @Override
                    public void done(List<userinformation> list, BmobException e) {
                        if(e==null){
                            if(list.size()!=0){
                                tverror.setText("该用户名已存在！");
                            }
                            else{
                                Bundle bundle=new Bundle();
                                bundle.putString("username",username);
                                bundle.putString("userpassword",userpassword);
                                bundle.putString("userphone",userphone);
                                Intent intent=new Intent(SignupActivity.this,CheckphoneActivity.class);
                                intent.putExtra("user",bundle);
                                startActivity(intent);
                            }
                        }
                        else {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


}
