package com.google.contentproviderandroid;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.google.contentproviderandroid.ClientesProvider.CONTENT_URI;
import static com.google.contentproviderandroid.ClientesProvider.Clientes;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnInsertar;
    private Button btnConsultar;
    private Button btnEliminar;
    private TextView textResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClienteSQLiteHelper clientes = new ClienteSQLiteHelper(this, "DBClientes", null, 1);
        SQLiteDatabase conex = clientes.getWritableDatabase();

        for (int i = 0; i < 2; i++) {
            conex.execSQL("INSERT INTO CLIENTES(nombre, telefono, email) values('cliente" + i + "', '6456466545','cliente@email.com')");
        }

        conex.close();

        btnInsertar = (Button) findViewById(R.id.btnInsertar);
        btnInsertar.setOnClickListener(this);

        btnConsultar = (Button) findViewById(R.id.btnConsultar);
        btnConsultar.setOnClickListener(this);

        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(this);

        textResultado = (TextView) findViewById(R.id.textResultado);

    }


    @Override
    public void onClick(View v) {
        ContentResolver cr;

        switch (v.getId()) {
            case R.id.btnInsertar:
                ContentValues values = new ContentValues();
                values.put(Clientes.COL_NOMBRE, "ClienteN");
                values.put(Clientes.COL_TELEFONO, "99995565");
                values.put(Clientes.COL_EMAIL, "nuevo@email.com");

                cr = getContentResolver();
                cr.insert(CONTENT_URI, values);

                break;
            case R.id.btnConsultar:
                //Columnas de la tabla a recuperar
                String[] projection = new String[]{Clientes._ID, Clientes.COL_NOMBRE, Clientes.COL_TELEFONO, Clientes.COL_EMAIL};
                Uri clientesUri = CONTENT_URI;
                cr = getContentResolver();
                //Hacemos la consulta
                Cursor cursor = cr.query(clientesUri,
                        projection, //Columnas a devolver
                        null,       //Condicion del Query
                        null,       //Argumentos variables de la query
                        null);      //Orden de los resultados
                if (cursor.moveToFirst()) {
                    String nombre;
                    String telefono;
                    String email;

                    int colNombre = cursor.getColumnIndex(Clientes.COL_NOMBRE);
                    int colTelefono = cursor.getColumnIndex(Clientes.COL_TELEFONO);
                    int colEmail = cursor.getColumnIndex(Clientes.COL_EMAIL);

                    textResultado.setText("");

                    do {
                        nombre = cursor.getString(colNombre);
                        telefono = cursor.getString(colTelefono);
                        email = cursor.getString(colEmail);

                        textResultado.append(nombre + " - " + telefono + " - " + email + "\n");

                    } while (cursor.moveToNext());
                }
                break;
            case R.id.btnEliminar:
                cr = getContentResolver();
                cr.delete(CONTENT_URI, Clientes.COL_NOMBRE + " = 'ClienteN'", null);
                break;
            default:
                break;
        }
    }
}
