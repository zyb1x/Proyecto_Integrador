
package Clases;

public class Empleado extends Datos{
    private int numDpto;
    private int idEmpleado;
    private String turno;
    private String direccion;

    public Empleado(){
        super();
    }

    public Empleado(int numDpto, String nombre, String materno, String paterno, int idEmpleado, String turno, String direccion){
       
        super(nombre, materno, paterno);
        this.numDpto = numDpto;
        this.idEmpleado = idEmpleado;
        this.turno = turno;
        this.direccion = direccion;
    }
}    

