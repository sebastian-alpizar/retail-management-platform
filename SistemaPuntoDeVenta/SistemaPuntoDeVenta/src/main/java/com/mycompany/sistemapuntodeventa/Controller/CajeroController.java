package com.mycompany.sistemapuntodeventa.Controller;

import mycompany.sistemaentidades.*;
import com.mycompany.sistemapuntodeventa.View.CajeroPanel;
import com.mycompany.sistemapuntodeventa.View.FacturaPanel;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class CajeroController {

    private CajeroPanel cajeroPanel;
    private FacturaPanel facturaPanel;
    private Proxy proxy;

    public CajeroController(CajeroPanel cajeroPanel, FacturaPanel facturaPanel, Proxy proxy) {
        this.cajeroPanel = cajeroPanel;
        this.facturaPanel = facturaPanel; // Inicializa el FacturaPanel
        this.proxy = proxy;

        try {
            List<Cajero> _cajeros = proxy.obtenerTodosLosCajeros();
            if (!_cajeros.isEmpty()) {
                cajeroPanel.actualizarTabla(_cajeros);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(cajeroPanel, "Error al obtener los cajeros: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        cajeroPanel.getBuscarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscarCajero();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(cajeroPanel, "Error al buscar el cajero: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cajeroPanel.getGuardarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarCajero();
                    actualizarFacturaPanel(); // Actualiza el panel de facturación
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(cajeroPanel, "Error al guardar el cajero: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cajeroPanel.getActualizarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actualizarCajero();
                    actualizarFacturaPanel(); // Actualiza el panel de facturación
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(cajeroPanel, "Error al actualizar el cajero: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cajeroPanel.getBorrarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    borrarCajero();
                    actualizarFacturaPanel(); // Actualiza el panel de facturación
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(cajeroPanel, "Error al borrar el cajero: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cajeroPanel.getLimpiarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    limpiarCampos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(cajeroPanel, "Error al limpiar los campos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void buscarCajero() {
        String buscar = cajeroPanel.getBuscarField().getText();
        if (buscar.isEmpty()) {
            JOptionPane.showMessageDialog(cajeroPanel, "Digite el id o nombre del cajero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Cajero cajero = proxy.buscarCajeroPorId(buscar);
            if (cajero == null) {
                cajero = proxy.buscarCajeroPorNombre(buscar);
            }
            if (cajero != null) {
                setTextFields(cajero);
            } else {
                JOptionPane.showMessageDialog(cajeroPanel, "Error. Cajero no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(cajeroPanel, "Error al buscar el cajero: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarCajero() {
        String id = cajeroPanel.getIdField().getText();
        String nombre = cajeroPanel.getNombreField().getText();
        String telefono = cajeroPanel.getTelefonoField().getText();
        String email = cajeroPanel.getEmailField().getText();

        Cajero cajero = new Cajero(id, nombre, telefono, email);
        try {
            if (proxy.agregarCajero(cajero)) {
                cajeroPanel.actualizarTabla(proxy.obtenerTodosLosCajeros());
                JOptionPane.showMessageDialog(cajeroPanel, "Cajero agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarFacturaPanel();
            } else {
                JOptionPane.showMessageDialog(cajeroPanel, "Error al agregar el cajero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(cajeroPanel, "Error al agregar el cajero: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarCajero() {
        String id = cajeroPanel.getIdField().getText();
        String nombre = cajeroPanel.getNombreField().getText();
        String telefono = cajeroPanel.getTelefonoField().getText();
        String email = cajeroPanel.getEmailField().getText();

        Cajero cajero = new Cajero(id, nombre, telefono, email);
        if (!id.isEmpty() || !nombre.isEmpty() || !telefono.isEmpty() || !email.isEmpty()) {
            try {
                if (proxy.actualizarCajero(cajero)) {
                    cajeroPanel.actualizarTabla(proxy.obtenerTodosLosCajeros());
                    JOptionPane.showMessageDialog(cajeroPanel, "Cajero actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarFacturaPanel();
                } else {
                    JOptionPane.showMessageDialog(cajeroPanel, "Error al actualizar el cajero.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(cajeroPanel, "Error al actualizar el cajero: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(cajeroPanel, "Error. No pueden haber campos vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void borrarCajero() {
        String id = cajeroPanel.getIdField().getText();
        if (id != null && !id.isEmpty()) {
            try {
                if (proxy.eliminarCajero(id)) {
                    limpiarCampos();
                    cajeroPanel.actualizarTabla(proxy.obtenerTodosLosCajeros());
                    JOptionPane.showMessageDialog(cajeroPanel, "Cajero eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarFacturaPanel();
                } else {
                    JOptionPane.showMessageDialog(cajeroPanel, "Error al eliminar el cajero.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(cajeroPanel, "Error al eliminar el cajero: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(cajeroPanel, "Error. Agregue el id del cajero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        cajeroPanel.getIdField().setText("");
        cajeroPanel.getNombreField().setText("");
        cajeroPanel.getTelefonoField().setText("");
        cajeroPanel.getEmailField().setText("");
        cajeroPanel.getBuscarField().setText("");
    }

    public void setTextFields(Cajero cajero) {
        cajeroPanel.getIdField().setText(cajero.getId());
        cajeroPanel.getNombreField().setText(cajero.getNombre());
        cajeroPanel.getTelefonoField().setText(cajero.getTelefono());
        cajeroPanel.getEmailField().setText(cajero.getEmail());
    }

    public List<Cajero> getCajeros() {
        try {
            return proxy.obtenerTodosLosCajeros();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(cajeroPanel, "Error al obtener los cajeros: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void actualizarFacturaPanel() {
        try {
            List<String> cajeros = proxy.obtenerTodosLosCajeros().stream()
                    .map(Cajero::getNombre)
                    .collect(Collectors.toList());
            facturaPanel.actualizarCajeros(cajeros);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(cajeroPanel, "Error al actualizar el panel de facturación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}