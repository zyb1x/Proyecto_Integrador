
package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/PLASTICOS";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Método para obtener la conexión
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método para cerrar la conexión (opcional, útil si no usas try-with-resources)
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            System.out.println("Conexion exitosa");
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                
            }
        }
    }
}