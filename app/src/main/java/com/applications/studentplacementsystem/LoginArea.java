package com.applications.studentplacementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class LoginArea extends AppCompatActivity {

    ImageView imga,imgt,imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_area);
        imga =findViewById(R.id.imgadm);
        imgt=findViewById(R.id.imgtpo);
        imgs=findViewById(R.id.imgstd);
        imga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),AdminLogin.class);
                startActivity(i);
            }
        }
        );
        imgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),TPOLogin.class);
                startActivity(i);
            }
        });
        imgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),StudentLogin.class);
                startActivity(i);
            }
        });
    }
}