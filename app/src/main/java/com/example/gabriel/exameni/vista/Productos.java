package com.example.gabriel.exameni.vista;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.gabriel.exameni.R;
import com.example.gabriel.exameni.controller.ControlDatos_Class;
import com.example.gabriel.exameni.model.Productos_Class;
import com.example.gabriel.exameni.utils.productoAdapter;

import java.util.ArrayList;

public class Productos extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ListView listView;
    private ControlDatos_Class controlDatos = new ControlDatos_Class(this);
    private ArrayList<Productos_Class> listaProductos;
    private productoAdapter productoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        // inicializar pantalla
        inicializarPantalla();

        // escuchar clicks al listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Productos_Class producto = (Productos_Class) listView.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(), modificarProducto.class);
                intent.putExtra("ProductoDatos", producto);
                startActivity(intent);
            }
        });

    }

    private void inicializarPantalla(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Productos");

        // inicialiar listview
        listaProductos = controlDatos.consultaProductos();
        listView = (ListView) findViewById(R.id.listView);
        productoAdapter = new productoAdapter(this, listaProductos);
        listView.setAdapter(productoAdapter);

        // inicializar Fab
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Productos.this, agregarProducto.class));
            }
        });
    }

    /*
    * Metodo para escuchar el click al boton de atras en el toolbar
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(Productos.this, Menu_principal.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view){

    }
}



