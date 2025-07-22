
package Clases;

//Datos en la base

import BaseDatos.connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

//idCliente es AUTO_INCREMENT, rfc, nombre, apellidoP, apellidoM, telefono, correoElec

public class Cliente extends Persona {
    
   public Cliente(String rfc, String nombre, String apellidoP, String apellidoM, String estado, String ciudad, String colonia,
                    String calle, int num_exterior, int codigo_postal, String correo) {
    String sql = "INSERT INTO CLIENTE (rfc, apellidoP, apellidoM, estado, ciudad, colonia, calle, num_exterior, codigo_postal, correo) "
        + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = connection.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql)) {
        
        pstmt.setString(1, rfc);
        pstmt.setString(2, apellidoP);
        pstmt.setString(3, apellidoM);
        pstmt.setString(4, estado);
        pstmt.setString(5, ciudad);
        pstmt.setString(6, colonia);
        pstmt.setString(7, calle);
        pstmt.setInt(8, num_exterior);
        pstmt.setInt(9, codigo_postal);
        pstmt.setString(10, correo);

        pstmt.executeUpdate();
        System.out.println("Nuevo cliente agregado con ID: " + apellidoM);
        
    } catch (SQLException e) {
        System.out.println("Error al agregar cliente: " + e.getMessage());
    }
}
   
   public int getIdCliente(String paterno){
     
    String sql = "SELECT idCliente FROM CLIENTE WHERE apellidoP = ?";
    int IDCliente = 1;
    
    try (Connection con = connection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
        
        pstmt.setString(1,paterno);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
           IDCliente = rs.getInt("idCliente");
        }
        
    } catch(SQLException e){
        JOptionPane.showMessageDialog(null,"Error al obtener idCliente");
        System.err.print("Error SQL: " + e.getMessage());
    }
    return IDCliente;
}

}
