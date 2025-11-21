package com.mycompany.backendspv.datos;

import mycompany.sistemaentidades.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public Boolean agregarCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (id, nombre, telefono, email, descuento) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cliente.getId()); // id
            pstmt.setString(2, cliente.getNombre()); // Nombre
            pstmt.setString(3, cliente.getTelefono()); // Teléfono
            pstmt.setString(4, cliente.getEmail()); // Email
            pstmt.setDouble(5, cliente.getDescuento()); // Descuento
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Cliente buscarClientePorId(String id) {
        String sql = "SELECT * FROM Cliente WHERE id = ?";
        Cliente cliente = null;

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                double descuento = rs.getDouble("descuento");
                cliente = new Cliente(id, nombre, telefono, email, descuento);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }
    
    public Cliente buscarClientePorNombre(String nombreCliente) {
        String sql = "SELECT * FROM Cliente WHERE nombre = ?";
        Cliente cliente = null;

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombreCliente);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                double descuento = rs.getDouble("descuento");
                cliente = new Cliente(id, nombreCliente, telefono, email, descuento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    
    public Boolean eliminarCliente(String id) {
        String sql = "DELETE FROM Cliente WHERE id = ?";

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas != 0) {
                return true;
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE Cliente SET nombre = ?, telefono = ?, email = ?, descuento = ? WHERE id = ?";

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getTelefono());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setDouble(4, cliente.getDescuento());
            pstmt.setString(5, cliente.getId());
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas != 0) {
                return true;
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Cliente> obtenerTodosLosClientes() {
        String sql = "SELECT * FROM Cliente";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                double descuento = rs.getDouble("descuento");

                Cliente cliente = new Cliente(id, nombre, telefono, email, descuento);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}