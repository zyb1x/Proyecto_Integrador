/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Ventanas;
import java.util.Date;

/**
 *
 * @author viann
 */
public class Operaciones extends javax.swing.JInternalFrame {
    

    /**
     * Creates new form Operaciones
     */
    public Operaciones() {
        initComponents();
        Date date1= new Date();
        
        date.setText(String.valueOf(date1));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Id = new javax.swing.JTextField();
        cantidad = new javax.swing.JTextField();
        precio = new javax.swing.JTextField();
        iva = new javax.swing.JTextField();
        total = new javax.swing.JTextField();
        cambio = new javax.swing.JTextField();
        pagar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();

        jPanel4.setBackground(new java.awt.Color(10, 54, 86));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(220, 225, 235));
        jLabel3.setText("Precio");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        jLabel4.setFont(new java.awt.Font("Gadugi", 3, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(220, 225, 235));
        jLabel4.setText("Fecha  y hora");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 20, 121, 20));

        jLabel5.setFont(new java.awt.Font("Gadugi", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(220, 225, 235));
        jLabel5.setText("Detalles del producto");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 34, -1, -1));

        jLabel6.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(220, 225, 235));
        jLabel6.setText("Cantidad");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        jLabel8.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(220, 225, 235));
        jLabel8.setText("IVA");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, -1));

        jLabel9.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(220, 225, 235));
        jLabel9.setText("Total a pagar");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 162, -1, -1));

        jLabel10.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(220, 225, 235));
        jLabel10.setText("Metodo de pago");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 260, -1, -1));

        jLabel11.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(220, 225, 235));
        jLabel11.setText("Cambio");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 350, -1, -1));
        jPanel4.add(Id, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 207, -1));
        jPanel4.add(cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 165, -1));
        jPanel4.add(precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 164, -1));
        jPanel4.add(iva, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 168, -1));
        jPanel4.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(612, 206, 182, -1));
        jPanel4.add(cambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 360, 137, -1));

        pagar.setBackground(new java.awt.Color(157, 178, 191));
        pagar.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        pagar.setForeground(new java.awt.Color(10, 54, 86));
        pagar.setText("Pagar");
        jPanel4.add(pagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 480, 137, -1));

        cancelar.setBackground(new java.awt.Color(102, 102, 102));
        cancelar.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        cancelar.setForeground(new java.awt.Color(10, 54, 86));
        cancelar.setText("Cancelar");
        jPanel4.add(cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 480, 113, -1));

        jLabel12.setFont(new java.awt.Font("Gadugi", 1, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(220, 225, 235));
        jLabel12.setText("Información de pago");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 72, -1, -1));

        jLabel14.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(220, 225, 235));
        jLabel14.setText("Nombre Cliente");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));
        jPanel4.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, 240, 30));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Tarjeta", "Efectivo" }));
        jPanel4.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 270, 150, 30));

        jPanel5.setBackground(new java.awt.Color(19, 25, 54));

        jLabel1.setFont(new java.awt.Font("Gadugi", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(220, 225, 235));
        jLabel1.setText("R e g i s t r o  d e  v e n t a");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(345, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(318, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Ventas", jPanel2);

        jPanel3.setBackground(new java.awt.Color(19, 25, 54));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1094, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 673, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Facturación", jPanel1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1094, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 773, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Consultar productos", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Id;
    private javax.swing.JTextField cambio;
    private javax.swing.JButton cancelar;
    private javax.swing.JTextField cantidad;
    private javax.swing.JLabel date;
    private javax.swing.JTextField iva;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton pagar;
    private javax.swing.JTextField precio;
    private javax.swing.JTextField total;
    // End of variables declaration//GEN-END:variables
}
