package com.example.gabriel.exameni.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabriel.exameni.R;
import com.example.gabriel.exameni.controller.ControlDatos_Class;
import com.example.gabriel.exameni.model.Clientes_Class;

public class modificarCliente extends AppCompatActivity {

    private Button BtnEliminar, BtnCancelar, BtnModificar;
    private TextView nombreCliente, telefonoCliente;
    private Toolbar toolbar;
    private ControlDatos_Class controlDatos = new ControlDatos_Class(this);
    private Clientes_Class cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_cliente);

        // inicializar pantalla
        inicializarPantalla();

        // escuchar botones
        BtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(modificarCliente.this, Clientes.class));
            }
        });

        BtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombreCliente.getText().toString().length() == 0){
                    Toast.makeText(modificarCliente.this, "Por favor indique el nuevo nombre.", Toast.LENGTH_SHORT).show();
                }
                else if(telefonoCliente.getText().toString().length() == 0){
                    Toast.makeText(modificarCliente.this, "Por favor indique el nuevo telefono.", Toast.LENGTH_SHORT).show();
                }
                else {
                    cliente.setNombre(nombreCliente.getText().toString());
                    cliente.setTelefono(telefonoCliente.getText().toString());
                    controlDatos.modificarCliente(cliente);

                    Toast.makeText(modificarCliente.this, "Cliente modificado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(modificarCliente.this, Clientes.class));
                }

            }
        });
        BtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controlDatos.eliminarCliente(cliente);
                Toast.makeText(modificarCliente.this, "Cliente eliminado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(modificarCliente.this, Clientes.class));
            }
        });
    }

    private void inicializarPantalla(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Modificar o eliminar clientes");

        // obtener el cliente del otro activity
        cliente = (Clientes_Class) getIntent().getSerializableExtra("ClienteDatos");

        // inicializar botones
        BtnCancelar = (Button) findViewById(R.id.BtnCancelar);
        BtnEliminar = (Button) findViewById(R.id.BtnEliminar);
        BtnModificar = (Button) findViewById(R.id.BtnModificar);

        // inicializar editTexts con informacion
        nombreCliente = (EditText) findViewById(R.id.nombreCliente);
        nombreCliente.setInputType(InputType.TYPE_CLASS_TEXT);
        nombreCliente.setText(cliente.getNombre());
        telefonoCliente = (EditText) findViewById(R.id.telefonoCliente);
        telefonoCliente.setInputType(InputType.TYPE_CLASS_PHONE);
        telefonoCliente.setText(cliente.getTelefono());

    }

    /*
     * Metodo para escuchar el click al boton de atras en el toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(modificarCliente.this, Clientes.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
