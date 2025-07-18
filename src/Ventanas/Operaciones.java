
package Ventanas;
import BaseDatos.connection;
import Clases.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;


public class Operaciones extends javax.swing.JInternalFrame {
    
   
   public JTabbedPane getTabPanel() {
    return TabPanel;
}
   
   
    DefaultTableModel modelo;
    ArrayList<Producto> listaProductos = new ArrayList<>();
   
   // Primero agregar cliente (este método está correcto)
   //Si se agrega el cliente correctamente
public void cliente(String rfc, String apellidoP, String apellidoM) {
    String sql = "INSERT INTO CLIENTE (rfc, apellidoP, apellidoM) "
        + "VALUES ( ?, ?, ?)";

    try (Connection con = connection.getConnection();
         PreparedStatement pstmt = con.prepareStatement(sql)) {
        
        pstmt.setString(1, rfc);
        pstmt.setString(2, apellidoP);
        pstmt.setString(3, apellidoM);
        
        pstmt.executeUpdate();
        System.out.println("Nuevo cliente agregado con ID: " + apellidoM);
        
    } catch (SQLException e) {
        System.out.println("Error al agregar cliente: " + e.getMessage());
    }
}

//Apartir de aqui el codigo deja de funcionar
//obtener idCliente
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

 public int getIdEmpleado(String correo) {
     int idEmpleado = 1;
     String sql = "SELECT idEmpleado FROM EMPLEADO WHERE correo = ?";
     
     try (Connection con = connection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
         pstmt.setString(1, correo);
         
         ResultSet rs = pstmt.executeQuery();
         
         if (rs.next()) {
             idEmpleado = rs.getInt("idEmpleado");
         }
         
     } catch(SQLException e) {
         JOptionPane.showMessageDialog(null, "Error al obtener idEmpleado");
         System.err.print("Error SQL: " + e.getMessage());
     }
     return idEmpleado;
 }

 
// Método para generar venta corregido
public void generarVenta(int idCliente, int idEmpleado, String fecha) {
    
    // Primero insertamos en VENTA
    String sqlVenta = "INSERT INTO VENTA (idCliente, idEmpleado, fecha) "
            + "VALUES (?, ?, ?)";

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


public int IDVenta(int idCliente){
     int IdVenta = 1;
     String sql = "SELECT idVenta FROM VENTA WHERE idCliente=?";
     
     try (Connection con = connection.getConnection();
             PreparedStatement pstmt = con .prepareStatement(sql)) {
         pstmt.setInt(1, idCliente);
         ResultSet rs = pstmt.executeQuery();
         
         if (rs.next()) {
             IdVenta = rs.getInt("idVenta");
         }
     } catch (SQLException e){
         JOptionPane.showMessageDialog(null, "Error al obtener idVenta");
         System.err.print("Error SQL: " + e);
     }
     return IdVenta;
 }

    public void DetalleVenta(int idVenta, int idProductos, 
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
    
    public Operaciones() {
        initComponents();
       Date date1= new Date();
        
        date.setText(String.valueOf(date1));
        cambio.setEnabled(false);  
        
      //numeros del combobox
       /*for(int i = 1; i<=99; i++) {
        cantidad.addItem(String.valueOf(i));  
       }*/
       
        op.removeAllItems();
        op.addItem("ID");
        op.addItem("Nombre");
        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String [] {"ID", "Nombre", "Tipo", "Categoria", "Stock", "Precio"});
        datos.setModel(modelo);
        
        tipo.removeAllItems();
        tipo.addItem("Seleccionar");
        tipo.addItem("Termoestables");
        tipo.addItem("Termoplasticos");
        
        
        tipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                categoria.removeAllItems();
                String sel = tipo.getSelectedItem().toString();
                if (sel.equals("Termoestables")) {
                    categoria.addItem("Limpieza");
                    categoria.addItem("Hogar y cocina");
                } else if (sel.equals("Termoplasticos")) {
                    categoria.addItem("Descartables alimentos ");
                    categoria.addItem("Bolsas y empaques");
                  }
               }
             }
        });
                 
    }
    
       private void mostrarPorTipo(String filtro) {
        modelo.setRowCount(0);
        for (Producto p : listaProductos) {
            if (filtro.equals("Todos") || p.getTipo().equalsIgnoreCase(filtro)) {
                modelo.addRow(new Object [] {
                    p.getIdProducts(), p.getNombre(), p.getTipo(), p.getCategoria(), p.getStock(), p.getPrecio()
                });
            }
        }
    }
     
       private void buscarProducto (){
          String criterio = op.getSelectedItem().toString();
          String valor = bp.getText().toLowerCase();
           modelo.setRowCount(0);
        
         for (Producto p : listaProductos) {
            if (criterio.equals("ID")) {
                if (String.valueOf(p.getIdProducts()).equals(valor)) {
                    modelo.addRow(new Object[]{
                        p.getIdProducts(), p.getNombre(), p.getTipo(), p.getCategoria(), p.getStock(), p.getPrecio()
                    });
                }
            } else if (criterio.equals("Nombre")) {
                if (p.getNombre().toLowerCase().contains(valor)) {
                    modelo.addRow(new Object[]{
                        p.getIdProducts(), p.getNombre(), p.getTipo(), p.getCategoria(), p.getStock(), p.getPrecio()
                    });
                }
             }
          }
        
     }
   
   private void limpiarCampos() {
            idProducts.setText("");
            nombre.setText("");
            stock.setText("");
            precio.setText("");
            tipo.setSelectedIndex(0);
            categoria.removeAllItems();           
        
   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel13 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        TabPanel = new javax.swing.JTabbedPane();
        VentasPan = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        generar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        apellidoM = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        idProducto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        precio = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        date = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        metodoPago = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cantidad = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        apellidoP = new javax.swing.JTextField();
        total = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        cambio = new javax.swing.JLabel();
        pago = new javax.swing.JTextField();
        FacturacionPan = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        nombreCliente = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        rfc = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        direccion = new javax.swing.JTextField();
        date1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        generarFactura = new javax.swing.JButton();
        cancelar1 = new javax.swing.JButton();
        AlmacenPan = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        op = new javax.swing.JComboBox<>();
        bp = new javax.swing.JTextField();
        buscar = new javax.swing.JButton();
        todost = new javax.swing.JButton();
        establest = new javax.swing.JButton();
        plasticost = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        datos = new javax.swing.JTable();
        RegistrarProductos = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        idProducts = new javax.swing.JTextField();
        nombre = new javax.swing.JTextField();
        stock = new javax.swing.JTextField();
        precio1 = new javax.swing.JTextField();
        tipo = new javax.swing.JComboBox<>();
        categoria = new javax.swing.JComboBox<>();
        guardar = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        idProducts1 = new javax.swing.JTextField();
        idProducts2 = new javax.swing.JTextField();
        idProducts3 = new javax.swing.JTextField();
        idProducts4 = new javax.swing.JTextField();
        idProducts5 = new javax.swing.JTextField();

        jLabel13.setText("jLabel13");

        jLabel27.setText("jLabel27");

        setClosable(true);

        VentasPan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(19, 25, 54));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Gadugi", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(220, 225, 235));
        jLabel1.setText("R e g i s t r o  d e  v e n t a");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        jPanel4.setBackground(new java.awt.Color(10, 54, 86));

        generar.setBackground(new java.awt.Color(157, 178, 191));
        generar.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        generar.setForeground(new java.awt.Color(10, 54, 86));
        generar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/calculator.png"))); // NOI18N
        generar.setText("Generar venta");
        generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarActionPerformed(evt);
            }
        });

        cancelar.setBackground(new java.awt.Color(102, 102, 102));
        cancelar.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        cancelar.setForeground(new java.awt.Color(10, 54, 86));
        cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/trash-x.png"))); // NOI18N
        cancelar.setText("Cancelar");

        apellidoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellidoMActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(220, 225, 235));
        jLabel3.setText("ID Producto");

        idProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idProductoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(220, 225, 235));
        jLabel5.setText("Cantidad");

        jLabel6.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(220, 225, 235));
        jLabel6.setText("Precio");

        precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                precioKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(220, 225, 235));
        jLabel8.setText("No. de venta");

        jLabel2.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(220, 225, 235));
        jLabel2.setText("Información de pago");

        jLabel9.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(220, 225, 235));
        jLabel9.setText("Total a pagar");

        jLabel10.setFont(new java.awt.Font("Gadugi", 3, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(220, 225, 235));
        jLabel10.setText("Fecha y hora");

        date.setBackground(new java.awt.Color(220, 225, 235));
        date.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(220, 225, 235));
        jLabel11.setText("Método de pago");

        metodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Efectivo", "Tarjeta" }));

        jLabel12.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(220, 225, 235));
        jLabel12.setText("Pago");

        jLabel16.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(220, 225, 235));
        jLabel16.setText("Apellido materno");

        jLabel43.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(220, 225, 235));
        jLabel43.setText("Apellido Paterno");

        apellidoP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellidoPActionPerformed(evt);
            }
        });

        total.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        total.setForeground(new java.awt.Color(255, 255, 255));

        jLabel45.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(220, 225, 235));
        jLabel45.setText("Cambio");

        cambio.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        cambio.setForeground(new java.awt.Color(255, 255, 255));

        pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel8))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(idProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(apellidoP, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cantidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(precio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(apellidoM, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 168, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(metodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(77, 77, 77)
                                .addComponent(pago, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel45)
                                    .addComponent(jLabel9))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cambio, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(341, 341, 341)
                .addComponent(generar)
                .addGap(84, 84, 84)
                .addComponent(cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel43)
                            .addComponent(apellidoP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(apellidoM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(idProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                            .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(45, 45, 45))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel11))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(metodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(119, 119, 119)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45)
                            .addComponent(cambio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generar)
                    .addComponent(cancelar))
                .addGap(124, 124, 124))
        );

        jPanel5.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 74, 1010, -1));

        VentasPan.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 580));

        TabPanel.addTab("Ventas", VentasPan);

        FacturacionPan.setPreferredSize(new java.awt.Dimension(1020, 580));

        jPanel3.setBackground(new java.awt.Color(19, 25, 54));
        jPanel3.setPreferredSize(new java.awt.Dimension(1020, 116));

        jLabel14.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("EMPRESA DE PLÁSTICOS");

        jLabel15.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("EPL9402011A5");

        jLabel17.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("RÉGIMEN FISCAL: 601- General de Ley Personas Morales");

        jLabel18.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Av. de la Reforma #456, Colonia Centro 44100");

        jLabel19.setFont(new java.awt.Font("Gadugi", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Guadalajara, Jalisco ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(330, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14))
                .addContainerGap(331, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(10, 54, 86));
        jPanel7.setPreferredSize(new java.awt.Dimension(1020, 580));

        jLabel20.setFont(new java.awt.Font("Gadugi", 0, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(220, 225, 235));
        jLabel20.setText("Cliente");

        jLabel21.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(220, 225, 235));
        jLabel21.setText("Nombre");

        nombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreClienteActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(220, 225, 235));
        jLabel22.setText("RFC");

        rfc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rfcActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(220, 225, 235));
        jLabel23.setText("Dirección");

        direccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                direccionActionPerformed(evt);
            }
        });

        date1.setBackground(new java.awt.Color(220, 225, 235));
        date1.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        date1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                date1ActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(220, 225, 235));
        jLabel24.setText("Fecha expedición");

        jLabel25.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(220, 225, 235));
        jLabel25.setText("Factura");

        jLabel26.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(220, 225, 235));
        jLabel26.setText("FOLIO INTERNO");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(220, 225, 235));
        jLabel28.setText("LUGAR DE EXPEDICIÓN: 44100 ");

        jTable1.setBackground(new java.awt.Color(11, 54, 110));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cantidad", "ID", "Descripción", "Precio unitario", "Importe"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel29.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(220, 225, 235));
        jLabel29.setText("SUBTOTAL");

        jLabel30.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(220, 225, 235));
        jLabel30.setText("IVA TASA 0.16");

        jLabel31.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(220, 225, 235));
        jLabel31.setText("$");

        jLabel32.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(220, 225, 235));
        jLabel32.setText("$");

        jLabel33.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(220, 225, 235));
        jLabel33.setText("TOTAL");

        jLabel34.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(220, 225, 235));
        jLabel34.setText("$");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        generarFactura.setBackground(new java.awt.Color(157, 178, 191));
        generarFactura.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        generarFactura.setForeground(new java.awt.Color(10, 54, 86));
        generarFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/calculator.png"))); // NOI18N
        generarFactura.setText("Generar venta");
        generarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarFacturaActionPerformed(evt);
            }
        });

        cancelar1.setBackground(new java.awt.Color(102, 102, 102));
        cancelar1.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        cancelar1.setForeground(new java.awt.Color(10, 54, 86));
        cancelar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/trash-x.png"))); // NOI18N
        cancelar1.setText("Cancelar");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jLabel25))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel21)
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addComponent(jLabel22)))
                                        .addGap(27, 27, 27))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addGap(18, 18, 18)))
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rfc, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(generarFactura)
                                        .addGap(40, 40, 40)
                                        .addComponent(cancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(110, 110, 110)
                                .addComponent(jLabel20))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel28))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel34))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel30)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel31)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(43, 43, 43)
                        .addComponent(jLabel32)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(33, 33, 33)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(rfc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))))
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel32)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generarFactura)
                    .addComponent(cancelar1))
                .addContainerGap(155, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout FacturacionPanLayout = new javax.swing.GroupLayout(FacturacionPan);
        FacturacionPan.setLayout(FacturacionPanLayout);
        FacturacionPanLayout.setHorizontalGroup(
            FacturacionPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FacturacionPanLayout.createSequentialGroup()
                .addGroup(FacturacionPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 958, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(FacturacionPanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        FacturacionPanLayout.setVerticalGroup(
            FacturacionPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FacturacionPanLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TabPanel.addTab("Facturación", FacturacionPan);

        jPanel10.setBackground(new java.awt.Color(10, 54, 86));

        jPanel12.setBackground(new java.awt.Color(19, 25, 54));

        jLabel41.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        jLabel41.setText("P r o d u c t o s");

        jLabel42.setFont(new java.awt.Font("Segoe UI Semibold", 2, 14)); // NOI18N
        jLabel42.setText("Buscar por");

        buscar.setText("Buscar");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        todost.setText("Todos");
        todost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todostActionPerformed(evt);
            }
        });

        establest.setText("Termoestables");
        establest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                establestActionPerformed(evt);
            }
        });

        plasticost.setText("Termoplasticos");
        plasticost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plasticostActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bp, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(op, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel41)
                                .addComponent(jLabel42)))
                        .addContainerGap(16, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addComponent(buscar)
                                .addGap(53, 53, 53))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(establest, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(todost, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(plasticost, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27))))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel41)
                .addGap(37, 37, 37)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(op, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buscar)
                .addGap(68, 68, 68)
                .addComponent(todost)
                .addGap(37, 37, 37)
                .addComponent(establest)
                .addGap(42, 42, 42)
                .addComponent(plasticost)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        datos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Tipo", "Categoria", "Cantidad", "Precio"
            }
        ));
        jScrollPane2.setViewportView(datos);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 92, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(225, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AlmacenPanLayout = new javax.swing.GroupLayout(AlmacenPan);
        AlmacenPan.setLayout(AlmacenPanLayout);
        AlmacenPanLayout.setHorizontalGroup(
            AlmacenPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AlmacenPanLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 131, Short.MAX_VALUE))
        );
        AlmacenPanLayout.setVerticalGroup(
            AlmacenPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AlmacenPanLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        TabPanel.addTab("Almacen", AlmacenPan);

        jPanel8.setBackground(new java.awt.Color(10, 54, 86));

        jPanel9.setBackground(new java.awt.Color(19, 25, 54));

        jLabel35.setBackground(new java.awt.Color(0, 0, 0));
        jLabel35.setFont(new java.awt.Font("Gadugi", 1, 36)); // NOI18N
        jLabel35.setText("R e g i s t r o   d e   p r o d u c t o s");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(177, 177, 177)
                    .addComponent(jLabel35)
                    .addContainerGap(155, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 108, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addComponent(jLabel35)
                    .addContainerGap(30, Short.MAX_VALUE)))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ID: ");

        jLabel36.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Nombre: ");

        jLabel37.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Tipo: ");

        jLabel38.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Categoría: ");

        jLabel39.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Cantidad: ");

        jLabel40.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Precio: ");

        guardar.setBackground(new java.awt.Color(66, 67, 73));
        guardar.setFont(new java.awt.Font("Segoe UI Semibold", 2, 14)); // NOI18N
        guardar.setText("Guardar");
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("ID Proveedor");

        jLabel47.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Razon social");

        jLabel48.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Telefono ");

        jLabel57.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Direccion");

        jLabel58.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Subtotal");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel40)
                    .addComponent(jLabel39)
                    .addComponent(jLabel38)
                    .addComponent(jLabel37)
                    .addComponent(jLabel36)
                    .addComponent(jLabel7))
                .addGap(68, 68, 68)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(idProducts)
                    .addComponent(nombre)
                    .addComponent(stock)
                    .addComponent(precio1)
                    .addComponent(tipo, 0, 230, Short.MAX_VALUE)
                    .addComponent(categoria, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel58))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(idProducts5, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                            .addComponent(idProducts1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                            .addComponent(idProducts2, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                            .addComponent(idProducts3, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                            .addComponent(idProducts4, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                        .addGap(214, 214, 214))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(guardar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(idProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)
                    .addComponent(idProducts1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47)
                    .addComponent(idProducts2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(idProducts3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57)
                    .addComponent(idProducts4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58)
                    .addComponent(idProducts5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(precio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(guardar))
                .addGap(0, 188, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout RegistrarProductosLayout = new javax.swing.GroupLayout(RegistrarProductos);
        RegistrarProductos.setLayout(RegistrarProductosLayout);
        RegistrarProductosLayout.setHorizontalGroup(
            RegistrarProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegistrarProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RegistrarProductosLayout.setVerticalGroup(
            RegistrarProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegistrarProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        TabPanel.addTab("Registrar productos", RegistrarProductos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabPanel)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabPanel)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarFacturaActionPerformed
        String RFC = rfc.getText();

        cliente(RFC," "," ");
    }//GEN-LAST:event_generarFacturaActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void date1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_date1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_date1ActionPerformed

    private void direccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_direccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_direccionActionPerformed

    private void rfcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rfcActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rfcActionPerformed

    private void nombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreClienteActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        try {
            int id = Integer.parseInt(idProducts.getText());
            String nom = nombre.getText();
            String tip = tipo.getSelectedItem().toString();
            String cat = categoria.getSelectedItem().toString();
            int stk = Integer.parseInt(stock.getText());
            float prc = Float.parseFloat(precio.getText());

            Producto p = new Producto( nom, id, stk, tip, cat,prc, null );
            listaProductos.add(p);

            mostrarPorTipo("Todos");
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Producto registrado con exito");

        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Error al registrar" + e.getMessage());
        }
    }//GEN-LAST:event_guardarActionPerformed

    private void plasticostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plasticostActionPerformed
        mostrarPorTipo("Termoplasticos");
    }//GEN-LAST:event_plasticostActionPerformed

    private void establestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_establestActionPerformed

        mostrarPorTipo("Termoestables");
    }//GEN-LAST:event_establestActionPerformed

    private void todostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todostActionPerformed
        mostrarPorTipo("Todos");
    }//GEN-LAST:event_todostActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        buscarProducto();
    }//GEN-LAST:event_buscarActionPerformed

    private void pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pagoActionPerformed

    private void apellidoPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidoPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoPActionPerformed

    private void precioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_precioKeyTyped
        // validacion de numeros con punto decimal en ventana de ventas
        int key = evt.getKeyChar();
        boolean numero = (key >= 48 && key <= 57) || key == 46 || key == 8;   //evalua si la tecla es un número del 0 al 9, 46= punto decimal
        if(!numero) {
            evt.consume();    //anula cualquier valor que no sea numero entero
            JOptionPane.showMessageDialog(null, "El precio no admite letras");
        }
    }//GEN-LAST:event_precioKeyTyped

    private void idProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idProductoActionPerformed

    private void apellidoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidoMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoMActionPerformed

    private void generarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generarActionPerformed
        String paterno = apellidoP.getText();
        String materno = apellidoM.getText();
        int Producto = Integer.parseInt(idProducto.getText());
        int Cantidad = Integer.parseInt(cantidad.getText());
        float Precio = Float.parseFloat(precio.getText());
        float subtotal = Cantidad * Precio;
        double resultado = subtotal * 0.16;
        String resultadoStr = String.valueOf(resultado + subtotal);
        String Metodopago = metodoPago.getSelectedItem().toString();
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        total.setText(resultadoStr);

        if (metodoPago.getSelectedIndex() == 1) {
            cambio.setEnabled(true);
            double recibido = Double.parseDouble(pago.getText());
            if(recibido>resultado){
                String sobrante = String.valueOf(recibido - resultado);
                cambio.setText(sobrante);
            } else {
                JOptionPane.showMessageDialog(null, "Ingresa una cantidad valida");
            }
        }

        cliente(null, paterno, materno);

        int idCliente = getIdCliente(paterno);
        String correo = login.email.getText();
        int idEmpleado = getIdEmpleado(correo);
        int idVenta = IDVenta(idCliente);

        generarVenta(idCliente,idEmpleado, fecha);

        DetalleVenta(idVenta,Producto, Cantidad,
            Precio, subtotal, Metodopago);
    }//GEN-LAST:event_generarActionPerformed


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AlmacenPan;
    private javax.swing.JPanel FacturacionPan;
    private javax.swing.JPanel RegistrarProductos;
    private javax.swing.JTabbedPane TabPanel;
    private javax.swing.JPanel VentasPan;
    private javax.swing.JTextField apellidoM;
    private javax.swing.JTextField apellidoP;
    private javax.swing.JTextField bp;
    private javax.swing.JButton buscar;
    private javax.swing.JLabel cambio;
    private javax.swing.JButton cancelar;
    private javax.swing.JButton cancelar1;
    private javax.swing.JTextField cantidad;
    private javax.swing.JComboBox<String> categoria;
    private javax.swing.JTextField date;
    private javax.swing.JTextField date1;
    private javax.swing.JTable datos;
    private javax.swing.JTextField direccion;
    private javax.swing.JButton establest;
    private javax.swing.JButton generar;
    private javax.swing.JButton generarFactura;
    private javax.swing.JButton guardar;
    private javax.swing.JTextField idProducto;
    private javax.swing.JTextField idProducts;
    private javax.swing.JTextField idProducts1;
    private javax.swing.JTextField idProducts2;
    private javax.swing.JTextField idProducts3;
    private javax.swing.JTextField idProducts4;
    private javax.swing.JTextField idProducts5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JComboBox<String> metodoPago;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField nombreCliente;
    private javax.swing.JComboBox<String> op;
    private javax.swing.JTextField pago;
    private javax.swing.JButton plasticost;
    private javax.swing.JTextField precio;
    private javax.swing.JTextField precio1;
    private javax.swing.JTextField rfc;
    private javax.swing.JTextField stock;
    private javax.swing.JComboBox<String> tipo;
    private javax.swing.JButton todost;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables
}
