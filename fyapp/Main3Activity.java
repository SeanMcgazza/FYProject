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
import java.text.SimpleDateFormat;
import java.util.Date;


public class Main3Activity extends AppCompatActivity {

    public Button Exercises;
    public Button EMG;
    public Button Send;
    public EditText Text;
    public EditText SendText;

    public static final String hostname = "192.168.43.145" + "";
    public static final int portNumber = 8082;
    public static final String debugString = "Debug";

    public static String clientCommand = null;
    public static String Recive;
    public static String send;

   // public static String send = "chat";

    private  Socket socket = null;
    public BufferedReader br;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Exercises = (Button) findViewById(R.id.Exercises);
        EMG = (Button) findViewById(R.id.EMG);
        Send = (Button) findViewById(R.id.Send);

        Text = (EditText) findViewById(R.id.editText3);
        SendText = (EditText) findViewById(R.id.SendText);

        final Intent i = new Intent(this, EMG_Layout.class);
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Text.setText(Recive);
            }
        };

        Intent de = getIntent();
        Bundle extras = de.getExtras();
        send = extras.getString("user_name", " ");
        System.out.println("The name sent was " + send + "///////////////");

        new Thread() {

            @Override
            public void run() {

                try

                {
                    //Connecting...
                    Log.i(debugString, "Attempting to connect to server");
                    Log.i(debugString, String.valueOf(portNumber));
                    Log.i(debugString, hostname);
                    socket = new Socket(hostname, portNumber);
                    Log.i(debugString, "Connection established");

                    //Send message to server
                    //Send mesage to the client

                    //Send message to the Server
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bw.write("Get_Exercise\n");
                    bw.newLine();
                    bw.flush();



                    //Receive Message from Server
                  ;

                   // Recive = br.readLine();



                } catch (IOException e)

                {

                    Log.e(debugString, e.getMessage());
                }
                handler.sendEmptyMessage(0);
            }

        }.start();

       // final String arm = "Dumbbell Curl. The gold standard of biceps workouts in my book. ... Forearm Plank. ... \n Chair Dips. ... \n Balanced Core Strengthener. ... \n The Cobra. ... \n ";

        Exercises.setOnClickListener(new View.OnClickListener() {
            final Handler handler1 = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    Text.setText(clientCommand);
                }
            };
            @Override
            public void onClick(View view) {
                new Thread() {

                    @Override
                    public void run() {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss-dd:mm:yyyy");
                            String currentDateandTime = sdf.format(new Date());
                            System.out.println("This is the current date  " + currentDateandTime);

                            String pasword1 = "1234";
                            String username = send;
                            ObjectOutputStream out = null;
                            String name = username;
                            String Rep = pasword1;
                            String array [] = {username,pasword1,currentDateandTime};
                            out = new ObjectOutputStream(socket.getOutputStream());
                            out.writeObject(array);
                            out.flush();

                            BufferedReader in = null;

                            in = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()));

                            clientCommand = in.readLine();
                           // Text.setText(clientCommand);
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            bw.write("Get_Exercise\n");
                            bw.newLine();
                            bw.flush();
                        } catch (IOException e) {
                            Log.e(debugString, e.getMessage());
                        }
                        handler1.sendEmptyMessage(0);
                    }
                }.start();
            }
        });

//        Arms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Text.setText(arm);
//            }
//        });

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    send=SendText.getText().toString();

                new Thread() {
                    BufferedWriter bw = null;
                    @Override
                    public void run() {
                    try{
                        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                       // bw.write("This is a message from client:\n" + send);
                        bw.write(send);
                        bw.newLine();
                        bw.flush();

                    }
                     catch (IOException e) {
                         Log.e(debugString, e.getMessage());
                    }
                    }

                }.start();
            }
        });

        EMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(i);
            }
        });


    }

}
