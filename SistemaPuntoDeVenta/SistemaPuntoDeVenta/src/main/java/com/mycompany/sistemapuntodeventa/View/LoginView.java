package com.mycompany.sistemapuntodeventa.View;

import com.mycompany.sistemapuntodeventa.Controller.Proxy;
import javax.swing.*;
import java.awt.*;
import mycompany.sistemaentidades.*;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private Proxy proxy;

    public LoginView() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana
        setIconImage(Icons.LOGIN.getImage());

        // Panel principal
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Login"));
        
        // Etiquetas y campos de texto
        panel.add(new JLabel("Usuario:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Contraseña:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // Botón de login
        loginButton = new JButton("Iniciar Sesión");
        loginButton.addActionListener(e-> loginButtonListener());
        panel.add(loginButton);

        // Botón de registro
        registerButton = new JButton("Registrarse");
        registerButton.addActionListener(e-> registerButtonListener());
        panel.add(registerButton);

        // Añadir el panel a la ventana
        add(panel);
    }

    private void loginButtonListener () {
        String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (!username.isEmpty() && !password.isEmpty()) {
                proxy = new Proxy(new Usuario(username, password), "localhost", 12345, new UsuariosPanel());
                if (autenticarUsuario()) {
                    JOptionPane.showMessageDialog(LoginView.this, "Inicio de session exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    abrirVentanaPrincipal(proxy);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginView.this, "Error al iniciar session", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }

    private void registerButtonListener () {
        abrirVentanaRegistro();
        dispose();
    }

    private Boolean autenticarUsuario() {
        return proxy.existeUsuario(proxy.getUser());
    }

    private void abrirVentanaPrincipal(Proxy proxy) {
        SwingUtilities.invokeLater(() -> new SistemaPuntoDeVenta(proxy ));
    }

    private void abrirVentanaRegistro() {
        SwingUtilities.invokeLater(() -> new RegisterView().setVisible(true));
    }
}
