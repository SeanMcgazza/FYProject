package com.example.sean.fyapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.*;
import java.net.Socket;

public class Main4ActivityDB extends AppCompatActivity {

    DataBase myDb;
    EditText Name ,rep , marks, id;
    Button Add , view, update, delete;

    public static final String hostname = "192.168.43.145" + "";
    public static final int portNumber = 8082;
    public static final String debugString = "Debug";

    public static String Recive = null;
    public static String send = "chat";

    private  Socket socket = null;
    public BufferedWriter bw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4_activity_db);
        myDb = new DataBase(this);

        Name = (EditText)findViewById(R.id.Name);
        rep = (EditText)findViewById(R.id.Reps);
        marks = (EditText)findViewById(R.id.Rate);
        id = (EditText)findViewById(R.id.ID);
        Add = (Button) findViewById(R.id.update);
        view = (Button) findViewById(R.id.View);
        update = (Button) findViewById(R.id.updb);
        delete = (Button) findViewById(R.id.Delete);

        AddData();
        viewAll();
        updateData();
        Delete();

        Intent in = getIntent();
        Bundle extras = in.getExtras();
        Recive = extras.getString("user_name", " ");
        System.out.println("The bunde is" + Recive + "///////////////////////////////////////");

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
                    bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bw.write("DataBase");
                    bw.newLine();
                    bw.flush();


                } catch (IOException e)

                {

                    Log.e(debugString, e.getMessage());
                }
            }

        }.start();

    }

    public void updateData(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isupdated = myDb.update(id.getText().toString(),
                        Name.getText().toString(),
                        rep.getText().toString(),
                        marks.getText().toString());



//                objects[0] = id.getText().toString();
//                objects[1] = Name.getText().toString();
//                objects[2] = rep.getText().toString();
//                objects[3] = marks.getText().toString();




                if(isupdated ==true){
                    Toast.makeText(Main4ActivityDB.this, "Data updated" ,Toast.LENGTH_LONG);
                }
                else{
                    Toast.makeText(Main4ActivityDB.this, "Data not updated" ,Toast.LENGTH_LONG);
            }

            }
        });
    }

    public void Delete(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleteRow = myDb.deleteData(id.getText().toString());
                if(deleteRow > 0){
                    Toast.makeText(Main4ActivityDB.this, "Data inserted" ,Toast.LENGTH_LONG);
                }
                else
                    Toast.makeText(Main4ActivityDB.this, "Data not inserted" ,Toast.LENGTH_LONG);
            }


        });
    }

    public void AddData(){
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              boolean inserted =  myDb.insertData(Name.getText().toString(),
                        rep.getText().toString(),
                        Add.getText().toString());

                System.out.println("Got into the add data");
//                if (inserted = true){
//                    Toast.makeText(Main4ActivityDB.this, "Data inserted" ,Toast.LENGTH_LONG);
//                }
//                else
//                    Toast.makeText(Main4ActivityDB.this, "Data not inserted" ,Toast.LENGTH_LONG);

                new Thread() {

                    @Override
                    public void run() {
                        try {

                            System.out.println("Got into the thread");
                            ObjectOutputStream out = null;
                            Object objects = new Object();
                            String name = Name.getText().toString();
                            String Rep = rep.getText().toString();
                            String rate = marks.getText().toString();
                            String ID = Recive;
                            String comment=id.getText().toString();
                            String array [] = {name,Rep,rate,ID,comment};
                            objects = new String[][]{array};
//                            bw.write("Harry");
//                            bw.newLine();
//                            bw.flush();
                            out = new ObjectOutputStream(socket.getOutputStream());
                            out.writeObject(array);
                            out.flush();
                            System.out.println("Sent data");
                            bw.write("DataBase");
                            bw.newLine();
                            bw.flush();

                            showToast("Data uploaded to DataBase");
                            //Toast.makeText(Main4ActivityDB.this, "Data inserted" ,Toast.LENGTH_LONG);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }.start();
            }

        });
    }
    public void viewAll(){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();

                if (res.getCount() == 0){
                    showMessage("Error","No data found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while(res.moveToNext()){
                    buffer.append("Id : " + res.getString(0) + "\n ");
                    buffer.append("Name :" + res.getString(1) + "\n ");
                    buffer.append("Rep :" + res.getString(2) + " \n");
                    buffer.append("Rate :" + res.getString(3) + "\n\n " );
                }

                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(Main4ActivityDB.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
