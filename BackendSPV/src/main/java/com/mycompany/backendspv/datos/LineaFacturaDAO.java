package com.mycompany.backendspv.datos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mycompany.sistemaentidades.*;

public class LineaFacturaDAO {
    private ProductoDAO productoDAO ;
    
    public LineaFacturaDAO(ProductoDAO productoDAO){
        this.productoDAO = productoDAO;
    }
    
    public Boolean guardarLineasFactura(String facturaId, List<LineaFactura> lineasFactura) throws SQLException {
        String sql = "INSERT INTO LineaFactura (factura_id, producto_codigo, cantidad, descuento, total) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (LineaFactura linea : lineasFactura) {
                pstmt.setString(1, facturaId);
                pstmt.setString(2, linea.getProductoCodigo());
                pstmt.setInt(3, linea.getCantidad());
                pstmt.setBigDecimal(4, BigDecimal.valueOf(linea.getDescuento()));
                pstmt.setBigDecimal(5, BigDecimal.valueOf(linea.getTotal()));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<LineaFactura> buscarLineasFactura(String facturaId) throws SQLException {
        List<LineaFactura> lineasFactura = new ArrayList<>();
        String sql = "SELECT * FROM LineaFactura WHERE factura_id = ?";

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, facturaId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String productoCodigo = rs.getString("producto_codigo");
                Producto producto = productoDAO.buscarProductoPorCodigo(productoCodigo);
                LineaFactura linea = new LineaFactura(
                        producto,
                        rs.getInt("cantidad"),
                        rs.getDouble("descuento")
                );
                linea.setTotal(rs.getBigDecimal("total").doubleValue()); // Asigna el total si es necesario
                lineasFactura.add(linea);
            }
        }
        return lineasFactura;
    }

}

