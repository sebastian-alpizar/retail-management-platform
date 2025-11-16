package com.mycompany.backendspv.datos;

import com.mycompany.backendspv.datos.ConectorDatabase;
import mycompany.sistemaentidades.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsuarioDAO {
    public Boolean agregarUsuario(Usuario usuario) {
        ConectorDatabase conector = new ConectorDatabase();
        String sql = "INSERT INTO Usuario (id, password) VALUES (?, ?)";
        try (Connection conn = conector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getId());
            pstmt.setString(2, usuario.getPassword());
            pstmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean existeUsuario(Usuario usuario) {
        ConectorDatabase conector = new ConectorDatabase();
        String sql = "SELECT * FROM Usuario WHERE id = ? AND password = ?";
        
        try (Connection conn = conector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getId());
            pstmt.setString(2, usuario.getPassword());
            ResultSet rs = pstmt.executeQuery();
            
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
