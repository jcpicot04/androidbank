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
import com.dao.PromocionesDAO;
import com.example.t3_a3_picot_juancarlos.utils.Constantes;

public class gestion_promociones extends AppCompatActivity {

    private PromocionesDAO promocionesDAO;
    private Cursor cursor;
    private int modo;
    private long id;
    private EditText name;
    private EditText description;
    private Button boton_guardar;
    private Button boton_cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_promociones);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        Toolbar toolbar = findViewById(R.id.appbarPromociones);
        setSupportActionBar(toolbar);
        boton_guardar = (Button) findViewById(R.id.buttonsave3);
        boton_cancelar = (Button) findViewById(R.id.buttoncancel3);
        System.out.println(extra.getInt(Constantes.C_MODO));
        if (extra == null) return;
        name = (EditText) findViewById(R.id.promo_name_value);
        description = (EditText) findViewById(R.id.promo_desc_value);

        promocionesDAO = new PromocionesDAO(this);
        try {
            promocionesDAO.abrir();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (extra.containsKey(PromocionesDAO.C_COLUMNA_ID)) {
            id = this.getIntent().getIntExtra(PromocionesDAO.C_COLUMNA_ID, 1);
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
            this.setTitle(name.getText().toString());
            this.setEdicion(false);
        }
        if (modo == Constantes.C_CREAR){
            this.setTitle(R.string.menu_crear2);
            this.setEdicion(true);
        }
        if (modo == Constantes.C_EDITAR) {
            this.setTitle(R.string.promo_updated);
            this.setEdicion(true);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_promociones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_actualizar2:
                establecerModo(Constantes.C_EDITAR);
                return true;
            case R.id.menu_eliminar2:
                borrar(id);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("Range")
    private void consultar(long id) {
        cursor = promocionesDAO.getRegistro(id);
        name.setText(cursor.getString(
                cursor.getColumnIndex(PromocionesDAO.C_COLUMNA_NAME)));
        description.setText(cursor.getString(
                cursor.getColumnIndex(PromocionesDAO.C_COLUMNA_DESCRIPTION)));
    }

    private void setEdicion(boolean opcion) {
        name.setEnabled(opcion);
        description.setEnabled(opcion);
    }

    private void guardar(){
        ContentValues reg = new ContentValues();
        if (modo == Constantes.C_EDITAR) {
            reg.put(CajerosDAO.C_COLUMNA_ID, id);
        }

        reg.put(PromocionesDAO.C_COLUMNA_NAME, name.getText().toString());
        reg.put(PromocionesDAO.C_COLUMNA_DESCRIPTION, description.getText().toString());
        if (modo == Constantes.C_CREAR){
            promocionesDAO.insert(reg);
            Toast.makeText(gestion_promociones.this, R.string.promo_created, Toast.LENGTH_SHORT).show();
        }
        if (modo == Constantes.C_EDITAR) {
            Toast.makeText(gestion_promociones.this, R.string.promo_updated, Toast.LENGTH_SHORT).show();
            promocionesDAO.update(reg);
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
        dgEliminar.setTitle(getResources().getString(R.string.promo_eliminar_titulo));
        dgEliminar.setMessage(getResources().getString(R.string.promo_eliminar_msg));
        dgEliminar.setCancelable(false);
        dgEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int boton) {
                promocionesDAO.delete(id);
                Toast.makeText(gestion_promociones.this,
                        R.string.promo_eliminar_confirmacion,
                        Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
        dgEliminar.setNegativeButton(android.R.string.no, null);
        dgEliminar.show();
    }
}