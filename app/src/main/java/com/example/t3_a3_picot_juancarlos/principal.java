package com.example.t3_a3_picot_juancarlos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.bd.CajerosSQLiteHelper;
import com.pojo.Cliente;

public class principal extends AppCompatActivity {

    private Button buttonpass;
    private Button buttonhome;
    private Button buttonorder;
    private Button buttonglobal;
    private Button buttoningresos;
    private Button buttonatm;
    private Button buttonpromo;
    private TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Cliente conectado = (Cliente) this.getIntent().getSerializableExtra("Cliente");
        CajerosSQLiteHelper dbHelper = new CajerosSQLiteHelper(getBaseContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Toast.makeText(getBaseContext(), "Base de datos preparada",Toast.LENGTH_LONG).show();
        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        buttonpass = (Button)findViewById(R.id.changepass);
        buttonhome = (Button)findViewById(R.id.buttonhome);
        buttonorder = (Button)findViewById(R.id.buttonorder);
        buttonglobal = (Button)findViewById(R.id.buttonglobal2);
        buttoningresos = (Button)findViewById(R.id.buttoningresos);
        buttonatm = (Button)findViewById(R.id.buttonatm);
        buttonpromo = (Button)findViewById(R.id.buttonpromo);
        user = (TextView)findViewById(R.id.textAccount);
        user.setText(conectado.getNif());
        buttonpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(principal.this, changepass.class);
                intent.putExtra("Cliente", conectado);
                startActivity(intent);
            }
        });

        buttonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(principal.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(principal.this, ordertrans.class);
                intent.putExtra("Cliente", conectado);
                startActivity(intent);
            }
        });

        buttonglobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(principal.this, global.class);
                intent.putExtra("Cliente", conectado);
                startActivity(intent);
            }
        });

        buttoningresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(principal.this, ContenedorFragments.class);
                intent.putExtra("Cliente", conectado);
                startActivity(intent);
            }
        });

        buttonatm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(principal.this, Cajeros.class);
                intent.putExtra("Cliente", conectado);
                startActivity(intent);
            }
        });

        buttonpromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(conectado.getNombre());
                if(conectado.getNombre().equals("Admin")) {
                    Intent intent = new Intent(principal.this, Promociones.class);
                    intent.putExtra("Cliente", conectado);
                    startActivity(intent);
                }else{
                    Toast.makeText(getBaseContext(), "Este usuario no tiene acceso a ésta sección",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Cliente conectado = (Cliente) this.getIntent().getSerializableExtra("Cliente");
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent sett = new Intent(this, SettingsActivity.class);
                sett.putExtra("Cliente", conectado);
                startActivity(sett);
                return true;
            case R.id.action_ordertransfers:
                Intent trans = new Intent(this, ordertrans.class);
                trans.putExtra("Cliente", conectado);
                startActivity(trans);
                return true;
            case R.id.action_global:
                Intent glo = new Intent(this, global.class);
                glo.putExtra("Cliente", conectado);
                startActivity(glo);
                return true;
            case R.id.action_movements:
                Intent mov = new Intent(this, ingresos.class);
                mov.putExtra("Cliente", conectado);
                startActivity(mov);
                return true;
            case R.id.action_changepwd:
                Intent pwd = new Intent(this, changepass.class);
                pwd.putExtra("Cliente", conectado);
                startActivity(pwd);
                return true;
            case R.id.action_home:
                Intent home = new Intent(this, MainActivity.class);
                home.putExtra("Cliente", conectado);
                startActivity(home);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}