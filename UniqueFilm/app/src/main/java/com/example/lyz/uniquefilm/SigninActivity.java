package com.example.lyz.uniquefilm;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class SigninActivity extends AppCompatActivity {

    private EditText edusername;
    private EditText edpassword;
    private EditText edemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        edusername=(EditText)findViewById(R.id.editText1);
        edpassword=(EditText)findViewById(R.id.editText2);
        edemail=(EditText)findViewById(R.id.editText3);
    }

    public void ButtonClick(View view){
        Button button1=(Button)findViewById(R.id.button_signup);
        //Intent intent=new Intent(SigninActivity.this,FilmDetailActivity.class);

        //startActivity(intent);
    }
}
