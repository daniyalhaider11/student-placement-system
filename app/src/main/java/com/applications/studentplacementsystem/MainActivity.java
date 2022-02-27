package com.applications.studentplacementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Thread timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img=findViewById(R.id.img);
        img.animate().scaleX(2).scaleY(2).setDuration(5000).start();
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep( 5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent i=new Intent(getApplicationContext(), LoginArea.class);
                    startActivity(i);
                }

            }
        };
        timer.start();
    }
}