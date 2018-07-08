package com.example.gabriel.exameni.model;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Factura_Class implements Serializable {

    public static final String TABLE_NAME = "facturas";
    public static final String COLUMNA_ID = "id_producto";
    public static final String COLUMNA_NOMBRE_PRODUCTO = "nombre_producto";
    public static final String COLUMNA_NOMBRE_CLIENTE = "nombre_cliente";
    public static final String COLUMNA_CANTIDAD_PRODUCTO = "cantidad_producto";
    public static final String COLUMNA_FECHA_COMPRA = "fecha_compra";

    // Crear query de SQL para creacion de tabla
    public static final String CREAR_TABLA = "CREATE TABLE " + TABLE_NAME +
            " (" + COLUMNA_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMNA_NOMBRE_PRODUCTO + " TEXT NOT NULL, " +
            COLUMNA_NOMBRE_CLIENTE + " TEXT NOT NULL, " +
            COLUMNA_CANTIDAD_PRODUCTO + " INTEGER NOT NULL, " +
            COLUMNA_FECHA_COMPRA + " TEXT NOT NULL )";

    private int id, cantidad_producto;
    private String nombre_producto, nombre_cliente, fecha_compra;

    public Factura_Class(){}
    public Factura_Class(int id, int cantidad_producto, String nombre_producto, String nombre_cliente, String fecha_compra){
        this.id = id;
        this.cantidad_producto = cantidad_producto;
        this.nombre_producto = nombre_producto;
        this.nombre_cliente = nombre_cliente;
        this.fecha_compra = fecha_compra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad_producto() {
        return cantidad_producto;
    }

    public void setCantidad_producto(int cantidad_producto) {
        this.cantidad_producto = cantidad_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getFecha_compra(){
        return fecha_compra;
    }

    public void setFecha_compra(String fecha_compra){
        this.fecha_compra = fecha_compra;
    }
}

