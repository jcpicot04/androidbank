package com.example.t3_a3_picot_juancarlos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bd.MiBancoOperacional;
import com.pojo.Cliente;
import com.pojo.Cuenta;

import java.util.ArrayList;

public class ordertrans extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Context cont;
    private Spinner spFirstAccount;
    private Spinner spSecondAccount;
    private Spinner spCurrency;
    private EditText epImport;
    private EditText epSecondAccount;
    private RadioGroup cuentas;
    private RadioButton propia;
    private RadioButton ajena;
    private CheckBox checkBox;
    private Button buttonenviar;
    private Button buttoncancelar;
    private String cuentadestino;
    private ArrayList<String> listaPrimera = new ArrayList<String>();
    private ArrayList<String> listaSegunda = new ArrayList<String>();
    private ArrayList<String> listacurrency = new ArrayList<String>();
    private MiBancoOperacional api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordertrans);

        Cliente conectado = (Cliente) this.getIntent().getSerializableExtra("Cliente");
        api = MiBancoOperacional.getInstance(this);
        cont=this;
        spFirstAccount = findViewById(R.id.spinner);
        spSecondAccount = findViewById(R.id.toaccount);
        epSecondAccount = findViewById(R.id.cuentaajena);
        propia = findViewById(R.id.propia);
        ajena = findViewById(R.id.ajena);
        epImport = findViewById(R.id.cantidad);
        spCurrency = findViewById(R.id.currency);
        cuentas = findViewById(R.id.radioGroup);
        checkBox = findViewById(R.id.checkBox);
        buttonenviar = findViewById(R.id.buttonenviar);
        buttoncancelar = findViewById(R.id.buttoncancelar);
        for(Cuenta c : api.getCuentas(conectado)){
            listaPrimera.add("ES60-" + c.getBanco() + "-" + c.getSucursal() + "-" + c.getDc() + "-" + c.getNumeroCuenta());
        }

        listacurrency.add("€");
        listacurrency.add("$");
        listacurrency.add("£");

        spFirstAccount.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaPrimera);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFirstAccount.setAdapter(adapter);

        spCurrency.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listacurrency);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCurrency.setAdapter(adapter2);

        buttonenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textChecked = "";
                boolean checked = checkBox.isChecked();

                if(checked){
                    textChecked = "Enviar recibo";
                }else{
                    textChecked = "No enviar recibo";
                }

                String finalTextChecked = textChecked;

                if(cuentas.getCheckedRadioButtonId() != -1){
                    RadioButton selectedRadio = findViewById(cuentas.getCheckedRadioButtonId());
                    String radioselect = selectedRadio.getText().toString();

                    if(radioselect.equals(propia.getText().toString())){
                        cuentadestino = spSecondAccount.getSelectedItem().toString();
                    }else if(radioselect.equals(ajena.getText().toString())){
                        cuentadestino = epSecondAccount.getText().toString();
                    }
                }

                Toast.makeText(cont, "Cuenta origen: " + spFirstAccount.getSelectedItem().toString() + "\nCuenta destino: " + cuentadestino + "\nImporte: " + epImport.getText().toString() + spCurrency.getSelectedItem().toString() + "\n" + finalTextChecked, Toast.LENGTH_SHORT).show();
            }
        });

        buttoncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.toggle();
                epImport.setText("");
                epImport.clearFocus();
                cuentas.clearCheck();
                epSecondAccount.setVisibility(View.INVISIBLE);
                spSecondAccount.setVisibility(View.INVISIBLE);
            }
        });

        cuentas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
               View radiobutton = radioGroup.findViewById(checkedId);
               int indice = radioGroup.indexOfChild(radiobutton);

               switch (indice) {
                   case 0:
                        spSecondAccount.setVisibility(View.VISIBLE);
                        epSecondAccount.setVisibility(View.INVISIBLE);
                       break;

                   case 1:
                       spSecondAccount.setVisibility(View.INVISIBLE);
                       epSecondAccount.setVisibility(View.VISIBLE);
                       break;
               }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId())
        {
            case R.id.spinner:

                listaSegunda.clear();
                for(String c : listaPrimera){
                    if(!c.equals(adapterView.getSelectedItem().toString())){
                        listaSegunda.add(c);
                    }
                }
                spSecondAccount.setOnItemSelectedListener(this);
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaSegunda);
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spSecondAccount.setAdapter(adapter3);
                break;

            case R.id.currency:
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}