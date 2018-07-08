package com.example.gabriel.exameni.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gabriel.exameni.R;
import com.example.gabriel.exameni.controller.ControlDatos_Class;

public class agregarCliente extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText nombre, telefono;
    private Button cancelBtn, agregarBtn;
    private ControlDatos_Class controlDatos = new ControlDatos_Class(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);

        // inicializar activity
        inicializarPantalla();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(agregarCliente.this, Clientes.class));
            }
        });

        agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombre.getText().toString().length() == 0){
                    Toast.makeText(agregarCliente.this, "Por favor indique un nombre para el cliente", Toast.LENGTH_SHORT).show();
                }
                else if(telefono.getText().toString().length() == 0){
                    Toast.makeText(agregarCliente.this, "Por favor indique un numero telefonico.", Toast.LENGTH_SHORT).show();
                }
                else {
                    controlDatos.guardarCliente(nombre.getText().toString(), telefono.getText().toString());
                    Toast.makeText(agregarCliente.this, "Cliente agregado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(agregarCliente.this, Clientes.class));
                }
            }
        });
    }

    private void inicializarPantalla(){
        // inicializar toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Agregar cliente");

        // inicializar editTexts
        nombre = (EditText) findViewById(R.id.nameTxt);
        telefono = (EditText) findViewById(R.id.phoneTxt);

        // inicializar botones
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        agregarBtn = (Button) findViewById(R.id.agregarBtn);

    }

    /*
     * Metodo para escuchar el click al boton de atras en el toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(agregarCliente.this, Clientes.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
