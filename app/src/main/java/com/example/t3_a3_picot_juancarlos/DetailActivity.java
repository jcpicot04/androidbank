package com.example.t3_a3_picot_juancarlos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.t3_a3_picot_juancarlos.fragments.DetailFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DetailActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView notext = (TextView) findViewById(R.id.notext);
        DetailFragment detall =
                (DetailFragment)getSupportFragmentManager()
                        .findFragmentById(R.id.frgDetalle);

        navigationView = (BottomNavigationView) findViewById(R.id.menu_navigation);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){

                    case R.id.zero:
                        if(getIntent().getStringExtra("TextoDetalle0").length() > 1){
                            notext.setText("");
                            detall.mostrarDetalle(getIntent().getStringExtra("TextoDetalle0"));
                        }else{
                            notext.setText("No hay movimientos de éste tipo.");
                            detall.mostrarDetalle(getIntent().getStringExtra("TextoDetalle0"));
                        }
                        break;
                    case R.id.uno:
                        if(getIntent().getStringExtra("TextoDetalle1").length() > 1){
                            notext.setText("");
                        detall.mostrarDetalle(getIntent().getStringExtra("TextoDetalle1"));
                        }else{
                            notext.setText("No hay movimientos de éste tipo.");
                            detall.mostrarDetalle(getIntent().getStringExtra("TextoDetalle1"));
                        }
                        break;
                    case R.id.dos:
                        if(getIntent().getStringExtra("TextoDetalle2").length() > 1){
                            notext.setText("");
                            detall.mostrarDetalle(getIntent().getStringExtra("TextoDetalle2"));
                        }else{
                            notext.setText("No hay movimientos de éste tipo.");
                            detall.mostrarDetalle(getIntent().getStringExtra("TextoDetalle2"));
                        }
                        break;
                    default:
                        if(getIntent().getStringExtra("TextoDetalle").length() > 1){
                            notext.setText("");
                        detall.mostrarDetalle(getIntent().getStringExtra("TextoDetalle"));
                        }else{
                            notext.setText("No hay movimientos de éste tipo.");
                            detall.mostrarDetalle(getIntent().getStringExtra("TextoDetalle"));
                        }
                }

                return false;
            }
        });
        detall.mostrarDetalle(getIntent().getStringExtra("TextoDetalle"));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frgDetalle,detall)
                .commit();
    }
}