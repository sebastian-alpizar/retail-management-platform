package com.mycompany.sistemapuntodeventa.Controller;

import com.mycompany.sistemapuntodeventa.View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class EstadisticasController {
    private EstadisticasPanel estadisticasPanel;
    private Proxy proxy;

    public EstadisticasController(EstadisticasPanel estadisticasPanel, Proxy proxy) {
        this.proxy = proxy;
        this.estadisticasPanel = estadisticasPanel;

        estadisticasPanel.getMostrarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mostrarEstadisticas();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(estadisticasPanel, "Error al mostrar las estadísticas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void mostrarEstadisticas() {
        String categoria = (String) estadisticasPanel.getCategoriaComboBox().getSelectedItem();
        Integer month = (Integer) estadisticasPanel.getMonthComboBox().getSelectedItem();
        // Verifica que la categoría y el mes no sean nulos
        if (categoria == null || month == null) {
            JOptionPane.showMessageDialog(estadisticasPanel, "Por favor, seleccione una categoría y un mes.");
            return;
        }
        // Año fijo de 2024
        int year = 2024;
        try {
            Map<String, Double> ventas = proxy.obtenerVentasMensualesPorCategoria(categoria, year, month);
            // Lógica para mostrar las estadísticas en la tabla
            estadisticasPanel.actualizarTabla(ventas);
            // Lógica para mostrar las estadísticas en la gráfica
            estadisticasPanel.actualizarGrafica(ventas);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(estadisticasPanel, "Error al obtener las estadísticas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}