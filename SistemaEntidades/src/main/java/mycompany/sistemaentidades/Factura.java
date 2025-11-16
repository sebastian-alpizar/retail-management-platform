package mycompany.sistemaentidades;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Factura implements Serializable {

    private String id;
    private String cliente;
    private String cajero;
    private Date fecha;
    private List<LineaFactura> lineas;
    private String formaPago;
    private double total;

    public Factura(String id, String cliente, String cajero, String formaPago, List<LineaFactura> lineas, Date fecha, double total) {
        this.id = id; // Usa el ID proporcionado
        this.cliente = cliente;
        this.cajero = cajero;
        this.formaPago = formaPago;
        this.lineas = (lineas != null) ? lineas : new ArrayList<>(); // Inicializa lineas si es null
        this.fecha = fecha != null ? fecha : new Date(); // Usa la fecha proporcionada o la fecha actual si es null
        this.total = calcularTotal(); // Calcula el total basado en las líneas de la factura
    }

    private double calcularTotal() {
        // Calcula el total basado en las líneas de la factura
        return lineas.stream().mapToDouble(LineaFactura::getTotalLinea).sum();
    }

    // Métodos getter y setter
    public String getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }
    public String getNombreCliente() {
        return cliente;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public List<LineaFactura> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaFactura> lineas) {
        this.lineas = (lineas != null) ? lineas : new ArrayList<>(); // Initialize lineas if null
        this.total = calcularTotal(); // Recalcula el total cuando se actualizan las líneas
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFormaPago() {
        return formaPago;
    }
}