package com.google.contentproviderandroid;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClienteSQLiteHelper extends SQLiteOpenHelper {

    private String query = "CREATE TABLE CLIENTES " +
            "(_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "NOMBRE TEXT, " +
            "TELEFONO TEXT, " +
            "EMAIL TEXT)";


    public ClienteSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
