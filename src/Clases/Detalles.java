
package Clases;

public class Detalles {
    protected int cantidad;
    protected float precio;
    
    public Detalles() {
        
    }

    public Detalles(int cantidad, float precio) {
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
    
    
    
}
