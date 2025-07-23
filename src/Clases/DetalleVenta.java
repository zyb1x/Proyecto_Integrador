package Clases;

//Datos de detalle de compra en la base

import BaseDatos.connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

//idDetalle AUTO_INCREMENT,cantidad, precioCompra,subtotal

public class DetalleVenta extends Detalles {
    
    private int id;
    private String nombre;
    private int cantidad;
    private double precio;
    
    public DetalleVenta(){
        
    }
    
    public void arrayDetalle(int id, String nombre, int cantidad, double precio){
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }
    public void generarDetalleVenta(int idVenta, int idProductos, 
        int cantidad, double precio, double subtotal, String metodoPago){
    // Luego insertamos en DETALLE_VENTA
    String sqlDetalle = "INSERT INTO DETALLE_VENTA (idVenta, idProductos, cantidad, precio, subtotal, metodoPago)"
            + "VALUES (?, ?, ?, ?, ?, ?)";
    
    try (Connection con = connection.getConnection()) {        // Iniciar transacción
        con.setAutoCommit(false);
    // 2. Insertar en DETALLE_VENTA
            try (PreparedStatement pstmtDetalle = con.prepareStatement(sqlDetalle)) {
                
                pstmtDetalle.setInt(1, idVenta);
                pstmtDetalle.setInt(2, idProductos);
                pstmtDetalle.setInt(3, cantidad);
                pstmtDetalle.setDouble(4, precio);
                pstmtDetalle.setDouble(5, subtotal);
                pstmtDetalle.setString(6, metodoPago);
                
                pstmtDetalle.executeUpdate();
                System.out.println("Detalle de venta registrado");
            } catch (SQLException e){
                con.rollback();
                
            System.out.println("Error al registrar venta: " + e.getMessage());
            throw e; // Relanzar la excepción
            }
            con.commit();
    } catch(SQLException e) {
        System.err.print("Error al conectar con la base de datos");
    }
}
    
    public int getIdDetalleVenta(int idVenta){
        String sql = "SELECT idDetalle FROM DETALLE_VENTA WHERE idVenta = ?";
    int idDetalle = 1;
    
    try (Connection con = connection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
        
        pstmt.setInt(1,idVenta);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
           idDetalle = rs.getInt("idCliente");
        }
        
    } catch(SQLException e){
        JOptionPane.showMessageDialog(null,"Error al obtener idCliente");
        System.err.print("Error SQL: " + e.getMessage());
    }
    return idDetalle;
    }
    
    public int getIdProductos(int idVenta){
        String sql = "SELECT idProductos FROM DETALLE_VENTA WHERE idVenta = ?";
    int idProductos = 1;
    
    try (Connection con = connection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
        
        pstmt.setInt(1,idVenta);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
           idProductos = rs.getInt("idCliente");
        }
        
    } catch(SQLException e){
        JOptionPane.showMessageDialog(null,"Error al obtener idCliente");
        System.err.print("Error SQL: " + e.getMessage());
    }
    return idProductos;
    }
 
    public int getCantidad(int idVenta){
        String sql = "SELECT cantidad FROM DETALLE_VENTA WHERE idVenta = ?";
    int cantidad = 1;
    
    try (Connection con = connection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
        
        pstmt.setInt(1,idVenta);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
           cantidad = rs.getInt("idCliente");
        }
        
    } catch(SQLException e){
        JOptionPane.showMessageDialog(null,"Error al obtener idCliente");
        System.err.print("Error SQL: " + e.getMessage());
    }
    return cantidad;
    }
    
    public double getPrecio(int idVenta){
        String sql = "SELECT precio FROM DETALLE_VENTA WHERE idVenta = ?";
    double precio = 1;
    
    try (Connection con = connection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
        
        pstmt.setInt(1,idVenta);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
           precio = rs.getInt("idCliente");
        }
        
    } catch(SQLException e){
        JOptionPane.showMessageDialog(null,"Error al obtener idCliente");
        System.err.print("Error SQL: " + e.getMessage());
    }
    return precio;
    }
    
    public double getSubtotal(int idVenta){
        String sql = "SELECT subtotal FROM DETALLE_VENTA WHERE idVenta = ?";
    double subtotal = 1;
    
    try (Connection con = connection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
        
        pstmt.setInt(1,idVenta);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
           subtotal = rs.getInt("idCliente");
        }
        
    } catch(SQLException e){
        JOptionPane.showMessageDialog(null,"Error al obtener idCliente");
        System.err.print("Error SQL: " + e.getMessage());
    }
    return subtotal;
    }
    
       public void getDetalleYMostrarEnTabla(int idVenta, DefaultTableModel modeloTabla) {
    String sql = "SELECT * FROM detalle_venta WHERE idVenta = ?";

    // Limpiar la tabla antes de llenarla
    modeloTabla.setRowCount(0);

    try (Connection con = connection.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql)) {

        pstmt.setInt(1, idVenta);

        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Object[] fila = new Object[6];  // Solo 6 columnas, excluimos idVenta porque ya lo tenemos
                fila[0] = rs.getInt("idDetalle");
                fila[1] = rs.getInt("idProductos");
                fila[2] = rs.getInt("cantidad");
                fila[3] = rs.getBigDecimal("precio");
                fila[4] = rs.getBigDecimal("subtotal");
                fila[5] = rs.getString("metodoPago");

                modeloTabla.addRow(fila);
            }
        }

    } catch (SQLException e) {
        System.out.print("Error al cargar datos: " + e.getMessage());
    }
}
}
