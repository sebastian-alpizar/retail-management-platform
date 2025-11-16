package com.mycompany.sistemapuntodeventa.View;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Icons {
    public static final ImageIcon ACTUALIZAR = createResizedIcon("/iconos/actualizar.png", 16, 16);
    public static final ImageIcon GUARDAR = createResizedIcon("/iconos/guardar.png", 16, 16);
    public static final ImageIcon BUSCAR = createResizedIcon("/iconos/buscar.png", 16, 16);
    public static final ImageIcon LIMPIAR = createResizedIcon("/iconos/limpiar.png", 16, 16);
    public static final ImageIcon BORRAR = createResizedIcon("/iconos/borrar.png", 16, 16);
    public static final ImageIcon PDF = createResizedIcon("/iconos/pdf.png", 16, 16);
    public static final ImageIcon MOSTRAR = createResizedIcon("/iconos/mostrar.png", 16, 16);
    public static final ImageIcon AGREGAR = createResizedIcon("/iconos/agregar.png", 16, 16);
    public static final ImageIcon COBRAR = createResizedIcon("/iconos/cobrar.png", 16, 16);
    public static final ImageIcon QUITAR = createResizedIcon("/iconos/borrar.png", 16, 16);
    public static final ImageIcon CANCELAR = createResizedIcon("/iconos/cancelar.png", 16, 16);
    public static final ImageIcon DESCUENTO = createResizedIcon("/iconos/descuento.png", 16, 16);
    public static final ImageIcon CANTIDAD = createResizedIcon("/iconos/cantidad.png", 16, 16);
    public static final ImageIcon CODIGO = createResizedIcon("/iconos/codigo.png", 16, 16);
    public static final ImageIcon CAJEROS = createResizedIcon("/iconos/cajeros.png", 16, 16);
    public static final ImageIcon CLIENTES = createResizedIcon("/iconos/clientes.png", 16, 16);
    public static final ImageIcon FACTURAR = createResizedIcon("/iconos/facturar.png", 16, 16);
    public static final ImageIcon HISTORICO = createResizedIcon("/iconos/historico.png", 16, 16);
    public static final ImageIcon ESTADISTICAS = createResizedIcon("/iconos/estadisticas.png", 16, 16);
    public static final ImageIcon PRODUCTOS = createResizedIcon("/iconos/productos.png", 16, 16);
    public static final ImageIcon LOGIN = createResizedIcon("/iconos/login.png", 16, 16);
    public static final ImageIcon NEWUSER = createResizedIcon("/iconos/newUser.png", 16, 16);
    public static final ImageIcon TIENDA = createResizedIcon("/iconos/tienda.png", 16, 16);

    private static ImageIcon createResizedIcon(String resource, int width, int height) {
        try {
            // Usa getClassLoader para evitar problemas con rutas
            java.net.URL location = Icons.class.getResource(resource);
            if (location == null) {
                System.err.println("Error: El recurso " + resource + " no se encontró.");
                return null;  // Retorna null si no se encuentra el recurso
            }
            ImageIcon icon = new ImageIcon(location);
            Image img = icon.getImage();
            Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (Exception e) {
            System.err.println("Error al cargar el icono: " + resource);
            e.printStackTrace();
            return null;
        }
    }
}
