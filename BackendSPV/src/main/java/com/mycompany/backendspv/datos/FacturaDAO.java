package com.mycompany.backendspv.datos;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mycompany.sistemaentidades.*;

public class FacturaDAO {
    private LineaFacturaDAO lineasDAO;
    
    public FacturaDAO(LineaFacturaDAO lineasDAO){
        this.lineasDAO = lineasDAO;
    }
    
    public Boolean agregarFactura(Factura factura) {
        String sql = "INSERT INTO Factura (id, cliente_nombre, cajero_nombre, fecha, formaPago, total) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, factura.getId());
            pstmt.setString(2, factura.getCliente());
            pstmt.setString(3, factura.getCajero());
            pstmt.setDate(4, new java.sql.Date(factura.getFecha().getTime())); // Conversión a java.sql.Date
            pstmt.setString(5, factura.getFormaPago());
            pstmt.setBigDecimal(6, BigDecimal.valueOf(factura.getTotal())); // Conversión a BigDecimal
            pstmt.executeUpdate();
            
            return lineasDAO.guardarLineasFactura(factura.getId(), factura.getLineas());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Factura buscarFactura(String facturaId) {
        String sql = "SELECT * FROM Factura WHERE id = ?";
        Factura factura = null;
        
        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, facturaId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                factura = new Factura(
                    rs.getString("id"),
                    rs.getString("cliente_nombre"),
                    rs.getString("cajero_nombre"),
                    rs.getString("formaPago"),
                    null,  // Lista de líneas (se asignará luego)
                    rs.getDate("fecha"),
                    rs.getDouble("total")
                );
                List<LineaFactura> lineas =  lineasDAO.buscarLineasFactura(facturaId);
                factura.setLineas(lineas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factura;
    }
    
    public List<Factura> obtenerTodasLasFacturas() {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM Factura";
        
        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
                
            while (rs.next()) {
                Factura factura = new Factura(
                    rs.getString("id"),
                    rs.getString("cliente_nombre"),
                    rs.getString("cajero_nombre"),
                    rs.getString("formaPago"),
                    null,  // Lista de líneas (se asignará luego)
                    rs.getDate("fecha"),
                    rs.getDouble("total")
                );
                List<LineaFactura> lineas = lineasDAO.buscarLineasFactura(factura.getId());
                factura.setLineas(lineas);
                facturas.add(factura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return facturas;
    }
}
