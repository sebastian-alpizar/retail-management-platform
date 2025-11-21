package com.mycompany.backendspv.datos;

import mycompany.sistemaentidades.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    public Boolean agregarProducto(Producto producto) {
        String sql = "INSERT INTO Producto (codigo, descripcion, unidadMedida, precioUnitario, existencias, categoria) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, producto.getCodigo());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setString(3, producto.getUnidadMedida());
            pstmt.setDouble(4, producto.getPrecioUnitario());
            pstmt.setInt(5, producto.getExistencias());
            pstmt.setString(6, producto.getCategoria());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Producto buscarProductoPorCodigo(String codigo) {
        String sql = "SELECT * FROM Producto WHERE codigo = ?";
        Producto producto = null;

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String descripcion = rs.getString("descripcion");
                String unidadMedida = rs.getString("unidadMedida");
                double precioUnitario = rs.getDouble("precioUnitario");
                int existencias = rs.getInt("existencias");
                String categoria = rs.getString("categoria");
                producto = new Producto(codigo, descripcion, unidadMedida, precioUnitario, existencias, categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }
    
    public Producto buscarProductoPorDescripcion(String descripcion) {
        String sql = "SELECT * FROM Producto WHERE descripcion = ?";
        Producto producto = null;

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, descripcion);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String codigo = rs.getString("codigo");
                String unidadMedida = rs.getString("unidadMedida");
                double precioUnitario = rs.getDouble("precioUnitario");
                int existencias = rs.getInt("existencias");
                String categoria = rs.getString("categoria");
                producto = new Producto(codigo, descripcion, unidadMedida, precioUnitario, existencias, categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

    public Boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE Producto SET descripcion = ?, unidadMedida = ?, precioUnitario = ?, existencias = ?, categoria = ? WHERE codigo = ?";

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, producto.getDescripcion());
            pstmt.setString(2, producto.getUnidadMedida());
            pstmt.setDouble(3, producto.getPrecioUnitario());
            pstmt.setInt(4, producto.getExistencias());
            pstmt.setString(5, producto.getCategoria());
            pstmt.setString(6, producto.getCodigo());

            int filasAfectadas = pstmt.executeUpdate();

            return filasAfectadas != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean eliminarProducto(String codigo) {
        String sql = "DELETE FROM Producto WHERE codigo = ?";

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigo);
            int filasAfectadas = pstmt.executeUpdate();

            return filasAfectadas != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Producto> obtenerTodosLosProductos() {
        String sql = "SELECT * FROM Producto";
        List<Producto> productos = new ArrayList<>();

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String descripcion = rs.getString("descripcion");
                String unidadMedida = rs.getString("unidadMedida");
                double precioUnitario = rs.getDouble("precioUnitario");
                int existencias = rs.getInt("existencias");
                String categoria = rs.getString("categoria");

                Producto producto = new Producto(codigo, descripcion, unidadMedida, precioUnitario, existencias, categoria);
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
    
    public List<String> obtenerCategorias() {
        String sql = "SELECT DISTINCT categoria FROM Producto";
        List<String> categorias = new ArrayList<>();

        try (Connection conn = ConectorDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String categoria = rs.getString("categoria");
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

}
