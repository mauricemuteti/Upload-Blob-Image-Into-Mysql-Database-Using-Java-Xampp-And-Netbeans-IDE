/*
 * Upload Blob Image Into Mysql Database Using Java, Xampp And Netbeans IDE
 */
package uploadandinsertimagetodatabaseasablob;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Genuine
 */
public class uploadandinsertimagetodatabaseasablob extends javax.swing.JFrame {

    /**
     * Creates new form uploadandinsertimagetodatabaseasablob
     */
    public uploadandinsertimagetodatabaseasablob() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));

        jButton1.setText("Upload");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Load ///");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    //Declaring variables
    String timeZone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String ServerUrl = "jdbc:mysql://localhost:3306";
    String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    String username = "root";
    String password = "";
    Connection conn = null;
    String databaseName = "InsertImageIntoMysqlDatabaseJava";
    String checkDatabase = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + databaseName + "' ";
    String UploadImageIntoMysqlDatabase = "INSERT INTO `blobimagestable`(`ImageName`, `ImagPath`, `ImageFile`) VALUES (?,?,?)";
    String createNewDatabase = "CREATE DATABASE IF NOT EXISTS `" + databaseName + "`";
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    boolean databaseFound = false;
    File f = null;
    String path = null;
    String createTableSQL = " "
            + "CREATE TABLE `blobimagestable` (\n"
            + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
            + "  `ImageName` varchar(200) NOT NULL,\n"
            + "  `ImagPath` varchar(200) NOT NULL,\n"
            + "  `ImageFile` longblob NOT NULL,\n"
            + "  PRIMARY KEY (`Id`)\n"
            + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {

            Class.forName(jdbcDriver);
            System.out.println("Driver loaded");
            //connecting to xampp localhost to check whether the database exists. 
            conn = DriverManager.getConnection(ServerUrl + timeZone, username, password);
            System.out.println("Connected to server");
            stmt = conn.createStatement();
            System.out.println(checkDatabase);
            rs = stmt.executeQuery(checkDatabase);
            if (rs.next()) {
                System.out.println("Database Found...!!! ");
                databaseFound = true;
                //Choosing existing database.
                conn = DriverManager.getConnection(ServerUrl + "/" + databaseName + timeZone, username, password);
                insertImageIntoDatabase();
            }

            if (!databaseFound) {
                System.out.println("db status - " + databaseFound);
                System.out.println("Database Not Found......!!!");
                System.out.println("Creating New Database - " + databaseName);
                System.out.println(createNewDatabase);
                int newDatabase = stmt.executeUpdate(createNewDatabase);
                if (newDatabase > 0) {
                    System.out.println(databaseName + " Database Created...!!!");
                    //Select newly created database
                    conn = DriverManager.getConnection(ServerUrl + "/" + databaseName + timeZone, username, password);
                    System.out.println("Creating new table.....");
                    stmt = conn.createStatement();
                    System.out.println(createTableSQL);
                    int newTableCreated = stmt.executeUpdate(createTableSQL);
                    System.out.println("Table Created Successfully");
                    //Function for inserting image into the database
                    insertImageIntoDatabase();
                }
            }

        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Failed to load");
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
//            System.out.println("Failed to connect to server");
            System.out.println(ex.getMessage());
        }

    }//GEN-LAST:event_jButton1ActionPerformed
    public void insertImageIntoDatabase() {
        System.out.println("Image Path - " + path);
        System.out.println("Image Name - " + f.getName());
        File f = new File(path);
        try {
            InputStream is = new FileInputStream(f);
            pstmt = conn.prepareStatement(UploadImageIntoMysqlDatabase);
            pstmt.setString(1, f.getName());
            pstmt.setString(2, path);
            pstmt.setBlob(3, is);
            int inserted = pstmt.executeUpdate();

            if (inserted > 0) {
                JOptionPane.showMessageDialog(null, "Image Inserted");
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        //Only search for images with these extensions.
        FileNameExtensionFilter fnwf = new FileNameExtensionFilter("PNG JPG AND JPEG", "png", "jpeg", "jpg");
        fileChooser.addChoosableFileFilter(fnwf);
        int load = fileChooser.showOpenDialog(null);
        //If open button has being clicked.
        if (load == fileChooser.APPROVE_OPTION) {
            f = fileChooser.getSelectedFile();
            //Store image path in a string.
            path = f.getAbsolutePath();
            //Show image path in the textfield.
            jTextField1.setText(path);
            ImageIcon ii = new ImageIcon(path);
            //Resize image to fit jlabel.
            Image img = ii.getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH);
            //display image on jpanel
            jLabel1.setIcon(new ImageIcon(img));
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(uploadandinsertimagetodatabaseasablob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(uploadandinsertimagetodatabaseasablob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(uploadandinsertimagetodatabaseasablob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(uploadandinsertimagetodatabaseasablob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new uploadandinsertimagetodatabaseasablob().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
