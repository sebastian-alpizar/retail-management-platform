package com.mycompany.sistemapuntodeventa.View;

import mycompany.sistemaentidades.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoricoFacturasPanel extends JPanel {
    private final JTextField clienteIdField;
    private final JButton buscarButton;
    private final JTable facturasTable;

    public HistoricoFacturasPanel(List<String> clientes) {
        setLayout(new BorderLayout());

        // Crear componentes
        clienteIdField = new JTextField(15);
        buscarButton = new JButton("Buscar", Icons.BUSCAR);
        facturasTable = new JTable();

        // Configurar el panel
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Nombre del Cliente:"));
        topPanel.add(clienteIdField);
        topPanel.add(buscarButton);
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(facturasTable), BorderLayout.CENTER);
    }
    
    public String getClienteId() {
        return clienteIdField.getText();
    }

    public JButton getBuscarButton() {
        return buscarButton;
    }

    public void mostrarFacturas(List<Factura> facturas) {
        String[] columnNames = {"ID", "Cliente", "Nombre Cliente", "Cajero", "Forma de Pago", "Fecha", "Total"};
        Object[][] data = new Object[facturas.size()][columnNames.length];

        for (int i = 0; i < facturas.size(); i++) {
            Factura factura = facturas.get(i);
            data[i][0] = factura.getId();
            data[i][1] = factura.getCliente();
            data[i][2] = factura.getNombreCliente();
            data[i][3] = factura.getCajero();
            data[i][4] = factura.getFormaPago();
            data[i][5] = new SimpleDateFormat("dd-MM-yyyy").format(factura.getFecha());
            data[i][6] = factura.getTotal();
        }

        facturasTable.setModel(new DefaultTableModel(data, columnNames));
    }
}
