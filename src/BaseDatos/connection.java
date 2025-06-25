/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BaseDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 *
 * @author LENOVO
 */
public class connection {
    
    private Connection conexion;
    
        private final String url = "jdbc:mysql://localhost:3306/PLASTICOS";
        private final String usuario = "root";
        private final String password = "";
        
        public connection() {
             try {
    conexion = DriverManager.getConnection(url, usuario, password);
    System.out.println("Conexion exitosa ");
    } catch(SQLException e) {
    System.out.print("Error al conectar: " + e.getMessage());
    }
        }
       public Connection getConexion(){
           return conexion;
       }
}

