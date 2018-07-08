package com.example.gabriel.exameni.model;

import java.io.Serializable;

public class Productos_Class implements Serializable{

    public static final String TABLE_NAME = "productos";
    public static final String COLUMNA_ID = "id_producto";
    public static final String COLUMNA_NOMBRE = "nombre_producto";
    public static final String COLUMNA_PRECIO_COMPRA = "precio_compra";
    public static final String COLUMNA_PRECIO_VENTA = "precio_venta";

    private int id_producto;
    private String nombre_producto;
    private float precio_compra;
    private float precio_venta;

    // Crear query de SQL para creacion de tabla
    public static final String CREAR_TABLA = "CREATE TABLE " + TABLE_NAME +
            " ( " + COLUMNA_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMNA_NOMBRE + " TEXT NOT NULL, " +
            COLUMNA_PRECIO_COMPRA + " REAL NOT NULL, " +
            COLUMNA_PRECIO_VENTA + " REAL NOT NULL )";

    public Productos_Class(){}

    public Productos_Class(int id_producto, String nombre_producto, float precio_compra, float precio_venta){
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.precio_compra = precio_compra;
        this.precio_venta = precio_venta;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public float getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(float precio_compra) {
        this.precio_compra = precio_compra;
    }

    public float getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(float precio_venta) {
        this.precio_venta = precio_venta;
    }

    @Override
    public String toString(){
        return nombre_producto;
    }
}
