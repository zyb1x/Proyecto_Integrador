
package Clases;
//Datos en base
//idProductos, idCategoria, nombre
//precio, stock, tammannio

public class Producto {
    private String nombre;
    private int idProducts, stock;
    private String tipo;
    private String categoria;
    private float precio;

    //public Producto(String nombre, int idProducts, int stock, String tamannio, String tipo, String categoria, float precio) {
    public Producto(String nombre, int idProducts, int stock, String tipo, String categoria, float precio, Object par6) {
        this.nombre = nombre;
        this.idProducts = idProducts;
        this.stock = stock;
       // this.tamannio = tamannio;
        this.tipo = tipo;
        this.categoria = categoria;
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

    /*public String getTamannio() {
        return tamannio;
    }

    public void setTamannio(String tamannio) {
        this.tamannio = tamannio;
    }*/

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
    
    
}
