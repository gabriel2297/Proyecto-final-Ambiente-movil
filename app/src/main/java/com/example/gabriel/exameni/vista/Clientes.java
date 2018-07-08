package com.example.gabriel.exameni.vista;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gabriel.exameni.R;
import com.example.gabriel.exameni.controller.ControlDatos_Class;
import com.example.gabriel.exameni.model.Clientes_Class;
import com.example.gabriel.exameni.model.Productos_Class;
import com.example.gabriel.exameni.utils.clienteAdapter;

import java.util.ArrayList;
import java.util.List;

public class Clientes extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ListView listView;
    private ControlDatos_Class controlDatos = new ControlDatos_Class(this);
    private FloatingActionButton fab;
    private ArrayList<Clientes_Class> listaClientes;
    private clienteAdapter clienteAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        // inicializar pantalla
        inicializarPantalla();

        // escuchar clicks al listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Clientes_Class cliente = (Clientes_Class) listView.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(), modificarCliente.class);
                intent.putExtra("ClienteDatos", cliente);
                startActivity(intent);
            }
        });
    }

    private void inicializarPantalla(){
        // inicializar toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Clientes");

        // inicializar listview
        listaClientes = controlDatos.consultarClientes();
        listView = (ListView) findViewById(R.id.lista_clientes);
        clienteAdapter = new clienteAdapter(this, listaClientes);
        listView.setAdapter(clienteAdapter);

        // inicializar floating action button
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Clientes.this, agregarCliente.class));
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
                Intent intent = new Intent(Clientes.this, Menu_principal.class);
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
