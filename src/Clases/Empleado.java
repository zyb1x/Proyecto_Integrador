
package Clases;

public class Empleado extends Persona{
    private int numDpto;
    private int idEmpleado;
    private String turno;

    public Empleado(){
        super();
    }
    
    //constructor con parametros

    public Empleado(int numDpto, int idEmpleado, String turno, String nombre, String materno, String paterno, String direccion) {
        super(nombre, materno, paterno, direccion);
        this.numDpto = numDpto;
        this.idEmpleado = idEmpleado;
        this.turno = turno;
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

    
}    


