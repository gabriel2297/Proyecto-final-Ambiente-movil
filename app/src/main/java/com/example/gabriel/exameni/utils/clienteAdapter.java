package com.example.gabriel.exameni.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gabriel.exameni.R;
import com.example.gabriel.exameni.model.Clientes_Class;
import com.example.gabriel.exameni.model.Productos_Class;

import java.util.ArrayList;
import java.util.List;

public class clienteAdapter extends ArrayAdapter<Clientes_Class> {

    private Context miContext;
    private List<Clientes_Class> listaClientes = new ArrayList<>();

    public clienteAdapter(@NonNull Context context, @LayoutRes ArrayList<Clientes_Class> lista){
        super(context, 0 , lista);
        miContext = context;
        listaClientes = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(miContext).inflate(R.layout.cliente_item, parent, false);
        }

        Clientes_Class clienteActual = listaClientes.get(position);

        TextView id = (TextView) listItem.findViewById(R.id.id_cliente);
        id.setText(Integer.toString(clienteActual.getId()));

        TextView nombre = (TextView) listItem.findViewById(R.id.nombre);
        nombre.setText(clienteActual.getNombre());

        TextView telefono = (TextView) listItem.findViewById(R.id.telefono);
        telefono.setText(clienteActual.getTelefono());

        return listItem;
    }
}
