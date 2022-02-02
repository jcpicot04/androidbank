package com.example.t3_a3_picot_juancarlos.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.t3_a3_picot_juancarlos.R;
import com.example.t3_a3_picot_juancarlos.adapters.CuentaAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pojo.Cuenta;
import com.pojo.CuentaDatos;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CuentaAdapter mAdapter;
    private ArrayList<Cuenta> datos = CuentaDatos.CUENTAS;
    private CuentasListener listener;
    ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
    // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ListFragment() {
// Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
// TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(ArrayList<Cuenta> cuentas) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("cuentas",cuentas);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cuentas  = getArguments().getParcelableArrayList("cuentas");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerIdList);
// Esta línea mejora el rendimiento, si sabemos que el contenido
// no va a afectar al tamaño del RecyclerView
        mRecyclerView.setHasFixedSize(true);
// Nuestro RecyclerView usará un linear layout manager --> VERTICAL
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
// Para poner un separador entre los diferente ítems de la lista
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mRecyclerView.getContext(),
                        layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
// Asociamos un adapter (ver más adelante cómo definirlo)
        mAdapter = new CuentaAdapter(cuentas);
// Para realizar onClick
        mAdapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                int posicion = mRecyclerView.getChildAdapterPosition(view);
                System.out.println(posicion);
                Toast.makeText(getContext(),
                        "Selección: " +
                                cuentas.get(posicion).getSucursal(),
                        Toast.LENGTH_LONG).show();

                if (listener != null) {
                    listener.onCuentaSeleccionada((Cuenta) cuentas.get(posicion));
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public void setCuentasListener(CuentasListener listener) {
        this.listener=listener;
    }

}
