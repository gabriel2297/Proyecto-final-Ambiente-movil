package com.example.gabriel.exameni.vista;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gabriel.exameni.R;
import com.example.gabriel.exameni.controller.ControlDatos_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class agregarCliente extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText nombre, telefono;
    private Button cancelBtn, agregarBtn;
    private Spinner paises_spinner;
    private ArrayList<String> listaPaises;
    private ControlDatos_Class controlDatos = new ControlDatos_Class(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);

        // inicializar activity
        inicializarPantalla();

        // llamar al web service
        web_service web_service = new web_service();
        web_service.execute();

        // inicializar spinner
        paises_spinner = (Spinner) findViewById(R.id.paises_spinner);
        listaPaises = web_service.getPaisesLista();
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,
                listaPaises);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paises_spinner.setAdapter(adaptador);

        // controlar clicks al boton de cancelar
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(agregarCliente.this, Clientes.class));
            }
        });

        // controlar click al boton de agregar
        agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pais_seleccionado = (String) paises_spinner.getSelectedItem();
                if(nombre.getText().toString().length() == 0){
                    Toast.makeText(agregarCliente.this, "Por favor indique un nombre para el cliente", Toast.LENGTH_SHORT).show();
                }
                else if(telefono.getText().toString().length() == 0){
                    Toast.makeText(agregarCliente.this, "Por favor indique un numero telefonico.", Toast.LENGTH_SHORT).show();
                }
                else if(pais_seleccionado == null){
                    Toast.makeText(agregarCliente.this, "Por favor indique un pais.", Toast.LENGTH_SHORT).show();
                }
                else if(pais_seleccionado != null){
                    if(pais_seleccionado.length() == 0){
                        Toast.makeText(agregarCliente.this, "Por favor indique un pais.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(agregarCliente.this, pais_seleccionado, Toast.LENGTH_SHORT).show();
                    controlDatos.guardarCliente(nombre.getText().toString(), telefono.getText().toString(), pais_seleccionado);
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

    public class web_service extends AsyncTask<Void, Void, Void> {
        String api_url = "https://restcountries.eu/rest/v2/all?fields=name";
        String textBuffer = "";
        String textFinal = "";
        ArrayList<String> paisesLista = new ArrayList<>();


        // devolver el arrayList de paises
        public ArrayList<String> getPaisesLista(){
            return paisesLista;
        }

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
            }
            catch(JSONException e){
                e.printStackTrace();
                Log.d("==>ERROR! ", e.toString());
            }
            super.onPostExecute(result);
        }
    }

}
