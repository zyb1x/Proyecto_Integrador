
package Clases;

//Datos de venta en la base de datos

import BaseDatos.connection;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import javax.swing.JOptionPane;

//idVenta AUTO_INCREMENT, idCliente AUTO_INCREMENT,  subtotal, iva, total, metodoPago
//idEmpleado, fecha
//hay dos id en venta solo se necesita uno idVenta

public class Venta extends Transaccion {
    
    public Venta(){
        
    }
    
    public void generarVenta(int idEmpleado, String fecha) {
    
    // Primero insertamos en VENTA
    String sqlVenta = "INSERT INTO VENTA (idEmpleado, fecha) "
            + "VALUES (?, ?)";

    try (Connection con = connection.getConnection()) {   
        // Iniciar transacción
        con.setAutoCommit(false);
        
        try {
            // 1. Insertar en VENTA
            try (PreparedStatement pstmtVenta = con.prepareStatement(sqlVenta)) {
                pstmtVenta.setInt(1, idEmpleado);
                pstmtVenta.setString(2, fecha);
                
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
    public int getIdVenta(String fechaExacta) {
    int idVenta = -1; // Valor por defecto que indica no encontrado
    String sql = "SELECT idVenta FROM venta WHERE fecha = ?";
    
    try (Connection con = connection.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql)) {
        
        pstmt.setString(1, fechaExacta);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                idVenta = rs.getInt("idVenta");
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al obtener idVenta");
        System.err.println("Error SQL: " + e);
    }
    return idVenta;
}
}
