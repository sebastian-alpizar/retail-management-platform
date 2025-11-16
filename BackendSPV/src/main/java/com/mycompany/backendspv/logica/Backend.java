package com.mycompany.backendspv.logica;

import com.mycompany.backendspv.datos.*;
import java.util.List;
import java.util.Map;
import mycompany.sistemaentidades.*;

public class Backend {
    private UsuarioDAO usuarios;
    private ClienteDAO clientes;
    private ProductoDAO productos;
    private CajeroDAO cajeros;
    private VentaDAO ventas;
    private FacturaDAO facturas;
    private LineaFacturaDAO lineas;
    
    public Backend(){
        usuarios = new UsuarioDAO();
        clientes = new ClienteDAO();
        productos = new ProductoDAO();
        cajeros = new CajeroDAO();
        ventas = new VentaDAO();
        lineas = new LineaFacturaDAO(productos);
        facturas = new FacturaDAO(lineas);
    }
    
    // Metodos de Usuarios para la base de datos
    public Boolean agregarUsuario(Usuario user) {
        return usuarios.agregarUsuario(user);
    }
    public Boolean existeUsuario(Usuario usuario) {
        return usuarios.existeUsuario(usuario);
    }
    public Boolean agregarCliente(Cliente cliente) {
        return clientes.agregarCliente(cliente);
    }
    public Cliente buscarClientePorId(String id) {
        return clientes.buscarClientePorId(id);
    }
    
    // Metodos de Clientes para la base de datos
    public Cliente buscarClientePorNombre(String id) {
        return clientes.buscarClientePorNombre(id);
    }
    public Boolean eliminarCliente(String id) {
        return clientes.eliminarCliente(id);
    }
    public Boolean actualizarCliente(Cliente cliente) {
       return clientes.actualizarCliente(cliente);
    }
    public List<Cliente> obtenerTodosLosClientes() {
        return clientes.obtenerTodosLosClientes();
    }
    
    // Metodos de Productos para la base de datos
    public Producto buscarProductoPorCodigo(String codigo){
        return productos.buscarProductoPorCodigo(codigo);
    }
    public Producto buscarProductoPorDescripcion(String descripcion){
        return productos.buscarProductoPorDescripcion(descripcion);
    }
    public List<Producto> obtenerTodosLosProductos(){
        return productos.obtenerTodosLosProductos();
    }
    public Boolean agregarProducto(Producto producto){
        return productos.agregarProducto(producto);
    }
    public Boolean actualizarProducto(Producto producto){
        return productos.actualizarProducto(producto);
    }
    public Boolean eliminarProducto(String codigo){
        return productos.eliminarProducto(codigo);
    }
    public List<String> obtenerCategorias(){
        return productos.obtenerCategorias();
    }
    
      // Cajeros
    public Cajero buscarCajeroPorId(String id){
        return cajeros.buscarCajeroPorId(id);
    }
    public Boolean actualizarCajero(Cajero cajero){
        return cajeros.actualizarCajero(cajero);
    }
    public Boolean eliminarCajero(String id){
        return cajeros.eliminarCajero(id);
    }
    public Cajero buscarCajeroPorNombre(String nombre){
        return cajeros.buscarCajeroPorNombre(nombre);
    }
    public Boolean agregarCajero(Cajero cajero){
        return cajeros.agregarCajero(cajero);
    }
    public List<Cajero> obtenerTodosLosCajeros(){
        return cajeros.obtenerTodosLosCajeros();
    }
    
    // Ventas
    public Boolean agregarVenta(String idFactura, String categoria, int year, int month, double total, String clienteId){
        return ventas.agregarVenta(idFactura, categoria, year, month, total, clienteId);
    }
    public Map<String, Double> obtenerVentasMensualesPorCategoria(String categoria, int year, int month){
        return ventas.obtenerVentasMensualesPorCategoria(categoria, year, month);
    }
    
    // Facturas
    public Boolean agregarFactura(Factura factura){
        return facturas.agregarFactura(factura);
    }
    public List<Factura> obtenerTodasLasFacturas(){
        return facturas.obtenerTodasLasFacturas();
    }
}
