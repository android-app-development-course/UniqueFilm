package com.example.lyz.uniquefilm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMovieintroActivity extends AppCompatActivity {

    private EditText etintro;
    private Button btnsave;
    private Button btnpause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movieintro);
        etintro=(EditText)findViewById(R.id.et_write);
        btnpause=(Button)findViewById(R.id.pause);
        btnsave=(Button)findViewById(R.id.save);

        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=etintro.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("content",content);
                setResult(2,intent);
                finish();
            }
        });
    }
}
