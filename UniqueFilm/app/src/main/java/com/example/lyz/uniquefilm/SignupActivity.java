package com.example.lyz.uniquefilm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private int leftchance=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button button1=(Button)findViewById(R.id.button1);
        button1.getBackground().setAlpha(200);
    }

    public void TextClick(View view)
    {
        //TextView textView1=(TextView)findViewById(R.id.textview_forget);
        Intent intent=new Intent(SignupActivity.this,SigninActivity.class);
        startActivity(intent);

    }


    public void myClick(View view){
        Button button1=(Button)findViewById(R.id.button1);
        EditText editText1=(EditText)findViewById(R.id.editText1);
        EditText editText2=(EditText)findViewById(R.id.editText2);


        if((editText1.getText().toString().equals("20152100132"))&&(editText2.getText().toString().equals("1234567"))){
            Toast toast2=Toast.makeText(SignupActivity.this, "Sign up successfully",Toast.LENGTH_LONG);
            toast2.show();
            button1.setEnabled(false);
        }
        else
        {
            Toast toast3=Toast.makeText(SignupActivity.this,"Sign up fail",Toast.LENGTH_LONG);
            toast3.show();


            leftchance--;
            AlertDialog alert=new AlertDialog.Builder(SignupActivity.this).create();
            alert.setMessage("还有"+leftchance+"次机会输入用户密码");
            alert.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(SignupActivity.this,"Try again",Toast.LENGTH_LONG).show();
                }

            });
            alert.show();
            if(leftchance==0) {
                button1.setEnabled(false);
            }
        }



    }
}
