/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Main.login;
import config.Session;
import config.config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Nino
 */
public class userProfile extends javax.swing.JFrame {

    /**
     * Creates new form userProfile
     */
    public userProfile() {
        initComponents();
        loadUserProfile();
    }
    
    private void loadUserProfile() {
        String sql = "SELECT u_fname, u_email, u_role FROM tbl_user WHERE u_uname = ?";
        try (Connection conn = config.connectDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, Session.getUsername()); // get logged-in username
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lbNameText.setText(rs.getString("u_fname"));
                lbEmailText.setText(rs.getString("u_email"));
                lbRoleText.setText(rs.getString("u_role"));
            } else {
                JOptionPane.showMessageDialog(this, "User not found!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading profile: " + e.getMessage());
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelHome = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panelProduct = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelProfile = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        panelLogout = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lbName = new javax.swing.JLabel();
        lbEmail = new javax.swing.JLabel();
        lbRole = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lbNameText = new javax.swing.JLabel();
        lbRoleText = new javax.swing.JLabel();
        lbEmailText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Picture2.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, -1, -1));

        panelHome.setBackground(new java.awt.Color(255, 102, 102));
        panelHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelHomeMouseExited(evt);
            }
        });
        panelHome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("Home");
        panelHome.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));

        jPanel1.add(panelHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 90, 40));

        panelProduct.setBackground(new java.awt.Color(255, 102, 102));
        panelProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelProductMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelProductMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelProductMouseExited(evt);
            }
        });
        panelProduct.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Product");
        panelProduct.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel1.add(panelProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 90, 40));

        panelProfile.setBackground(new java.awt.Color(255, 102, 102));
        panelProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelProfileMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelProfileMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelProfileMouseExited(evt);
            }
        });
        panelProfile.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("Profile");
        panelProfile.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel1.add(panelProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 90, 40));

        panelLogout.setBackground(new java.awt.Color(255, 102, 102));
        panelLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelLogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelLogoutMouseExited(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setText("Logout");
        panelLogout.add(jLabel6);

        jPanel1.add(panelLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 30, 90, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 90));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-profile-100.png"))); // NOI18N
        jLabel7.setText("jLabel7");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 100, 100));

        lbName.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbName.setText("Name:");
        jPanel2.add(lbName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, -1, -1));

        lbEmail.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbEmail.setText("Email: ");
        jPanel2.add(lbEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, -1, -1));

        lbRole.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        lbRole.setText("Role:");
        jPanel2.add(lbRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, -1, -1));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbNameText.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jPanel3.add(lbNameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, 20));

        lbRoleText.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jPanel3.add(lbRoleText, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 150, 20));

        lbEmailText.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jPanel3.add(lbEmailText, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 250, 20));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 280, 100));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 800, 410));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void panelHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHomeMouseEntered
        panelHome.setBackground(new java.awt.Color(0, 153, 255)); // Hover text color (blue)
        // jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_panelHomeMouseEntered

    private void panelHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHomeMouseExited
        panelHome.setBackground(new java.awt.Color(255,102,102));

    }//GEN-LAST:event_panelHomeMouseExited

    private void panelProductMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelProductMouseEntered
        panelProduct.setBackground(new java.awt.Color(0, 153, 255));
    }//GEN-LAST:event_panelProductMouseEntered

    private void panelProductMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelProductMouseExited
        panelProduct.setBackground(new java.awt.Color(255,102,102));
    }//GEN-LAST:event_panelProductMouseExited

    private void panelProfileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelProfileMouseEntered
        panelProfile.setBackground(new java.awt.Color(0, 153, 255));
    }//GEN-LAST:event_panelProfileMouseEntered

    private void panelProfileMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelProfileMouseExited
        panelProfile.setBackground(new java.awt.Color(255,102,102));
    }//GEN-LAST:event_panelProfileMouseExited

    private void panelLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLogoutMouseEntered
        panelLogout.setBackground(new java.awt.Color(0, 153, 255));
    }//GEN-LAST:event_panelLogoutMouseEntered

    private void panelLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLogoutMouseExited
        panelLogout.setBackground(new java.awt.Color(255,102,102));
    }//GEN-LAST:event_panelLogoutMouseExited

    private void panelHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHomeMouseClicked
        userDashboard ud = new userDashboard();
        ud.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_panelHomeMouseClicked

    private void panelProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelProfileMouseClicked
        userProfile up = new userProfile();
        up.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_panelProfileMouseClicked

    private void panelLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLogoutMouseClicked
        login log = new login();
        log.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_panelLogoutMouseClicked

    private void panelProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelProductMouseClicked
        userProduct upr = new userProduct();
        upr.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_panelProductMouseClicked
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new userProfile().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbEmailText;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbNameText;
    private javax.swing.JLabel lbRole;
    private javax.swing.JLabel lbRoleText;
    private javax.swing.JPanel panelHome;
    private javax.swing.JPanel panelLogout;
    private javax.swing.JPanel panelProduct;
    private javax.swing.JPanel panelProfile;
    // End of variables declaration//GEN-END:variables
}
