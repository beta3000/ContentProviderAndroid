package com.google.contentproviderandroid;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClienteSQLiteHelper clientes = new ClienteSQLiteHelper(this, "DBClientes", null, 1);
        SQLiteDatabase conex = clientes.getWritableDatabase();

        for (int i = 0; i < 10; i++) {
            conex.execSQL("INSERT INTO CLIENTES(nombre, telefono, email) values('cliente" + i + "', '6456466545','cliente@email.com')");
        }

        conex.close();
    }


}
