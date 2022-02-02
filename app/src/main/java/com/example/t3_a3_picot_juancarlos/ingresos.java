package com.example.t3_a3_picot_juancarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bd.MiBancoOperacional;
import com.pojo.Cliente;
import com.pojo.Cuenta;
import com.pojo.Movimiento;

import java.util.ArrayList;
import java.util.List;

public class ingresos extends AppCompatActivity {

    private ListView lv;
    private MiBancoOperacional api;
    private Context cont;
    private ArrayList<Movimiento> message = new ArrayList<Movimiento>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresos);

        Cliente conectado = (Cliente) this.getIntent().getSerializableExtra("Cliente");
        api = MiBancoOperacional.getInstance(this);
        lv = (ListView) findViewById(R.id.ingresos);
        cont=this;

        List<String> accounts = new ArrayList<String>();
        for(Cuenta c : api.getCuentas(conectado)) {
            String cuenta = "ES60-" + c.getBanco() + "-" + c.getSucursal() + "-" + c.getDc() + "-" + c.getNumeroCuenta();
            accounts.add(cuenta);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String clickAccount = (String) parent.getItemAtPosition(position);
                message.clear();
                for(Cuenta c : api.getCuentas(conectado)) {
                    if(clickAccount.endsWith(c.getNumeroCuenta())){
                        for(Movimiento d : api.getMovimientos(c)){
                            if(d.getCuentaOrigen().getId() == c.getId() || d.getCuentaDestino().getId() == c.getId()){
                                message.add(d);
                            }
                        }
                    }
                }

                Intent intent = new Intent(ingresos.this, movimiento.class);
                intent.putExtra("ingresos", message);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                accounts );

        lv.setAdapter(arrayAdapter);
    }
}