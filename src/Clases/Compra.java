
package Clases;


public class Compra extends Transaccion {
    private int idCompra;

     public Compra() {
        super();
    }

    public Compra(int idCompra, int id, String fecha) {
        super(id, fecha); //llama al constructor de clase padre
        this.idCompra = idCompra;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    
    
    
     
     
    
    
    
    
    
    
    
    
    
}
