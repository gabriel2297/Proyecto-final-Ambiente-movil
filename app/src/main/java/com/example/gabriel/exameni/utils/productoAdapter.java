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
import com.example.gabriel.exameni.model.Productos_Class;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class productoAdapter extends ArrayAdapter<Productos_Class> {

    private Context miContext;
    private List<Productos_Class> listaProductos = new ArrayList<>();

    public productoAdapter(@NonNull Context context, @LayoutRes ArrayList<Productos_Class> lista){
        super(context, 0 , lista);
        miContext = context;
        listaProductos = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(miContext).inflate(R.layout.producto_item, parent, false);
        }

        Productos_Class productoActual = listaProductos.get(position);

        TextView id = (TextView) listItem.findViewById(R.id.id);
        id.setText(Integer.toString(productoActual.getId_producto()));

        TextView nombre = (TextView) listItem.findViewById(R.id.nombre);
        nombre.setText(productoActual.getNombre_producto());

        TextView precio = (TextView) listItem.findViewById(R.id.precio_de_compra);
        precio.setText(Float.toString(productoActual.getPrecio_compra()));

        return listItem;

    }

}
