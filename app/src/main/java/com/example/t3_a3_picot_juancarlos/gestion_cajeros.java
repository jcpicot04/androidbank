package com.example.t3_a3_picot_juancarlos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dao.CajerosDAO;
import com.example.t3_a3_picot_juancarlos.utils.Constantes;

public class gestion_cajeros extends AppCompatActivity {

    private CajerosDAO cajerosDAO;
    private Cursor cursor;
    private int modo;
    private long id;
    private EditText address;
    private EditText lat;
    private EditText lng;
    private Button boton_guardar;
    private Button boton_cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_cajeros);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        Toolbar toolbar = findViewById(R.id.appbarCajeros);
        setSupportActionBar(toolbar);
        boton_guardar = (Button) findViewById(R.id.buttonsave3);
        boton_cancelar = (Button) findViewById(R.id.buttoncancel3);
        System.out.println(extra.getInt(Constantes.C_MODO));
        if (extra == null) return;
        address = (EditText) findViewById(R.id.address);
        lat = (EditText) findViewById(R.id.lat);
        lng = (EditText) findViewById(R.id.lng);

        cajerosDAO = new CajerosDAO(this);
        try {
            cajerosDAO.abrir();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (extra.containsKey(CajerosDAO.C_COLUMNA_ID)) {
            id = this.getIntent().getIntExtra(CajerosDAO.C_COLUMNA_ID, 1);
            consultar(id);
        }
        establecerModo(extra.getInt(Constantes.C_MODO));
        boton_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        boton_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });
    }
    private void establecerModo(int m) {
        this.modo = m ;
        if (modo == Constantes.C_VISUALIZAR) {
            this.setTitle(address.getText().toString());
            this.setEdicion(false);
        }
        if (modo == Constantes.C_CREAR){
            this.setTitle(R.string.menu_crear);
            this.setEdicion(true);
        }
        if (modo == Constantes.C_EDITAR) {
            this.setTitle(R.string.atm_updated);
            this.setEdicion(true);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cajeros, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_actualizar:
                establecerModo(Constantes.C_EDITAR);
                return true;
            case R.id.menu_eliminar:
                borrar(id);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("Range")
    private void consultar(long id) {
        cursor = cajerosDAO.getRegistro(id);
        address.setText(cursor.getString(
                cursor.getColumnIndex(CajerosDAO.C_COLUMNA_DIRECCION)));
        lat.setText(cursor.getString(
                cursor.getColumnIndex(CajerosDAO.C_COLUMNA_LAT)));
        lng.setText(cursor.getString(
                cursor.getColumnIndex(CajerosDAO.C_COLUMNA_LNG)));

    }

    private void setEdicion(boolean opcion) {
        address.setEnabled(opcion);
        lat.setEnabled(opcion);
        lng.setEnabled(opcion);
    }

    private void guardar(){
        ContentValues reg = new ContentValues();
        if (modo == Constantes.C_EDITAR) {
            reg.put(CajerosDAO.C_COLUMNA_ID, id);
        }

        reg.put(CajerosDAO.C_COLUMNA_DIRECCION, address.getText().toString());
        reg.put(CajerosDAO.C_COLUMNA_LAT, lat.getText().toString());
        reg.put(CajerosDAO.C_COLUMNA_LNG, lng.getText().toString());
        if (modo == Constantes.C_CREAR){
            cajerosDAO.insert(reg);
            Toast.makeText(gestion_cajeros.this, R.string.atm_created, Toast.LENGTH_SHORT).show();
        }
        if (modo == Constantes.C_EDITAR) {
            Toast.makeText(gestion_cajeros.this, R.string.atm_updated, Toast.LENGTH_SHORT).show();
            cajerosDAO.update(reg);
        }
        setResult(RESULT_OK);
        finish();
    }
    private void cancelar(){
        setResult(RESULT_CANCELED, null);
        finish();
    }

    private void borrar(final long id) {
        AlertDialog.Builder dgEliminar = new AlertDialog.Builder(this);
        dgEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dgEliminar.setTitle(getResources().getString(R.string.atm_eliminar_titulo));
        dgEliminar.setMessage(getResources().getString(R.string.atm_eliminar_msg));
        dgEliminar.setCancelable(false);
        dgEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int boton) {
                        cajerosDAO.delete(id);
                        Toast.makeText(gestion_cajeros.this,
                                R.string.atm_eliminar_confirmacion,
                                Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
        dgEliminar.setNegativeButton(android.R.string.no, null);
        dgEliminar.show();
    }


}