package com.mycompany.sistemapuntodeventa.View;

import com.mycompany.sistemapuntodeventa.Controller.CajeroController;
import mycompany.sistemaentidades.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CajeroPanel extends JPanel {

    private JTextField idField;
    private JTextField nombreField;
    private JTextField telefonoField;
    private JTextField emailField;
    private JTextField buscarField;
    private JButton buscarButton;
    private JButton guardarButton;
    private JButton actualizarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JTable cajerosTable;
    private CajeroController cajeroController; // Cambiado a CajeroController

    public CajeroPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // Inicialización de JTextFields
        buscarField = new JTextField();
        telefonoField = new JTextField();
        nombreField = new JTextField();
        emailField = new JTextField();
        cajerosTable = new JTable();
        idField = new JTextField();

        // Creacion de JPanels
        JPanel datosPanel = new JPanel(new GridLayout(2, 6, 20, 10));
        datosPanel.setBorder(BorderFactory.createTitledBorder("Cajero"));
        datosPanel.setPreferredSize(new Dimension(780, 88));

        JPanel panelBusqueda = new JPanel(new GridLayout(1, 3, 20, 0));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
        panelBusqueda.setPreferredSize(new Dimension(500, 50));

        JScrollPane jScrollPane = new JScrollPane(cajerosTable);
        jScrollPane.setBorder(BorderFactory.createTitledBorder("Listado"));
        jScrollPane.setPreferredSize(new Dimension(600, 125));

        // Inicialización de JButtons
        actualizarButton = new JButton("Actualizar", Icons.ACTUALIZAR);
        guardarButton = new JButton("Guardar", Icons.GUARDAR);
        buscarButton = new JButton("Buscar", Icons.BORRAR);
        limpiarButton = new JButton("Limpiar", Icons.LIMPIAR);
        borrarButton = new JButton("Borrar", Icons.BORRAR);

        // Agregar elementos al panel de datos
        datosPanel.add(new JLabel("ID:"));
        datosPanel.add(idField);
        datosPanel.add(new JLabel("Email:"));
        datosPanel.add(emailField);
        datosPanel.add(guardarButton);
        datosPanel.add(limpiarButton);
        datosPanel.add(new JLabel("Nombre:"));
        datosPanel.add(nombreField);
        datosPanel.add(new JLabel("Teléfono:"));
        datosPanel.add(telefonoField);
        datosPanel.add(borrarButton);
        datosPanel.add(actualizarButton);
        add(datosPanel);

        // Agregar elementos al panel de busqueda
        panelBusqueda.add(new JLabel("Nombre o Id: "));
        panelBusqueda.add(buscarField);
        panelBusqueda.add(buscarButton);
        add(panelBusqueda);

        add(jScrollPane);
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

    public JTable getCajerosTable() {
        return cajerosTable;
    }

    public JTextField getBuscarField() {
        return buscarField;
    }

    public JButton getLimpiarButton() {
        return limpiarButton;
    }
    
    public void actualizarTabla(List<Cajero> cajeros) {
        String[] columnNames = {"ID", "Nombre", "Teléfono", "Email"};
        Object[][] data = new Object[cajeros.size()][4];
        for (int i = 0; i < cajeros.size(); i++) {
            Cajero cajero = cajeros.get(i);
            data[i][0] = cajero.getId();
            data[i][1] = cajero.getNombre();
            data[i][2] = cajero.getTelefono();
            data[i][3] = cajero.getEmail();
        }
        cajerosTable.setModel(new DefaultTableModel(data, columnNames));
    }

    public void setController(CajeroController controller) {
        this.cajeroController = controller;
        // Inicializar con datos actuales
        actualizarCajeros();
    }

    public void actualizarCajeros() {
        if (cajeroController != null) {
            List<Cajero> cajeros = cajeroController.getCajeros();
            actualizarTabla(cajeros);
        }
    }
}
