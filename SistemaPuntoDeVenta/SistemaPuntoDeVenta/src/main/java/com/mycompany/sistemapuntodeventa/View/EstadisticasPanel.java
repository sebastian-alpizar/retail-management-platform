package com.mycompany.sistemapuntodeventa.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class EstadisticasPanel extends JPanel {

    private JComboBox<String> categoriaComboBox;
    private JComboBox<Integer> yearComboBox;
    private JComboBox<Integer> monthComboBox;
    private JButton mostrarButton;
    private JTable estadisticasTable;
    private JPanel graficaPanel;

    public EstadisticasPanel(List<String> categorias, List<Integer> months) {
        setLayout(new BorderLayout());

        // Crear y añadir selectionPanel
        JPanel selectionPanel = new JPanel(new GridLayout(2, 6, 20, 10));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Estadística"));
        selectionPanel.setPreferredSize(new Dimension(780, 88));
        
        categoriaComboBox = new JComboBox<>();
        yearComboBox = new JComboBox<>();
        monthComboBox = new JComboBox<>();
        mostrarButton = new JButton("Mostrar", Icons.MOSTRAR);

        selectionPanel.add(new JLabel("Categoría:"));
        selectionPanel.add(categoriaComboBox);
        selectionPanel.add(new JLabel("Año:"));
        selectionPanel.add(yearComboBox);
        selectionPanel.add(new JLabel("Mes:"));
        selectionPanel.add(monthComboBox);
        selectionPanel.add(mostrarButton);

        add(selectionPanel, BorderLayout.NORTH);

        // Crear y añadir la tabla
        estadisticasTable = new JTable();
        add(new JScrollPane(estadisticasTable), BorderLayout.CENTER);

        // Crear y añadir el panel gráfico
        graficaPanel = new JPanel();
        graficaPanel.setPreferredSize(new Dimension(800, 400)); // Ajusta el tamaño del panel gráfico
        add(graficaPanel, BorderLayout.SOUTH);

        // Llenar yearComboBox con solo el año 2024
        yearComboBox.addItem(2024);

        // Llenar monthComboBox
        for (Integer month : months) {
            monthComboBox.addItem(month);
        }

        // Inicializar el comboBox de categorías
        actualizarCategorias(categorias);
    }

    public JComboBox<String> getCategoriaComboBox() {
        return categoriaComboBox;
    }

    public JComboBox<Integer> getYearComboBox() {
        return yearComboBox;
    }

    public JComboBox<Integer> getMonthComboBox() {
        return monthComboBox;
    }

    public JButton getMostrarButton() {
        return mostrarButton;
    }

    public void actualizarTabla(Map<String, Double> ventas) {
        String[] columnNames = {"Categoría", "Ventas"};
        Object[][] data = new Object[ventas.size()][2];
        int index = 0;
        for (Map.Entry<String, Double> entry : ventas.entrySet()) {
            data[index][0] = entry.getKey();
            data[index][1] = entry.getValue();
            index++;
        }
        estadisticasTable.setModel(new DefaultTableModel(data, columnNames));
    }

    public void actualizarGrafica(Map<String, Double> ventas) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : ventas.entrySet()) {
            // Ajustar los valores para que no sean tan grandes
            double adjustedValue = entry.getValue() / 1000; // Dividir por 1000 para reducir el tamaño
            dataset.addValue(adjustedValue, "Ventas", entry.getKey());
        }

        // Verificar si la clave "totalVentas" está presente y agregarla al dataset
        if (ventas.containsKey("totalVentas")) {
            double totalVentasValue = ventas.get("totalVentas") / 1000; // Dividir por 1000 para reducir el tamaño
            dataset.addValue(totalVentasValue, "Ventas", "totalVentas");
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Ventas Mensuales por Categoría",
                "Categoría",
                "Ventas (en miles)",
                dataset,
                PlotOrientation.HORIZONTAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(780, 400)); // Ajusta el tamaño del gráfico

        graficaPanel.removeAll();
        graficaPanel.setLayout(new BorderLayout());
        graficaPanel.add(chartPanel, BorderLayout.CENTER);

        graficaPanel.validate();
    }

    public void actualizarCategorias(List<String> categorias) {
        categoriaComboBox.removeAllItems(); // Limpiar categorías existentes
        for (String categoria : categorias) {
            categoriaComboBox.addItem(categoria); // Añadir nuevas categorías
        }
    }
}