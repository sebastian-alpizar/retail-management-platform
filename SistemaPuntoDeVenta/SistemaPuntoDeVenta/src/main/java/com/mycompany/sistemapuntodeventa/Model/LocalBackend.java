package com.mycompany.sistemapuntodeventa.Model;

import com.mycompany.sistemapuntodeventa.Controller.UsuarioController;
import com.mycompany.sistemapuntodeventa.View.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;
import mycompany.sistemaentidades.*;

public class LocalBackend implements BackendInterface {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Usuario user;
    private String host;
    private int port;
    private List<String> clientesEnLinea;
    private UsuariosPanel panel;
    private Boolean permiso;
    private SistemaPuntoDeVenta frame;
    private UsuarioController usuarioController;

    public LocalBackend(Usuario user, String host, int port, UsuariosPanel panel) {
        this.user = user;
        this.host = host;
        this.port = port;
        this.panel = panel;
        permiso = false;
        frame = null;
        this.usuarioController = null;
                
        try {
            socket = new Socket(host, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class MessageListener implements Runnable {
        public void run() {
            try {
                Object objeto = input.readObject();
                if (objeto instanceof Boolean) {
                    setPermiso((Boolean) objeto);
                }
                while (true) {
                    objeto = input.readObject();
                    if (objeto instanceof List<?>) {
                        clientesEnLinea = (List<String>) objeto;
                        SwingUtilities.invokeLater(() -> panel.updateTable(clientesEnLinea, user.getId()));
                        
                    } else if (objeto instanceof Map) {
                        procesarUsuariosEnLinea((Map<String, Object>) objeto);
                        
                    } else if (objeto instanceof Boolean){
                        setPermiso((Boolean) objeto);
                        
                    } else if (objeto instanceof Mensaje){
                        Mensaje mensaje = (Mensaje) objeto;
                        usuarioController.agregarMensaje(mensaje);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                cerrarRecursos();
            }
        }
    }
    
    private void procesarUsuariosEnLinea(Map<String, Object> mensajeActualizacion) {
        clientesEnLinea = (ArrayList<String>) mensajeActualizacion.get("clientesEnLinea");
        String usuarioDesconectado = (String) mensajeActualizacion.get("usuarioDesconectado");

        if (usuarioDesconectado != null && frame != null) {
            SwingUtilities.invokeLater(() -> frame.notificarDesconexion(usuarioDesconectado));
        }
        SwingUtilities.invokeLater(() -> panel.updateTable(clientesEnLinea, user.getId()));
    }
    
    private <T extends Serializable> Object enviarMensaje(T objeto)  {
        try (Socket sock = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(sock.getInputStream())) {
            
            out.writeObject(objeto);
            out.flush();
            return in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Usuario getUser() {
        return user;
    }
    
    private synchronized void setPermiso(Boolean permiso) {
        this.permiso = permiso;
        notify();
    }

    public UsuariosPanel getPanel() {
        return panel;
    }
    
    public void setFrame(SistemaPuntoDeVenta frame){
        this.frame = frame;
    }
    
    public void setUsuarioController(UsuarioController controller){
        this.usuarioController = controller;
    }
    
    private void cerrarRecursos() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public Boolean existeUsuario(Usuario usuario){
        try {
            output.writeObject(new Mensaje<>("Verificar", usuario));
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new MessageListener()).start();
        synchronized (this) {
            try {
                 this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return permiso;
    }
    
    @Override
    public Boolean agregarUsuario(Usuario usuario){
        try {
            output.writeObject(new Mensaje<>("Registrar", usuario));
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new MessageListener()).start();
        synchronized (this) {
            try {
                 this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return permiso;
    }
    
                                    // Clientes
    @Override
     public Cliente buscarClientePorId(String id) {
        return (Cliente) enviarMensaje("BuscarClientePorId:" + id);
     }
    @Override
     public Boolean agregarCliente(Cliente cliente) {
        return (Boolean) enviarMensaje(new Mensaje<>("Agregar", cliente));
     }
    @Override
     public List<Cliente> obtenerTodosLosClientes(){
         return (List<Cliente>) enviarMensaje("ObtenerTodosLosClientes");
     }
    @Override
     public Cliente buscarClientePorNombre(String nombre){
         return (Cliente) enviarMensaje("BuscarClientePorNombre:" + nombre);
     }
    @Override
     public Boolean eliminarCliente(String id){
         return (Boolean) enviarMensaje("EliminarCliente:" + id);
     }
    @Override
     public Boolean actualizarCliente(Cliente cliente){
         return (Boolean) enviarMensaje(new Mensaje<>("Actualizar", cliente));
     }
     
                                    // Cajeros
    @Override
    public Cajero buscarCajeroPorId(String id) {
        return (Cajero) enviarMensaje("BuscarCajeroPorId:" + id);
    }
    @Override
    public Boolean actualizarCajero(Cajero cajero) {
        return (Boolean) enviarMensaje(new Mensaje<>("Actualizar", cajero));
    }
    @Override
    public Boolean eliminarCajero(String id) {
        return (Boolean) enviarMensaje("EliminarCajero:"+ id);
    }
    @Override
    public Cajero buscarCajeroPorNombre(String nombre) {
        return (Cajero) enviarMensaje("BuscarCajeroPorNombre:" + nombre);
    }
    @Override
    public Boolean agregarCajero(Cajero cajero) {
        return (Boolean) enviarMensaje(new Mensaje<>("Agregar", cajero));
    }
    @Override
    public List<Cajero> obtenerTodosLosCajeros() {
        return (List<Cajero>) enviarMensaje("ObtenerTodosLosCajeros");
    }
    
                                    // Productos
    @Override
    public List<Producto> obtenerTodosLosProductos() {
        return (List<Producto>) enviarMensaje("ObtenerTodosLosProductos");
    }
    @Override
    public Producto buscarProductoPorCodigo(String codigo) {
        return (Producto) enviarMensaje("BuscarProductoPorCodigo:" + codigo);
    }
    @Override
    public List<String> obtenerCategorias() {
        return (List<String>) enviarMensaje("ObtenerCategorias");
    }
    @Override
    public Boolean agregarProducto(Producto producto) {
        return (Boolean) enviarMensaje(new Mensaje<>("Agregar", producto));
    }
    @Override
    public Boolean actualizarProducto(Producto producto) {
        return (Boolean) enviarMensaje(new Mensaje<>("Actualizar", producto));
    }
    @Override
    public Boolean eliminarProducto(String codigo) {
        return (Boolean) enviarMensaje("EliminarProducto:" + codigo);
    }
    @Override
    public Producto buscarProductoPorDescripcion(String descripcion){
        return (Producto) enviarMensaje("BuscarProductoPorDescripcion:" + descripcion);
    }
    
                                    // Ventas
    @Override
    public Boolean agregarVenta(String idFactura, String categoria, int year, int month, double total, String clienteId) {
        return (Boolean) enviarMensaje(new Mensaje<>("AgregarVenta", new Object[]{idFactura, categoria, year, month, total, clienteId}));
    }
    @Override
    public Map<String, Double> obtenerVentasMensualesPorCategoria(String categoria, int year, int month) {
        return (Map<String, Double>) enviarMensaje(new Mensaje<>("ObtenerVentasMensualesPorCategoria", new Object[]{categoria, year, month}));
    }
    
                                    // Facturas
    @Override
    public Boolean agregarFactura(Factura factura){
        return (Boolean) enviarMensaje(new Mensaje<>("Agregar", factura));
    }
    @Override
    public List<Factura> obtenerTodasLasFacturas(){
        return (List<Factura>) enviarMensaje("ObtenerTodasLasFacturas");
    }
    
    public Boolean enviarFactura(Factura factura, String destino){
        Mensaje mensaje = new Mensaje<>("Enviar a:" + destino, factura);
        mensaje.setEmisor(user.getId());
        return (Boolean) enviarMensaje(mensaje);
    }
}
