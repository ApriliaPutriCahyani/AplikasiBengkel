/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasibengkelaprilia;

import com.mysql.jdbc.Connection;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Aprilia
 */

public class TransaksiBengkelAprilia extends javax.swing.JDialog {

    /**
     * Creates new form TransaksiBengkelAprilia
     */
    Koneksi con;
    String kd_sp;
//    static Statement stmt;
//    static ResultSet rs;
    
    public TransaksiBengkelAprilia(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        con = new Koneksi();
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2);
        autoNumber();
        listSparePart();
//        inisial();
    }

    public void autoNumber(){
        String noServices = "";
        int i = 0;
        try{
            Connection con2 = con.openConnect();
            Statement st = con2.createStatement();
            String sql = "select no_services from transaksi";
            ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    noServices = rs.getString("no_services");
                }
            noServices = noServices.substring(3);
            i = Integer.parseInt(noServices)+1;
            noServices = "00"+i;
            noServices = "SER"+noServices.substring(noServices.length()-3);
            txtNoServies.setText(noServices);
        }catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
    }
    
    public void listSparePart(){
        try{
            Connection con2 = con.openConnect();
            Statement st = con2.createStatement();
            String sql = "select name_spare from m_spareparts";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                slcSpareParts.addItem(rs.getString("name_spare"));
            }
            con2.close();
            st.close();
        }catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
    }
    
    public void getPrice(){
        try{
            Connection con2 = con.openConnect();
            Statement st = con2.createStatement();
            String sql = "select * from m_spareparts where name_spare = '"+slcSpareParts.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                txtHarga.setText(rs.getString("harga"));
                kd_sp = rs.getString("kode");
            }
            con2.close();
            st.close();
        }catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
    }
    
    public void inisial(){
        btnAdd.setEnabled(false);
        btnEdit.setEnabled(false);
        btnDel.setEnabled(false);
    }
    
    public void insert(){
        int discount = 0;
        int total = 0;
        
        if(radio5.isSelected()){
            discount=5;
        }
        if(radio10.isSelected()){
            discount=10;
        }
        if(radio15.isSelected()){
            discount=15;
        }
        
        total = Integer.parseInt(txtHarga.getText())*Integer.parseInt(txtJumlah.getText());
        total = total - (total * discount/100);
        try{
            Connection con2 = con.openConnect();
            Statement st = con2.createStatement();
            String sql = "insert into transaksi values('"+txtNoServies.getText()+"','"+kd_sp+"','"+txtJumlah.getText()+"','"+discount+"','"+total+"')";
            int success = st.executeUpdate(sql);
            if(success > 0){
                JOptionPane.showMessageDialog(rootPane, "Berhasil");
            }else{
                JOptionPane.showMessageDialog(rootPane, "Gagal");
            }
            con2.close();
            st.close();
        }catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
    }
    
    public void update(){
        int discount = 0;
        int total = 0;
        
        if(radio5.isSelected()){
            discount=5;
        }
        if(radio10.isSelected()){
            discount=10;
        }
        if(radio15.isSelected()){
            discount=15;
        }
        
        total = Integer.parseInt(txtHarga.getText())*Integer.parseInt(txtJumlah.getText());
        total = total - (total * discount/100);
        try{
            Connection con2 = con.openConnect();
            Statement st = con2.createStatement();
            String sql = "update transaksi set jml_item='"+txtJumlah.getText()+"', discount = '"+discount+"', jml_bayar = '"+total+"' where no_services = '"+txtNoServies.getText()+"' and kode_spare = '"+kd_sp+"'";
            int success = st.executeUpdate(sql);
            if(success > 0){
                JOptionPane.showMessageDialog(rootPane, "Berhasil");
            }else{
                JOptionPane.showMessageDialog(rootPane, "Gagal");
            }
            con2.close();
            st.close();
        }catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
    }
    
    public void search(){
        try{
            Connection con2 = con.openConnect();
            Statement st = con2.createStatement();
            String sql = "select * from transaksi where no_services = '"+txtNoServies.getText()+"' and kode_spare = '"+kd_sp+"'";
            ResultSet rs = st.executeQuery(sql);
                if(rs.next()){
                    txtJumlah.setText(rs.getString("jml_item"));
                    if(rs.getString("discount") == "5"){
                        radio5.setSelected(true);
                    }
                    if(rs.getString("discount") == "10"){
                        radio10.setSelected(true);
                    }
                    if(rs.getString("discount") == "15"){
                        radio15.setSelected(true);
                    }
                }
            con2.close();
            st.close();
        }catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
    }
    
    public void delete(){
         try{
            Connection con2 = con.openConnect();
            Statement st = con2.createStatement();
            String sql = "delete from transaksi where no_services = '"+txtNoServies.getText()+"' and kode_spare = '"+kd_sp+"'";
            int success = st.executeUpdate(sql);
            if(success > 0){
                JOptionPane.showMessageDialog(rootPane, "Berhasil");
                clean();
                inisial();
            }else{
                JOptionPane.showMessageDialog(rootPane, "Gagal");
            }
            con2.close();
            st.close();
        }catch(SQLException e){
            System.out.println("Error : "+e.getMessage());
        }
    }
    
    public void clean(){
        txtJumlah.setText("");
//        txtHarga.setText("");
        radio5.setSelected(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNoServies = new javax.swing.JTextField();
        btnCreate = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        slcSpareParts = new javax.swing.JComboBox<>();
        radio15 = new javax.swing.JRadioButton();
        radio5 = new javax.swing.JRadioButton();
        radio10 = new javax.swing.JRadioButton();
        btnExit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(500, 500));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/gambar logo.png"))); // NOI18N
        jLabel1.setText("Bengkel Aprilia");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("No Services");

        txtNoServies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoServiesActionPerformed(evt);
            }
        });

        btnCreate.setText("Buat Baru");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Spare Parts");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Harga");

        txtHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Jumlah");

        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Discount");

        slcSpareParts.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Item" }));
        slcSpareParts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slcSparePartsActionPerformed(evt);
            }
        });

        buttonGroup1.add(radio15);
        radio15.setText("15%");
        radio15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio15ActionPerformed(evt);
            }
        });

        buttonGroup1.add(radio5);
        radio5.setText("5%");
        radio5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio5ActionPerformed(evt);
            }
        });

        buttonGroup1.add(radio10);
        radio10.setText("10%");
        radio10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio10ActionPerformed(evt);
            }
        });

        btnExit.setText("Keluar");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnAdd.setText("Tambah");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setText("Ubah");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDel.setText("Hapus");
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(17, 17, 17)
                        .addComponent(txtNoServies)
                        .addGap(20, 20, 20)
                        .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(slcSpareParts, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(radio5)
                                .addGap(7, 7, 7)
                                .addComponent(radio10)
                                .addGap(1, 1, 1)
                                .addComponent(radio15)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtHarga)
                            .addComponent(txtJumlah)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExit)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(41, 41, 41))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNoServies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreate))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slcSpareParts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radio15)
                    .addComponent(radio5)
                    .addComponent(radio10))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit)
                    .addComponent(btnDel)
                    .addComponent(btnExit))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNoServiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoServiesActionPerformed
        // TODO add your handling code here:
        search();
    }//GEN-LAST:event_txtNoServiesActionPerformed

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaActionPerformed

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void radio15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radio15ActionPerformed

    private void radio5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radio5ActionPerformed

    private void radio10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radio10ActionPerformed

    private void slcSparePartsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slcSparePartsActionPerformed
        // TODO add your handling code here:
        getPrice();
        search();
    }//GEN-LAST:event_slcSparePartsActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnDelActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        // TODO add your handling code here:
        autoNumber();
        clean();
//        inisial();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

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
            java.util.logging.Logger.getLogger(TransaksiBengkelAprilia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransaksiBengkelAprilia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransaksiBengkelAprilia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransaksiBengkelAprilia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TransaksiBengkelAprilia dialog = new TransaksiBengkelAprilia(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton radio10;
    private javax.swing.JRadioButton radio15;
    private javax.swing.JRadioButton radio5;
    private javax.swing.JComboBox<String> slcSpareParts;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtNoServies;
    // End of variables declaration//GEN-END:variables
}
