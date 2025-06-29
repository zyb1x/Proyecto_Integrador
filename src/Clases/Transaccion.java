
package Clases;

public class Transaccion {
     protected int id;
     protected String fecha;
     
    public Transaccion() {
        
    } 
    
     public Transaccion(int id, String fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
     
     
     
     
    
}
