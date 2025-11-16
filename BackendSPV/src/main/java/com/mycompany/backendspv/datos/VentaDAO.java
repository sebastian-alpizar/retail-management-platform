
package com.mycompany.backendspv.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class VentaDAO {
    private ConectorDatabase conector;

    public VentaDAO() {
        this.conector = new ConectorDatabase();
    }

    public Boolean agregarVenta(String facturaId, String categoria, int year, int month, double totalLinea, String cliente) {
        String sql = "INSERT INTO Venta (facturaId, categoria, year, month, totalLinea, cliente) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = conector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, facturaId);
            pstmt.setString(2, categoria);
            pstmt.setInt(3, year);
            pstmt.setInt(4, month);
            pstmt.setDouble(5, totalLinea);
            pstmt.setString(6, cliente);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Double> obtenerVentasMensualesPorCategoria(String categoria, int year, int month) {
        String sql = "SELECT AVG(totalLinea) AS promedioPrecio, COUNT(*) AS totalVentas, SUM(totalLinea) AS totalCantidad " +
                     "FROM Venta " +
                     "WHERE categoria = ? AND year = ? AND month = ?";
        Map<String, Double> estadisticas = new HashMap<>();

        try (Connection conn = conector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoria);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double promedioPrecio = rs.getDouble("promedioPrecio");
                int totalVentas = rs.getInt("totalVentas");
                double totalCantidad = rs.getDouble("totalCantidad");

                estadisticas.put("promedioPrecio", promedioPrecio);
                estadisticas.put("totalVentas", (double) totalVentas);
                estadisticas.put("totalCantidad", totalCantidad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estadisticas;
    }
}
