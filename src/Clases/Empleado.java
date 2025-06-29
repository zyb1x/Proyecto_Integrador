
package Clases;

public class Empleado extends Persona{
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

    public int getNumDpto() {
        return numDpto;
    }

    public void setNumDpto(int numDpto) {
        this.numDpto = numDpto;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
    
}    

