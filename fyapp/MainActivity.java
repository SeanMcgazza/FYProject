package com.example.sean.fyapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

        public Button Enter;
        public Button New_user;
        public EditText password;
        public EditText userName;
        public String pass = "1234";
        UserLoginDataBase myLoginDB;

        public static final String hostname = "192.168.43.145" + "";
        public static final int portNumber = 8082;
        public static final String debugString = "Debug";

        public static String Recive = null;
        public static String send = "chat";

    public Socket socket = null;
    public BufferedWriter bw;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
           // UserLoginDataBase ub = new UserLoginDataBase(this);


            Enter = (Button) findViewById(R.id.button);
            New_user = (Button) findViewById(R.id.New_user);
            password = (EditText) findViewById(R.id.editText2);
            userName = (EditText) findViewById(R.id.editText4);


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


                        //Send message to the Server
                        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        bw.write("Logindatabase");
                        bw.newLine();
                        bw.flush();


                    } catch (IOException e)

                    {

                        Log.e(debugString, e.getMessage());
                    }
                }

            }.start();




            final Intent i = new Intent(this, Main2Activity.class);


            Enter.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

//                    Cursor res = myLoginDB.getAllData();
//
//                    if (res.getCount() == 0){
//
//                        return;
//                    }
//
//                    StringBuffer buffer_user = new StringBuffer();
//                    StringBuffer buffer_Password = new StringBuffer();
//
//                    String[] user = {};
//
//                    while(res.moveToNext()){
//                        buffer_user.append("User :" + res.getString(1) + "\n ");
//                        buffer_Password.append("Password :" + res.getString(2) + " \n");
//                    }
//
//
//                        String user_name = buffer_user.toString();
//
//                        System.out.println(user_name);

//                    System.out.println(userName.getText().toString()+
//                            password.getText().toString());
//
//                    boolean inserted =  myLoginDB.insertData(userName.getText().toString(),
//                            password.getText().toString());
//                    if (inserted = true){
//                        Toast.makeText(MainActivity.this, "Data inserted" ,Toast.LENGTH_LONG);
//                    }
//                    else
//                        Toast.makeText(MainActivity.this, "Data not inserted" ,Toast.LENGTH_LONG);
//
//                    System.out.println(password.getText().toString());
//                    System.out.println(pass.toString());

                    String pasword1;

                    new Thread() {

                        @Override
                        public void run() {
                            try {

                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss-dd:mm:yyyy");
                                String currentDateandTime = sdf.format(new Date());
                                System.out.println("This is the current date  " + currentDateandTime);

                                String pasword1 = password.getText().toString();
                                String username = userName.getText().toString();
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

                                String clientCommand = in.readLine();

                                System.out.println("The client sent back"+clientCommand);
                             //   Toast.makeText(MainActivity.this, "User Checked" ,Toast.LENGTH_LONG);


                                if(clientCommand.equals("Correct")){
                                    showToast("Welcome");

                                    Bundle extras = new Bundle();
                                    extras.putString("user_name",username);
                                    i.putExtras(extras);
                                    startActivity(i);
                                }

                                if(clientCommand.equals("NewUser")){
                                    //   toast.show();
                                    showToast("New User");
                                    startActivity(i);
                                }

                                if(clientCommand.equals("Password Incorect")){
                                    //   toast.show();
                                    showToast(clientCommand);
//

                                    incorecct();
                                }



                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }.start();




//                    if(pasword1.equals(pass.toString())) {
//                        toast.show();
//                        startActivity(i);
//                    }


                }
            });


}

    private void incorecct() {
        System.out.println("Got into the incorrect function");
//        Toast.makeText(new MainActivity(), "This is my Toast message!",
//                                            Toast.LENGTH_LONG).show();
        final Intent i1 = new Intent(this, MainActivity.class);
        startActivity(i1);
    }

    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

