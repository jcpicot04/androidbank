package com.example.t3_a3_picot_juancarlos.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dao.CajerosDAO;
import com.example.t3_a3_picot_juancarlos.R;

public class CajerosAdapter extends RecyclerView.Adapter<CajerosAdapter.ViewHolderDatos> implements View.OnClickListener{
    private CajerosDAO cajerosDAO = null;
    private Cursor cursor = null;
    private View.OnClickListener listener;

    public CajerosAdapter(Context context, Cursor cursor) {
        this.cajerosDAO = new CajerosDAO(context);
        this.cajerosDAO.abrir();
        this.cursor = cursor;
    }
    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        cursor.moveToPosition(position);
        @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("direccion"));
        @SuppressLint("Range") String idcajero = cursor.getString(cursor.getColumnIndex("_id"));
        holder.asignarDatos(nombre,idcajero);
    }
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tv_nombre;
        TextView tv_id;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.txtNombre);
            tv_nombre = itemView.findViewById(R.id.txtDirección);
        }
        public void asignarDatos(String nombre,String idcajero) {
            tv_nombre.setText(nombre);
            tv_id.setText("ATM " + idcajero);
        }
    }
}
