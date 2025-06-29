
package Clases;

public class DetalleVenta extends Detalles {
    private int idProducts;
    
    public DetalleVenta() {
        super(); 
    }

    public DetalleVenta(int idProducts, int cantidad, float precio) {
        super(cantidad, precio); //llama al constructor de clase padre
        this.idProducts = idProducts;
    }

    // Getter
    public int getIdProducts() {
        return idProducts;
    }

    // Setter
    public void setIdProducts(int idProducts) {
        this.idProducts = idProducts;
    }
        
}
