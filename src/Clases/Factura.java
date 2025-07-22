/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import BaseDatos.connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author LENOVO
 */
public class Factura {
    public Factura(){
        
    }
    
    public void generarFactura(int idCliente, int idEmpleado, String fecha, int idVenta) {
    
    // Primero insertamos en VENTA
    String sqlVenta = "INSERT INTO FACTURA (idCliente, idEmpleado, fecha, int idVenta) "
            + "VALUES (?, ?, ?, ?)";

    try (Connection con = connection.getConnection()) {   
        // Iniciar transacción
        con.setAutoCommit(false);
        
        try {
            // 1. Insertar en VENTA
            try (PreparedStatement pstmtVenta = con.prepareStatement(sqlVenta)) {
                pstmtVenta.setInt(1, idCliente);
                pstmtVenta.setInt(2, idEmpleado);
                pstmtVenta.setString(3, fecha);
                
                pstmtVenta.executeUpdate();
                System.out.println("Venta registrada");
            }
            
            // Confirmar transacción
            con.commit();
            
        } catch (SQLException e) {
            // Revertir en caso de error
            con.rollback();
            System.out.println("Error al registrar venta: " + e.getMessage());
            throw e; // Relanzar la excepción
        }
        
    } catch (SQLException e) {
        System.out.println("Error de conexión: " + e.getMessage());
    }
}
}
