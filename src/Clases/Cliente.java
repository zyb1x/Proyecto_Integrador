
package Clases;

public class Cliente extends Persona {
    
    private String correo;
    private String telefono;
    private String curp;
    private String rfc;
    private String folio; 
    
  //constructor vacio
    public Cliente() {
        super();
    }

    //constructor con parametros

    public Cliente(String correo, String telefono, String curp, String rfc, String folio, String nombre, String materno, String paterno, String direccion) {
        super(nombre, materno, paterno, direccion);
        this.correo = correo;
        this.telefono = telefono;
        this.curp = curp;
        this.rfc = rfc;
        this.folio = folio;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }
    
    


    
    


    

    



}
