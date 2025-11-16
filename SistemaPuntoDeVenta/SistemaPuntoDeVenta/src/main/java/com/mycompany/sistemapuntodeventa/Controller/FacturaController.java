package com.mycompany.sistemapuntodeventa.Controller;

import com.mycompany.sistemapuntodeventa.View.MessageDialog.*;
import mycompany.sistemaentidades.*;
import com.mycompany.sistemapuntodeventa.View.FacturaPanel;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.mycompany.sistemapuntodeventa.View.Icons;
import com.mycompany.sistemapuntodeventa.View.ProductoPanel;

import javax.swing.*;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class FacturaController {

    private FacturaPanel facturaPanel;
    private Producto producto;
    private Proxy proxy;
    private int cantidad;
    private double descuento;
    private Boolean bandera;
    private ProductoPanel productoPanel;

    public FacturaController(FacturaPanel facturaPanel, ProductoPanel productoPanel, Proxy proxy) {
        this.facturaPanel = facturaPanel;
        this.proxy = proxy;
        producto = null;
        cantidad = 0;
        descuento = 0.0;
        bandera = false;
        this.productoPanel = productoPanel;
        cargarDatos();

        facturaPanel.getAgregarProductoButton().addActionListener(e -> agregarProducto());
        facturaPanel.getCobrarButton().addActionListener(e -> cobrar());
        facturaPanel.getCantidadButton().addActionListener(e -> agregarCantidad());
        facturaPanel.getQuitarButton().addActionListener(e -> quitarProducto());
        facturaPanel.getCancelarButton().addActionListener(e -> cancelarCompra());
        facturaPanel.getDescuentoButton().addActionListener(e -> agregarDescuento());
        facturaPanel.getBuscarButton().addActionListener(e -> buscarProducto());
    }

    private void cargarDatos() {
        List<String> clientes = proxy.obtenerTodosLosClientes().stream()
                .map(Cliente::getNombre)
                .collect(Collectors.toList());

        List<String> cajeros = proxy.obtenerTodosLosCajeros().stream()
                .map(Cajero::getNombre)
                .collect(Collectors.toList());

        facturaPanel.actualizarClientes(clientes);
        facturaPanel.actualizarCajeros(cajeros);
    }

    private void agregarProducto() {
       if (!facturaPanel.getProductoField().getText().isEmpty() && producto != null){
           LineaFactura nuevaLinea;
           if (bandera == true){
               nuevaLinea = new LineaFactura(producto, cantidad, descuento);
           } else {
               Cliente cliente = proxy.buscarClientePorNombre((String) facturaPanel.getClienteComboBox().getSelectedItem());
               nuevaLinea = new LineaFactura(producto, cantidad, cliente.getDescuento());
           }
           if (cantidad != 0){
                facturaPanel.getLineas().add(nuevaLinea);
                facturaPanel.actualizarTablaLineasFactura(facturaPanel.getLineas());
                cantidad = 0;
                descuento = 0.0;
                producto = null;
                bandera = false;
                facturaPanel.actualizarMontos();
                facturaPanel.getProductoField().setText("");
           } else {
               JOptionPane.showMessageDialog(facturaPanel, "Error. Digite la cantidad primero.", "Error", JOptionPane.ERROR_MESSAGE);
           }
       } else {
           JOptionPane.showMessageDialog(facturaPanel, "Error. Agrege la descripcion del producto.", "Error", JOptionPane.ERROR_MESSAGE);
       }
    }
    
    private void buscarProducto() {
       List<Producto> productos = proxy.obtenerTodosLosProductos();
       BuscarDialog dialog = new BuscarDialog(null, productos);
       String descripcion = dialog.mostrarDialogo();
       
       for (Producto produc: productos) {
           if (produc.getDescripcion().equals(descripcion)) {
               producto = produc;
               break;
           }
       }
       if (producto != null){
           facturaPanel.setProductoField(descripcion);
           
       } else {
           JOptionPane.showMessageDialog(facturaPanel, "Error. No se econtró el producto.", "Error", JOptionPane.ERROR_MESSAGE);
       }
    }
    
    private void agregarCantidad(){
        if (!facturaPanel.getProductoField().getText().isEmpty()) {
            producto = proxy.buscarProductoPorDescripcion(facturaPanel.getProductoField().getText());
            String input = (String) JOptionPane.showInputDialog(facturaPanel, "¿Cantidad?", producto.getDescripcion(), JOptionPane.QUESTION_MESSAGE, Icons.CANTIDAD, null, null);
            if (input != null) {
                try {
                    int cantidadIngresada = Integer.parseInt(input);
                    if (cantidadIngresada > producto.getExistencias()){
                        JOptionPane.showMessageDialog(facturaPanel, "Error. La cantidad sobrepasa la existencia del produto.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        cantidad = cantidadIngresada;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(facturaPanel, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(facturaPanel, "No se ingresó ninguna cantidad.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(facturaPanel, "Por favor, ingrese primero el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregarDescuento() {
        if (producto != null) {
            String input = (String) JOptionPane.showInputDialog(facturaPanel, "¿Descuento (%)?", producto.getDescripcion(), JOptionPane.QUESTION_MESSAGE, Icons.DESCUENTO, null, null);
            if (input != null) {
                try {
                    double desc = Double.parseDouble(input);
                    if (desc < 0 || desc > 100) {
                        JOptionPane.showMessageDialog(facturaPanel, "El descuento debe estar entre 0 y 100.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                         descuento = Double.parseDouble(input);
                         bandera = true;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(facturaPanel, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(facturaPanel, "No se ingresó ningún descuento.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(facturaPanel, "Por favor, ingrese primero el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void quitarProducto(){
        String codigo = (String) JOptionPane.showInputDialog(facturaPanel, "Ingrese el codigo del producto:", "Eliminar Producto" , JOptionPane.QUESTION_MESSAGE, Icons.QUITAR, null, null);
        if (codigo != null) {
            if (!facturaPanel.removerLineaPorCodigo(codigo)){
                JOptionPane.showMessageDialog(facturaPanel, "No se encontró el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                bandera = false;
            }
        } else {
            JOptionPane.showMessageDialog(facturaPanel, "No se ingresó ningun codigo.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void actualizarClientes(List<String> clientes) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(clientes.toArray(new String[0]));
        facturaPanel.getClienteComboBox().setModel(model);
    }

    public void actualizarCajeros(List<String> cajeros) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(cajeros.toArray(new String[0]));
        facturaPanel.getCajeroComboBox().setModel(model);
    }
    
    public void cancelarCompra() {
        bandera = false;
        facturaPanel.vaciarFactura();
    }

    private void cobrar() {
        try {
            if (!facturaPanel.getLineas().isEmpty()){
                String cliente = (String) facturaPanel.getClienteComboBox().getSelectedItem();
                String cajero = (String) facturaPanel.getCajeroComboBox().getSelectedItem();
                double total = Double.parseDouble(facturaPanel.getTotalLabel().getText().replace("Total: ", ""));

                CobrarDialog dialog = new CobrarDialog(null, total);
                String formaPago = dialog.mostrarDialogo();
                double montoIngresado = dialog.getMontoIngresado();
                if (montoIngresado >= total) {
                    double vuelto = montoIngresado - total;
                    Factura factura = new Factura(UUID.randomUUID().toString(), cliente, cajero, formaPago, facturaPanel.getLineas(), new Date(), total);
                    if (verificarCantidadesDisponibles(factura)){
                        if (proxy.agregarFactura(factura)){
                            actualizarInventario(factura);
                            for (LineaFactura linea : facturaPanel.getLineas()) {
                                Producto product = linea.getProducto();
                                proxy.agregarVenta(factura.getId(), product.getCategoria(), factura.getFecha().getYear() + 1900, factura.getFecha().getMonth() + 1, linea.getTotalLinea(), cliente);
                            }
                            productoPanel.actualizarTabla(proxy.obtenerTodosLosProductos());
                            generarPDF(factura);
                            facturaPanel.vaciarFactura();
                            JOptionPane.showMessageDialog(facturaPanel, "Su vuelto: " + vuelto, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(facturaPanel, "Error al ingresar la factura.","Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(facturaPanel, "El monto de pago es insuficiente.","Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(facturaPanel, "Error. No hay ningun producto agregado","Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(facturaPanel, "Error al procesar el cobro. Verifique los valores ingresados.");
        }
    }

    private void generarPDF(Factura factura) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("factura_" + factura.getId() + ".pdf"));
            document.open();

            // Información del cliente
            Cliente cliente = proxy.buscarClientePorId(factura.getCliente());
            if (cliente != null) {
                document.add(new Paragraph("Factura"));
                document.add(new Paragraph("ID Cliente: " + cliente.getId())); // ID del cliente
                document.add(new Paragraph("Cliente: " + cliente.getNombre())); // Nombre del cliente
            }

            // Información de la factura
            document.add(new Paragraph("ID Factura: " + factura.getId())); // ID de la factura
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Formato de fecha
            document.add(new Paragraph("Fecha: " + dateFormat.format(factura.getFecha()))); // Fecha de la factura
            document.add(new Paragraph("Cajero: " + factura.getCajero()));
            document.add(new Paragraph("Total: $" + factura.getTotal()));
            document.add(new Paragraph("Forma de Pago: " + factura.getFormaPago()));

            // Información de los productos
            document.add(new Paragraph("Productos:"));
            for (LineaFactura linea : facturaPanel.getLineas()) {
                document.add(new Paragraph(linea.getProducto().getDescripcion() + " - Cantidad: " + linea.getCantidad() + " - Total: $" + linea.getTotalLinea()));
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(facturaPanel, "Error al generar el PDF.");
        }
    }
    
    public void setearPanel(Factura factura) {
        facturaPanel.getTotalLabel().setText("Total: " + factura.getTotal());
        facturaPanel.getClienteComboBox().setSelectedItem(factura.getCliente());
        facturaPanel.getCajeroComboBox().setSelectedItem(factura.getCajero());
        
        facturaPanel.getLineas().clear();
        for (LineaFactura linea : factura.getLineas()) {
            facturaPanel.getLineas().add(linea);
        }
        facturaPanel.actualizarTablaLineasFactura(facturaPanel.getLineas());
        facturaPanel.actualizarMontos();
    }
    
    public Factura extraerFactura() {
        String cliente = (String) facturaPanel.getClienteComboBox().getSelectedItem();
        String cajero = (String) facturaPanel.getCajeroComboBox().getSelectedItem();

        double total = Double.parseDouble(facturaPanel.getTotalLabel().getText().replace("Total: ", ""));

        List<LineaFactura> lineasFactura = facturaPanel.getLineas();
        
        Factura factura = new Factura(
            null,
            cliente,
            cajero,
            "",
            lineasFactura,
            null,
            total
        );
        return factura;
    }
    
    private void actualizarInventario(Factura factura) {
        Map<String, Integer> cantidadesPorProducto = new HashMap<>();
        for (LineaFactura linea : factura.getLineas()) {
            String codigoProducto = linea.getProducto().getCodigo();
            int cantidadActual = cantidadesPorProducto.getOrDefault(codigoProducto, 0);
            cantidadesPorProducto.put(codigoProducto, cantidadActual + linea.getCantidad());
        }
        for (Map.Entry<String, Integer> entry : cantidadesPorProducto.entrySet()) {
            String codigoProducto = entry.getKey();
            int cantidadTotal = entry.getValue();
            Producto product = proxy.buscarProductoPorCodigo(codigoProducto);
            if (product != null) {
                int existenciasActuales = product.getExistencias();
                if (cantidadTotal >= existenciasActuales) {
                    proxy.eliminarProducto(codigoProducto);
                } else {
                    int nuevaExistencia = existenciasActuales - cantidadTotal;
                    product.setExistencias(nuevaExistencia);
                    proxy.actualizarProducto(product);
                }
            } else {
                JOptionPane.showMessageDialog(facturaPanel, "Error: Producto no encontrado en el inventario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private boolean verificarCantidadesDisponibles(Factura factura) {
        Map<String, Integer> cantidadesRequeridas = new HashMap<>();
        for (LineaFactura linea : factura.getLineas()) {
            String codigoProducto = linea.getProducto().getCodigo();
            int cantidadActual = cantidadesRequeridas.getOrDefault(codigoProducto, 0);
            cantidadesRequeridas.put(codigoProducto, cantidadActual + linea.getCantidad());
        }
        for (Map.Entry<String, Integer> entry : cantidadesRequeridas.entrySet()) {
            String codigoProducto = entry.getKey();
            int cantidadTotal = entry.getValue();
            Producto product = proxy.buscarProductoPorCodigo(codigoProducto);
            if (product == null || product.getExistencias() < cantidadTotal) {
                JOptionPane.showMessageDialog(facturaPanel, 
                    "No hay suficiente stock para el producto: " + codigoProducto, 
                    "Error de Stock", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }


    public List<Cliente> getClientes() {
        return proxy.obtenerTodosLosClientes();
    }
}
