package com.example.t3_a3_picot_juancarlos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bd.CajerosSQLiteHelper;
import com.dao.CajerosDAO;
import com.example.t3_a3_picot_juancarlos.adapters.CajerosAdapter;
import com.example.t3_a3_picot_juancarlos.utils.Constantes;

public class CajerosList extends AppCompatActivity {

    private CajerosDAO cajerosDAO;
    private Cursor cursor;
    private RecyclerView mRecyclerView;
    private CajerosAdapter mAdapter;
    private TextView txtSinDatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cajeros_list);
        cajerosDAO = new CajerosDAO(this);
        try {
            cajerosDAO.abrir();
            cursor = cajerosDAO.getCursor();
            System.out.println("cursor: " + cursor.toString());
            startManagingCursor(cursor);
            iniciarRecyclerView();

            if(cursor.getCount()>0){
                txtSinDatos = (TextView) findViewById(R.id.txtSinDatos);
                txtSinDatos.setVisibility(View.INVISIBLE);
                txtSinDatos.invalidate();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(),"Se ha producido un error al abrir la base de datos.",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        }

        private void iniciarRecyclerView() {
        mRecyclerView = findViewById(R.id.lista);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
        layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new CajerosAdapter(this, cursor);

            mAdapter.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = mRecyclerView.getChildAdapterPosition(view) + 1;
                    Toast.makeText(getApplicationContext(), "Selección: " + position, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CajerosList.this, gestion_cajeros.class);
                    i.putExtra(Constantes.C_MODO, Constantes.C_VISUALIZAR);
                    i.putExtra(CajerosDAO.C_COLUMNA_ID, position);
                    startActivityForResult(i, Constantes.C_VISUALIZAR);
                }
            });


            mRecyclerView.setAdapter(mAdapter);
        }

}