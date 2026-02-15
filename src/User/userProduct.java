/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Main.login;
import config.config;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Nino
 */
public class userProduct extends javax.swing.JFrame {

    private JPanel productsGrid;
    private JScrollPane productsScroll;
    
    
    public userProduct() {
        initComponents();

        // Hide template card from GUI builder
        cardPanel.setVisible(false);

        setupProductsGrid();
        loadProducts("");

        // Search button
        searchBtn.addActionListener(e -> searchProducts());

        // Enter key in search field
        SearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchProducts();
                }
            }
        });

        // Clear placeholder
        SearchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SearchField.getText().trim().equalsIgnoreCase("Search")) {
                    SearchField.setText("");
                }
            }
        });
        
    }
    
    private void searchProducts() {
        String keyword = SearchField.getText().trim();
        if (keyword.equalsIgnoreCase("Search")) keyword = "";
        loadProducts(keyword);
    }
     
     private void setupProductsGrid() {
        productsGrid = new JPanel();
        productsGrid.setBackground(Color.WHITE);
        productsGrid.setLayout(new GridLayout(0, 3, 15, 15));
        productsGrid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        productsScroll = new JScrollPane(productsGrid);
        productsScroll.setBorder(null);
        productsScroll.getVerticalScrollBar().setUnitIncrement(16);

        // ✅ IMPORTANT: Because jPanel2 uses AbsoluteLayout, use AbsoluteConstraints
        jPanel2.add(productsScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 810, 280));
    }
     
    private void loadProducts(String keyword) {
        productsGrid.removeAll();

        String sql =
            "SELECT p_id, p_name, p_price, p_stock, p_category, p_image " +
            "FROM tbl_product " +
            "WHERE p_status='Available' " +
            "AND (p_name LIKE ? OR p_category LIKE ?) " +
            "ORDER BY p_id DESC";

        try (Connection conn = config.connectDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            String like = "%" + keyword + "%";
            pst.setString(1, like);
            pst.setString(2, like);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("p_id");
                    String name = rs.getString("p_name");
                    double price = rs.getDouble("p_price");
                    int stock = rs.getInt("p_stock");
                    String imagePath = rs.getString("p_image");

                    productsGrid.add(createProductCard(id, name, price, stock, imagePath));
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load products error: " + e.getMessage());
            e.printStackTrace();
        }

        productsGrid.revalidate();
        productsGrid.repaint();
    }

      
    private JPanel createProductCard(int id, String name, double price, int stock, String imagePath) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        card.setPreferredSize(new Dimension(240, 260));

        // Image
        JLabel img = new JLabel();
        img.setHorizontalAlignment(JLabel.CENTER);
        img.setPreferredSize(new Dimension(240, 140));

        ImageIcon icon = loadAndScaleImage(imagePath, 200, 130);
        if (icon != null) {
            img.setIcon(icon);
        } else {
            img.setText("No Image");
        }

        // Info
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.WHITE);
        info.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));

        JLabel priceLabel = new JLabel("₱ " + String.format("%.2f", price));
        priceLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        priceLabel.setForeground(new Color(255, 51, 51));

        JLabel stockLabel = new JLabel("Stock: " + stock);
        stockLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));

        info.add(nameLabel);
        info.add(Box.createVerticalStrut(4));
        info.add(priceLabel);
        info.add(Box.createVerticalStrut(4));
        info.add(stockLabel);

        // Buttons
        JPanel btns = new JPanel(new GridLayout(1, 2, 8, 0));
        btns.setBackground(Color.WHITE);
        btns.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton addBtn = new JButton("Add to Cart");
        JButton viewBtn = new JButton("View");

        addBtn.setEnabled(stock > 0);

        addBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, name + " added to cart!");
        });

        viewBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Product: " + name +
                "\nPrice: ₱ " + String.format("%.2f", price) +
                "\nStock: " + stock
            );
        });

        btns.add(addBtn);
        btns.add(viewBtn);

        card.add(img, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);
        card.add(btns, BorderLayout.SOUTH);

        return card;
    }
      
    private ImageIcon loadAndScaleImage(String path, int w, int h) {
        try {
            if (path == null || path.trim().isEmpty()) return null;
            File f = new File(path);
            if (!f.exists()) return null;

            ImageIcon raw = new ImageIcon(path);
            Image scaled = raw.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            return null;
        }
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        SearchField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        searchBtn = new javax.swing.JButton();
        cartBtn = new javax.swing.JButton();
        cardPanel = new javax.swing.JPanel();
        imgLabel = new javax.swing.JLabel();
        pNameLabel = new javax.swing.JLabel();
        pPriceLabel = new javax.swing.JLabel();
        pStockLabel = new javax.swing.JLabel();
        AddToCartLabel = new javax.swing.JLabel();
        viewLabel = new javax.swing.JLabel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SearchField.setText("Search");
        jPanel2.add(SearchField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 200, 40));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setText("Product");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 4, 110, 30));

        searchBtn.setText("Search");
        jPanel2.add(searchBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, -1, 40));

        cartBtn.setText("Cart");
        jPanel2.add(cartBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, -1, 40));

        cardPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        cardPanel.add(imgLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 100));

        pNameLabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cardPanel.add(pNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 80, 20));

        pPriceLabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cardPanel.add(pPriceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 70, 20));

        pStockLabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cardPanel.add(pStockLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 60, 20));

        AddToCartLabel.setText("Add to Cart");
        cardPanel.add(AddToCartLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, 20));

        viewLabel.setText("View");
        cardPanel.add(viewLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 40, 20));

        jPanel2.add(cardPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 180, 230));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 850, 410));

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

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 90));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void panelHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHomeMouseClicked
        userDashboard ud = new userDashboard();
        ud.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_panelHomeMouseClicked

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

    private void panelProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelProfileMouseClicked
        userProfile up = new userProfile();
        up.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_panelProfileMouseClicked

    private void panelProfileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelProfileMouseEntered
        panelProfile.setBackground(new java.awt.Color(0, 153, 255));
    }//GEN-LAST:event_panelProfileMouseEntered

    private void panelProfileMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelProfileMouseExited
        panelProfile.setBackground(new java.awt.Color(255,102,102));
    }//GEN-LAST:event_panelProfileMouseExited

    private void panelLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLogoutMouseClicked
        login log = new login();
        log.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_panelLogoutMouseClicked

    private void panelLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLogoutMouseEntered
        panelLogout.setBackground(new java.awt.Color(0, 153, 255));
    }//GEN-LAST:event_panelLogoutMouseEntered

    private void panelLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLogoutMouseExited
        panelLogout.setBackground(new java.awt.Color(255,102,102));
    }//GEN-LAST:event_panelLogoutMouseExited

    private void panelProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelProductMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelProductMouseClicked

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
            java.util.logging.Logger.getLogger(userProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(userProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(userProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(userProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new userProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AddToCartLabel;
    private javax.swing.JTextField SearchField;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JButton cartBtn;
    private javax.swing.JLabel imgLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel pNameLabel;
    private javax.swing.JLabel pPriceLabel;
    private javax.swing.JLabel pStockLabel;
    private javax.swing.JPanel panelHome;
    private javax.swing.JPanel panelLogout;
    private javax.swing.JPanel panelProduct;
    private javax.swing.JPanel panelProfile;
    private javax.swing.JButton searchBtn;
    private javax.swing.JLabel viewLabel;
    // End of variables declaration//GEN-END:variables
}
