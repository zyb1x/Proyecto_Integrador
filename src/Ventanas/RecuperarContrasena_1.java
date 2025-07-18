
package Ventanas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javax.mail.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class RecuperarContrasena extends javax.swing.JInternalFrame {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_FROM = "pablovel333@gmail.com"; // Cambiar por tu correo
    private static final String EMAIL_PASSWORD = "Juanpablo0"; // Cambiar por tu contraseña
    
      private static final String DB_URL = "jdbc:mysql://localhost:3306/sistema_recuperacion";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    /**
     * Creates new form RecuperarContraseña
     */
    private final Properties mProperties;
    private Session mSession;
    private MimeMessage MCorreo;
    public RecuperarContrasena() {
        initComponents();
         mProperties = new Properties ();
    }
    
    private void enviarCorreoRecuperacion(String emailDestino) {
    try {
        // 1. Verificar si el correo existe en la base de datos
        if (!existeEmailEnBD(emailDestino)) {
            JOptionPane.showMessageDialog(this, "El correo no está registrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 2. Generar token de recuperación (simplificado)
        String token = generarTokenRecuperacion(emailDestino);
        
        // 3. Configurar propiedades del correo
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        
        // 4. Crear sesión
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
            }
        });
        
        // 5. Crear mensaje
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
        message.setSubject("Recuperación de contraseña");
        
        String contenido = "<h2>Recuperación de contraseña</h2>"
                + "<p>Hemos recibido una solicitud para restablecer tu contraseña.</p>"
                + "<p>Por favor haz clic en el siguiente enlace para crear una nueva contraseña:</p>"
                + "<a href='http://tusitio.com/recuperar?token=" + token + "'>Restablecer contraseña</a>"
                + "<p>Si no solicitaste este cambio, ignora este mensaje.</p>";
        
        message.setContent(contenido, "text/html");
        
        // 6. Enviar correo
        Transport.send(message);
        JOptionPane.showMessageDialog(this, "Se ha enviado un correo con instrucciones", 
                                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al enviar el correo: " + e.getMessage(), 
                                    "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
    /* private void enviarCorreoRecuperacion(String emailDestino) {
    // 1. Verificar si el correo existe en la base de datos
    if (!existeEmailEnBD(emailDestino)) {
    JOptionPane.showMessageDialog(this, "El correo no está registrado", "Error", JOptionPane.ERROR_MESSAGE);
    return;
    }
    // 2. Generar token de recuperación (simplificado)
    String token = generarTokenRecuperacion(emailDestino);
    // 3. Configurar propiedades del correo
    Properties props = new Properties();
    props.put("mail.smtp.host", SMTP_HOST);
    props.put("mail.smtp.port", SMTP_PORT);
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
    // 4. Crear sesión
    Session Session = mSession.getDefaultInstance(mProperties);
    Session = Session.getInstance(props, new javax.mail.Authenticator() {
    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
    return new javax.mail.PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
    }
    });
    // 5. Crear mensaje
    MimeMessage MCorreo;
    MCorreo = new MimeMessage(session);
    message.setFrom(new InternetAddress(EMAIL_FROM));
    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
    message.setSubject("Recuperación de contraseña");
    String contenido = "<h2>Recuperación de contraseña</h2>"
    + "<p>Hemos recibido una solicitud para restablecer tu contraseña.</p>"
    + "<p>Por favor haz clic en el siguiente enlace para crear una nueva contraseña:</p>"
    + "<a href='http://tusitio.com/recuperar?token=" + token + "'>Restablecer contraseña</a>"
    + "<p>Si no solicitaste este cambio, ignora este mensaje.</p>";
    message.setContent(contenido, "text/html");
    // 6. Enviar correo
    Transport.send(message);
    JOptionPane.showMessageDialog(this, "Se ha enviado un correo con instrucciones", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }*/
      private boolean existeEmailEnBD(String email) {
        String query = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RecuperarContrasena.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
       private String generarTokenRecuperacion(String email) {
        // En una implementación real, esto generaría un token seguro y lo guardaría en la BD
        // con una fecha de expiración
        return java.util.UUID.randomUUID().toString();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        session = new javax.swing.JTextField();
        recuperar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(19, 25, 54));

        jLabel2.setBackground(new java.awt.Color(255, 255, 204));
        jLabel2.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(189, 194, 199));
        jLabel2.setText("R E C U P E R A R   C O N T R A S E Ñ A");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addContainerGap(438, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 470, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(10, 54, 86));

        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(11, 54, 110));

        jLabel3.setBackground(new java.awt.Color(51, 51, 51));
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Por favor escriba su nombre de usuario o correo electrónico. ");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Recibirá un enlace para crear la nueva contraseña ");

        jPanel5.setBackground(new java.awt.Color(10, 54, 150));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(322, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.setBackground(new java.awt.Color(11, 54, 110));
        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        session.setForeground(new java.awt.Color(204, 204, 204));
        session.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sessionActionPerformed(evt);
            }
        });

        recuperar.setBackground(new java.awt.Color(11, 54, 120));
        recuperar.setForeground(new java.awt.Color(204, 204, 204));
        recuperar.setText("Recuperar contraseña");
        recuperar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recuperarActionPerformed(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Correo electronico:");

        jButton3.setBackground(new java.awt.Color(11, 54, 120));
        jButton3.setForeground(new java.awt.Color(204, 204, 204));
        jButton3.setText("Olvidalo, la he recordado");
        jButton3.setBorder(null);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(recuperar)
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jButton3)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(session, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addGap(9, 9, 9)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(session, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(26, 26, 26)
                .addComponent(recuperar)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(199, 199, 199))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(201, 201, 201)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(jLabel7)
                .addGap(507, 507, 507)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sessionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sessionActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void recuperarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recuperarActionPerformed
   ContrasenaNueva N = new ContrasenaNueva();
          String email = session.getText().trim();
         if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese su correo electrónico", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
             }
        enviarCorreoRecuperacion(email);
   N.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_recuperarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JButton recuperar;
    private javax.swing.JTextField session;
    // End of variables declaration//GEN-END:variables

    private static class MessagingException {

        public MessagingException() {
        }

        private String getMessage() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}
