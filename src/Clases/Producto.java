
package Clases;

public class Producto {
    private String nombre;
    private int idProducts, stock;
    private String tamannio;
    private float precio;

    public Producto(String nombre, int idProducts, int stock, String tamannio, float precio) {
        this.nombre = nombre;
        this.idProducts = idProducts;
        this.stock = stock;
        this.tamannio = tamannio;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdProducts() {
        return idProducts;
    }

    public void setIdProducts(int idProducts) {
        this.idProducts = idProducts;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTamannio() {
        return tamannio;
    }

    public void setTamannio(String tamannio) {
        this.tamannio = tamannio;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
    
    
}
