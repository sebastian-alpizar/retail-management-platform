package com.mycompany.sistemapuntodeventa.View;
import mycompany.sistemaentidades.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ProductoPanel extends JPanel {

    private JTextField codigoField;
    private JTextField descripcionField;
    private JTextField unidadMedidaField;
    private JTextField precioUnitarioField;
    private JTextField existenciasField;
    private JTextField categoriaField;
    private JButton buscarButton;
    private JButton guardarButton;
    private JButton actualizarButton;
    private JButton borrarButton;
    private JTable productosTable;
    private JTextField buscarField;
    private JButton limpiarButton;

    public ProductoPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        buscarField = new JTextField();
        codigoField = new JTextField();
        descripcionField = new JTextField();
        unidadMedidaField = new JTextField();
        precioUnitarioField = new JTextField();
        existenciasField = new JTextField();
        categoriaField = new JTextField();
        productosTable = new JTable();

        ImageIcon buscarIcon = createResizedIcon("/iconos/buscar.png", 16, 16);
        ImageIcon guardarIcon = createResizedIcon("/iconos/guardar.png", 16, 16);
        ImageIcon actualizarIcon = createResizedIcon("/iconos/actualizar.png", 16, 16);
        ImageIcon borrarIcon = createResizedIcon("/iconos/borrar.png", 16, 16);
        ImageIcon limpiarIcon = createResizedIcon("/iconos/limpiar.png", 16, 16);

        // Panel de ingreso de datos del producto
        JPanel datosPanel = new JPanel(new GridLayout(2, 6, 20, 10));
        datosPanel.setBorder(BorderFactory.createTitledBorder("Producto"));
        datosPanel.setPreferredSize(new Dimension(780, 88));

        JPanel panelBusqueda = new JPanel(new GridLayout(1, 3, 20, 0));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
        panelBusqueda.setPreferredSize(new Dimension(500, 50));

        JScrollPane jScrollPane = new JScrollPane(productosTable);
        jScrollPane.setBorder(BorderFactory.createTitledBorder("Listado"));
        jScrollPane.setPreferredSize(new Dimension(600, 125));

        buscarButton = new JButton("Buscar", buscarIcon);
        guardarButton = new JButton("Guardar", guardarIcon);
        actualizarButton = new JButton("Actualizar", actualizarIcon);
        borrarButton = new JButton("Borrar", borrarIcon);
        limpiarButton = new JButton("Limpiar", limpiarIcon);

        datosPanel.add(new JLabel("Código:"));
        datosPanel.add(codigoField);
        datosPanel.add(new JLabel("Descripción:"));
        datosPanel.add(descripcionField);
        datosPanel.add(new JLabel("Unidad de Medida:"));
        datosPanel.add(unidadMedidaField);
        datosPanel.add(new JLabel("Precio Unitario:"));
        datosPanel.add(precioUnitarioField);
        datosPanel.add(new JLabel("Existencias:"));
        datosPanel.add(existenciasField);
        datosPanel.add(new JLabel("Categoría:"));
        datosPanel.add(categoriaField);
        add(datosPanel);

        // Panel de botones
        JPanel botonesPanel = new JPanel(new GridLayout(1, 4, 20, 10));
        botonesPanel.add(guardarButton);
        botonesPanel.add(actualizarButton);
        botonesPanel.add(borrarButton);
        botonesPanel.add(limpiarButton);
        add(botonesPanel);

        // Agregar elementos al panel de busqueda
        panelBusqueda.add(new JLabel("Codigo o descripcion: "));
        panelBusqueda.add(buscarField);
        panelBusqueda.add(buscarButton);
        add(panelBusqueda);

        add(jScrollPane);
    }
    
    private ImageIcon createResizedIcon(String resource, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(resource));
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
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
    
    public JButton getLimpiarButton() {
        return limpiarButton;
    }

    public JButton getBorrarButton() {
        return borrarButton;
    }

    public JTextField getCodigoField() {
        return codigoField;
    }

    public JTextField getDescripcionField() {
        return descripcionField;
    }

    public JTextField getUnidadMedidaField() {
        return unidadMedidaField;
    }

    public JTextField getPrecioUnitarioField() {
        return precioUnitarioField;
    }

    public JTextField getExistenciasField() {
        return existenciasField;
    }

    public JTextField getCategoriaField() {
        return categoriaField;
    }
    
    public JTextField getBuscarField() {
        return buscarField;
    }

    public JTable getProductosTable() {
        return productosTable;
    }

    public void actualizarTabla(List<Producto> productos) {
        String[] columnNames = {"Código", "Descripción", "Unidad de Medida", "Precio Unitario", "Existencias", "Categoría"};
        Object[][] data = new Object[productos.size()][6];

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            data[i][0] = producto.getCodigo();
            data[i][1] = producto.getDescripcion();
            data[i][2] = producto.getUnidadMedida();
            data[i][3] = producto.getPrecioUnitario();
            data[i][4] = producto.getExistencias();
            data[i][5] = producto.getCategoria();
        }

        productosTable.setModel(new DefaultTableModel(data, columnNames));
    }

    public void limpiarCampos() {
        buscarField.setText("");
        codigoField.setText("");
        descripcionField.setText("");
        unidadMedidaField.setText("");
        precioUnitarioField.setText("");
        existenciasField.setText("");
        categoriaField.setText("");
    }
    
    public void setTextFields(Producto producto) {
        codigoField.setText(producto.getCodigo());
        descripcionField.setText(producto.getDescripcion());
        unidadMedidaField.setText(producto.getUnidadMedida());
        precioUnitarioField.setText(String.valueOf(producto.getPrecioUnitario()));
        existenciasField.setText(String.valueOf(producto.getExistencias()));
        categoriaField.setText(producto.getCategoria());
    }
}
