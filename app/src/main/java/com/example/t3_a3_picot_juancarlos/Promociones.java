package com.example.t3_a3_picot_juancarlos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dao.CajerosDAO;
import com.dao.PromocionesDAO;
import com.example.t3_a3_picot_juancarlos.adapters.PromocionesAdapter;
import com.example.t3_a3_picot_juancarlos.utils.Constantes;
import com.pojo.Cliente;

public class Promociones extends AppCompatActivity {

    private Button buttonlist;
    private Button buttonadd;
    private Cursor cursor;
    private RecyclerView mRecyclerView;
    private PromocionesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promociones);

        Cliente conectado = (Cliente) this.getIntent().getSerializableExtra("Cliente");
        buttonlist =(Button)findViewById(R.id.buttonlist2);
        buttonadd =(Button)findViewById(R.id.buttonadd2);

        buttonlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Promociones.this, promociones_list.class);
                startActivity(intent);
            }
        });
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Promociones.this, gestion_promociones.class);
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
        PromocionesDAO promocionesDAO = new PromocionesDAO(getBaseContext());
        promocionesDAO.abrir();
        PromocionesAdapter promocionesCursorAdapter = new PromocionesAdapter(this, promocionesDAO.getCursor());
        iniciarRecyclerView();
    }

    private void iniciarRecyclerView() {
        mRecyclerView = findViewById(R.id.lista2);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new PromocionesAdapter(this, cursor);

        mAdapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int position = mRecyclerView.getChildAdapterPosition(view) + 1;
                Toast.makeText(getApplicationContext(), "Selección: " + position, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Promociones.this, gestion_promociones.class);
                i.putExtra(Constantes.C_MODO, Constantes.C_VISUALIZAR);
                i.putExtra(PromocionesDAO.C_COLUMNA_ID, position);
                startActivityForResult(i, Constantes.C_VISUALIZAR);
            }
        });


        mRecyclerView.setAdapter(mAdapter);
    }
}