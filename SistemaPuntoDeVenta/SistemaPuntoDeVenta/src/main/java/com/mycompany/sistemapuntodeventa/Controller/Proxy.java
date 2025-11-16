package com.mycompany.sistemapuntodeventa.Controller;

import com.mycompany.sistemapuntodeventa.Model.*;
import com.mycompany.sistemapuntodeventa.View.SistemaPuntoDeVenta;
import com.mycompany.sistemapuntodeventa.View.UsuariosPanel;
import java.util.List;
import java.util.Map;
import mycompany.sistemaentidades.*;


public class Proxy {
    private LocalBackend backend;
    
    // Metodos propios de la clase
    public Usuario getUser() {
        return backend.getUser();
    }
    public UsuariosPanel getPanel() {
        return backend.getPanel();
    }
    public void setFrame(SistemaPuntoDeVenta frame){
        backend.setFrame(frame);
    }
    public void setUsuarioController(UsuarioController usuarioController){
        backend.setUsuarioController(usuarioController);
    }
    public Boolean enviarFactura(Factura factura, String destino){
        return backend.enviarFactura(factura, destino);
    }

    // Usuarios
    public Proxy(Usuario user, String host, int port, UsuariosPanel panel) {
        backend = new LocalBackend(user, host, port, panel);
    }
    public Boolean existeUsuario(Usuario usuario){
        return backend.existeUsuario(usuario);
    }
    public Boolean agregarUsuario(Usuario usuario){
        return backend.agregarUsuario(usuario);
    }
    
    // Clientes
    public Cliente buscarClientePorId(String id) {
        return backend.buscarClientePorId(id);
     }
     public Boolean agregarCliente(Cliente cliente){
         return backend.agregarCliente(cliente);
     }
     public List<Cliente> obtenerTodosLosClientes(){
         return backend.obtenerTodosLosClientes();
     }
     public Cliente buscarClientePorNombre(String nombre){
         return backend.buscarClientePorNombre(nombre);
     }
     public Boolean eliminarCliente(String id){
         return backend.eliminarCliente(id);
     }
     public Boolean actualizarCliente(Cliente cliente){
         return backend.actualizarCliente(cliente);
     }
     
     // Productos
    public Producto buscarProductoPorCodigo(String codigo) {
        return backend.buscarProductoPorCodigo(codigo);
    }
    public Boolean agregarProducto(Producto producto) {
        return backend.agregarProducto(producto);
    }
    public Boolean actualizarProducto(Producto producto) {
        return backend.actualizarProducto(producto);
    }
    public Boolean eliminarProducto(String codigo) {
        return backend.eliminarProducto(codigo);
    }
    public List<Producto> obtenerTodosLosProductos(){
        return backend.obtenerTodosLosProductos();
    }
    public Producto buscarProductoPorDescripcion(String descripcion) {
        return backend.buscarProductoPorDescripcion(descripcion);
    }
    public List<String> obtenerCategorias(){
        return backend.obtenerCategorias();
    }
    
      // Cajeros
    public Cajero buscarCajeroPorId(String id){
        return backend.buscarCajeroPorId(id);
    }
    public Boolean actualizarCajero(Cajero cajero){
        return backend.actualizarCajero(cajero);
    }
    public Boolean eliminarCajero(String id){
        return backend.eliminarCajero(id);
    }
    public Cajero buscarCajeroPorNombre(String nombre){
        return backend.buscarCajeroPorNombre(nombre);
    }
    public Boolean agregarCajero(Cajero cajero){
        return backend.agregarCajero(cajero);
    }
    public List<Cajero> obtenerTodosLosCajeros(){
        return backend.obtenerTodosLosCajeros();
    }
    
    // Ventas
    public Boolean agregarVenta(String idFactura, String categoria, int year, int month, double total, String clienteId){
        return backend.agregarVenta(idFactura, categoria, year, month, total, clienteId);
    }
    public Map<String, Double> obtenerVentasMensualesPorCategoria(String categoria, int year, int month){
        return backend.obtenerVentasMensualesPorCategoria(categoria, year, month);
    }
    
    // Facturas
    public Boolean agregarFactura(Factura factura){
        return backend.agregarFactura(factura);
    }
    public List<Factura> obtenerTodasLasFacturas(){
        return backend.obtenerTodasLasFacturas();
    }
}

