package com.mycompany.backendspv.logica;

import mycompany.sistemaentidades.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private List<String> clientesEnLinea;
    private Map<String, ObjectOutputStream> flujosClientes;
    private Backend backend;

    public Server() {
        this.clientesEnLinea = new ArrayList<>();
        this.flujosClientes = new HashMap<>();
        backend = new Backend();
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor desde el Real. Esperando conexiones...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<String> getClientesEnLinea(){
        return clientesEnLinea;
    }

    private class ServerHandler extends Thread {
        private Socket socket;
        private String userId;
        private ObjectOutputStream output;
        private ObjectInputStream input;
        Boolean bandera;

        public ServerHandler(Socket socket) {
            this.socket = socket;
            bandera = true;
            
            try {
                 output = new ObjectOutputStream(socket.getOutputStream());
                 input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    try {
                        Object inObjeto = input.readObject();
                        Object outObjeto = null;
                        if (inObjeto instanceof Mensaje) {
                            outObjeto = procesarMensaje((Mensaje) inObjeto);
                            if (!bandera){
                                output.writeObject(false);
                                output.flush();
                                return;
                            }
                        } else if (inObjeto instanceof String) {
                            outObjeto = procesarString((String) inObjeto);
                        }
                        output.writeObject(outObjeto);
                        output.flush();
                    } catch (EOFException e) {
                        break;
                    }
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Error: Clase no encontrada. " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error de E/S con el cliente " + userId + ": " + e.getMessage());
            } finally {
                cerrarConexion();
            }
        }
        
        private void cerrarConexion() {
            if (userId != null) {
                synchronized (clientesEnLinea) {
                    clientesEnLinea.remove(userId);
                    flujosClientes.remove(userId);
                    listaClientesActualizada(userId);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el socket: " + e.getMessage());
                }
            }
        }

        private void listaClientesActualizada(String userDesconectado) {
            synchronized (clientesEnLinea) {
                for (ObjectOutputStream out : flujosClientes.values()) {
                    try {
                        Map<String, Object> mensajeActualizacion = new HashMap<>();
                        mensajeActualizacion.put("clientesEnLinea", new ArrayList<>(clientesEnLinea));
                        mensajeActualizacion.put("usuarioDesconectado", userDesconectado);
                        out.writeObject(mensajeActualizacion);
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        private Boolean procesarUsuario(Usuario usuario, String detalle) throws IOException{
            if (!clientesEnLinea.contains(usuario.getId())){
                if (detalle.equals("Verificar")){
                    if(backend.existeUsuario(usuario)){
                        userId = usuario.getId();
                        output.writeObject(true);
                        output.flush();
                        synchronized (clientesEnLinea) {
                            clientesEnLinea.add(userId);
                            flujosClientes.put(userId, output);
                        }
                        listaClientesActualizada(null);
                        return true;
                    }
                }
                else{
                    if (backend.agregarUsuario(usuario)){
                        userId = usuario.getId();
                        output.writeObject(true);
                        output.flush();
                        synchronized (clientesEnLinea) {
                            clientesEnLinea.add(userId);
                            flujosClientes.put(userId, output);
                        }
                        listaClientesActualizada(null);
                        return true;
                    }
                }
            }
            bandera = false;
            return false;
        }
        
        public <T extends Serializable> Object procesarString(String mensaje) {
            int indiceDosPuntos;
            String msj;
            
            if (mensaje.startsWith("BuscarClientePorId:")){
                indiceDosPuntos = mensaje.indexOf(":");
                msj = mensaje.substring(indiceDosPuntos + 1);
                return backend.buscarClientePorId(msj);
                
            } else if (mensaje.startsWith("BuscarClientePorNombre:")){
                indiceDosPuntos = mensaje.indexOf(":");
                msj = mensaje.substring(indiceDosPuntos + 1);
                return backend.buscarClientePorNombre(msj);
                
            } else if (mensaje.startsWith("ObtenerTodosLosClientes")){
                return backend.obtenerTodosLosClientes();
                
            } else if (mensaje.startsWith("EliminarCliente:")){
                indiceDosPuntos = mensaje.indexOf(":");
                msj = mensaje.substring(indiceDosPuntos + 1);
                return backend.eliminarCliente(msj);
                
            } else if (mensaje.startsWith("BuscarProductoPorCodigo:")){
                indiceDosPuntos = mensaje.indexOf(":");
                msj = mensaje.substring(indiceDosPuntos + 1);
                return backend.buscarProductoPorCodigo(msj);
                
            } else if (mensaje.startsWith("ObtenerTodosLosProductos")){
                return backend.obtenerTodosLosProductos();
                
            } else if (mensaje.startsWith("BuscarProductoPorDescripcion:")){
                indiceDosPuntos = mensaje.indexOf(":");
                msj = mensaje.substring(indiceDosPuntos + 1);
                return backend.buscarProductoPorDescripcion(msj);
                
            } else if (mensaje.startsWith("EliminarProducto:")){
                indiceDosPuntos = mensaje.indexOf(":");
                msj = mensaje.substring(indiceDosPuntos + 1);
                return backend.eliminarProducto(msj);
                
            } else if (mensaje.startsWith("ObtenerCategorias")) {
                return backend.obtenerCategorias();
                
            } else if (mensaje.startsWith("EliminarCajero:")){
                indiceDosPuntos = mensaje.indexOf(":");
                msj = mensaje.substring(indiceDosPuntos + 1);
                return backend.eliminarCajero(msj);
                
            } else if (mensaje.startsWith("ObtenerTodosLosCajeros")){
                return backend.obtenerTodosLosCajeros();
                
            } else if (mensaje.startsWith("BuscarCajeroPorNombre:")){
                indiceDosPuntos = mensaje.indexOf(":");
                msj = mensaje.substring(indiceDosPuntos + 1);
                return backend.buscarCajeroPorNombre(msj);
                
            } else if (mensaje.startsWith("BuscarCajeroPorId:")){
                indiceDosPuntos = mensaje.indexOf(":");
                msj = mensaje.substring(indiceDosPuntos + 1);
                return backend.buscarCajeroPorId(msj);
                
            } else if (mensaje.startsWith("ObtenerTodasLasFacturas")){
                return backend.obtenerTodasLasFacturas();
            }
            return null;
        }
        
        private Boolean procesarCliente(Cliente cliente, String detalle) {
            if (detalle.equals("Agregar")){
                return backend.agregarCliente(cliente);
            } else if (detalle.equals("Actualizar")){
                return backend.actualizarCliente(cliente);
            }
            return false;
        }
        
        private Boolean procesarProducto(Producto producto, String detalle) {
            if (detalle.equals("Agregar")){
                return backend.agregarProducto(producto);
            } else if (detalle.equals("Actualizar")){
                return backend.actualizarProducto(producto);
            }
            return false;
        }
        
        private Boolean procesarCajero(Cajero cajero, String detalle) {
            if (detalle.equals("Agregar")){
                return backend.agregarCajero(cajero);
            } else if (detalle.equals("Actualizar")){
                return backend.actualizarCajero(cajero);
            }
            return false;
        }
        
        private Boolean procesaFactura(Mensaje mensaje, String detalle) {
            if (detalle.equals("Agregar")){
                Factura factura = (Factura) mensaje.getDatos();
                return backend.agregarFactura(factura);
            } else if (detalle.startsWith("Enviar a:")){
                return enviarFactura(mensaje, detalle);
            }
            return false;
        }
        
        private <T extends Serializable> Object procesarMensaje(Mensaje<?> mensaje) throws IOException {
            String detalle = (String) mensaje.getDetalle();

            if ((mensaje.getDatos() instanceof Usuario)){
                Usuario usuario = (Usuario) mensaje.getDatos();
                return procesarUsuario(usuario, detalle);

            } else if ((mensaje.getDatos() instanceof Factura)){
                return procesaFactura(mensaje, detalle);

            } else if ((mensaje.getDatos() instanceof Cajero)){
                Cajero cajero = (Cajero) mensaje.getDatos();
                return procesarCajero(cajero, detalle);

            } else if ((mensaje.getDatos() instanceof Cliente)){
                Cliente cliente = (Cliente) mensaje.getDatos();
                return procesarCliente(cliente, detalle);

            } else if ((mensaje.getDatos() instanceof Producto)){
                Producto producto = (Producto) mensaje.getDatos();
                return procesarProducto(producto, detalle);
                
            } else if ((mensaje.getDatos() instanceof Object[])){
                Object[] datos = (Object[]) mensaje.getDatos();
                return procesarArrayDeParametros(datos, detalle);
            }
            return null;
        }
        
        private Boolean enviarFactura(Mensaje mensaje, String detalle){
            String destino = detalle.substring("Enviar a:".length()).trim();
            ObjectOutputStream flujoDestino = flujosClientes.get(destino);
            
            if (flujoDestino != null) {
                try {
                    flujoDestino.writeObject(mensaje);
                    flujoDestino.flush();
                    return true;
                } catch (IOException e) {
                    System.err.println("Error al enviar la factura al usuario " + destino + ": " + e.getMessage());
                }
            }
            return false;
        }

        private <T extends Serializable> Object procesarArrayDeParametros(Object[] datos, String detalle) throws IOException {
            switch (detalle) {
                    case "AgregarVenta":
                        String idFactura = (String) datos[0];
                        String categoria = (String) datos[1];
                        int year = (int) datos[2];
                        int month = (int) datos[3];
                        double total = (double) datos[4];
                        String clienteId = (String) datos[5];
                        return backend.agregarVenta(idFactura, categoria, year, month, total, clienteId);

                    case "ObtenerVentasMensualesPorCategoria":
                        categoria = (String) datos[0];
                        year = (int) datos[1];
                        month = (int) datos[2];
                        return backend.obtenerVentasMensualesPorCategoria(categoria, year, month);
                }
            return null;
        }
    }
}
