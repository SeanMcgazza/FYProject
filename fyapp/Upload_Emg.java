package com.example.sean.fyapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.*;
import java.net.Socket;

public class Upload_Emg extends AppCompatActivity {

    public static final String hostname = "192.168.43.145" + "";
    public static final int portNumber = 8082;
    public static final String debugString = "Debug";

    public static String Recive = null;

    private  Socket socket = null;

    private Button upload;
    private Button Emg_layout;
    private EditText exercise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__emg);

        upload = (Button) findViewById(R.id.Upload);
        Emg_layout = (Button) findViewById(R.id.Emg_Layout);
        exercise = (EditText) findViewById(R.id.exercise);


        Intent in = getIntent();
        Bundle extras = in.getExtras();
        Recive = extras.getString("user_name", " ");

        final Intent i = new Intent(this, EMG_Layout.class);

        Emg_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread() {

                    @Override
                    public void run() {
                        try {

                            socket = new Socket(hostname, portNumber);
                            System.out.println("The name is ///////////////////////// ");


                            System.out.println("The name is //////////////******************8/////////// ");


                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            bw.write("Emg Request");
                            bw.newLine();
                            bw.flush();
                            System.out.println("The name is //////////////******11111111111111************8/////////// ");
                            String name = Recive;
                            String Exercise = exercise.getText().toString();
                            String array [] = {name,Exercise};

                            System.out.println("The name is " + name + "the exercise is " + Exercise);
                            bw.write(name);
                            bw.newLine();
                            bw.flush();

                            bw.write(Exercise);
                            bw.newLine();
                            bw.flush();

                        } catch (IOException e) {
                            Log.e(debugString, e.getMessage());
                        }

                    }
                }.start();
            }
        });

    }


}
