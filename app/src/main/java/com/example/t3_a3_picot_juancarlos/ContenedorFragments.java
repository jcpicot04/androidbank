package com.example.t3_a3_picot_juancarlos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bd.MiBancoOperacional;
import com.example.t3_a3_picot_juancarlos.fragments.CuentasListener;
import com.example.t3_a3_picot_juancarlos.fragments.DetailFragment;
import com.example.t3_a3_picot_juancarlos.fragments.ListFragment;
import com.pojo.Cliente;
import com.pojo.Cuenta;
import com.pojo.Movimiento;

import java.util.ArrayList;
import java.util.List;

public class ContenedorFragments extends AppCompatActivity implements CuentasListener {

    private MiBancoOperacional api;
    private Context cont;
    private ArrayList<Movimiento> message = new ArrayList<Movimiento>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_fragments);

        Cliente conectado = (Cliente) this.getIntent().getSerializableExtra("Cliente");
        api = MiBancoOperacional.getInstance(this);

        List<String> accounts = new ArrayList<String>();
        ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
        for(Cuenta c : api.getCuentas(conectado)) {
            String cuenta = "ES60-" + c.getBanco() + "-" + c.getSucursal() + "-" + c.getDc() + "-" + c.getNumeroCuenta();
            cuentas.add(c);
            accounts.add(cuenta);
        }

        ListFragment listFragment = ListFragment.newInstance(cuentas);
        FragmentTransaction transaction = getSupportFragmentManager()
                                          .beginTransaction()
                                          .replace(R.id.frgListado, listFragment);
        listFragment.setCuentasListener(this);
        transaction.commit();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCuentaSeleccionada(Cuenta c) {
        String mov = "";
        String mov0 = "";
        String mov1 = "";
        String mov2= "";
        ArrayList<String> movements = new ArrayList<String>();
        ArrayList<String> movements0 = new ArrayList<String>();
        ArrayList<String> movements1 = new ArrayList<String>();
        ArrayList<String> movements2 = new ArrayList<String>();
        api = MiBancoOperacional.getInstance(this);
        for(Movimiento m : api.getMovimientos(c)){
            movements.add(m.toString());
        }
        mov = String.join("\n\n",movements);
        for(Movimiento m : api.getMovimientosTipo(c,0)){
            movements0.add(m.toString());
        }
        mov0 = String.join("\n\n",movements0);

        for(Movimiento m : api.getMovimientosTipo(c,1)){
            movements1.add(m.toString());
        }
        mov1 = String.join("\n\n",movements1);
        for(Movimiento m : api.getMovimientosTipo(c,2)){
            movements2.add(m.toString());
        }
        mov2 = String.join("\n\n",movements2);

        boolean hayDetalle =
                (getSupportFragmentManager().findFragmentById(R.id.frgDetalle) != null);

        if(hayDetalle) {
            ((DetailFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.frgDetalle)).mostrarDetalle(mov);
        } else {
            Intent i = new Intent(this, DetailActivity.class);
            i.putExtra("TextoDetalle", mov);
            i.putExtra("TextoDetalle0", mov0);
            i.putExtra("TextoDetalle1", mov1);
            i.putExtra("TextoDetalle2", mov2);
            startActivity(i);
        }

    }

}