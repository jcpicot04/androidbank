package com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bd.PromocionesSQLiteHelper;

public class PromocionesDAO {
    public static final String C_TABLA = "promociones" ;
    public static final String C_COLUMNA_ID = "_id";
    public static final String C_COLUMNA_NAME = "name";
    public static final String C_COLUMNA_DESCRIPTION = "description";

    private Context contexto;
    private PromocionesSQLiteHelper dbHelper;
    private SQLiteDatabase db;

    private String[] columnas = new String[]{C_COLUMNA_ID, C_COLUMNA_NAME,
            C_COLUMNA_DESCRIPTION} ;

    public PromocionesDAO(Context context) {
        this.contexto = context;
    }

    public PromocionesDAO abrir(){
        dbHelper = new PromocionesSQLiteHelper(contexto);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbHelper.close();
    }

    public long insert(ContentValues reg) {
        if (db == null)
            abrir();
        return db.insert(C_TABLA, null, reg);
    }

    public Cursor getCursor() {
        Cursor c = db.query(false, C_TABLA, columnas, null, null, null, null, null,null);
        return c;
    }

    public Cursor getRegistro(long id) {
        String condicion = C_COLUMNA_ID + "=" + id;
        Cursor c = db.query( false, C_TABLA, columnas, condicion, null,
                null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public long update(ContentValues reg) {
        long result = 0;
        if (db == null)
            abrir();
        if (reg.containsKey(C_COLUMNA_ID)) {
            long id = reg.getAsLong(C_COLUMNA_ID);
            reg.remove(C_COLUMNA_ID);
            result = db.update(C_TABLA, reg, "_id=" + id, null);
        }
        return result;
    }

    public long delete(long id) {
        if (db == null)
            abrir();
        return db.delete(C_TABLA, "_id=" + id, null);
    }
}
