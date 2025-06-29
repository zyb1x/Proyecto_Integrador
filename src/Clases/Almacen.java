
package Clases;


public class Almacen {
    private int idAlmacen;
    private String movimiento;
    private int cantidad;

    public Almacen(int idAlmacen, String movimiento, int cantidad){
        this.idAlmacen = idAlmacen;
        this. movimiento = movimiento;
        this.cantidad = cantidad;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
}
