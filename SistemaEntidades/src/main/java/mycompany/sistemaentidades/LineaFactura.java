package mycompany.sistemaentidades;

import java.io.Serializable;

public class LineaFactura implements Serializable {

    private Producto producto;
    private int cantidad;
    private double descuento;
    private double total;

    public LineaFactura(Producto producto, int cantidad, double descuento) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.descuento = descuento;
        this.total = calcularTotal();
    }

    private double calcularTotal() {
        return (producto.getPrecioUnitario() * cantidad) * (1 - descuento / 100);
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotalLinea() {
        return total;
    }

    public String getFacturaId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getProductoCodigo() {
        return producto.getCodigo();
    }
}
