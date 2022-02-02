package com.example.t3_a3_picot_juancarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.pojo.Movimiento;

import java.util.ArrayList;

public class movimiento extends AppCompatActivity {

    private ListView ingresos;
    private ArrayList<String> me = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento);

        ingresos = findViewById(R.id.ingresos);


        ArrayList<Movimiento> message = (ArrayList<Movimiento>) this.getIntent().getSerializableExtra("ingresos");

        for(Movimiento m : message){
            me.add(m.toString());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                 me);

        ingresos.setAdapter(arrayAdapter);
    }
}