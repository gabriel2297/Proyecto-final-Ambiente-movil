package com.example.gabriel.exameni.vista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gabriel.exameni.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Menu_principal extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    Button clientes;
    Button productos;
    Button facturacion;
    private ProgressBar progressBar;
    private String ip;
    private String pais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hacer web service
        web_service web_service = new web_service();
        web_service.execute();

        // dormir por dos segundos (no mostrar pantalla aun)
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        // si es login de Costa Rica, cargar menu principal
        setContentView(R.layout.activity_menu_principal);
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

    /*
     * Inner class que se encarga de consumir dos REST APIs
     * APIs: 1. Recoger la IP publica y guardarla en un String
     *       2. Verificar con un web service si la IP pertenece a Costa Rica o no
     */
    private class web_service extends AsyncTask<Void, Void, Void> {
        String api_url = "https://api.ipify.org/?format=json";
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

                // obtener la IP que es regresada del web service
                JSONObject objetoJSON = new JSONObject(new String(textFinal));
                ip = objetoJSON.getString("ip");

                // conectarse al siguiente servicio web con la IP
                api_url = "https://api.ip2country.info/ip?"+ip;

                // crear nueva URL del web service
                url = new URL(api_url);
                elbufferReader = new BufferedReader(new InputStreamReader(url.openStream()));

                // inicializar de nuevo los buffers
                textBuffer = "";
                textFinal = "";

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
            catch(JSONException e){
                e.printStackTrace();
                Log.d("==>ERROR! ", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            try{
                JSONObject jsonObject = new JSONObject(new String(textFinal));
                pais = jsonObject.getString("countryName");

                // si el pais de acceso no es Costa Rica, sacar al usuario de la app
                if(!pais.equals("Costa Rica")){
                    System.exit(0);
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
