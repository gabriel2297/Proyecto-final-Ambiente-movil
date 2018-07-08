package com.example.gabriel.exameni.vista;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabriel.exameni.R;
import com.example.gabriel.exameni.controller.ControlDatos_Class;
import com.example.gabriel.exameni.model.Factura_Class;
import com.example.gabriel.exameni.utils.clienteAdapter;

public class detallesFactura extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView idFactura, nombreFactura, productoFactura, fechaFactura, cantidadFactura;
    private Button eliminarFacturaBtn;
    private Factura_Class factura;
    private ControlDatos_Class controlDatos = new ControlDatos_Class(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_factura);

        // inicializar pantalla
        inicializarPantalla();
    }

    private void inicializarPantalla(){
        // inicializar toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalles de factura");
        // obtener datos del activity Facturacion
        factura = (Factura_Class) getIntent().getSerializableExtra("FacturaDatos");

        // inicializar textViews con informacion del objeto
        idFactura = (TextView) findViewById(R.id.idFactura);
        idFactura.setText(Integer.toString(factura.getId()));
        nombreFactura = (TextView) findViewById(R.id.nombreFactura);
        nombreFactura.setText(factura.getNombre_cliente());
        productoFactura = (TextView) findViewById(R.id.productoFactura);
        productoFactura.setText(factura.getNombre_producto());
        fechaFactura = (TextView) findViewById(R.id.fechaFactura);
        fechaFactura.setText(factura.getFecha_compra());
        cantidadFactura = (TextView) findViewById(R.id.cantidadFactura);
        cantidadFactura.setText(Integer.toString(factura.getCantidad_producto()));

        // inicializar boton de eliminar y escuchar onclicks
        eliminarFacturaBtn = (Button) findViewById(R.id.eliminarFacturaBtn);
        eliminarFacturaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controlDatos.eliminarFactura(factura);
                Toast.makeText(detallesFactura.this, "Factura eliminada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(detallesFactura.this, Facturacion.class));
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
                Intent intent = new Intent(detallesFactura.this, Facturacion.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
