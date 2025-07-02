/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author LENOVO
 */
public class Factura {
    
    private int idVenta;
    private int idCliente;//AUTO_INCREMENT
    private int idEmpleado;
    private String fecha;//date
    
    public void Factura(int idVenta, int idCliente, int idEmpleado, String fecha){
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
    }
}
