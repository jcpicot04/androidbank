package com.example.t3_a3_picot_juancarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bd.MiBancoOperacional;
import com.pojo.Cliente;

public class login extends AppCompatActivity {

    private Button buttonopen;
    EditText nif;
    EditText contra;
    MiBancoOperacional api;
    Context context;
    ProgressBar bar;
    TextView carga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        api = MiBancoOperacional.getInstance(this);
        context = this.getApplicationContext();
        buttonopen = (Button) findViewById(R.id.buttonop);
        nif = (EditText) findViewById(R.id.editTextTextPersonName);
        contra = (EditText) findViewById(R.id.repeatpass);
        bar = (ProgressBar) findViewById(R.id.determinateBar);
        carga = (TextView) findViewById(R.id.cargando);

            buttonopen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cliente a = new Cliente();
                    a.setNif(nif.getText().toString());
                    a.setClaveSeguridad(contra.getText().toString());
                    ProcesarUsuario procesarUsuario = new ProcesarUsuario();
                    procesarUsuario.execute(a);
                }
            });
        }
    class ProcesarUsuario extends AsyncTask<Cliente,String,Cliente>{

        @Override
        protected Cliente doInBackground(Cliente... c) {
            Cliente conectado = api.login(c[0]);
            runOnUiThread(new Runnable() {
                public void run() {
                    carga.setText("Procesando... ");
                }
            });
            if(conectado != null){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, conectado.getNif() + " ha iniciado sesión.", Toast.LENGTH_SHORT).show();
                    }
                });
                for (int i= 0; i<15;i++){
                    SystemClock.sleep(100);
                    bar.incrementProgressBy(i);
                }
                Intent intent = new Intent(login.this, principal.class);
                intent.putExtra("Cliente", conectado);
                startActivity(intent);
            }else{
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, "El usuario o la contraseña no son correctos.", Toast.LENGTH_SHORT).show();
                        carga.setText("Vuelve a intentarlo. ");
                    }
                });
            }
            return conectado;
        }
        protected void onPostExecute(Cliente c) {
            if (c != null) {
                carga.setText("Bienvenido " + c.getNombre());
            }
        }
    }

}