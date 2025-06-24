
package Clases;

public class Cliente extends Datos {
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





}
