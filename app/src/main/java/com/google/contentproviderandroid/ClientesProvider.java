package com.google.contentproviderandroid;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

public class ClientesProvider extends ContentProvider {
    private static final String uri = "content://com.google.contentproviderandroid/CLIENTES";
    public static final Uri CONTENT_URI = Uri.parse(uri);
    private static final String BD_NOMBRE = "DBClientes";
    private static final int BD_VERSION = 1;
    private static final String TABLA_NOMBRE = "Clientes";
    //UriMatcher
    private static final int CLIENTES = 1;
    private static final int CLIENTES_ID = 2;
    private static final UriMatcher URI_MATCHER;

    //Inicializamos el UriMatcher
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI("com.google.contentproviderandroid", "CLIENTES", CLIENTES);
        URI_MATCHER.addURI("com.google.contentproviderandroid", "CLIENTES/#", CLIENTES_ID);
    }

    private ClienteSQLiteHelper db;

    @Override
    public boolean onCreate() {
        db = new ClienteSQLiteHelper(getContext(), BD_NOMBRE, null, BD_VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //Si es una consulta a una ID concreto contruimos el where
        String where = selection;
        if (URI_MATCHER.match(uri) == CLIENTES_ID) {
            where = "_ID=" + uri.getLastPathSegment();
        }
        SQLiteDatabase access = db.getWritableDatabase();
        Cursor c = access.query(TABLA_NOMBRE, projection, where, selectionArgs, null, null, sortOrder);

        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long regId = 1;
        SQLiteDatabase access = db.getWritableDatabase();
        regId = access.insert(TABLA_NOMBRE, null, values);
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);

        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cont;
        //Si es una consulta a una ID concreto contruimos el where
        String where = selection;
        if (URI_MATCHER.match(uri) == CLIENTES_ID) {
            where = "_ID=" + uri.getLastPathSegment();
        }
        SQLiteDatabase access = db.getWritableDatabase();
        cont = access.delete(TABLA_NOMBRE, where, selectionArgs);

        return cont;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int cont;
        //Si es una consulta a una ID concreto contruimos el where
        String where = selection;
        if (URI_MATCHER.match(uri) == CLIENTES_ID) {
            where = "_ID=" + uri.getLastPathSegment();
        }
        SQLiteDatabase access = db.getWritableDatabase();
        cont = access.update(TABLA_NOMBRE, values, where, selectionArgs);

        return cont;
    }

    public static final class Clientes implements BaseColumns {
        //Nombres de columnas
        public static final String COL_NOMBRE = "NOMBRE";
        public static final String COL_TELEFONO = "TELEFONO";
        public static final String COL_EMAIL = "EMAIL";

        private Clientes() {
        }
    }

}
