package com.mycompany.sistemapuntodeventa.View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import mycompany.sistemaentidades.*;

public class FacturaPanel extends JPanel {
    
    private JTextField productoField;
    private JButton agregarProductoButton;
    private JButton cobrarButton;
    private JButton buscarButton;
    private JButton cantidadButton;
    private JButton quitarButton;
    private JButton descuentoButton;
    private JButton cancelarButton;
    private JComboBox<String> clienteComboBox;
    private JComboBox<String> cajeroComboBox;
    private JTable productosTable;
    private JTable lineaFacturaTable;
    private JLabel articulosLabel;
    private JLabel descuentoLabel;
    private JLabel subtotalLabel;
    private JLabel totalLabel;
    private List<LineaFactura> lineas;

    public FacturaPanel(List<String> clientes, List<String> cajeros) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        productosTable = new JTable();
        agregarProductoButton = new JButton();
        cobrarButton = new JButton();
        lineaFacturaTable = new JTable();
        lineas = new ArrayList<>();
        
        JLabel iconLabel = new JLabel(Icons.CODIGO);
        
        clienteComboBox = new JComboBox<>(clientes.toArray(new String[0]));
        cajeroComboBox = new JComboBox<>(cajeros.toArray(new String[0]));
        clienteComboBox.setPreferredSize(new Dimension(250, 25));
        cajeroComboBox.setPreferredSize(new Dimension(250, 25));
        
        JPanel clientePanel = new JPanel();
        clientePanel.setBorder(BorderFactory.createTitledBorder("Cliente"));
        JPanel cajeroPanel = new JPanel();
        cajeroPanel.setBorder(BorderFactory.createTitledBorder("Cajero"));
        
        JScrollPane scrollPane = new JScrollPane(lineaFacturaTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Listado"));
        scrollPane.setPreferredSize(new Dimension(600, 125));
        
        clientePanel.add(clienteComboBox);
        cajeroPanel.add(cajeroComboBox);
        
        JPanel panelProducto = new JPanel(new GridLayout(1, 3, 20, 10));
        panelProducto.setBorder(BorderFactory.createTitledBorder("Producto"));
        panelProducto.setPreferredSize(new Dimension(380, 50));
        
        productoField = new JTextField();
        agregarProductoButton = new JButton("Agregar", Icons.AGREGAR);
        panelProducto.add(iconLabel);
        panelProducto.add(productoField);
        panelProducto.add(agregarProductoButton);
        
        JPanel panelFunciones = new JPanel(new GridLayout(1, 6, 20, 10));
        panelFunciones.setBorder(BorderFactory.createTitledBorder("Funciones"));
        panelFunciones.setPreferredSize(new Dimension(750, 50));
        
        cobrarButton = new JButton("Cobrar", Icons.COBRAR);
        buscarButton = new JButton("Buscar", Icons.BUSCAR);
        cantidadButton = new JButton("Cantidad", Icons.CANTIDAD);
        quitarButton = new JButton("Quitar", Icons.QUITAR);
        descuentoButton = new JButton("Descuento", Icons.DESCUENTO);
        cancelarButton = new JButton("Cancelar", Icons.CANCELAR);
        
        panelFunciones.add(cobrarButton);
        panelFunciones.add(buscarButton);
        panelFunciones.add(cantidadButton);
        panelFunciones.add(quitarButton);
        panelFunciones.add(descuentoButton);
        panelFunciones.add(cancelarButton);
        
        JPanel panelTotales = new JPanel(new GridLayout(1, 4, 20, 10));
        panelTotales.setBorder(BorderFactory.createTitledBorder("Totales"));
        panelTotales.setPreferredSize(new Dimension(600, 60));
        
        articulosLabel = new JLabel("Articulos: 0");
        descuentoLabel = new JLabel("Descuentos: 0.0");
        subtotalLabel = new JLabel("Subtotal: 0.0");
        totalLabel = new JLabel("Total: 0.0");
        
        panelTotales.add(articulosLabel);
        panelTotales.add(subtotalLabel);
        panelTotales.add(descuentoLabel);
        panelTotales.add(totalLabel);
        
        add(clientePanel);
        add(cajeroPanel);
        add(scrollPane);
        add(panelProducto);
        add(panelFunciones);
        add(panelTotales);
    }

    public void actualizarClientes(List<String> clientes) {
        clienteComboBox.removeAllItems();
        for (String cliente : clientes) {
            clienteComboBox.addItem(cliente);
        }
    }

    public void actualizarCajeros(List<String> cajeros) {
        cajeroComboBox.removeAllItems();
        for (String cajero : cajeros) {
            cajeroComboBox.addItem(cajero);
        }
    }
    
    public void actualizarTablaLineasFactura(List<LineaFactura> lineasFactura) {
        String[] columnNames = {"Código", "Articulo", "Categoría", "Cantidad", "Precio", "Descuento", "Neto", "Importe"};
        Object[][] data = new Object[lineasFactura.size()][8];

        for (int i = 0; i < lineasFactura.size(); i++) {
            LineaFactura linea = lineasFactura.get(i);
            Producto producto = linea.getProducto();

            data[i][0] = producto.getCodigo();   
            data[i][1] = producto.getDescripcion();      
            data[i][2] = producto.getCategoria();
            data[i][3] = linea.getCantidad();
            data[i][4] = producto.getPrecioUnitario();   
            data[i][5] = linea.getDescuento();
            data[i][6] = (producto.getPrecioUnitario() * (1 - linea.getDescuento() / 100)); // Neto
            data[i][7] = linea.getTotal();  // Importe total de la línea
        }
        lineaFacturaTable.setModel( new DefaultTableModel(data, columnNames));
        lineaFacturaTable.getColumnModel().getColumn(1).setPreferredWidth(200);
    }
    
     public void actualizarMontos() {
        int totalArticulos = 0;
        double totalDescuentos = 0.0;
        double subtotal = 0.0;
        double total;

        for (LineaFactura linea : lineas) {
            totalArticulos += linea.getCantidad();
            totalDescuentos += linea.getDescuento();
            subtotal += linea.getTotal();
        }
        total = subtotal - totalDescuentos;
        articulosLabel.setText("Articulos: " + totalArticulos);
        descuentoLabel.setText("Descuentos: " + totalDescuentos);
        subtotalLabel.setText("Subtotal: " + subtotal);
        totalLabel.setText("Total: " + total);
    }
     
     public Boolean removerLineaPorCodigo(String codigo) {
        LineaFactura lineaARemover = null;
        for (LineaFactura linea : lineas) {
            if (linea.getProducto().getCodigo().equals(codigo)) {
                lineaARemover = linea;
                break;
            }
        }
        if (lineaARemover != null) {
            lineas.remove(lineaARemover);
            actualizarTablaLineasFactura(lineas);
            actualizarMontos();
            return true;
        } 
        return false;
    }
     
     public void vaciarFactura() {
        lineas.clear();
        actualizarTablaLineasFactura(lineas);
        articulosLabel.setText("Articulos: 0");
        descuentoLabel.setText("Descuentos: 0.0");
        subtotalLabel.setText("Subtotal: 0.0");
        totalLabel.setText("Total: 0.0");
        productoField.setText("");
    }
     
     public JTextField getProductoField() {
        return productoField;
    }

    public JButton getAgregarProductoButton() {
        return agregarProductoButton;
    }

    public JButton getCobrarButton() {
        return cobrarButton;
    }

    public JButton getBuscarButton() {
        return buscarButton;
    }

    public JButton getCantidadButton() {
        return cantidadButton;
    }

    public JButton getQuitarButton() {
        return quitarButton;
    }

    public JButton getDescuentoButton() {
        return descuentoButton;
    }

    public JButton getCancelarButton() {
        return cancelarButton;
    }

    public JComboBox<String> getClienteComboBox() {
        return clienteComboBox;
    }

    public JComboBox<String> getCajeroComboBox() {
        return cajeroComboBox;
    }

    public JTable getProductosTable() {
        return productosTable;
    }

    public JTable getLinaFacturaTable() {
        return lineaFacturaTable;
    }
    
    public List<LineaFactura> getLineas(){
        return lineas;
    }
    
    public JLabel getTotalLabel(){
        return totalLabel;
    }
    
    public void setProductoField(String descripcion){
        productoField.setText(descripcion);
    }
}
