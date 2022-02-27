package com.applications.studentplacementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TPOArea extends AppCompatActivity {

    Button btnaddcompany,btnaddpastpaer,btnaddselectedstudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpo_area);
        btnaddcompany = findViewById(R.id.btnaddcompany);
        btnaddpastpaer = findViewById(R.id.btnpastpaper);
        btnaddselectedstudent = findViewById(R.id.btnselectedstudent);
        btnaddcompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TPOArea.this, AddCompany.class);
                startActivity(intent);
//                finish();
            }
        });
        btnaddpastpaer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TPOArea.this, AddPastPapers.class);
                startActivity(intent);
//                finish();
            }
        });
        btnaddselectedstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TPOArea.this, AddSelectedStudent.class);
                startActivity(intent);
//                finish();
            }
        });
    }
}