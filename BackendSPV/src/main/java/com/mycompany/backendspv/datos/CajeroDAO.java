package com.mycompany.backendspv.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mycompany.sistemaentidades.*;

public class CajeroDAO {
    public Boolean agregarCajero(Cajero cajero) {
        String sql = "INSERT INTO Cajero (id, nombre, telefono, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cajero.getId());
            pstmt.setString(2, cajero.getNombre());
            pstmt.setString(3, cajero.getTelefono());
            pstmt.setString(4, cajero.getEmail());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Cajero buscarCajeroPorId(String id) {
        String sql = "SELECT * FROM Cajero WHERE id = ?";
        Cajero cajero = null;

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                cajero = new Cajero(id, nombre, telefono, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cajero;
    }
    
    public Cajero buscarCajeroPorNombre(String nombre) {
        String sql = "SELECT * FROM Cajero WHERE nombre = ?";
        Cajero cajero = null;

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                cajero = new Cajero(id, nombre, telefono, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cajero;
    }

    public Boolean actualizarCajero(Cajero cajero) {
        String sql = "UPDATE Cajero SET nombre = ?, telefono = ?, email = ? WHERE id = ?";

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cajero.getNombre());
            pstmt.setString(2, cajero.getTelefono());
            pstmt.setString(3, cajero.getEmail());
            pstmt.setString(4, cajero.getId());

            int filasAfectadas = pstmt.executeUpdate();

            return filasAfectadas != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean eliminarCajero(String id) {
        String sql = "DELETE FROM Cajero WHERE id = ?";

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            int filasAfectadas = pstmt.executeUpdate();

            return filasAfectadas != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Cajero> obtenerTodosLosCajeros() {
        String sql = "SELECT * FROM Cajero";
        List<Cajero> cajeros = new ArrayList<>();

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");

                Cajero cajero = new Cajero(id, nombre, telefono, email);
                cajeros.add(cajero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cajeros;
    }
}
