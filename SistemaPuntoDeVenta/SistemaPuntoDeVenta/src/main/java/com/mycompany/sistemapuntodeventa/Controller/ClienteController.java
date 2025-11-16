package com.mycompany.sistemapuntodeventa.Controller;

import mycompany.sistemaentidades.*;
import com.mycompany.sistemapuntodeventa.View.ClientePanel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.mycompany.sistemapuntodeventa.View.FacturaPanel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class ClienteController {
    private ClientePanel clientePanel;
    private FacturaPanel facturaPanel;
    private Proxy proxy;

    public ClienteController(ClientePanel clientePanel, FacturaPanel facturaPanel, Proxy proxy) {
        this.clientePanel = clientePanel;
        this.facturaPanel = facturaPanel; // Inicializa el FacturaPanel
        this.proxy = proxy;

        try {
            List<Cliente> _clientes = proxy.obtenerTodosLosClientes();
            if (!_clientes.isEmpty()) {
                clientePanel.actualizarTabla(_clientes);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(clientePanel, "Error al obtener los clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        clientePanel.getBuscarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscarCliente();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(clientePanel, "Error al buscar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clientePanel.getGuardarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    guardarCliente();
                    actualizarFacturaPanel();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(clientePanel, "Error al guardar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clientePanel.getActualizarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actualizarCliente();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(clientePanel, "Error al actualizar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clientePanel.getBorrarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    borrarCliente();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(clientePanel, "Error al borrar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clientePanel.getLimpiarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    limpiarCampos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(clientePanel, "Error al limpiar los campos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clientePanel.getReporteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    generarReporteClientePDF();
                } catch (DocumentException | FileNotFoundException | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(clientePanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void buscarCliente() {
        String buscar = clientePanel.getBuscarField().getText();
        if (buscar.isEmpty()) {
            JOptionPane.showMessageDialog(clientePanel, "Digite el id o nombre del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Cliente cliente = proxy.buscarClientePorId(buscar);
            if (cliente == null) {
                cliente = proxy.buscarClientePorNombre(buscar);
            }
            if (cliente != null) {
                setTextFields(cliente);
            } else {
                JOptionPane.showMessageDialog(clientePanel, "Error. Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(clientePanel, "Error al buscar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setTextFields(Cliente cliente) {
        clientePanel.getIdField().setText(cliente.getId());
        clientePanel.getNombreField().setText(cliente.getNombre());
        clientePanel.getTelefonoField().setText(cliente.getTelefono());
        clientePanel.getEmailField().setText(cliente.getEmail());
        clientePanel.getDescuentoField().setText(String.valueOf(cliente.getDescuento()));
    }

    private void guardarCliente() {
        try {
            String id = clientePanel.getIdField().getText();
            String nombre = clientePanel.getNombreField().getText();
            String telefono = clientePanel.getTelefonoField().getText();
            String email = clientePanel.getEmailField().getText();
            double descuento;
            if (clientePanel.getDescuentoField().getText().isEmpty()) {
                descuento = 0.0;
            } else {
                descuento = Double.parseDouble(clientePanel.getDescuentoField().getText());
            }
            Cliente cliente = new Cliente(id, nombre, telefono, email, descuento);
            if (proxy.agregarCliente(cliente)) {
                clientePanel.actualizarTabla(proxy.obtenerTodosLosClientes());
                JOptionPane.showMessageDialog(clientePanel, "Cliente agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(clientePanel, "Error al agregar al cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(clientePanel, "Descuento debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(clientePanel, "Error al agregar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarCliente() {
        try {
            String id = clientePanel.getIdField().getText();
            String nombre = clientePanel.getNombreField().getText();
            String telefono = clientePanel.getTelefonoField().getText();
            String email = clientePanel.getEmailField().getText();
            double descuento = Double.parseDouble(clientePanel.getDescuentoField().getText());
            Cliente cliente = new Cliente(id, nombre, telefono, email, descuento);
            if (!id.isEmpty() || !nombre.isEmpty() || !telefono.isEmpty() || !email.isEmpty() || !clientePanel.getDescuentoField().getText().isEmpty()) {
                if (proxy.actualizarCliente(cliente)) {
                    clientePanel.actualizarTabla(proxy.obtenerTodosLosClientes());
                    JOptionPane.showMessageDialog(clientePanel, "Cliente modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(clientePanel, "Error al modificar al cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(clientePanel, "Error. No pueden haber campos vacíos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(clientePanel, "Descuento debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(clientePanel, "Error al actualizar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void borrarCliente() {
        String id = clientePanel.getIdField().getText();
        if (id != null && !id.isEmpty()) {
            try {
                if (proxy.eliminarCliente(id)) {
                    limpiarCampos();
                    clientePanel.actualizarTabla(proxy.obtenerTodosLosClientes());
                    JOptionPane.showMessageDialog(clientePanel, "Cliente eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(clientePanel, "Error al eliminar al cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(clientePanel, "Error al eliminar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(clientePanel, "Error. Agregue el id del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        clientePanel.getIdField().setText("");
        clientePanel.getNombreField().setText("");
        clientePanel.getTelefonoField().setText("");
        clientePanel.getEmailField().setText("");
        clientePanel.getDescuentoField().setText("");
        clientePanel.getBuscarField().setText("");
    }

    private void generarReporteClientePDF() throws DocumentException, FileNotFoundException {
        String nombre = clientePanel.getNombreField().getText();
        Cliente cliente = proxy.buscarClientePorNombre(nombre);
        if (cliente == null) {
            throw new IllegalArgumentException("El nombre del cliente no se encuentra");
        }
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("reporte_cliente_" + nombre + ".pdf"));
        document.open();
        String id = cliente.getId();
        nombre = cliente.getNombre();
        String telefono = cliente.getTelefono();
        String email = cliente.getEmail();
        double descuento = cliente.getDescuento();
        document.add(new Paragraph("Reporte de Cliente"));
        document.add(new Paragraph("ID: " + id));
        document.add(new Paragraph("Nombre: " + nombre));
        document.add(new Paragraph("Teléfono: " + telefono));
        document.add(new Paragraph("Email: " + email));
        document.add(new Paragraph("Descuento: " + descuento));
        document.close();
        JOptionPane.showMessageDialog(clientePanel, "Reporte generado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarFacturaPanel() {
        try {
            List<String> clientes = proxy.obtenerTodosLosClientes().stream()
                    .map(Cliente::getNombre)
                    .collect(Collectors.toList());
            facturaPanel.actualizarClientes(clientes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(clientePanel, "Error al actualizar el panel de facturación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Cliente> getClientes() {
        try {
            return proxy.obtenerTodosLosClientes();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(clientePanel, "Error al obtener los clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}