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
import com.example.gabriel.exameni.model.Clientes_Class;
import com.example.gabriel.exameni.model.Factura_Class;
import com.example.gabriel.exameni.utils.facturaAdapter;

import java.util.ArrayList;

public class Facturacion extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ListView listView;
    private ControlDatos_Class controlDatos = new ControlDatos_Class(this);
    private FloatingActionButton fab;
    private ArrayList<Factura_Class> listaFacturas;
    private facturaAdapter facturaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturacion);

        inicializarPantalla();

        // escuchar clicks al listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Factura_Class factura = (Factura_Class) listView.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(), detallesFactura.class);
                intent.putExtra("FacturaDatos", factura);
                startActivity(intent);
            }
        });
    }

    private void inicializarPantalla(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Facturación");

        // inicializar listview
        listaFacturas = controlDatos.consultarFacturas();
        listView = (ListView) findViewById(R.id.lista_facturas);
        facturaAdapter = new facturaAdapter(this, listaFacturas);
        listView.setAdapter(facturaAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Facturacion.this, agregarFactura.class));
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
                Intent intent = new Intent(Facturacion.this, Menu_principal.class);
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
