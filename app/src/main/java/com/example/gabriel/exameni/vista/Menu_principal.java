package com.example.gabriel.exameni.vista;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.gabriel.exameni.R;

public class Menu_principal extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    Button clientes;
    Button productos;
    Button facturacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        

        // inicializar pantalla
        inicializarPantalla();
    }

    private void inicializarPantalla(){
        // encontrar los botones de la pantalla principal
        clientes = (Button) findViewById(R.id.clientes);
        productos = (Button) findViewById(R.id.productos);
        facturacion = (Button) findViewById(R.id.facturacion);

        // buscar toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Polacos S.A");


        // fijar el click listener a los botones
        clientes.setOnClickListener(this);
        productos.setOnClickListener(this);
        facturacion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.clientes:
                startActivity(new Intent(Menu_principal.this, Clientes.class));
                break;
            case R.id.productos:
                startActivity(new Intent(Menu_principal.this, Productos.class));
                break;
            case R.id.facturacion:
                startActivity(new Intent(Menu_principal.this, Facturacion.class));
                break;
        }
    }
}
