
package Clases;

import BaseDatos.connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Empleado{
    
    private int idEmpleado;
    private String turno;
    private String telefono;
    private String password;
    private String correo;
    private String puesto;
    
    public Empleado(int idEmpleado, String turno, String telefono, String password, String correo, String puesto){
        this.idEmpleado = idEmpleado;
        this.turno = turno;
        this.telefono = telefono;
        this.password = password;
        this.correo = correo;
        this.puesto = puesto;
    }
    
    public void insertarEmpleado(int idEmpleado, 
            String turno, String telefono, String password, String correo, String puesto){
        
    String sql = "INSERT INTO EMPLEADO (idEmpleado, turno, telefono, password, correo, puesto) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    
    try (Connection con = connection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)
            ){
        
            pstmt.setInt(1, idEmpleado);
            pstmt.setString(2, turno);
            pstmt.setString(3, telefono);
            pstmt.setString(4, password);
            pstmt.setString(5, correo);
            pstmt.setString(6, puesto);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLEADO");
            
            if (stmt.execute("SELECT * FROM EMPLEADO")) {
            rs = stmt.getResultSet();
            }
             pstmt.executeUpdate();
             JOptionPane.showMessageDialog(null, "Se registró correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Empleado insertado correctamente.");
            
            System.out.println(rs);
            
            rs.close();
            stmt.close();
            con.close();
            
    } catch (SQLException e){
        System.out.print(e);
        JOptionPane.showMessageDialog(null, "Error al registrar empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } 
}
}    


