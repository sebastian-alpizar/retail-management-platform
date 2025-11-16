package com.mycompany.sistemapuntodeventa.View;

import com.mycompany.sistemapuntodeventa.Controller.ClienteController;
import mycompany.sistemaentidades.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClientePanel extends JPanel {

    private JTextField buscarField;
    private JTextField idField;
    private JTextField nombreField;
    private JTextField telefonoField;
    private JTextField emailField;
    private JTextField descuentoField;
    private JButton reporteButton;
    private JButton buscarButton;
    private JButton guardarButton;
    private JButton actualizarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JTable clientesTable;
    private ClienteController clienteController; // Cambiado a ClienteController

    public ClientePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // Inicialización de JTextFields
        buscarField = new JTextField();
        descuentoField = new JTextField();
        telefonoField = new JTextField();
        nombreField = new JTextField();
        emailField = new JTextField();
        clientesTable = new JTable();
        idField = new JTextField();

        // Creacion de JPanels
        JPanel datosPanel = new JPanel(new GridLayout(3, 6, 20, 10));
        datosPanel.setBorder(BorderFactory.createTitledBorder("Cliente"));
        datosPanel.setPreferredSize(new Dimension(780, 125));

        JPanel panelBusqueda = new JPanel(new GridLayout(1, 4, 20, 0));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
        panelBusqueda.setPreferredSize(new Dimension(500, 50));

        JScrollPane jScrollPane = new JScrollPane(clientesTable);
        jScrollPane.setBorder(BorderFactory.createTitledBorder("Listado"));
        jScrollPane.setPreferredSize(new Dimension(600, 125));

        // Inicialización de JButtons
        actualizarButton = new JButton("Actualizar", Icons.ACTUALIZAR);
        guardarButton = new JButton("Guardar", Icons.GUARDAR);
        buscarButton = new JButton("Buscar", Icons.BUSCAR);
        limpiarButton = new JButton("Limpiar", Icons.LIMPIAR);
        borrarButton = new JButton("Borrar", Icons.BORRAR);
        reporteButton = new JButton("Reporte", Icons.PDF);

        // Agregar elementos al panel de datos
        datosPanel.add(new JLabel("ID:"));
        datosPanel.add(idField);
        datosPanel.add(new JLabel("Email:"));
        datosPanel.add(emailField);
        datosPanel.add(guardarButton);
        datosPanel.add(limpiarButton);
        datosPanel.add(new JLabel("Nombre:"));
        datosPanel.add(nombreField);
        datosPanel.add(new JLabel("Descuento:"));
        datosPanel.add(descuentoField);
        datosPanel.add(borrarButton);
        datosPanel.add(actualizarButton);
        datosPanel.add(new JLabel("Teléfono:"));
        datosPanel.add(telefonoField);
        datosPanel.add(new JLabel());
        datosPanel.add(new JLabel());
        datosPanel.add(new JLabel());
        datosPanel.add(new JLabel());
        add(datosPanel);

        // Agregar elementos al panel de busqueda
        panelBusqueda.add(new JLabel("Nombre o Id: "));
        panelBusqueda.add(buscarField);
        panelBusqueda.add(buscarButton);
        panelBusqueda.add(reporteButton);
        add(panelBusqueda);

        add(jScrollPane);
    }

    public JButton getReporteButton() {
        return reporteButton;
    }

    public JButton getBuscarButton() {
        return buscarButton;
    }

    public JButton getGuardarButton() {
        return guardarButton;
    }

    public JButton getActualizarButton() {
        return actualizarButton;
    }

    public JButton getBorrarButton() {
        return borrarButton;
    }

    public JTextField getIdField() {
        return idField;
    }

    public JTextField getNombreField() {
        return nombreField;
    }

    public JTextField getTelefonoField() {
        return telefonoField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JTextField getDescuentoField() {
        return descuentoField;
    }

    public JTable getClientesTable() {
        return clientesTable;
    }

    public JTextField getBuscarField() {
        return buscarField;
    }

    public JButton getLimpiarButton(){
        return limpiarButton;
    }

    public void actualizarTabla(List<Cliente> clientes) {
        String[] columnNames = {"ID", "Nombre", "Teléfono", "Email", "Descuento"};
        Object[][] data = new Object[clientes.size()][5];
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            data[i][0] = cliente.getId();
            data[i][1] = cliente.getNombre();
            data[i][2] = cliente.getTelefono();
            data[i][3] = cliente.getEmail();
            data[i][4] = cliente.getDescuento();
        }
        clientesTable.setModel(new DefaultTableModel(data, columnNames));
    }

    public void setController(ClienteController controller) {
        this.clienteController = controller;
        // Inicializar con datos actuales
        actualizarClientes();
    }

    public void actualizarClientes() {
        if (clienteController != null) {
            List<Cliente> clientes = clienteController.getClientes();
            actualizarTabla(clientes);
        }
    }
}
