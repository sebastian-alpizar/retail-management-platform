package com.mycompany.sistemapuntodeventa.View;

import com.mycompany.sistemapuntodeventa.Controller.Proxy;
import java.awt.GridLayout;
import javax.swing.*;
import mycompany.sistemaentidades.*;

public class RegisterView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private Proxy proxy;

    public RegisterView() {
        setTitle("Register");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setIconImage(Icons.NEWUSER.getImage());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Register"));
        
        panel.add(new JLabel("Nuevo Usuario:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Nueva Contraseña:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        registerButton = new JButton("Registrar");
        registerButton.addActionListener(e -> registerButtonListener());
        panel.add(registerButton);

        add(panel);
    }

    private void registerButtonListener() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        if (!username.isEmpty() && !password.isEmpty()) {
            proxy = new Proxy(new Usuario(username, password), "localhost", 12345, new UsuariosPanel());
            if(ingresarUsuario()){
                JOptionPane.showMessageDialog(RegisterView.this, "Registro exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                abrirVentanaPrincipal(proxy);
                dispose();
            }else{
                JOptionPane.showMessageDialog(this, "Error al registrarse","Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private Boolean ingresarUsuario(){
        return proxy.agregarUsuario(proxy.getUser());
    }

    private void abrirVentanaPrincipal(Proxy proxy) {
        SwingUtilities.invokeLater(() -> new SistemaPuntoDeVenta(proxy));
    }
}

