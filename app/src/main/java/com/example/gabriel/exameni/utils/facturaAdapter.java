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
import com.example.gabriel.exameni.model.Factura_Class;

import java.util.ArrayList;
import java.util.List;

public class facturaAdapter extends ArrayAdapter<Factura_Class> {
    private Context miContext;
    private List<Factura_Class> listaFacturas = new ArrayList<>();

    public facturaAdapter(@NonNull Context context, @LayoutRes ArrayList<Factura_Class> lista){
        super(context, 0 , lista);
        miContext = context;
        listaFacturas = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(miContext).inflate(R.layout.factura_item, parent, false);
        }

        Factura_Class facturaActual = listaFacturas.get(position);

        TextView id_factura = (TextView)  listItem.findViewById(R.id.id_factura);
        id_factura.setText(Integer.toString(facturaActual.getId()));

        TextView nombre_cliente = (TextView) listItem.findViewById(R.id.nombre_cliente);
        nombre_cliente.setText(facturaActual.getNombre_cliente());

        TextView fecha_compra = (TextView) listItem.findViewById(R.id.fecha_compra);
        fecha_compra.setText(facturaActual.getFecha_compra());

        return listItem;
    }
}
