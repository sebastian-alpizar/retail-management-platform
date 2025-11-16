package com.mycompany.sistemapuntodeventa.View.MessageDialog;

import com.mycompany.sistemapuntodeventa.View.Icons;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CobrarDialog extends JDialog {
    private String formaPagoSeleccionada = null;
    private double montoIngresado = 0.0;
    private JTextField montoField;

    public CobrarDialog(Frame parent, double total) {
        super(parent, "Cobrar", true);
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(330, 295));
        setIconImage(Icons.COBRAR.getImage());
        setResizable(false);
        
        JPanel formaPagoPanel = new JPanel(new FlowLayout());
        formaPagoPanel.setBorder(BorderFactory.createTitledBorder("Pagos recibidos"));
        formaPagoPanel.setPreferredSize(new Dimension(150, 180));
        
        JPanel importePanel = new JPanel();
        importePanel.setBorder(BorderFactory.createTitledBorder("Importe a pagar"));
        importePanel.setPreferredSize(new Dimension(150, 50));
        importePanel.add(new JLabel("" + total));
        
        JRadioButton tarjetaButton = new JRadioButton("Tarjeta");
        JRadioButton chequeButton = new JRadioButton("Cheque");
        JRadioButton efectivoButton = new JRadioButton("Efectivo");
        JRadioButton simpleButton = new JRadioButton("Simple");
        ButtonGroup group = new ButtonGroup();
        
        group.add(tarjetaButton);
        group.add(chequeButton);
        group.add(efectivoButton);
        group.add(simpleButton);
        
        formaPagoPanel.add(tarjetaButton);
        formaPagoPanel.add(chequeButton);
        formaPagoPanel.add(efectivoButton);
        formaPagoPanel.add(simpleButton);

        // Crear un solo campo de texto para el monto
        montoField = new JTextField();
        JPanel montoPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        montoPanel.setPreferredSize(new Dimension(180, 20));
        montoPanel.add(new JLabel("Monto:"));
        montoPanel.add(montoField);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancelar");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tarjetaButton.isSelected()) {
                    formaPagoSeleccionada = "Tarjeta";
                } else if (chequeButton.isSelected()) {
                    formaPagoSeleccionada = "Cheque";
                } else if (efectivoButton.isSelected()) {
                    formaPagoSeleccionada = "Efectivo";
                } else if (simpleButton.isSelected()) {
                    formaPagoSeleccionada = "Simple";
                }
                try {
                    montoIngresado = Double.parseDouble(montoField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CobrarDialog.this, "Monto inválido. Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formaPagoSeleccionada = null;
                montoIngresado = 0.0;
                dispose();
            }
        });
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(formaPagoPanel);
        add(importePanel);
        add(montoPanel);
        add(buttonPanel);
        pack();
        setLocationRelativeTo(parent);
    }

    public String mostrarDialogo() {
        setVisible(true);
        return formaPagoSeleccionada;
    }

    public double getMontoIngresado() {
        return montoIngresado;
    }
}
