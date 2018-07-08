package com.example.gabriel.exameni.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.gabriel.exameni.model.Clientes_Class;
import com.example.gabriel.exameni.model.Factura_Class;
import com.example.gabriel.exameni.model.Productos_Class;
import com.example.gabriel.exameni.model.SQLite_Class;

import java.util.ArrayList;

public class ControlDatos_Class {
    Context context;
    private SQLite_Class controlDB;

    public ControlDatos_Class(Context context){
        this.context = context;
        controlDB = new SQLite_Class(context);
    }

    /*
    * Metodo para guardar clientes
    */
    public void guardarCliente(String nombre, String telefono, String pais){
        Clientes_Class cliente = new Clientes_Class();
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setPais(pais);
        controlDB.insertarCliente(cliente);
    }

    /*
    * Metodo para consultar la BD
    */
    public ArrayList<Clientes_Class> consultarClientes() {
        return controlDB.consultaClientes();
    }

    /*
     * Metodo para modificar productos
     */
    public void modificarCliente(Clientes_Class cliente){
        controlDB.updateCliente(cliente);
    }

    /*
     * Metodo para eliminar un producto
     */
    public void eliminarCliente(Clientes_Class cliente){
        controlDB.borrarCliente(cliente);
    }

    /*
    * Metodo para consultar BD para productos
    */
    public ArrayList<Productos_Class> consultaProductos(){
        return controlDB.consultaProductos();
    }

    /*
    * Metodo para insertar productos
    */
    public void guardarProducto(String nombre, float precio_compra, float precio_venta){
        Productos_Class producto = new Productos_Class();
        producto.setNombre_producto(nombre);
        producto.setPrecio_compra(precio_compra);
        producto.setPrecio_venta(precio_venta);
        controlDB.insertarProductos(producto);
    }

    /*
    * Metodo para modificar productos
    */
    public void modificarProducto(Productos_Class producto){
        controlDB.updateProducto(producto);
    }

    /*
    * Metodo para eliminar un producto
    */
    public void eliminarProducto(Productos_Class producto){
        controlDB.borrarProducto(producto);
    }

    /*
     * Metodo para insertar factura
     */
    public void guardarFactura(String nombre_cliente, String nombre_producto, String fecha_compra, int cantidad_producto){
        Factura_Class factura = new Factura_Class();
        factura.setNombre_cliente(nombre_cliente);
        factura.setNombre_producto(nombre_producto);
        factura.setCantidad_producto(cantidad_producto);
        factura.setFecha_compra(fecha_compra);
        controlDB.insertarFactura(factura);
    }

    /*
     * Metodo para consultar facturas en base de datos
     */
    public ArrayList<Factura_Class> consultarFacturas(){
        return controlDB.consultaFacturas();
    }

    /*
    * Metodo para eliminar facturas
    */
    public void eliminarFactura(Factura_Class factura){
        controlDB.borrarFactura(factura);
    }

}
