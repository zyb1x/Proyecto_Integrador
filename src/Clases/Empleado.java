
package Clases;

public class Empleado{
    private int idEmpleado;
    private String Dpto;
    private String turno;
    private String telefono;
    private String correo;
    private String contrasenia;
    private String puesto;
    
    //constructor con parametros

    public Empleado(int idEmpleado, String Dpto, String turno, String telefono, String correo, String contrasenia, String puesto) {
        
        this.Dpto = Dpto;
        this.idEmpleado = idEmpleado;
        this.turno = turno;
        this.telefono = telefono;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.puesto = puesto;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getDpto() {
        return Dpto;
    }

    public void setDpto(String Dpto) {
        this.Dpto = Dpto;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    
    

    
}    


