/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Main.login;
import config.config;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class adminProduct extends javax.swing.JFrame {

    private String imagePath = "";
    private int selectedId = -1; 
    
    public adminProduct() {
        initComponents();
        setupImagePreview(); 
        getData();
        setupTableClick(); 
        setupButtons();   
    }
     private void setupButtons() {
        // ✅ connect update and delete buttons
        updateBtn.addActionListener(e -> updateProduct());
        deleteBtn.addActionListener(e -> deleteProduct());
    }
    
    public void getData(){
        config con = new config();
        String sql = "SELECT * FROM tbl_product";
        con.displayData(sql, productTable);
        
    }
    
     // ✅ put a JLabel inside jPanel7 for image preview
    private void setupImagePreview() {
        imagePreviewLabel = new JLabel();
        imagePreviewLabel.setBounds(0, 0, jPanel7.getWidth(), jPanel7.getHeight());
        imagePreviewLabel.setHorizontalAlignment(JLabel.CENTER);
        jPanel7.setLayout(null);
        jPanel7.add(imagePreviewLabel);
    }
    
    
    private void showImagePreview(String path) {
        try {
            if (path == null || path.trim().isEmpty()) {
                imagePreviewLabel.setIcon(null);
                return;
            }
            File f = new File(path);
            if (!f.exists()) {
                imagePreviewLabel.setIcon(null);
                return;
            }

            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage().getScaledInstance(
                    jPanel7.getWidth(),
                    jPanel7.getHeight(),
                    Image.SCALE_SMOOTH
            );
            imagePreviewLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imagePreviewLabel.setIcon(null);
        }
    }
    
    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();

            // ✅ IMPORTANT: save to class variable (NOT local)
            this.imagePath = file.getAbsolutePath();

            ImageIcon icon = new ImageIcon(this.imagePath);
            Image img = icon.getImage().getScaledInstance(
                    jPanel7.getWidth(),
                    jPanel7.getHeight(),
                    Image.SCALE_SMOOTH
            );

            imagePreviewLabel.setIcon(new ImageIcon(img));
        }
    }
    
    private void setupTableClick() {
        productTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = productTable.getSelectedRow();
                if (row < 0) return;

                // Column order from getData():
                // 0 p_id, 1 p_name, 2 p_price, 3 p_stock, 4 p_category, 5 p_status, 6 p_image
                selectedId = Integer.parseInt(productTable.getValueAt(row, 0).toString());

                pName.setText(productTable.getValueAt(row, 1).toString());
                pPrice.setText(productTable.getValueAt(row, 2).toString());
                pStock.setText(productTable.getValueAt(row, 3).toString());
                pCat.setText(productTable.getValueAt(row, 4).toString());

                Object img = productTable.getValueAt(row, 6);
                imagePath = (img == null) ? "" : img.toString();

                showImagePreview(imagePath);
            }
        });
    }
    
    private boolean validateInputs(boolean par) {
        if (pName.getText().trim().isEmpty()
                || pPrice.getText().trim().isEmpty()
                || pStock.getText().trim().isEmpty()
                || pCat.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill up all fields!");
            return false;
        }

        // require image
        if (imagePath == null || imagePath.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please choose an image first!");
            return false;
        }

        // numeric validation
        try {
            double price = Double.parseDouble(pPrice.getText().trim());
            if (price <= 0) {
                JOptionPane.showMessageDialog(this, "Price must be greater than 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Price! Please enter a number.");
            return false;
        }

        try {
            int stock = Integer.parseInt(pStock.getText().trim());
            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "Stock cannot be negative.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Stock! Please enter a whole number.");
            return false;
        }

        return true;
    }
    
    private void updateProduct() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Select a product from table first!");
            return;
        }

        // image optional on update (keep old if not changed)
        if (!validateInputs(false)) return;

        config con = new config();
        String sql = "UPDATE tbl_product SET p_name=?, p_price=?, p_stock=?, p_category=?, p_image=? WHERE p_id=?";

        con.updateRecord(sql,
                pName.getText().trim(),
                Double.parseDouble(pPrice.getText().trim()),
                Integer.parseInt(pStock.getText().trim()),
                pCat.getText().trim(),
                imagePath,
                selectedId
        );

        JOptionPane.showMessageDialog(this, "Product updated successfully!");
        getData();
        clearBtnActionPerformed(null);
    }
    
    private void deleteProduct() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Select a product from table first!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this product?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        config con = new config();
        String sql = "DELETE FROM tbl_product WHERE p_id=?";

        con.deleteRecord(sql, selectedId);

        JOptionPane.showMessageDialog(this, "Product deleted successfully!");
        getData();
        clearBtnActionPerformed(null);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        userBtn = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        userBtn1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        userBtn2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        saveBtn = new java.awt.Button();
        updateBtn = new java.awt.Button();
        deleteBtn = new java.awt.Button();
        clearBtn = new java.awt.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();
        pName = new javax.swing.JTextField();
        pPrice = new javax.swing.JTextField();
        pStock = new javax.swing.JTextField();
        pCat = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        imagePreviewLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 102, 102));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/image1.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 130, 110));
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userBtn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        userBtn.setText("Users");
        userBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userBtnMouseClicked(evt);
            }
        });
        jPanel1.add(userBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 40, -1));

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 100, 40));

        jPanel5.setBackground(new java.awt.Color(255, 102, 102));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel5MouseEntered(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("Dashboard");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 70, -1));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 100, 40));

        jPanel6.setBackground(new java.awt.Color(255, 102, 102));
        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Logout");
        jPanel6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 100, 40));

        jPanel8.setBackground(new java.awt.Color(255, 102, 102));
        jPanel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userBtn1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        userBtn1.setText("Product");
        userBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userBtn1MouseClicked(evt);
            }
        });
        jPanel8.add(userBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 50, -1));

        jPanel3.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 100, 40));

        jPanel9.setBackground(new java.awt.Color(255, 102, 102));
        jPanel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userBtn2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        userBtn2.setText("Orders");
        userBtn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userBtn2MouseClicked(evt);
            }
        });
        jPanel9.add(userBtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 50, -1));

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 100, 40));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 500));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel3.setText("Product Dashboard");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, -1));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Dashboard");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 70, 20));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 100, 40));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("Category:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 80, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel9.setText(" Name: ");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, -1, -1));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel10.setText("Price:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 50, -1));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("Stock:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 50, -1));

        saveBtn.setLabel("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        jPanel2.add(saveBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 270, 60, 30));

        updateBtn.setLabel("Update");
        jPanel2.add(updateBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, 60, 30));

        deleteBtn.setLabel("Delete");
        jPanel2.add(deleteBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 270, 60, 30));

        clearBtn.setLabel("Clear");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });
        jPanel2.add(clearBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, 60, 30));

        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(productTable);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, 550, 170));
        jPanel2.add(pName, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, 180, 30));
        jPanel2.add(pPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 180, 30));
        jPanel2.add(pStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, 180, 30));
        jPanel2.add(pCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 180, 30));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel7.add(imagePreviewLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 120));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, 140, 120));

        jButton1.setText("Choose Image");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 210, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void userBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userBtnMouseClicked
        admimUser au = new admimUser();
        au.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_userBtnMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jPanel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseEntered

    }//GEN-LAST:event_jPanel5MouseEntered

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        login log = new login();
        log.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void userBtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userBtn1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_userBtn1MouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel8MouseClicked

    private void userBtn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userBtn2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_userBtn2MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel9MouseClicked

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        if (pName.getText().trim().isEmpty() ||
            pPrice.getText().trim().isEmpty() ||
            pStock.getText().trim().isEmpty() ||
            pCat.getText().trim().isEmpty()) {

            javax.swing.JOptionPane.showMessageDialog(this, "Please fill up all fields!");
            return;
        }

        // ✅ numeric validation
        double price;
        int stock;

        try {
            price = Double.parseDouble(pPrice.getText().trim());
            if (price <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Price must be greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Invalid Price! Please enter a number.");
            return;
        }

        try {
            stock = Integer.parseInt(pStock.getText().trim());
            if (stock < 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Stock cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Invalid Stock! Please enter a whole number.");
            return;
        }

        // ✅ insert
        config con = new config();
        String sql = "INSERT INTO tbl_product (p_name, p_price, p_stock, p_category, p_status, p_image) VALUES (?, ?, ?, ?, ?, ?)";

        con.addRecord(sql,
                pName.getText().trim(),
                String.valueOf(price),          // ensure valid number
                String.valueOf(stock),          // ensure valid integer
                pCat.getText().trim(),
                "Available",
                imagePath
        );

        getData();
        clearBtnActionPerformed(null);

    javax.swing.JOptionPane.showMessageDialog(this, "Product saved successfully!");
    }//GEN-LAST:event_saveBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         chooseImage();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
       pName.setText("");
        pPrice.setText("");
        pStock.setText("");
        pCat.setText("");
        imagePath = "";
        imagePreviewLabel.setIcon(null);
    }//GEN-LAST:event_clearBtnActionPerformed

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
            java.util.logging.Logger.getLogger(adminProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button clearBtn;
    private java.awt.Button deleteBtn;
    private javax.swing.JLabel imagePreviewLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField pCat;
    private javax.swing.JTextField pName;
    private javax.swing.JTextField pPrice;
    private javax.swing.JTextField pStock;
    private javax.swing.JTable productTable;
    private java.awt.Button saveBtn;
    private java.awt.Button updateBtn;
    private javax.swing.JLabel userBtn;
    private javax.swing.JLabel userBtn1;
    private javax.swing.JLabel userBtn2;
    // End of variables declaration//GEN-END:variables
}
