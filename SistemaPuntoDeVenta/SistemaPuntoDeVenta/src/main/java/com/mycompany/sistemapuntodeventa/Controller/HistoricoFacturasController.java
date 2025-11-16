package com.mycompany.sistemapuntodeventa.Controller;

import mycompany.sistemaentidades.*;
import com.mycompany.sistemapuntodeventa.View.HistoricoFacturasPanel;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.stream.Collectors;

public class HistoricoFacturasController {
    private Proxy proxy;
    private final HistoricoFacturasPanel historicoFacturasPanel;

    public HistoricoFacturasController(HistoricoFacturasPanel historicoFacturasPanel, Proxy proxy) {
        this.proxy = proxy;
        this.historicoFacturasPanel = historicoFacturasPanel;

        // Añadir un listener para el botón de búsqueda
        historicoFacturasPanel.getBuscarButton().addActionListener(e -> {
            try {
                buscarFacturas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(historicoFacturasPanel, "Error al buscar las facturas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void buscarFacturas() {
        try {
            String clienteId = historicoFacturasPanel.getClienteId();
            List<Factura> facturas = proxy.obtenerTodasLasFacturas();

            // Filtrar las facturas por cliente si se especifica un ID de cliente
            if (clienteId != null && !clienteId.trim().isEmpty()) {
                facturas = facturas.stream()
                        .filter(factura -> factura.getCliente().equals(clienteId))
                        .collect(Collectors.toList());
            }

            // Mostrar las facturas en el panel
            historicoFacturasPanel.mostrarFacturas(facturas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(historicoFacturasPanel, "Error al obtener las facturas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}