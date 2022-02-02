package com.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class PromocionesSQLiteHelper extends SQLiteOpenHelper {
    private static int version = 1;
    private static String nombreBD = "PromocionesDB" ;
    private static SQLiteDatabase.CursorFactory factory = null;

    private String sqlCreacionTablaPromociones = "CREATE TABLE promociones(" +
            " _id INTEGER PRIMARY KEY," +
            " name TEXT, " +
            " description TEXT)";

    private String sqlIndiceDireccionPromociones = "CREATE UNIQUE INDEX namepromociones " +
            "ON promociones(name ASC)";

    private String[] sqlInsertPromociones = {
            "INSERT INTO promociones(rowid,_id, name, description) VALUES(null,null,'Tú y la ONG que elijas podréis recibir hasta 5.000€ cada uno','Participa en el sorteo con tus tarjetas JK. ¡Habrá 12 ganadores!')",
            "INSERT INTO promociones(rowid,_id, name, description) VALUES(null,null,'Consigue hasta 500€ para invertir al invitar a tus amigos','Al servicio de inversión automatizada JKvisor. Consulta condiciones y riesgos.')",
            "INSERT INTO promociones(rowid,_id, name, description) VALUES(null,null,'¡Tu apoyo les hace grandes!','Recupera hasta 10€ de tus compras en pequeño comercio con tu tarjeta de crédito.')",
            "INSERT INTO promociones(rowid,_id, name, description) VALUES(null,null,'¡Sorteamos un iPhone 13 Pro Max cada mes!','Participa con tu nómina o pensión domiciliada en JK Bank')",
            "INSERT INTO promociones(rowid,_id, name, description) VALUES(null,null,'Consigue hasta 2.000€ al invitar a tus amigos.','Invita a tus amigos a contratar o cambiar su hipoteca a JK Bank')"};




    public PromocionesSQLiteHelper(Context contexto) {
        super(contexto, nombreBD, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(this.getClass().toString(), "Creando tabla promociones");
        db.execSQL(sqlCreacionTablaPromociones);
        db.execSQL(sqlIndiceDireccionPromociones);

        Log.i(this.getClass().toString(), "Tabla promociones creada");
        Log.i(this.getClass().toString(), "Insertando datos iniciales");
        for(int i=0;i<sqlInsertPromociones.length;i++){
            db.execSQL(sqlInsertPromociones[i]);
        }
        Log.i(this.getClass().toString(), "Datos iniciales cargados");
        Log.i(this.getClass().toString(), "Base de datos inicial creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
