package com.mycompany.sistemapuntodeventa.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsuariosPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable userTable;
    private JButton recibirButton;
    private JButton enviarButton;

    public UsuariosPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(180, 500));
        setBorder(BorderFactory.createTitledBorder("Usuarios"));
        
        recibirButton = new JButton("Recibir");
        enviarButton = new JButton("Enviar");
        
        tableModel = new DefaultTableModel(new Object[]{"Id", "Factura?"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 1 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };
        
        userTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Usuarios"));
        scrollPane.setPreferredSize(new Dimension(150, 500));
        
        add(recibirButton);
        add(enviarButton);
        add(scrollPane);
    }

    public String getSelectedUser() {
        String selectedUser = null;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean isSelected = (Boolean) tableModel.getValueAt(i, 1);
            if (isSelected != null && isSelected) {
                if (selectedUser != null) {
                    return null; // Hay más de un usuario marcado
                }
                selectedUser = (String) tableModel.getValueAt(i, 0);
            }
        }
        return selectedUser;
    }
    
    public void updateTable(List<String> clientesEnLinea, String id) {
        tableModel.setRowCount(0);

        for (String usuario : clientesEnLinea) {
            if (!usuario.equals(id)) {
                tableModel.addRow(new Object[]{usuario, false});
            }
        }
    }
    
    public void addUser(String usuario) {
        tableModel.addRow(new Object[]{usuario, false});
    }
    
    public JButton getEnviarButton() {
        return enviarButton;
    }
    
    public JButton getRecibirButton() {
        return recibirButton;
    }
    
    public void limpiarCheckBox() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(false, i, 1);
        }
    }
    
    public void marcarCheckboxPorId(String idUsuario) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String usuario = (String) tableModel.getValueAt(i, 0);
            if (usuario.equals(idUsuario)) {
                tableModel.setValueAt(true, i, 1);
                break;
            } 
        }
    }
}
