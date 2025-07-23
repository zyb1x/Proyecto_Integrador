
package Ventanas;
import BaseDatos.connection;
import Clases.Cliente;
import Clases.DetalleVenta;
import Clases.Factura;
import Clases.Producto;
import Clases.Venta;
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
    
    public ArrayList<DetalleVenta> detalles = new ArrayList<>();
    
    public JTabbedPane getTabPanel() {
    return TabPanel;
}
    public ArrayList<Cliente> cli = new ArrayList<>();
   
    public void registroProductos(int idProductos, int idCategoria, String nombre, double precio, int stock, String tamannio){
    String sql = "INSERT INTO PRODUCTOS (idProductos, idCategoria, nombre, precio, stock, tamannio)"
            + "VALUES(?, ?, ?, ?, ?, ?)";
    
    try (Connection con = connection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){
        pstmt.setInt(1, idProductos);
        pstmt.setInt(2, idCategoria);
        pstmt.setString(3, nombre);
        pstmt.setDouble(4, precio);
        pstmt.setInt(5, stock);
        pstmt.setString(6, tamannio);

        pstmt.executeUpdate();
        
        System.out.print("Producto registrado con exito");
    } catch(SQLException e){
        System.out.print("Error al conectar con la base de datos");
    }
   }
   
    public void InsertarProveedor(int idProveedor, String nombre, String telefono, String direccion){
       String sql = "INSERT INTO PROVEEDOR (idProveedor, nombre, telefono, direccion)"
               + "VALUES(?, ?, ?, ?)";
       
       try(Connection con = connection.getConnection();
               PreparedStatement pstmt = con.prepareStatement(sql)){
           pstmt.setInt(1, idProveedor);
           pstmt.setString(2, nombre);
           pstmt.setString(3, telefono);
           pstmt.setString(4, direccion);
           
           System.out.println("PRoveedor registrado");
       } catch (SQLException e){
           System.out.println("Error al conectar con la base de datos");
       }
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
 
 
 
    public Operaciones() {
        initComponents();
        FacturacionPan.setEnabled(false);
       Date date1= new Date();
        
        date.setText(String.valueOf(date1));
        
        DefaultTableModel modeloVentas = new DefaultTableModel();
        modeloVentas.addColumn("ID");
        modeloVentas.addColumn("Cantidad");
        modeloVentas.addColumn("Precio unitario");
        tablaVenta.setModel(modelo);  // 'tabla' es tu JTable

        
         //creo que este codigo pertenece a registrar productos 
       tipo.removeAllItems();
      tipo.addItem("Seleccionar");
      tipo.addItem("Termoestables");
      tipo.addItem("Termoplasticos");
      
      //este lo movi, cualquier cosa en el bloc de notas
             tipo.addItemListener((java.awt.event.ItemEvent evt) -> {
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
        });
             
             
  
      
     //Codigo perteneciente a almacen 
      modelo = new DefaultTableModel();
      String[] columnas = {"ID", "Nombre", "Tipo", "Categoria", "Stock", "Precio"};
       modelo.setColumnIdentifiers(columnas);  
       datos.setModel(modelo);   //termina aqui la definicion de la tabla ubicada almacen
 
                 
    }
    
    //aqui inicia la logica de la ventana almacen   
    DefaultTableModel modelo;
    DefaultTableModel model = new DefaultTableModel();

    ArrayList<Producto> listaProductos = new ArrayList<>();  
          
    private void mostrarPorTipo(String filtro) {
        modelo.setRowCount(0);
        for (Producto p : listaProductos) {
            if (filtro.equals("Mostrar todo") || p.getTipo().equalsIgnoreCase(filtro)) {
                modelo.addRow(new Object [] {
                    p.getIdProducts(), p.getNombre(), p.getTipo(), p.getCategoria(), p.getStock(), p.getPrecio()
                });
            }
        }
    }
          
     //filtrar busquedas  
    private void buscarProducto (){         
           filtros.removeAllItems();
           filtros.addItem("ID");
           filtros.addItem("Nombre");
           
         String criterio = filtros.getSelectedItem().toString();
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
     }   //termina aqui, esto me corresponde
       
          //segun eso esto va en registrar productos
    private void limpiarCampos() {
            idProducts.setText("");
            nombre.setText("");
            stock.setText("");
            precio.setText("");
            tipo.setSelectedIndex(0);
            categoria.removeAllItems();           
        
   }
       
      //conexion de almacen a base de datos
      

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
        jLabel3 = new javax.swing.JLabel();
        idProducto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        precio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        metodoPago = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cantidad = new javax.swing.JTextField();
        total = new javax.swing.JLabel();
        pago = new javax.swing.JTextField();
        generar1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        nombreCliente = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        apellidoPaterno = new javax.swing.JTextField();
        apellidoMaterno = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaVenta = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        NombreProductoVenta = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        date = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
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
        apellidoM = new javax.swing.JTextField();
        date1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaFactura = new javax.swing.JTable();
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
        jLabel44 = new javax.swing.JLabel();
        colonia = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        apellidoP = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        nombreCliente3 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        codigoPostal = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        ciudad = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        estado = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        rfc = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        exterior = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        calle = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        correo = new javax.swing.JTextField();
        AlmacenPan = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        filtros = new javax.swing.JComboBox<>();
        buscar = new javax.swing.JButton();
        todost = new javax.swing.JButton();
        establest = new javax.swing.JButton();
        plasticost = new javax.swing.JButton();
        bp = new javax.swing.JTextField();
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
        idProveedor = new javax.swing.JTextField();
        nombreProveedor = new javax.swing.JTextField();
        telefono = new javax.swing.JTextField();
        direccion = new javax.swing.JTextField();
        subtotal = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        tamannio = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        idCategoria = new javax.swing.JTextField();

        jLabel13.setText("jLabel13");

        jLabel27.setText("jLabel27");

        setClosable(true);

        VentasPan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(19, 25, 54));

        jLabel1.setFont(new java.awt.Font("Gadugi", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(220, 225, 235));
        jLabel1.setText("R e g i s t r o  d e  v e n t a");

        jPanel4.setBackground(new java.awt.Color(10, 54, 86));

        generar.setBackground(new java.awt.Color(157, 178, 191));
        generar.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        generar.setForeground(new java.awt.Color(10, 54, 86));
        generar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/calculator.png"))); // NOI18N
        generar.setText("Guardar Venta");
        generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarActionPerformed(evt);
            }
        });

        cancelar.setBackground(new java.awt.Color(102, 102, 102));
        cancelar.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        cancelar.setForeground(new java.awt.Color(10, 54, 86));
        cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/trash-x.png"))); // NOI18N
        cancelar.setText("Eliminar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(220, 225, 235));
        jLabel3.setText("ID Producto");

        idProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idProductoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(220, 225, 235));
        jLabel5.setText("Cantidad");

        jLabel6.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(220, 225, 235));
        jLabel6.setText("Precio");

        precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                precioKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(220, 225, 235));
        jLabel2.setText("Información de pago");

        jLabel9.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(220, 225, 235));
        jLabel9.setText("Total a pagar");

        jLabel11.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(220, 225, 235));
        jLabel11.setText("Método de pago");

        metodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Efectivo", "Tarjeta" }));

        jLabel12.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(220, 225, 235));
        jLabel12.setText("Pago");

        total.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        total.setForeground(new java.awt.Color(255, 255, 255));

        pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagoActionPerformed(evt);
            }
        });

        generar1.setBackground(new java.awt.Color(102, 102, 102));
        generar1.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        generar1.setForeground(new java.awt.Color(10, 54, 86));
        generar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/calculator.png"))); // NOI18N
        generar1.setText("Generar factura");
        generar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generar1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(220, 225, 235));
        jLabel4.setText("Nombre");

        nombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreClienteActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(220, 225, 235));
        jLabel16.setText("Apellido Paterno");

        jLabel22.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(220, 225, 235));
        jLabel22.setText("Apellido Materno");

        apellidoPaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellidoPaternoActionPerformed(evt);
            }
        });

        apellidoMaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellidoMaternoActionPerformed(evt);
            }
        });

        tablaVenta.setBackground(new java.awt.Color(11, 54, 110));
        tablaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID", "nombre", "Cantidad", "Precio unitario"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tablaVenta);

        jButton1.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(220, 225, 235));
        jLabel23.setText("Nombre Producto");

        NombreProductoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NombreProductoVentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(apellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(apellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(NombreProductoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(idProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(jLabel2)
                    .addComponent(metodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(pago, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(generar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(generar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(nombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(apellidoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(apellidoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(idProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(metodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(NombreProductoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(generar)
                        .addGap(26, 26, 26)
                        .addComponent(cancelar)
                        .addGap(27, 27, 27)
                        .addComponent(generar1))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        jLabel10.setFont(new java.awt.Font("Gadugi", 3, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(220, 225, 235));
        jLabel10.setText("Fecha y hora");

        date.setBackground(new java.awt.Color(220, 225, 235));
        date.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(220, 225, 235));
        jLabel8.setText("No. de venta");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(155, 155, 155)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel8))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        VentasPan.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1030, 530));

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
                .addContainerGap(367, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14))
                .addContainerGap(367, Short.MAX_VALUE))
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
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Gadugi", 0, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(220, 225, 235));
        jLabel20.setText("Cliente");
        jPanel7.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jLabel21.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(220, 225, 235));
        jLabel21.setText("Apellido Paterno");
        jPanel7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 110, 20));

        apellidoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellidoMActionPerformed(evt);
            }
        });
        jPanel7.add(apellidoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 160, -1));

        date1.setBackground(new java.awt.Color(220, 225, 235));
        date1.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        date1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                date1ActionPerformed(evt);
            }
        });
        jPanel7.add(date1, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 20, 70, -1));

        jLabel24.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(220, 225, 235));
        jLabel24.setText("Fecha expedición");
        jPanel7.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, -1, -1));

        jLabel26.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(220, 225, 235));
        jLabel26.setText("ID Venta");
        jPanel7.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 10, 60, -1));

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 10, 101, -1));

        jLabel28.setFont(new java.awt.Font("Gadugi", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(220, 225, 235));
        jLabel28.setText("LUGAR DE EXPEDICIÓN: 44100 ");
        jPanel7.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 50, -1, -1));

        tablaFactura.setBackground(new java.awt.Color(11, 54, 110));
        tablaFactura.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaFactura);

        jPanel7.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(603, 134, 461, 172));

        jLabel29.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(220, 225, 235));
        jLabel29.setText("SUBTOTAL");
        jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 336, -1, -1));

        jLabel30.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(220, 225, 235));
        jLabel30.setText("IVA TASA 0.16");
        jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(812, 370, -1, -1));

        jLabel31.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(220, 225, 235));
        jLabel31.setText("$");
        jPanel7.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(919, 370, -1, -1));

        jLabel32.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(220, 225, 235));
        jLabel32.setText("$");
        jPanel7.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(919, 336, -1, -1));

        jLabel33.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(220, 225, 235));
        jLabel33.setText("TOTAL");
        jPanel7.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(812, 410, 52, -1));

        jLabel34.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(220, 225, 235));
        jLabel34.setText("$");
        jPanel7.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(919, 410, -1, -1));

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(938, 336, 101, -1));

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(938, 370, 101, -1));

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        jPanel7.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(938, 410, 101, -1));

        generarFactura.setBackground(new java.awt.Color(157, 178, 191));
        generarFactura.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        generarFactura.setForeground(new java.awt.Color(10, 54, 86));
        generarFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/calculator.png"))); // NOI18N
        generarFactura.setText("Generar factura");
        generarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generarFacturaActionPerformed(evt);
            }
        });
        jPanel7.add(generarFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 330, -1, -1));

        cancelar1.setBackground(new java.awt.Color(102, 102, 102));
        cancelar1.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        cancelar1.setForeground(new java.awt.Color(10, 54, 86));
        cancelar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/trash-x.png"))); // NOI18N
        cancelar1.setText("Cancelar");
        jPanel7.add(cancelar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 330, 140, -1));

        jLabel44.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(220, 225, 235));
        jLabel44.setText("colonia");
        jPanel7.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 90, 20));

        colonia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coloniaActionPerformed(evt);
            }
        });
        jPanel7.add(colonia, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 160, -1));

        jLabel25.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(220, 225, 235));
        jLabel25.setText("Apellido Materno");
        jPanel7.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 110, 20));

        apellidoP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellidoPActionPerformed(evt);
            }
        });
        jPanel7.add(apellidoP, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 160, -1));

        jLabel45.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(220, 225, 235));
        jLabel45.setText("Nombre");
        jPanel7.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 90, 20));

        nombreCliente3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreCliente3ActionPerformed(evt);
            }
        });
        jPanel7.add(nombreCliente3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 160, -1));

        jLabel49.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(220, 225, 235));
        jLabel49.setText("codigo postal");
        jPanel7.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, 90, 20));

        codigoPostal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoPostalActionPerformed(evt);
            }
        });
        jPanel7.add(codigoPostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 160, -1));

        jLabel50.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(220, 225, 235));
        jLabel50.setText("ciudad");
        jPanel7.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 90, 20));

        ciudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ciudadActionPerformed(evt);
            }
        });
        jPanel7.add(ciudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 160, -1));

        jLabel51.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(220, 225, 235));
        jLabel51.setText("estado");
        jPanel7.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 90, 20));

        estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estadoActionPerformed(evt);
            }
        });
        jPanel7.add(estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 160, -1));

        jLabel52.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(220, 225, 235));
        jLabel52.setText("rfc");
        jPanel7.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, 90, 20));

        rfc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rfcActionPerformed(evt);
            }
        });
        jPanel7.add(rfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 160, -1));

        jLabel53.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(220, 225, 235));
        jLabel53.setText("No. Exterior");
        jPanel7.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 90, 20));

        exterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exteriorActionPerformed(evt);
            }
        });
        jPanel7.add(exterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 160, -1));

        jLabel54.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(220, 225, 235));
        jLabel54.setText("calle");
        jPanel7.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 90, 20));

        calle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calleActionPerformed(evt);
            }
        });
        jPanel7.add(calle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 160, -1));

        jLabel55.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(220, 225, 235));
        jLabel55.setText("correo");
        jPanel7.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 90, 20));

        correo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correoActionPerformed(evt);
            }
        });
        jPanel7.add(correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 160, -1));

        javax.swing.GroupLayout FacturacionPanLayout = new javax.swing.GroupLayout(FacturacionPan);
        FacturacionPan.setLayout(FacturacionPanLayout);
        FacturacionPanLayout.setHorizontalGroup(
            FacturacionPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FacturacionPanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1031, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FacturacionPanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 1057, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        FacturacionPanLayout.setVerticalGroup(
            FacturacionPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FacturacionPanLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        TabPanel.addTab("Facturación", FacturacionPan);

        jPanel10.setBackground(new java.awt.Color(10, 54, 86));

        jPanel12.setBackground(new java.awt.Color(19, 25, 54));

        jLabel41.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(189, 194, 199));
        jLabel41.setText("P r o d u c t o s");

        jLabel42.setFont(new java.awt.Font("Gadugi", 3, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(189, 194, 199));
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menu-filter.png"))); // NOI18N
        jLabel42.setText("Filtrar");

        filtros.setBackground(new java.awt.Color(157, 178, 191));
        filtros.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        filtros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "    Buscar por...", "ID", "Nombre" }));

        buscar.setBackground(new java.awt.Color(157, 178, 191));
        buscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search-alt.png"))); // NOI18N
        buscar.setText("Buscar");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        todost.setBackground(new java.awt.Color(157, 178, 191));
        todost.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        todost.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menu-select.png"))); // NOI18N
        todost.setText("Mostrar todo");
        todost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todostActionPerformed(evt);
            }
        });

        establest.setBackground(new java.awt.Color(157, 178, 191));
        establest.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        establest.setText("Termoestables");
        establest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                establestActionPerformed(evt);
            }
        });

        plasticost.setBackground(new java.awt.Color(157, 178, 191));
        plasticost.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        plasticost.setText("Termoplásticos");
        plasticost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plasticostActionPerformed(evt);
            }
        });

        bp.setBackground(new java.awt.Color(157, 178, 191));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel41)
                            .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(buscar)))
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(establest, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(plasticost, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(bp, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(filtros, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(todost, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(28, 28, 28))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel41)
                .addGap(37, 37, 37)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(filtros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(buscar)
                .addGap(44, 44, 44)
                .addComponent(todost)
                .addGap(104, 104, 104)
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
                "ID", "Nombre", "Tipo", "Categoría", "Stock", "Precio"
            }
        ));
        jScrollPane2.setViewportView(datos);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 48, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AlmacenPanLayout = new javax.swing.GroupLayout(AlmacenPan);
        AlmacenPan.setLayout(AlmacenPanLayout);
        AlmacenPanLayout.setHorizontalGroup(
            AlmacenPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AlmacenPanLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 77, Short.MAX_VALUE))
        );
        AlmacenPanLayout.setVerticalGroup(
            AlmacenPanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AlmacenPanLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 144, Short.MAX_VALUE))
        );

        TabPanel.addTab("Almacén", AlmacenPan);

        jPanel8.setBackground(new java.awt.Color(10, 54, 86));

        jPanel9.setBackground(new java.awt.Color(19, 25, 54));

        jLabel35.setBackground(new java.awt.Color(0, 0, 0));
        jLabel35.setFont(new java.awt.Font("Gadugi", 1, 36)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(189, 194, 199));
        jLabel35.setText("R e g i s t r o   d e   p r o d u c t o s");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 970, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(177, 177, 177)
                    .addComponent(jLabel35)
                    .addContainerGap(220, Short.MAX_VALUE)))
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
        jLabel47.setText("Razón social");

        jLabel48.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Teléfono ");

        jLabel57.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Dirección");

        jLabel58.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Subtotal");

        jLabel43.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Tamaño");

        jLabel56.setFont(new java.awt.Font("Segoe UI Historic", 3, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Id Categoria");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel40)
                    .addComponent(jLabel39)
                    .addComponent(jLabel38)
                    .addComponent(jLabel37)
                    .addComponent(jLabel36)
                    .addComponent(jLabel7)
                    .addComponent(jLabel43)
                    .addComponent(jLabel56))
                .addGap(68, 68, 68)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(idProducts)
                    .addComponent(nombre)
                    .addComponent(stock)
                    .addComponent(precio1)
                    .addComponent(tipo, 0, 230, Short.MAX_VALUE)
                    .addComponent(categoria, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tamannio)
                    .addComponent(idCategoria, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(subtotal)
                            .addComponent(idProveedor)
                            .addComponent(nombreProveedor)
                            .addComponent(telefono)
                            .addComponent(direccion))
                        .addGap(214, 214, 214))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(guardar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(idProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47)
                    .addComponent(nombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(idCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57)
                    .addComponent(direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58)
                    .addComponent(subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(precio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(guardar))
                .addGap(47, 47, 47)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(tamannio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 119, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout RegistrarProductosLayout = new javax.swing.GroupLayout(RegistrarProductos);
        RegistrarProductos.setLayout(RegistrarProductosLayout);
        RegistrarProductosLayout.setHorizontalGroup(
            RegistrarProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegistrarProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );
        RegistrarProductosLayout.setVerticalGroup(
            RegistrarProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegistrarProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        String Nombre = nombre.getText();
        String ApellidoP = apellidoP.getText();
        String ApellidoM = apellidoM.getText();
        String Estado = estado.getText();
        String Ciudad = ciudad.getText();
        String Colonia = colonia.getText();
        String Calle = calle.getText();
        int numExterior = Integer.parseInt(exterior.getText());
        int codigo_postal = Integer.parseInt(codigoPostal.getText());
        String Correo = correo.getText();
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        
        Cliente newCliente = new Cliente(); 
        newCliente.guardarCliente(RFC, Nombre, ApellidoP, ApellidoM, Estado, Ciudad, Colonia, Calle, numExterior, codigo_postal, Correo);
        
        int idCliente = newCliente.getIdCliente(ApellidoP);
        int idEmpleado = getIdEmpleado(login.email.getText());
        Venta newVenta = new Venta();
        int idVenta = newVenta.getIdVenta(idCliente);
        
        //Acceder a la clase
        DetalleVenta detalle = new DetalleVenta();
        int idProductos = detalle.getIdProductos(idVenta);
        int cantidad = detalle.getCantidad(idVenta);
        double precio = detalle.getPrecio(idVenta);
        double subtotal = detalle.getSubtotal(idVenta);
        
        DefaultTableModel model = new DefaultTableModel();

// Agrega las columnas (solo una vez, antes de llenar la tabla)
        model.addColumn("ID Detalle");
        model.addColumn("ID Producto");
        model.addColumn("Cantidad");
        model.addColumn("Precio");
        model.addColumn("Subtotal");
        model.addColumn("Método de Pago");

// Asocia el modelo con la tabla
        tablaFactura.setModel(model);
        detalle.getDetalleYMostrarEnTabla(idVenta, model);
        
        Factura factura = new Factura();
        factura.generarFactura(idEmpleado, idCliente, fecha, idVenta);
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

    private void apellidoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidoMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoMActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        try {
            int id = Integer.parseInt(idProducts.getText());
            String nom = nombre.getText();
            String tip = tipo.getSelectedItem().toString();
            String cat = categoria.getSelectedItem().toString();
            int stk = Integer.parseInt(stock.getText());
            float prc = Float.parseFloat(precio.getText());
            String Tamannio = tamannio.getText();
            int idCat = Integer.parseInt(idCategoria.getText());
            int idPro = Integer.parseInt(idProveedor.getText());
            String nombre = nombreProveedor.getText();
            String Telefono = telefono.getText();
            String Direccion = direccion.getText();

            Producto p = new Producto( nom, id, stk, tip, cat,prc, null );
            listaProductos.add(p);

            mostrarPorTipo("Todos");
            limpiarCampos();

            registroProductos(id, idCat, nom, prc, stk, Tamannio);
            InsertarProveedor(idPro, nombre, Telefono, Direccion);
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
        mostrarPorTipo("Mostrar todo");
    }//GEN-LAST:event_todostActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        buscarProducto();
    }//GEN-LAST:event_buscarActionPerformed

    private void pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pagoActionPerformed

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

        String correo = login.email.getText();
        int idEmpleado = getIdEmpleado(correo);
        Cliente cliente = new Cliente();
        int idCliente = cliente.getIdCliente(paterno);
        
        Venta newVenta = new Venta();
        newVenta.generarVenta(idEmpleado, fecha, idCliente);
        
        int idVenta = newVenta.getIdVenta(idCliente);
        System.out.println("Id de venta a registrar en detalle de venta" + idVenta);

        DetalleVenta detalle = new DetalleVenta();
        detalle.generarDetalleVenta(idVenta,Producto, 
        Cantidad, Precio, subtotal, Metodopago);
        
        JOptionPane.showMessageDialog(this, "Venta registrada con exito!");
        FacturacionPan.setEnabled(true);
    }//GEN-LAST:event_generarActionPerformed

    private void coloniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coloniaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_coloniaActionPerformed

    private void apellidoPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidoPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoPActionPerformed

    private void nombreCliente3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreCliente3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreCliente3ActionPerformed

    private void codigoPostalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoPostalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoPostalActionPerformed

    private void ciudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ciudadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ciudadActionPerformed

    private void estadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estadoActionPerformed

    private void rfcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rfcActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rfcActionPerformed

    private void exteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exteriorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exteriorActionPerformed

    private void calleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_calleActionPerformed

    private void correoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_correoActionPerformed

    private void generar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generar1ActionPerformed
        FacturacionPan.setEnabled(true);
    }//GEN-LAST:event_generar1ActionPerformed

    private void nombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreClienteActionPerformed

    private void apellidoPaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidoPaternoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoPaternoActionPerformed

    private void apellidoMaternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidoMaternoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidoMaternoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
  modelo.setRowCount(0);
  
  int id = Integer.parseInt(idProducto.getText());
  String name = nombre.getText();
  int Cantidad = Integer.parseInt(cantidad.getText());
  double Precio = Double.parseDouble(precio.getText());
  double subtotal = Cantidad * Precio;
  double iva = 0.16*subtotal;
  double total = subtotal + iva;
  
  
    modelo.addColumn("ID");
    modelo.addColumn("Nombre");
    modelo.addColumn("Cantidad");
    modelo.addColumn("Precio unitario");
    tablaVenta.setModel(modelo); 
    
    modelo.addRow(new Object[]{
        id,name, Cantidad, Precio
    });

    }//GEN-LAST:event_jButton1ActionPerformed

    private void NombreProductoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NombreProductoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NombreProductoVentaActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        // TODO add your handling code here:
        int fila = tablaVenta.getSelectedRow();
        if (fila >= 0) {
            model.removeRow(fila);
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Selecciona una tarea para borrar");
        }
    }//GEN-LAST:event_cancelarActionPerformed


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AlmacenPan;
    private javax.swing.JPanel FacturacionPan;
    private javax.swing.JTextField NombreProductoVenta;
    private javax.swing.JPanel RegistrarProductos;
    public static javax.swing.JTabbedPane TabPanel;
    private javax.swing.JPanel VentasPan;
    private javax.swing.JTextField apellidoM;
    private javax.swing.JTextField apellidoMaterno;
    private javax.swing.JTextField apellidoP;
    private javax.swing.JTextField apellidoPaterno;
    private javax.swing.JTextField bp;
    private javax.swing.JButton buscar;
    private javax.swing.JTextField calle;
    private javax.swing.JButton cancelar;
    private javax.swing.JButton cancelar1;
    private javax.swing.JTextField cantidad;
    private javax.swing.JComboBox<String> categoria;
    private javax.swing.JTextField ciudad;
    private javax.swing.JTextField codigoPostal;
    private javax.swing.JTextField colonia;
    private javax.swing.JTextField correo;
    private javax.swing.JTextField date;
    private javax.swing.JTextField date1;
    private javax.swing.JTable datos;
    private javax.swing.JTextField direccion;
    private javax.swing.JButton establest;
    private javax.swing.JTextField estado;
    private javax.swing.JTextField exterior;
    private javax.swing.JComboBox<String> filtros;
    private javax.swing.JButton generar;
    private javax.swing.JButton generar1;
    private javax.swing.JButton generarFactura;
    private javax.swing.JButton guardar;
    private javax.swing.JTextField idCategoria;
    private javax.swing.JTextField idProducto;
    private javax.swing.JTextField idProducts;
    private javax.swing.JTextField idProveedor;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JComboBox<String> metodoPago;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField nombreCliente;
    private javax.swing.JTextField nombreCliente3;
    private javax.swing.JTextField nombreProveedor;
    private javax.swing.JTextField pago;
    private javax.swing.JButton plasticost;
    private javax.swing.JTextField precio;
    private javax.swing.JTextField precio1;
    private javax.swing.JTextField rfc;
    private javax.swing.JTextField stock;
    private javax.swing.JTextField subtotal;
    private javax.swing.JTable tablaFactura;
    private javax.swing.JTable tablaVenta;
    private javax.swing.JTextField tamannio;
    private javax.swing.JTextField telefono;
    private javax.swing.JComboBox<String> tipo;
    private javax.swing.JButton todost;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables
}
