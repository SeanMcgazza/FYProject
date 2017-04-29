package com.example.sean.fyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    private Button btnExit;
    private Button DB;
    private Button Exercise;
    private Button upload;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        btnExit = (Button) findViewById(R.id.Back);
        DB = (Button) findViewById(R.id.DB);
        Exercise = (Button) findViewById(R.id.Exercises);


        final Intent i = new Intent(this, MainActivity.class);
        final Intent e = new Intent(this, Main3Activity.class);
        final Intent d = new Intent(this, Main4ActivityDB.class);


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(i);
            }
        });

        Exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = getIntent().getExtras().getString("user_name", " ");
                System.out.println("The bundle is " + user_name);
                Bundle extras = new Bundle();
                extras.putString("user_name",user_name);
                e.putExtras(extras);
                startActivity(e);
            }
        });

        DB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("The bunde is //////////////////////////////////////" );
                String user_name = getIntent().getExtras().getString("user_name", " ");
                System.out.println("The bunde is" + user_name);
                Bundle extras = new Bundle();
                extras.putString("user_name",user_name);

                d.putExtras(extras);
                startActivity(d);
            }
        });


    }
}
