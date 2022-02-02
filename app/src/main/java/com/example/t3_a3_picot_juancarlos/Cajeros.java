package com.example.t3_a3_picot_juancarlos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bd.CajerosSQLiteHelper;
import com.dao.CajerosDAO;
import com.example.t3_a3_picot_juancarlos.adapters.CajerosAdapter;
import com.example.t3_a3_picot_juancarlos.utils.Constantes;

public class Cajeros extends AppCompatActivity {

    private Button buttonlist;
    private Button buttonadd;
    private Cursor cursor;
    private RecyclerView mRecyclerView;
    private CajerosAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cajeros);

        buttonlist =(Button)findViewById(R.id.buttonlist);
        buttonadd =(Button)findViewById(R.id.buttonadd);
        buttonlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cajeros.this, CajerosList.class);
                startActivity(intent);
            }
        });
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cajeros.this, gestion_cajeros.class);
                intent.putExtra(Constantes.C_MODO, Constantes.C_CREAR);
                startActivityForResult(intent, Constantes.C_CREAR);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case Constantes.C_CREAR:
                if (resultCode == RESULT_OK)
                    recargar_lista();
            case Constantes.C_VISUALIZAR:
                if (resultCode == RESULT_OK)
                    recargar_lista();
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void recargar_lista() {
        CajerosDAO cajerosDAO = new CajerosDAO(getBaseContext());
        cajerosDAO.abrir();
        CajerosAdapter cajerosCursorAdapter = new CajerosAdapter(this, cajerosDAO.getCursor());
        iniciarRecyclerView();
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
                Intent i = new Intent(Cajeros.this, gestion_cajeros.class);
                i.putExtra(Constantes.C_MODO, Constantes.C_VISUALIZAR);
                i.putExtra(CajerosDAO.C_COLUMNA_ID, position);
                startActivityForResult(i, Constantes.C_VISUALIZAR);
            }
        });


        mRecyclerView.setAdapter(mAdapter);
    }
}