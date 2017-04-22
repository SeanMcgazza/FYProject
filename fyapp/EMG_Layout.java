package com.example.sean.fyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class EMG_Layout extends AppCompatActivity {

    private Button F_upper;
    private Button B_upper;
    private Button F_lower;
    private Button B_lower;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emg__layout);

        F_upper = (Button) findViewById(R.id.F_upper);
        B_upper = (Button) findViewById(R.id.B_upper);
        F_lower = (Button) findViewById(R.id.F_lower);
        B_lower = (Button) findViewById(R.id.B_lower);

        imageView = (ImageView) findViewById(R.id.imageView);

        F_upper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageResource(R.drawable.front_upper);
            }
        });

        B_upper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageResource(R.drawable.back_upper);
            }
        });

        F_lower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageResource(R.drawable.front_lower);
            }
        });

        B_lower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageResource(R.drawable.back_lower);
            }
        });
    }
}
