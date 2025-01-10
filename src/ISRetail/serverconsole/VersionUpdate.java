/*
 * VersionUpdate.java
 *
 * Created on December 10, 2008, 3:00 PM
 */

package ISRetail.serverconsole;

import ISRetail.employee.EmployeeMasterDO;
import ISRetail.msdeconnection.MsdeConnection;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;

/**
 *
 * @author  Administrator
 */
public class VersionUpdate extends javax.swing.JPanel {
    
    /** Creates new form VersionUpdate */
    public VersionUpdate() {
        initComponents();
        MsdeConnection msdeconn;
        Connection con;
        EmployeeMasterDO employeeMasterDO;
        String currentposversion;
        String latestposversion;
        try
        {
         msdeconn=new MsdeConnection();
        con=msdeconn.createConnection();
        employeeMasterDO=new EmployeeMasterDO();
        currentposversion=employeeMasterDO.getCurrentVersionofPOS(con);
        currentversionofpos.setText(currentposversion);
        latestposversion=employeeMasterDO.getLatestVersionofPOS(con);
        latestversionofpos.setText(latestposversion);
        
        if(currentposversion!=null&&latestposversion!=null)
        {
        if(currentposversion.equalsIgnoreCase(latestposversion))
        {
        updateversion.setEnabled(false);
        informationtouser.setText("Updation of version is possible only when there is version mismatch");
        }
        else
        {
        updateversion.setEnabled(true);
        }
        
        
        }
        
        }catch(Exception e){
        
        }finally{
         msdeconn=null;
        con=null;
        employeeMasterDO=null;
        currentposversion=null;
        }






    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        currentversionofpos = new javax.swing.JTextField();
        latestversionofpos = new javax.swing.JTextField();
        updateversion = new javax.swing.JButton();
        informationtouser = new javax.swing.JLabel();

        jDesktopPane1.setBackground(new java.awt.Color(153, 204, 255));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel1.setText("Current Version");
        jLabel1.setBounds(130, 180, 130, 16);
        jDesktopPane1.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel2.setText("Latest Version");
        jLabel2.setBounds(130, 240, 110, 14);
        jDesktopPane1.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        currentversionofpos.setBounds(300, 180, 80, 19);
        jDesktopPane1.add(currentversionofpos, javax.swing.JLayeredPane.DEFAULT_LAYER);
        latestversionofpos.setBounds(300, 240, 80, 19);
        jDesktopPane1.add(latestversionofpos, javax.swing.JLayeredPane.DEFAULT_LAYER);

        updateversion.setFont(new java.awt.Font("Verdana", 1, 12));
        updateversion.setText("Update Version");
        updateversion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateversionActionPerformed(evt);
            }
        });
        updateversion.setBounds(180, 310, 150, 25);
        jDesktopPane1.add(updateversion, javax.swing.JLayeredPane.DEFAULT_LAYER);

        informationtouser.setFont(new java.awt.Font("Verdana", 1, 12));
        informationtouser.setBounds(30, 90, 520, 20);
        jDesktopPane1.add(informationtouser, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void updateversionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateversionActionPerformed
        // TODO add your handling code here:
          try
          {
             
             
          URL url = new URL("ftp://172.20.6.73/Lalit/dist.zip;type=i");
          URLConnection uRLConnection = url.openConnection();
          System.out.println("Connection to ftp opened..");
          BufferedInputStream in = new BufferedInputStream(uRLConnection.getInputStream());
          System.out.println("dowloading.......");
          FileOutputStream out = new FileOutputStream("C:\\dist.zip");
          int i = 0;
          byte[] bytesIn = new byte[1024];
          while ((i = in.read(bytesIn)) >= 0) {
	  out.write(bytesIn, 0, i);
          }
          out.close();
          in.close();
          System.out.println("Received");
          }catch(Exception e){
          e.printStackTrace();}

    }//GEN-LAST:event_updateversionActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField currentversionofpos;
    private javax.swing.JLabel informationtouser;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField latestversionofpos;
    private javax.swing.JButton updateversion;
    // End of variables declaration//GEN-END:variables
    
}
