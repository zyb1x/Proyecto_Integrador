
package Clases;
//Datos en Base
//idProveedor, nombre, telefono, direccion

public class Proveedor {
    private String fecha;
        private int id;

      /*  public Proveedor(String fecha, int id){
            
        }  */

    public Proveedor(String fecha, int id) {
        this.fecha = fecha;
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
        
        
        
        
    
}
