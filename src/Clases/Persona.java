
package Clases;

public class Persona {
   
    private String nombre, materno, paterno, direccion;

    public Persona(){

    }
    
    //constructor con parametros
    public Persona(String nombre, String materno, String paterno, String direccion) {
        this.nombre = nombre;
        this.materno = materno;
        this.paterno = paterno;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
   
    
    
}
