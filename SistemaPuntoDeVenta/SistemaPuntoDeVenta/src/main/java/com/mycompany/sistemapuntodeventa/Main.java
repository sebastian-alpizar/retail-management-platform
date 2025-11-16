package com.mycompany.sistemapuntodeventa;

import com.mycompany.sistemapuntodeventa.View.LoginView;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
