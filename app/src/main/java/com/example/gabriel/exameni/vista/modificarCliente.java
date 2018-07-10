package com.example.gabriel.exameni.vista;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabriel.exameni.R;
import com.example.gabriel.exameni.controller.ControlDatos_Class;
import com.example.gabriel.exameni.model.Clientes_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class modificarCliente extends AppCompatActivity {

    private Button BtnEliminar, BtnCancelar, BtnModificar;
    private TextView nombreCliente, telefonoCliente;
    private Toolbar toolbar;
    private Spinner paises_spinner;
    private ArrayList<String> paisesLista = new ArrayList<>();
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
                String pais_seleccionado = (String) paises_spinner.getSelectedItem();

                if(nombreCliente.getText().toString().length() == 0){
                    Toast.makeText(modificarCliente.this, "Por favor indique el nuevo nombre.", Toast.LENGTH_SHORT).show();
                }
                else if(telefonoCliente.getText().toString().length() == 0){
                    Toast.makeText(modificarCliente.this, "Por favor indique el nuevo telefono.", Toast.LENGTH_SHORT).show();
                }
                else if(pais_seleccionado.length() == 0){
                    Toast.makeText(modificarCliente.this, "Por favor indique el nuevo pais", Toast.LENGTH_SHORT).show();
                }
                else {
                    cliente.setNombre(nombreCliente.getText().toString());
                    cliente.setTelefono(telefonoCliente.getText().toString());
                    cliente.setPais(pais_seleccionado);
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

    /*
     * Metodo que se encarga de inicializar la pantalla con todos los componentes
     */
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

        // iniciar el web service
        web_service web_service = new web_service();
        web_service.execute();

    }

    /*
     * Metodo que se encarga de inicializar el spinner con la informacion del web service.
     */
    private void crearSpinner() {
        if(!paisesLista.isEmpty()){

            // obtener la posicion del guardado en base de datos
            int seleccionado = 0;
            for(int x = 0; x < paisesLista.size(); x++){
                if(cliente.getPais().equals(paisesLista.get(x))){
                    seleccionado = x;
                    break;
                }
            }

            paises_spinner = (Spinner) findViewById(R.id.paises_spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paisesLista);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            paises_spinner.setAdapter(adapter);
            paises_spinner.setSelection(seleccionado);

        }else{
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
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

    /*
    * Inner class que se encarga de consumir un REST API de todos los paises del mundo
    */
    private class web_service extends AsyncTask<Void, Void, Void> {
        String api_url = "https://restcountries.eu/rest/v2/all?fields=name";
        String textBuffer = "";
        String textFinal = "";

        @Override
        protected Void doInBackground(Void... params){
            URL url;
            try{
                // conectarse al web service y traer los datos, luego leerlos
                url = new URL(api_url);
                BufferedReader elbufferReader = new BufferedReader(new InputStreamReader(url.openStream()));

                // leemos linea por linea el contenido de lo leido
                while((textBuffer = elbufferReader.readLine()) != null){
                    textFinal += textBuffer;
                }

                // cerrar buffer
                elbufferReader.close();
            }catch(MalformedURLException e){
                e.printStackTrace();
                Log.d("==>ERROR! ", e.toString());
            }
            catch (IOException e){
                e.printStackTrace();
                Log.d("==>ERROR! ", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            try{
                // el web service retorna un JSON array
                JSONArray jsonArray = new JSONArray(new String(textFinal));

                // crear un objeto JSON para cada uno de los objetos dentro del array
                JSONObject objetoJSON = new JSONObject();

                // obtener tamaño del arreglo
                int jsonArrayLength = jsonArray.length();

                // recorrer el arreglo JSON
                for (int i = 0; i < jsonArrayLength; i++){
                    // obtener cada objeto JSON del arreglo JSON
                    objetoJSON = new JSONObject(jsonArray.getString(i));

                    // agregarlo al arrayList
                    paisesLista.add(objetoJSON.getString("name"));
                }
                // se termino la ejecucion del web service, crear el spinner
                crearSpinner();
            }
            catch(JSONException e){
                e.printStackTrace();
                Log.d("==>ERROR! ", e.toString());
            }
            super.onPostExecute(result);
        }
    }
}
