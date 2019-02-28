package com.akihitoq.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class dbManage extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbUser.db";

    private static final String SQL_CREATE_TABLE_USER = "CREATE TABLE USER(username TEXT, password TEXT)";

    public dbManage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int insertUser(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",password);

        long result = db.insert("user",null,values);

        return (int) result;
    }

    public ArrayList<ArrayList<String>> selectUser(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "USER"
                ,null
                ,null
                , null
                , null,
                null,
                null
        );

        ArrayList<ArrayList<String>> result = new ArrayList<>();
        while (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

            ArrayList<String> result_row = new ArrayList<>();
            result_row.add(username);
            result_row.add(password);
            result.add(result_row);
        }
        return result;
    }

    public int deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("USER","username =? ",new String[]{username});
        return result;
    }
}
