package com.mycompany.sistemapuntodeventa.View.MessageDialog;

import com.mycompany.sistemapuntodeventa.View.Icons;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import mycompany.sistemaentidades.Producto;

public class BuscarDialog extends JDialog {
    private String descripcionIngresada = null;
    private JTextField descripcionField;
    private JTable table;

    public BuscarDialog(Frame parent, List<Producto> productos) {
        super(parent, "Productos", true);
        setLayout(new BorderLayout());
        setResizable(false);
        setIconImage(Icons.BUSCAR.getImage());

        JPanel descripcionPanel = new JPanel();
        descripcionPanel.add(new JLabel("Descripción:"));
        descripcionField = new JTextField(20);
        descripcionPanel.add(descripcionField);
        add(descripcionPanel, BorderLayout.NORTH);

        String[] columnNames = {"Código", "Descripción", "Unidad", "Precio", "Categoría"};
        Object[][] data = new Object[productos.size()][5];
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            data[i][0] = producto.getCodigo();
            data[i][1] = producto.getDescripcion();
            data[i][2] = producto.getUnidadMedida();
            data[i][3] = producto.getPrecio();
            data[i][4] = producto.getCategoria();
        }
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        table.setEnabled(false);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 180));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancelar");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                descripcionIngresada = descripcionField.getText();
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                descripcionIngresada = null;
                dispose();
            }
        });
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
    }

    public String mostrarDialogo() {
        setVisible(true); 
        return descripcionIngresada;
    }
}
