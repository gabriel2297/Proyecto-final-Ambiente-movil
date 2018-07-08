package com.example.gabriel.exameni.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gabriel.exameni.R;
import com.example.gabriel.exameni.controller.ControlDatos_Class;
import com.example.gabriel.exameni.model.Clientes_Class;
import com.example.gabriel.exameni.model.Productos_Class;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class agregarFactura extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText cantidad_productos;
    private Spinner spinner_clientes, spinner_productos;
    private ArrayList<Clientes_Class> lista_clientes;
    private ArrayList<Productos_Class> lista_productos;
    private Button cancelarFacturaBtn, facturarBtn;
    private ControlDatos_Class controlDatos = new ControlDatos_Class(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_factura);

        // inicializar la pantalla
        inicializarPantalla();
    }

    private void inicializarPantalla(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nueva factura");

        // iniciar spinner de clientes
        spinner_clientes = (Spinner) findViewById(R.id.clientes_spinner);
        lista_clientes = controlDatos.consultarClientes();
        ArrayAdapter<Clientes_Class> adapterClientes = new ArrayAdapter<Clientes_Class>(getApplicationContext(),
            android.R.layout.simple_spinner_dropdown_item, lista_clientes);
        adapterClientes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_clientes.setAdapter(adapterClientes);

        // iniciar spinner de productos
        spinner_productos = (Spinner) findViewById(R.id.productos_spinner);
        lista_productos = controlDatos.consultaProductos();
        ArrayAdapter<Productos_Class> adapterProductos = new ArrayAdapter<Productos_Class>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, lista_productos);
        adapterProductos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_productos.setAdapter(adapterProductos);

        // iniciar edit texts
        cantidad_productos = (EditText) findViewById(R.id.cantidad_productos);
        cantidad_productos.setInputType(InputType.TYPE_CLASS_NUMBER);

        // iniciar botones y escuchar on clicks
        cancelarFacturaBtn = (Button) findViewById(R.id.cancelarFacturaBtn);
        cancelarFacturaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(agregarFactura.this, Facturacion.class));
            }
        });

        facturarBtn = (Button) findViewById(R.id.facturarBtn);
        facturarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int cantidad = Integer.parseInt(cantidad_productos.getText().toString());
                    if (cantidad <= 0){
                        Toast.makeText(agregarFactura.this, "La cantidad del producto debe de ser mayor a 0.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // obtener el objeto de producto seleccionado del spinner de productos
                    Productos_Class producto = new Productos_Class();
                    producto = (Productos_Class) spinner_productos.getSelectedItem();

                    // lo mismo para el nombre del cliente
                    Clientes_Class cliente = new Clientes_Class();
                    cliente = (Clientes_Class) spinner_clientes.getSelectedItem();

                    // obtener hora exacta y fecha, convertirla
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String fecha = dateFormat.format(timestamp);

                    // agregar a la base de datos
                    controlDatos.guardarFactura(cliente.getNombre(), producto.getNombre_producto(), fecha, cantidad);

                    // mostrar toast y volver a facturacion
                    Toast.makeText(agregarFactura.this, "Factura agregada", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(agregarFactura.this, Facturacion.class));
                }catch(NumberFormatException e){
                    Productos_Class producto = new Productos_Class();
                    producto = (Productos_Class) spinner_productos.getSelectedItem();
                    Toast.makeText(agregarFactura.this, "Por favor indique la cantidad de " + producto.getNombre_producto() + " a llevar", Toast.LENGTH_SHORT).show();
                }
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
                Intent intent = new Intent(agregarFactura.this, Facturacion.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
