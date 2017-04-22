package com.example.sean.fyapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

        public Button Enter;
        public EditText password;
        public EditText userName;
        public String pass = "1234";
        UserLoginDataBase myLoginDB;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            UserLoginDataBase ub = new UserLoginDataBase(this);


            Enter = (Button) findViewById(R.id.button);
            password = (EditText) findViewById(R.id.editText2);
            userName = (EditText) findViewById(R.id.editText4);



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

                    System.out.println(userName.getText().toString()+
                            password.getText().toString());

                    boolean inserted =  myLoginDB.insertData(userName.getText().toString(),
                            password.getText().toString());
                    if (inserted = true){
                        Toast.makeText(MainActivity.this, "Data inserted" ,Toast.LENGTH_LONG);
                    }
                    else
                        Toast.makeText(MainActivity.this, "Data not inserted" ,Toast.LENGTH_LONG);

                    System.out.println(password.getText().toString());
                    System.out.println(pass.toString());

                    String pasword1 = password.getText().toString();

                    Context context = getApplicationContext();
                    CharSequence text = "Welcome!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);

                    if(pasword1.equals(pass.toString())) {
                        toast.show();
                        startActivity(i);
                    }


                }
            });


}
}

