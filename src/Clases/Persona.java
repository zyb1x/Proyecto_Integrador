
package Clases;

public class Persona {
   
    private String nombre, materno, paterno;

    public Persona(){

    }
    
    public Persona(String nombre, String materno, String paterno){
        this.nombre = nombre;
        this.materno = materno;
        this.paterno = paterno;
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

    
    
}
