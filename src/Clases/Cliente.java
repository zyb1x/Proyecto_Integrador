
package Clases;

public class Cliente extends Persona {
private String correo;
private String direccion;
private String telefono;
private String curp;
private String rfc;
private String folio; 

public Cliente() {
    super();
}

public Cliente(String correo, String direccion, String telefono, String curp, String materno, String paterno, String nombre, String rfc, String folio){
    super(nombre, materno, paterno);
    this.correo = correo;
    this.direccion = direccion;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
