package com.example.gabriel.exameni.model;

import java.io.Serializable;

public class Clientes_Class implements Serializable {

    // datos para creacion de tabla cliente
    public static final String TABLE_NAME = "clientes";
    public static final String COLUMNA_ID = "id_cliente";
    public static final String COLUMNA_NOMBRE = "nombre_cliente";
    public static final String COLUMNA_TELEFONO = "telefono_cliente";
    public static final String COLUMNA_PAIS = "pais_cliente";

    // informacion necesaria del cliente
    private int id;
    private String nombre;
    private String telefono;
    private String pais;

    // query
    // Crear query de SQL para creacion de tabla
    public static final String CREAR_TABLA = "CREATE TABLE " + TABLE_NAME +
            " (" + COLUMNA_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMNA_NOMBRE + " TEXT NOT NULL, " +
            COLUMNA_PAIS + " TEXT NOT NULL, " +
            COLUMNA_TELEFONO + " TEXT NOT NULL )";

    public Clientes_Class(){}

    public Clientes_Class(int id, String nombre, String telefono, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.pais = pais;
    }

    public String getPais() { return pais; }

    public void setPais(String pais ) { this.pais = pais; }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString(){
        return "ID: " + id + " | Cliente: "+ nombre;
    }
}
