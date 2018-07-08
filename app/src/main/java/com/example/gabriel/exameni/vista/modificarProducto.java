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
import android.widget.Toast;

import com.example.gabriel.exameni.R;
import com.example.gabriel.exameni.controller.ControlDatos_Class;
import com.example.gabriel.exameni.model.Productos_Class;

import java.io.Serializable;

public class modificarProducto extends AppCompatActivity{


    private Button cancelBtn, modificarBtn, eliminarBtn;
    private EditText nameTxt, precio_compra, precio_venta;
    private Toolbar toolbar;
    private ControlDatos_Class controlDatos = new ControlDatos_Class(this);
    private Productos_Class producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_producto);

        // inicializar pantalla
        inicializarPantalla();

        // escuchar click en los botones
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(modificarProducto.this, Productos.class));
            }
        });

        modificarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameTxt.getText().toString().length() == 0){
                    Toast.makeText(modificarProducto.this, "Por favor digite el nuevo nombre", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
                    producto.setPrecio_compra(Float.parseFloat(precio_compra.getText().toString()));
                }catch(NumberFormatException e){
                    Toast.makeText(modificarProducto.this, "Por favor digite un precio de compra valido", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
                    producto.setPrecio_venta(Float.parseFloat(precio_venta.getText().toString()));
                }catch(NumberFormatException e){
                    Toast.makeText(modificarProducto.this, "Por favor digite un precio de venta valido", Toast.LENGTH_SHORT).show();
                    return;
                }

                controlDatos.modificarProducto(producto);
                Toast.makeText(modificarProducto.this, "Producto modificado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(modificarProducto.this, Productos.class));
            }
        });
        eliminarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controlDatos.eliminarProducto(producto);
                Toast.makeText(modificarProducto.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(modificarProducto.this, Productos.class));
            }
        });
    }


    private void inicializarPantalla(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Modificar o eliminar productos");

        // obtener el producto del otro activity
        producto = (Productos_Class) getIntent().getSerializableExtra("ProductoDatos");

        // inicializar botones
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        modificarBtn = (Button) findViewById(R.id.modificarBtn);
        eliminarBtn = (Button) findViewById(R.id.eliminarBtn);

        // inicializar edit texts
        nameTxt = (EditText) findViewById(R.id.nameTxt);
        nameTxt.setInputType(InputType.TYPE_CLASS_TEXT);
        nameTxt.setText(producto.getNombre_producto());
        precio_compra = (EditText) findViewById(R.id.precio_compra);
        precio_compra.setInputType(android.text.InputType. 	TYPE_CLASS_NUMBER);
        precio_compra.setText(Float.toString(producto.getPrecio_compra()));
        precio_venta = (EditText) findViewById(R.id.precio_venta);
        precio_venta.setInputType(InputType.TYPE_CLASS_NUMBER);
        precio_venta.setText(Float.toString(producto.getPrecio_venta()));
    }

    /*
     * Metodo para escuchar el click al boton de atras en el toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(modificarProducto.this, Productos.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
