
package Clases;

public class Empleado extends Persona{
    private int numDpto;
    private int idEmpleado;
    private String turno;
    private String usuario;
    private String contrasenia;
    

    public Empleado(String Nombre, String Usuario, String Contrasenia, String ConfContrasenia){
        super();
    }
    
    //constructor con parametros

    public Empleado(int numDpto, int idEmpleado, String turno, String usuario, String contrasenia, String nombre, String materno, String paterno, String direccion) {
        super(nombre, materno, paterno, direccion);
        this.numDpto = numDpto;
        this.idEmpleado = idEmpleado;
        this.turno = turno;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasena(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    

    
}    


