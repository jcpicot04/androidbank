package com.example.t3_a3_picot_juancarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bd.MiBancoOperacional;
import com.pojo.Cliente;
import com.pojo.Cuenta;

import java.util.ArrayList;
import java.util.List;

public class global extends AppCompatActivity {

    private ListView lv;
    private MiBancoOperacional api;
    private Context cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);

        Cliente conectado = (Cliente) this.getIntent().getSerializableExtra("Cliente");
        api = MiBancoOperacional.getInstance(this);
        lv = (ListView) findViewById(R.id.accounts);
        cont=this;

        List<String> accounts = new ArrayList<String>();

        for(Cuenta c : api.getCuentas(conectado)) {
            String cuenta = "ES60-" + c.getBanco() + "-" + c.getSucursal() + "-" + c.getDc() + "-" + c.getNumeroCuenta() + "\n" + c.getSaldoActual();
            accounts.add(cuenta);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                accounts );

        lv.setAdapter(arrayAdapter);
    }
    }