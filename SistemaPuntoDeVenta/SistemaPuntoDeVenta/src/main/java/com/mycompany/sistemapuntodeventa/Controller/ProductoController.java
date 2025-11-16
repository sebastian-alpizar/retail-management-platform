package com.mycompany.sistemapuntodeventa.Controller;

import mycompany.sistemaentidades.*;
import com.mycompany.sistemapuntodeventa.View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private ProductoPanel productoPanel;
    private EstadisticasPanel estadisticasPanel;
    private Proxy proxy;

    public ProductoController(ProductoPanel productoPanel, EstadisticasPanel estadisticasPanel, Proxy proxy) {
        this.productoPanel = productoPanel;
        this.estadisticasPanel = estadisticasPanel;
        this.proxy = proxy;

        try {
            List<Producto> _productos = proxy.obtenerTodosLosProductos();
            if (!_productos.isEmpty()) {
                productoPanel.actualizarTabla(_productos);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productoPanel, "Error al obtener los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        productoPanel.getBuscarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscarProducto();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(productoPanel, "Error al buscar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        productoPanel.getGuardarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarProducto();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(productoPanel, "Error al guardar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        productoPanel.getActualizarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actualizarProducto();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(productoPanel, "Error al actualizar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        productoPanel.getBorrarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    borrarProducto();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(productoPanel, "Error al borrar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        productoPanel.getLimpiarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    limpiarCampos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(productoPanel, "Error al limpiar los campos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Actualiza la lista de categorías en el panel de estadísticas al iniciar
        try {
            actualizarCategoriasEstadisticas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productoPanel, "Error al actualizar las categorías en el panel de estadísticas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarProducto() {
        String buscar = productoPanel.getBuscarField().getText();
        if (buscar.isEmpty()) {
            JOptionPane.showMessageDialog(productoPanel, "Digite el código o detalle del producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Producto producto = proxy.buscarProductoPorCodigo(buscar);
            if (producto == null) {
                producto = proxy.buscarProductoPorDescripcion(buscar);
            }
            if (producto != null) {
                productoPanel.setTextFields(producto);
            } else {
                JOptionPane.showMessageDialog(productoPanel, "Error. Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productoPanel, "Error al buscar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarProducto() {
        try {
            String codigo = productoPanel.getCodigoField().getText();
            String descripcion = productoPanel.getDescripcionField().getText();
            String unidadMedida = productoPanel.getUnidadMedidaField().getText();
            double precioUnitario = Double.parseDouble(productoPanel.getPrecioUnitarioField().getText());
            int existencias = Integer.parseInt(productoPanel.getExistenciasField().getText());
            String categoria = productoPanel.getCategoriaField().getText();
            Producto producto = new Producto(codigo, descripcion, unidadMedida, precioUnitario, existencias, categoria);
            if (proxy.agregarProducto(producto)) {
                productoPanel.actualizarTabla(proxy.obtenerTodosLosProductos());
                productoPanel.limpiarCampos();
                JOptionPane.showMessageDialog(productoPanel, "Producto agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(productoPanel, "Error al agregar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            // Actualizar categorías en el panel de estadísticas
            actualizarCategoriasEstadisticas();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(productoPanel, "Error en el formato de los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productoPanel, "Error al guardar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarProducto() {
        try {
            String codigo = productoPanel.getCodigoField().getText();
            String descripcion = productoPanel.getDescripcionField().getText();
            String unidadMedida = productoPanel.getUnidadMedidaField().getText();
            double precioUnitario = Double.parseDouble(productoPanel.getPrecioUnitarioField().getText());
            int existencias = Integer.parseInt(productoPanel.getExistenciasField().getText());
            String categoria = productoPanel.getCategoriaField().getText();

            Producto producto = new Producto(codigo, descripcion, unidadMedida, precioUnitario, existencias, categoria);

            if (proxy.actualizarProducto(producto)) {
                productoPanel.actualizarTabla(proxy.obtenerTodosLosProductos());
                productoPanel.limpiarCampos();
                JOptionPane.showMessageDialog(productoPanel, "Producto actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(productoPanel, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            // Actualizar categorías en el panel de estadísticas
            actualizarCategoriasEstadisticas();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(productoPanel, "Error en el formato de los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productoPanel, "Error al actualizar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void borrarProducto() {
        String codigo = productoPanel.getCodigoField().getText();

        if (!codigo.isEmpty()) {
            try {
                if (proxy.eliminarProducto(codigo)) {
                    productoPanel.actualizarTabla(proxy.obtenerTodosLosProductos());
                    productoPanel.limpiarCampos();
                    JOptionPane.showMessageDialog(productoPanel, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(productoPanel, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                // Actualizar categorías en el panel de estadísticas
                actualizarCategoriasEstadisticas();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(productoPanel, "Error al eliminar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(productoPanel, "Error. Digite el código del producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        productoPanel.limpiarCampos();
    }

    private void actualizarCategoriasEstadisticas() {
        try {
            List<String> categorias = proxy.obtenerCategorias(); // Obtener las categorías de los productos
            estadisticasPanel.actualizarCategorias(categorias);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(productoPanel, "Error al actualizar las categorías en el panel de estadísticas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}