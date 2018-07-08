package com.example.gabriel.exameni.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.gabriel.exameni.vista.Productos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SQLite_Class extends SQLiteOpenHelper {

    // informacion de la BD
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ventas.db";

    public SQLite_Class(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DB_NAME, factory, DB_VERSION);
    }

    public SQLite_Class(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(Clientes_Class.CREAR_TABLA);
        db.execSQL(Productos_Class.CREAR_TABLA);
        db.execSQL(Factura_Class.CREAR_TABLA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // borrar las tablas si existen (es requisito)
        db.execSQL("DROP TABLE IF EXISTS " + Clientes_Class.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Productos_Class.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Factura_Class.TABLE_NAME);
        onCreate(db);
    }

    /*
    * Metodo para insertar un nuevo cliente a la base de datos
    */
    public int insertarCliente(Clientes_Class cliente){
        // metodos para escribir en bases de datos
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre_cliente", cliente.getNombre());
        values.put("telefono_cliente", cliente.getTelefono());

        long id = db.insert(Clientes_Class.TABLE_NAME, null, values);
        db.close();
        return (int) id;
    }

    /*
    * Metodo para consultar clientes en base de datos
    */
    public ArrayList<Clientes_Class> consultaClientes(){
        ArrayList<Clientes_Class> listaClientes = new ArrayList<>();

        // query para traer todos los clientes
        String selectQuery = "SELECT * FROM " + Clientes_Class.TABLE_NAME;

        // abrir una conexion a BD con permiso de escritura
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // loop para revisar cada registro de la tabla y agregarlos a la lista
        if(cursor.moveToFirst()){
            do{
                Clientes_Class cliente = new Clientes_Class();
                cliente.setId(cursor.getInt(cursor.getColumnIndex(Clientes_Class.COLUMNA_ID)));
                cliente.setNombre(cursor.getString(cursor.getColumnIndex(Clientes_Class.COLUMNA_NOMBRE)));
                cliente.setTelefono(cursor.getString(cursor.getColumnIndex(Clientes_Class.COLUMNA_TELEFONO)));

                // agregar cliente al arraylist de clientes
                listaClientes.add(cliente);
            }while(cursor.moveToNext());
        }

        // cerrar conexion a cursor
        cursor.close();

        // cerrar conexion a base de datos
        db.close();
        return listaClientes;
    }

    /*
     * Hacerle un update a un cliente
     */
    public int updateCliente(Clientes_Class cliente){
        // abrir conexion a BD para modificar
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Clientes_Class.COLUMNA_NOMBRE, cliente.getNombre());
        values.put(Clientes_Class.COLUMNA_TELEFONO, cliente.getTelefono());

        // hacer el cambio en base de datos
        return db.update(Clientes_Class.TABLE_NAME, values, Clientes_Class.COLUMNA_ID + "=?",
                new String[]{String.valueOf(cliente.getId())});
    }

    /*
     * Borrar un cliente
     */
    public void borrarCliente(Clientes_Class cliente){
        // obtener permiso para escribir
        SQLiteDatabase db = this.getWritableDatabase();
        // query para borrar en BD
        db.delete(Clientes_Class.TABLE_NAME, Clientes_Class.COLUMNA_ID + "=?",
                new String[]{String.valueOf(cliente.getId())});
        // cerrar conexion a BD
        db.close();
    }

    /*
    * Inserta un producto a la tabla
    */
    public int insertarProductos(Productos_Class producto){
        // metodos para escribir en bases de datos
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre_producto", producto.getNombre_producto());
        values.put("precio_compra", producto.getPrecio_compra());
        values.put("precio_venta", producto.getPrecio_venta());

        long id = db.insert(Productos_Class.TABLE_NAME, null, values);
        db.close();
        return (int) id;
    }

    /*
    * Retorna todos los productos en un arreglo de Producto_class
    */
    public ArrayList<Productos_Class> consultaProductos(){
        ArrayList<Productos_Class> productos = new ArrayList<>();

        // query para traer todos los productos
        String selectQuery = "SELECT  * FROM " + Productos_Class.TABLE_NAME;

        // abrir una conexion a BD con permiso de escritura
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // loop para revisar cada registro de la tabla y agregarlos a la lista
        if(cursor.moveToFirst()){
            do{
                Productos_Class producto = new Productos_Class();
                producto.setId_producto(cursor.getInt(cursor.getColumnIndex(Productos_Class.COLUMNA_ID)));
                producto.setNombre_producto(cursor.getString(cursor.getColumnIndex(Productos_Class.COLUMNA_NOMBRE)));
                producto.setPrecio_compra(cursor.getFloat(cursor.getColumnIndex(Productos_Class.COLUMNA_PRECIO_COMPRA)));
                producto.setPrecio_venta(cursor.getFloat(cursor.getColumnIndex(Productos_Class.COLUMNA_PRECIO_VENTA)));

                // agregar producto al arraylist de productos
                productos.add(producto);
            }while(cursor.moveToNext());
        }

        // cerrar conexion a cursor
        cursor.close();

        // cerrar conexion a BD
        db.close();

        // retornar arraylist de objetos de todos los productos en BD
        return productos;
    }

    /*
    * Hacerle un update a un producto
    */
    public int updateProducto(Productos_Class producto){
        // abrir conexion a BD para modificar
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Productos_Class.COLUMNA_NOMBRE, producto.getNombre_producto());
        values.put(Productos_Class.COLUMNA_PRECIO_COMPRA, producto.getPrecio_compra());
        values.put(Productos_Class.COLUMNA_PRECIO_VENTA, producto.getPrecio_venta());

        // update al producto por ID
        return db.update(Productos_Class.TABLE_NAME, values, Productos_Class.COLUMNA_ID + "=?",
                new String[]{String.valueOf(producto.getId_producto())});
    }

    /*
    * Borrar un producto
    */
    public void borrarProducto(Productos_Class producto){
        // obtener permiso para escribir
        SQLiteDatabase db = this.getWritableDatabase();
        // query para borrar en BD
        db.delete(Productos_Class.TABLE_NAME, Productos_Class.COLUMNA_ID + "=?",
                new String[]{String.valueOf(producto.getId_producto())});
        // cerrar conexion a BD
        db.close();
    }

    /*
     * Crear una nueva factura
     */
    public int insertarFactura(Factura_Class factura){
        // metodos para escribir en bases de datos
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre_producto", factura.getNombre_producto());
        values.put("nombre_cliente", factura.getNombre_cliente());
        values.put("cantidad_producto", factura.getCantidad_producto());
        values.put("fecha_compra", factura.getFecha_compra());

        long id = db.insert(Factura_Class.TABLE_NAME, null, values);
        db.close();
        return (int) id;
    }

    /*
     * Retorna todas las facturas en un arreglo de Factura_Class
     */
    public ArrayList<Factura_Class> consultaFacturas(){
        ArrayList<Factura_Class> facturas = new ArrayList<>();

        // query para traer todos los productos
        String selectQuery = "SELECT  * FROM " + Factura_Class.TABLE_NAME;

        // abrir una conexion a BD con permiso de escritura
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // loop para revisar cada registro de la tabla y agregarlos a la lista
        if(cursor.moveToFirst()){
            do{
                Factura_Class factura = new Factura_Class();
                factura.setId(cursor.getInt(cursor.getColumnIndex(Factura_Class.COLUMNA_ID)));
                factura.setNombre_producto(cursor.getString(cursor.getColumnIndex(Factura_Class.COLUMNA_NOMBRE_PRODUCTO)));
                factura.setNombre_cliente(cursor.getString(cursor.getColumnIndex(Factura_Class.COLUMNA_NOMBRE_CLIENTE)));
                factura.setCantidad_producto(cursor.getInt(cursor.getColumnIndex(Factura_Class.COLUMNA_CANTIDAD_PRODUCTO)));
                factura.setFecha_compra(cursor.getString(cursor.getColumnIndex(Factura_Class.COLUMNA_FECHA_COMPRA)));

                // agregar producto al arraylist de productos
                facturas.add(factura);
            }while(cursor.moveToNext());
        }

        // cerrar conexion a cursor
        cursor.close();

        // cerrar conexion a BD
        db.close();

        // retornar arraylist de objetos de todos los productos en BD
        return facturas;
    }

    /*
     * Borrar una factura
     */
    public void borrarFactura(Factura_Class factura){
        // obtener permiso para escribir
        SQLiteDatabase db = this.getWritableDatabase();
        // query para borrar en BD
        db.delete(Factura_Class.TABLE_NAME, Factura_Class.COLUMNA_ID + "=?",
                new String[]{String.valueOf(Integer.toString(factura.getId()))});
        // cerrar conexion a BD
        db.close();
    }

}
