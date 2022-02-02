package com.example.t3_a3_picot_juancarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bd.MiBancoOperacional;
import com.pojo.Cliente;

public class changepass extends AppCompatActivity {

    private Button buttonop;
    private EditText oldpass;
    private EditText newpass;
    private EditText repeatpass;
    private MiBancoOperacional api;
    private Context cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);

        Cliente conectado = (Cliente) this.getIntent().getSerializableExtra("Cliente");

        api = MiBancoOperacional.getInstance(this);
        cont=this;
        buttonop = (Button)findViewById(R.id.buttonop);
        oldpass = findViewById(R.id.oldpass);
        newpass = findViewById(R.id.newpass);
        repeatpass = findViewById(R.id.repeatpass);
        buttonop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oldpass.getText().toString().equals(conectado.getClaveSeguridad())){
                    if(newpass.getText().toString().equals(repeatpass.getText().toString())){
                        conectado.setClaveSeguridad(newpass.getText().toString());
                        if(api.changePassword(conectado) == 1){
                            Toast.makeText(cont, "Contraseña actualizada", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(changepass.this, principal.class);
                            intent.putExtra("Cliente", conectado);
                            startActivity(intent);
                        }else{
                            Toast.makeText(cont, "Contraseña no actualizada. Verifica que todo esté correctamente.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(cont, "Contraseñas no coinciden. Verifica que todo esté correctamente.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(cont, "Contraseña antigua no corresponde.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}