package mycompany.sistemaentidades;

import java.io.Serializable;

public class Mensaje<T> implements Serializable {
    private String emisor;
    private String detalle;
    private T datos;

    public Mensaje(String detalle, T datos) {
        this.detalle = detalle;
        this.datos = datos;
        emisor = null;
    }

    public String getDetalle() {
        return detalle;
    }
    
    public String getEmisor(){
        return emisor;
    }

    public void setTipo(String detalle) {
        this.detalle = detalle;
    }

    public T getDatos() {
        return datos;
    }

    public void setDatos(T datos) {
        this.datos = datos;
    }
    
    public void setEmisor(String emisor){
        this.emisor = emisor;
    }
}

