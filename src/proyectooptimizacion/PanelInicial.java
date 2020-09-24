/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectooptimizacion;

import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class PanelInicial extends javax.swing.JPanel {

    /**
     * Creates new form PanelInicial
     */
    public PanelInicial() {
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
        jLabel2 = new javax.swing.JLabel();
        nVariables = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        mRestricciones = new javax.swing.JTextField();
        btnIniciar = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1000, 720));
        setMinimumSize(new java.awt.Dimension(1000, 720));
        setPreferredSize(new java.awt.Dimension(1000, 720));

        jLabel1.setText("Metodo Simplex Revisado");

        jLabel2.setText("¿Cuantas Variables?");

        jLabel3.setText("¿Cuantas Restricciones?");

        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(302, 302, 302)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnIniciar)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nVariables)
                            .addComponent(mRestricciones, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(465, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel1)
                .addGap(132, 132, 132)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nVariables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(mRestricciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(134, 134, 134)
                .addComponent(btnIniciar)
                .addContainerGap(276, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    public static boolean isNum(String strNum) {
    boolean ret = true;
    try {

        Integer.parseInt(strNum);

    }catch (NumberFormatException e) {
        ret = false;
    }
    return ret;
    }
    
    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        // TODO add your handling code here:

        
        if(mRestricciones.getText().isEmpty() || nVariables.getText().isEmpty()){
            
            JOptionPane.showMessageDialog(this, "llena los campos");
            return;
            
        }else{
            if( isNum(mRestricciones.getText()) && isNum(nVariables.getText())){
                PanelDatos.n  = Integer.parseInt(nVariables.getText());
                PanelDatos.m = Integer.parseInt(mRestricciones.getText());
                FramePrincipal.PD = new PanelDatos();
                FramePrincipal.PanelPrincipal.add(FramePrincipal.PD);
                FramePrincipal.PD.setBounds(0, 0, 1000, 720);
                FramePrincipal.PA.setVisible(false);
                FramePrincipal.PD.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(this, "Todos los campos deben ser numericos");
            return;
            }
        }
        

      
        
    }//GEN-LAST:event_btnIniciarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField mRestricciones;
    private javax.swing.JTextField nVariables;
    // End of variables declaration//GEN-END:variables
}
