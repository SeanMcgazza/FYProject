package com.example.sean.fyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sean on 01/04/2017.
 */
public class DataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Exercise.db";
    public static final String TABLE_NAME = "Exercise_table";
    public static final String COL_1= "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "REP";
    public static final String COL_4 = "MARKS";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT,REP TEXT,MARKS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String rep, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,rep);
        contentValues.put(COL_4,marks);

        long result =  db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return false;
        }
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME , null);
        return  res;
    }

    public boolean update(String id, String name, String rep, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,rep);
        contentValues.put(COL_4,marks);
        db.update(TABLE_NAME,contentValues,"id = ?", new String[] {id});
        return true;
    }

    public Integer deleteData (String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[]{ID});
    }

}
