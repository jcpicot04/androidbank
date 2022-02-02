package com.example.t3_a3_picot_juancarlos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t3_a3_picot_juancarlos.R;
import com.pojo.Cuenta;

import java.util.ArrayList;

public class CuentaAdapter extends RecyclerView.Adapter<CuentaAdapter.ViewHolderDatos>
        implements View.OnClickListener{

    ArrayList<Cuenta> listaCuentas;

    // Propiedad para el onClick
    private View.OnClickListener listener;
    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
    public CuentaAdapter(ArrayList<Cuenta> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }
    // El layout manager invoca este método
// para renderizar cada elemento del RecyclerView
    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
// Creamos una nueva vista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element, null, false);
// Para que cada ítem ocupe el match_parent
        RecyclerView.LayoutParams lp =
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
// Para realizar onClick
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }
    // Este método asigna valores para cada elemento de la lista
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
// - obtenemos un elemento del dataset según su posición
// - reemplazamos el contenido usando tales datos
        holder.asignarDatos(listaCuentas.get(position));
    }
    // Método que define la cantidad de elementos del RecyclerView
// Puede ser más complejo (por ejem, si implementamos filtros o búsquedas)
    @Override
    public int getItemCount() {
        return listaCuentas.size();
    }
    // Para realizar onClick
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    // Para realizar onClick
    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }
    // Obtener referencias de los componentes visuales para cada elemento
// Es decir, referencias de los EditText, TextViews, Buttons
    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        // en este ejemplo cada elemento consta solo de un título y subtítulo
        TextView de;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            de = itemView.findViewById(R.id.tvCuenta);
        }
        public void asignarDatos(Cuenta c) {
            de.setText("ES60-" + c.getBanco() + "-" + c.getSucursal() + "-" + c.getDc() + "-" + c.getNumeroCuenta());
        }
    }
}
