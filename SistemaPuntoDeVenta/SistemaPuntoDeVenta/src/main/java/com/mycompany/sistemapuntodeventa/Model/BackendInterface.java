package com.mycompany.sistemapuntodeventa.Model;

import java.util.List;
import java.util.Map;
import mycompany.sistemaentidades.*;

public interface BackendInterface {
    // Usuarios
    public Boolean agregarUsuario(Usuario user);
    public Boolean existeUsuario(Usuario user);

    // Clientes
    public Cliente buscarClientePorId(String id);
    public Boolean agregarCliente(Cliente cliente);
    public List<Cliente> obtenerTodosLosClientes();
    public Cliente buscarClientePorNombre(String nombre);
    public Boolean eliminarCliente(String id);
    public Boolean actualizarCliente(Cliente cliente);

    // Cajeros
    public Cajero buscarCajeroPorId(String id);
    public Boolean actualizarCajero(Cajero cajero);
    public Boolean eliminarCajero(String id);
    public Cajero buscarCajeroPorNombre(String nombre);
    public Boolean agregarCajero(Cajero cajero);
    public List<Cajero> obtenerTodosLosCajeros();

    // Productos
    Boolean agregarProducto(Producto producto);
    Boolean actualizarProducto(Producto producto);
    Boolean eliminarProducto(String codigo);
    Producto buscarProductoPorCodigo(String codigo);
    Producto buscarProductoPorDescripcion(String descripcion);
    List<Producto> obtenerTodosLosProductos();
    List<String> obtenerCategorias();
    
    // Ventas
    public Boolean agregarVenta(String idFactura, String categoria, int year, int month, double total, String clienteId);
    public Map<String, Double> obtenerVentasMensualesPorCategoria(String categoria, int year, int month);
    
    // Facturas
    public Boolean agregarFactura(Factura factura);
    public List<Factura> obtenerTodasLasFacturas();
}