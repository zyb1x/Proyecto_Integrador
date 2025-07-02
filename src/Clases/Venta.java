
package Clases;

//Datos de venta en la base de datos
//idVenta AUTO_INCREMENT, idCliente AUTO_INCREMENT,  subtotal, iva, total, metodoPago
//idEmpleado, fecha
//hay dos id en venta solo se necesita uno idVenta

public class Venta extends Transaccion {
    private int idVenta;

    public Venta() {
        super();
    }

    public Venta(int idVenta, int id, String fecha) {
        super(id, fecha);  //llama al constructor de clase padre
        this.idVenta = idVenta;
    }

      public int getIdVenta() {
        return idVenta;
      }

      public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
      }
    
    
    
    

   
    
    
    
  
    
}
