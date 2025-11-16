package com.mycompany.sistemapuntodeventa.Controller;

import com.mycompany.sistemapuntodeventa.View.UsuariosPanel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import mycompany.sistemaentidades.*;

public class UsuarioController {
    private UsuariosPanel usuarioPanel;
    private FacturaController controller;
    private Proxy proxy;
    private List<Mensaje> mensajes;
    
    public UsuarioController(UsuariosPanel usuariosPanel, Proxy proxy, FacturaController controller){
        this.usuarioPanel = usuariosPanel;
        this.proxy = proxy;
        this.controller = controller;
        mensajes = new ArrayList();
        
        usuarioPanel.getEnviarButton().addActionListener(e -> enviarFactura());
        usuarioPanel.getRecibirButton().addActionListener(e -> recibirFactura());
    }
    
    private void enviarFactura() {
        Factura factura = controller.extraerFactura();

        if (factura == null) {
            JOptionPane.showMessageDialog(usuarioPanel, "Error. No se a digitado la factura.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String destino = usuarioPanel.getSelectedUser();
        if (destino != null){
            usuarioPanel.limpiarCheckBox();
            if (!proxy.enviarFactura(factura, destino)) {
                JOptionPane.showMessageDialog(usuarioPanel, "El usuario no está en línea.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                controller.cancelarCompra();
            }
        } else {
            JOptionPane.showMessageDialog(usuarioPanel, "Error. Seleccione un usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void recibirFactura(){
        String user = usuarioPanel.getSelectedUser();
        if (!mensajes.isEmpty()){
            if (user != null){
                for (Mensaje msj: mensajes){
                    if (msj.getEmisor().equals(user)){
                        Factura fac = (Factura) msj.getDatos();
                        controller.setearPanel(fac);
                        mensajes.remove(msj);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(usuarioPanel, "No tiene ninguna factura en trámite por ese usuario.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(usuarioPanel, "Error. Seleccione un usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(usuarioPanel, "No posee ninguna factura.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    public void agregarMensaje(Mensaje mensaje){
        mensajes.add(mensaje);
        usuarioPanel.marcarCheckboxPorId(mensaje.getEmisor());
    }
}
