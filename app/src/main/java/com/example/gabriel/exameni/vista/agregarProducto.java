package com.example.gabriel.exameni.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.gabriel.exameni.R;
import com.example.gabriel.exameni.controller.ControlDatos_Class;
import com.example.gabriel.exameni.model.Productos_Class;

public class agregarProducto extends AppCompatActivity {

    Toolbar toolbar;
    Button cancelarBtn, agregarBtn;
    EditText nombre, precio_compra, precio_venta;
    ControlDatos_Class controlDatos = new ControlDatos_Class(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        // inicializar pantalla
        inicializarPantalla();

        // escuchar click a cancelar
        cancelarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(agregarProducto.this, Productos.class));
            }
        });

        agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float precioVenta = 0, precioCompra = 0;
                if(nombre.getText().toString().length() == 0){
                    Toast.makeText(agregarProducto.this, "Por favor agregue un nombre al producto.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    precioCompra = Float.parseFloat(precio_compra.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(agregarProducto.this, "Por favor agregue precio de compra.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
                    precioVenta = Float.parseFloat(precio_venta.getText().toString());
                }catch(NumberFormatException e){
                    Toast.makeText(agregarProducto.this, "Por favor agregue el precio de venta.", Toast.LENGTH_SHORT).show();
                    return;
                }

                controlDatos.guardarProducto(
                    nombre.getText().toString(),
                    precioCompra,
                    precioVenta
                );
                Toast.makeText(agregarProducto.this, "Producto agregado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(agregarProducto.this, Productos.class));
            }
        });
    }

    private void inicializarPantalla(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Agregar productos");

        // inicializar editTexts
        cancelarBtn = (Button) findViewById(R.id.cancelBtn);
        agregarBtn = (Button) findViewById(R.id.agregarBtn);

        // inicializar edit texts
        nombre = (EditText) findViewById(R.id.nameTxt);
        nombre.setInputType(InputType.TYPE_CLASS_TEXT);
        precio_compra = (EditText) findViewById(R.id.precio_compra);
        precio_compra.setInputType(android.text.InputType. 	TYPE_CLASS_NUMBER);
        precio_venta = (EditText) findViewById(R.id.precio_venta);
        precio_venta.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    /*
     * Metodo para escuchar el click al boton de atras en el toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(agregarProducto.this, Productos.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
