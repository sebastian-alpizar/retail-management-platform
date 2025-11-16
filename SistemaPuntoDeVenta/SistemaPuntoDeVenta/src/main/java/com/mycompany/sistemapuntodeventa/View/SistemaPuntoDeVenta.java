package com.mycompany.sistemapuntodeventa.View;
/*INTEGRANTES:
    -CRISTHIAN MORALES
    -SEBASTIAN ALPIZAR
    -ALEXANDER VEGA
*/
import com.mycompany.sistemapuntodeventa.Controller.Proxy;
import mycompany.sistemaentidades.*;
import com.mycompany.sistemapuntodeventa.Controller.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SistemaPuntoDeVenta {
    private JFrame frame;

    public SistemaPuntoDeVenta(Proxy proxy) {
        frame = new JFrame(" POS: Point Of Sale - " + proxy.getUser().getId());
        proxy.setFrame(this);
        frame.setIconImage(Icons.TIENDA.getImage());
        

        // Crear instancias Panel
        List<String> clientes = proxy.obtenerTodosLosClientes().stream().map(Cliente::getNombre).collect(Collectors.toList());

        HistoricoFacturasPanel historicoFacturasPanel = new HistoricoFacturasPanel(clientes);
        List<String> categorias = proxy.obtenerCategorias(); // Obtener categorías desde ProductoDAO
        List<Integer> months = IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList());
        EstadisticasPanel estadisticasPanel = new EstadisticasPanel(
                categorias,
                months
        );
        ProductoPanel productoPanel = new ProductoPanel();
        CajeroPanel cajeroPanel = new CajeroPanel();
        ClientePanel clientePanel = new ClientePanel();
        FacturaPanel facturaPanel = new FacturaPanel(clientes,
                proxy.obtenerTodosLosCajeros().stream().map(Cajero::getNombre).collect(Collectors.toList())
        );
        // Crear instancias Control
        FacturaController facturaController = new FacturaController(facturaPanel, productoPanel, proxy);
        new HistoricoFacturasController(historicoFacturasPanel, proxy);
        new EstadisticasController(estadisticasPanel, proxy);
        new ProductoController(productoPanel, estadisticasPanel, proxy);
        new CajeroController(cajeroPanel, facturaPanel, proxy);
        new ClienteController(clientePanel, facturaPanel, proxy);
        UsuarioController usuarioController = new UsuarioController(proxy.getPanel(), proxy, facturaController);
        proxy.setUsuarioController(usuarioController);
        
        // Configurar la ventana de la aplicación principal
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // Añadir panels al frame
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(790, 600));
        tabbedPane.addTab("Histórico de Facturas", Icons.HISTORICO, historicoFacturasPanel);
        tabbedPane.addTab("Estadísticas", Icons.ESTADISTICAS, estadisticasPanel);
        tabbedPane.addTab("Productos" , Icons.PRODUCTOS, productoPanel);
        tabbedPane.addTab("Cajeros", Icons.CAJEROS, cajeroPanel);
        tabbedPane.addTab("Clientes", Icons.CLIENTES, clientePanel);
        tabbedPane.addTab("Facturar",  Icons.FACTURAR, facturaPanel);
        frame.add(tabbedPane);

        // Mostrar la ventana
        frame.setSize(1000, 600);
        frame.setVisible(true);
        frame.add(proxy.getPanel());
    }
    
    public void notificarDesconexion(String userId) {
        JOptionPane.showMessageDialog(frame, "El usuario: " + userId + " se desconectó", "Desconexión", JOptionPane.ERROR_MESSAGE);
    }
}
