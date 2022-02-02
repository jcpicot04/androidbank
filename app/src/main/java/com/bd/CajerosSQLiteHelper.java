package com.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CajerosSQLiteHelper extends SQLiteOpenHelper {
    private static int version = 1;
    private static String nombreBD = "CajerosDB" ;
    private static SQLiteDatabase.CursorFactory factory = null;

    private String sqlCreacionTablaHipotecas = "CREATE TABLE cajeros(" +
            " _id INTEGER PRIMARY KEY," +
            " direccion TEXT, " +
            " lat TEXT, " +
            " lng TEXT," +
            " zom TEXT)";

    private String sqlIndiceDireccionCajeros = "CREATE UNIQUE INDEX direccioncajeros " +
            "ON cajeros(direccion ASC)";

    private String[] sqlInsertCajeros = {
            "INSERT INTO cajeros(rowid,_id, direccion, lat, lng, zom) VALUES(null,null,'Carrer del Clariano, 1, 46021 Valencia, Valencia, España',39.47600769999999,-0.3524475000000393,'')",
            "INSERT INTO cajeros(rowid,_id, direccion, lat, lng, zom) VALUES(null,null,'Avinguda del Cardenal Benlloch, 65, 46021 València, Valencia, España',39.4710366,-0.3547525000000178,'')",
            "INSERT INTO cajeros(rowid,_id, direccion, lat, lng, zom) VALUES(null,null,'Av. del Port, 237, 46011 València, Valencia, España',39.46161999999999,-0.3376299999999901,'')",
            "INSERT INTO cajeros(rowid,_id, direccion, lat, lng, zom) VALUES(null,null,'Carrer del Batxiller, 6, 46010 València, Valencia, España',39.4826729,-0.3639118999999482,'')",
            "INSERT INTO cajeros(rowid,_id, direccion, lat, lng, zom) VALUES(null,null,'Av. del Regne de València, 2, 46005 València, Valencia, España',39.4647669,-0.3732760000000326,'')"};




    public CajerosSQLiteHelper(Context contexto) {
        super(contexto, nombreBD, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(this.getClass().toString(), "Creando tabla cajeros");
        db.execSQL(sqlCreacionTablaHipotecas);
        db.execSQL(sqlIndiceDireccionCajeros);

        Log.i(this.getClass().toString(), "Tabla cajeros creada");
        Log.i(this.getClass().toString(), "Insertando datos iniciales");
        for(int i=0;i<sqlInsertCajeros.length;i++){
            db.execSQL(sqlInsertCajeros[i]);
        }
        Log.i(this.getClass().toString(), "Datos iniciales cargados");
        Log.i(this.getClass().toString(), "Base de datos inicial creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
