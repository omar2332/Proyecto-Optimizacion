/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectooptimizacion;

/**
 *
 * @author HP
 */
public class funcionObjetivo extends javax.swing.JPanel {

    /**
     * Creates new form funcionObjetivo
     */
    public funcionObjetivo() {
        initComponents();
        formaNormal.setEditable(false);
        FormaAmpliada.setEditable(false);
        int i,j;
        String modelo1,modelo2,info = "";
        
        if(PanelDatos.fo == 1){
                modelo1 = "MAXIMIZAR Z = ";
                modelo2 = "MAXIMIZAR Z = ";
        }else{
                modelo1 = "MINIMIZAR Z = ";
                modelo2 = "MINIMIZAR Z = ";
        }
        
       
        
        
        for(i=0;i<PanelDatos.n;i++){
            if(i==PanelDatos.n-1){
                if(PanelDatos.c[i] > 0 ){
                        modelo1 = modelo1 + "+"+ PanelDatos.c[i] +" X" + (i+1);
                        modelo2 = modelo2 + "+"+ PanelDatos.c[i] +" X" + (i+1);
                        
                    }else{
                        modelo1 = modelo1 + PanelDatos.c[i] +" X" + (i+1);
                        modelo2 = modelo2 + PanelDatos.c[i] +" X" + (i+1);
                    }
            }else{
                if(i == 0){
                    modelo1 = modelo1 + PanelDatos.c[i] +"X" + (i+1);
                    modelo2 = modelo2 + PanelDatos.c[i] +"X" + (i+1);
                }else{
                    if(PanelDatos.c[i] > 0 ){
                        modelo1 = modelo1 + "+"+ PanelDatos.c[i] +"X" + (i+1);
                        modelo2 = modelo2 + "+"+ PanelDatos.c[i] +" X" + (i+1);
                        
                    }else{
                        modelo1 = modelo1 + PanelDatos.c[i] +"X" + (i+1);
                        modelo2 = modelo2 + PanelDatos.c[i] +"X" + (i+1);
                    }
                }
            }
        }
        int var = PanelDatos.n;
        for(i = 0;i< PanelDatos.m;i++){
            if(PanelDatos.r[i] == 1 && PanelDatos.a[i][PanelDatos.n] < 0){
                info += "**Como la restricción" + (i+1) +"es del tipo '≤', y el término independiente negativo (la restricción se multiplica por -1), se agrega la variable de exceso X" + (var+1)+" y la variable artificial X"+ (var +2)+".\n";
                modelo2 = modelo2 +"+0X" + (var + 1) + "+0X" + (var +2);
                 
                 var += 2;
                 for(j = 0;j<=PanelDatos.n;j++){
                     PanelDatos.a[i][j] *=-1;   
                 }
                 PanelDatos.r[i] = 3;
                 continue;
            }
            if(PanelDatos.r[i] == 2 && PanelDatos.a[i][PanelDatos.n] != 0){
                if(PanelDatos.a[i][PanelDatos.n] < 0){
                    info+= "**Como la restricción" +(i + 1)+ " es del tipo '=', y el término independiente negativo (la restricción se multiplica por -1), se agrega la variable artificial X"+(var+1) + ".\n";
                    for(j = 0;j<=PanelDatos.n;j++){
                     PanelDatos.a[i][j] *=-1;   
                    }
                    
                     modelo2 = modelo2 +"+0X" + (var + 1);
                     var++;
                     continue;
                }else{
                    info += "**Como la restricción "+ (i+1)+" es del tipo '=' se agrega la variable artificial X"+(var + 1) +". \n";
                    modelo2 = modelo2 +"+0X" + (var + 1);
                    var++;
                    continue;
                }
                
            }
            
            if(PanelDatos.r[i] == 3 && PanelDatos.a[i][PanelDatos.n] <= 0){
                info  += "**Como la restricción" + (i+1)+"es del tipo '≥', y el término independiente negativo o nulo (la restricción se multiplica por -1), se agrega la variable de holgura X" + (var + 1)+". \n";
                modelo2 = modelo2 +"+0X" + (var + 1);
                for(j = 0;j<=PanelDatos.n;j++){
                     PanelDatos.a[i][j] *=-1;   
                }
                var++;
                PanelDatos.r[i] = 1;
                continue;
                
            }
            
            if(PanelDatos.r[i] == 1 && PanelDatos.a[i][PanelDatos.n] > 0){
                info += "**Como la restricción "+ (i +1)+ " es del tipo '≤' se agrega la variable de holgura X"+ (var+1)+ ".\n";
                modelo2 = modelo2 +"+0X" + (var + 1);
                var++;
                continue;
            }
            if(PanelDatos.r[i] == 3 && PanelDatos.a[i][PanelDatos.n] > 0){
                info += "**Como la restricción"+(i+1) +"es del tipo '≥' se agrega la variable de exceso X" + (var+1)+ "y la variable artificial X"+ (var+2)+".\n";
                modelo2 = modelo2 +"+0X" + (var+1) + "+0X" + (var +2);
                var+=2;
            }
        }
        
        modelo1 = modelo1 + "\n";
        var = PanelDatos.n;
        modelo2 = modelo2 + "\n";
        for (i = 0; i < PanelDatos.m; i++) {
            for (j = 0; j <= PanelDatos.n; j++) {
                if(j != PanelDatos.n){
                    if(j==0){
                        modelo1 = modelo1 + PanelDatos.a[i][j]+"X" + (j+1);
                        modelo2 = modelo2 + PanelDatos.a[i][j]+"X" + (j+1);
                    }else{
                        if(PanelDatos.a[i][j]>0){
                            modelo1 = modelo1 + "+"+ PanelDatos.a[i][j]+"X" + (j+1);
                            modelo2 = modelo2 + "+"+ PanelDatos.a[i][j]+"X" + (j+1);
                        }else{
                            modelo1 = modelo1 + PanelDatos.a[i][j]+"X" + (j+1);
                            modelo2 = modelo2 + PanelDatos.a[i][j]+"X" + (j+1);
                        }
                        
                    }
                 
                }else{
                    
                    if(PanelDatos.r[i] == 1 && PanelDatos.a[i][PanelDatos.n] < 0){
     
                        modelo2 = modelo2 +"+1X" + (var + 1) + "+1X" + (var +2);

                         var += 2;

                         
                    }
                    if(PanelDatos.r[i] == 2 && PanelDatos.a[i][PanelDatos.n] != 0){
                        if(PanelDatos.a[i][PanelDatos.n] < 0){
                            
                             modelo2 = modelo2 +"+1X" + (var + 1);
                             var++;
                            
                        }else{
                         
                            modelo2 = modelo2 +"+1X" + (var + 1);
                            var++;
                         
                            
                        }

                    }

                    if(PanelDatos.r[i] == 3 && PanelDatos.a[i][PanelDatos.n] <= 0){
                        
                        modelo2 = modelo2 +"+1X" + (var + 1);
                        var++;
                      

                    }

                    if(PanelDatos.r[i] == 1 && PanelDatos.a[i][PanelDatos.n] > 0){
                        
                        modelo2 = modelo2 +"+1X" + (var + 1);
                        var++;

                    }
                    if(PanelDatos.r[i] == 3 && PanelDatos.a[i][PanelDatos.n] > 0){
                        
                        modelo2 = modelo2 +"+1X" + (var+1) + "+1X" + (var +2);
                        var+=2;
                    }
                    if(PanelDatos.r[i] == 1){
                        modelo1 = modelo1 + " <= ";
                        modelo2 = modelo2 + " = ";
                    }
                    if(PanelDatos.r[i] == 2){
                        modelo1 = modelo1 + " = ";
                        modelo2 = modelo2 + " = ";
                    }
                    if(PanelDatos.r[i] == 3){
                        modelo1 = modelo1 + " >= ";
                        modelo2 = modelo2 + " = ";
                    }
                    if(j != PanelDatos.n){
                        modelo1 = modelo1 +  PanelDatos.a[i][j]+"X" + (j+1);
                        modelo2 = modelo2 +  PanelDatos.a[i][j]+"X" + (j+1);
                    }else{
                        modelo1 = modelo1 +  PanelDatos.a[i][j];
                        modelo2 = modelo2 +  PanelDatos.a[i][j];
                    }
                    
 
                    
                }               
            }
            modelo1 = modelo1 + "\n";
            modelo2 = modelo2 + "\n";
        }
        
        int bandera = 0,k=0;
        for(i = 0;i<PanelDatos.m;i++){
            bandera = 0;
            k=0;
            for(j = 0;j<PanelDatos.n;j++){
                if(bandera == 0 && (PanelDatos.a[i][j]%PanelDatos.c[j]==0 || PanelDatos.c[j]%PanelDatos.a[i][j]==0)){
                    k++;
                    if(k == PanelDatos.n){
                        bandera = 2;
                        break;
                    }
                }else{
                    bandera = 1;
                }
            }
        }
        
        if(bandera != 2){
            multiple.setText("");
        }else{
            multiple.setText("Existe una posibilidad de que exista solucion multiple...");
        }
        formaNormal.setText(modelo1);
        FormaAmpliada.setText(modelo2);
        infoLabel.setText(info);
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        formaNormal = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        btnContinuar = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        FormaAmpliada = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        infoLabel = new javax.swing.JTextArea();
        solucionDirecta = new javax.swing.JButton();
        multiple = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1000, 720));
        setMinimumSize(new java.awt.Dimension(1000, 720));

        jLabel1.setText("Modelo");

        jLabel2.setText("Forma Ampliada");

        formaNormal.setColumns(20);
        formaNormal.setRows(5);
        jScrollPane1.setViewportView(formaNormal);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 317, Short.MAX_VALUE)
        );

        btnContinuar.setText("Continuar");
        btnContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinuarActionPerformed(evt);
            }
        });

        btnAtras.setText("Atras");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        FormaAmpliada.setColumns(20);
        FormaAmpliada.setRows(5);
        jScrollPane2.setViewportView(FormaAmpliada);

        jLabel3.setText("Forma Ampliada");

        infoLabel.setColumns(20);
        infoLabel.setRows(5);
        jScrollPane3.setViewportView(infoLabel);

        solucionDirecta.setText("Solucion Directa");
        solucionDirecta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solucionDirectaActionPerformed(evt);
            }
        });

        multiple.setText("Existe");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addComponent(jLabel1)
                .addGap(309, 309, 309)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(208, 208, 208))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 867, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(438, 438, 438))
            .addGroup(layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAtras)
                        .addGap(48, 48, 48)
                        .addComponent(btnContinuar)
                        .addGap(47, 47, 47)
                        .addComponent(solucionDirecta)
                        .addGap(719, 719, 719)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(multiple, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(multiple)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnContinuar)
                    .addComponent(btnAtras)
                    .addComponent(solucionDirecta))
                .addGap(74, 74, 74))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
        
        FramePrincipal.PD = new PanelDatos();
        FramePrincipal.PanelPrincipal.add(FramePrincipal.PD);
        FramePrincipal.PD.setBounds(0, 0, 1000, 720);
        PanelDatos.FO.setVisible(false);
        FramePrincipal.PD.setVisible(true);
        MetodoSimplexRevisado.salidaFinal.clear();
    }//GEN-LAST:event_btnAtrasActionPerformed

    public static Resultados RE;
    public static boolean directo = false;
    private void btnContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinuarActionPerformed
        // TODO add your handling code here:
        directo = false;
        MetodoSimplexRevisado MSR = new MetodoSimplexRevisado();
        MSR.metodo(PanelDatos.n,PanelDatos.m, PanelDatos.fo, PanelDatos.c, PanelDatos.a, PanelDatos.r);
        RE = new Resultados();
        FramePrincipal.PanelPrincipal.add(RE);
        RE.setBounds(0, 0, 1000, 720);
        FramePrincipal.PA.setVisible(false);
        FramePrincipal.PD.setVisible(false);
        PanelDatos.FO.setVisible(false);
        RE.setVisible(true);
        
    }//GEN-LAST:event_btnContinuarActionPerformed

    private void solucionDirectaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solucionDirectaActionPerformed
        // TODO add your handling code here:
        directo = true;
        MetodoSimplexRevisado MSR = new MetodoSimplexRevisado();
        MSR.metodo(PanelDatos.n,PanelDatos.m, PanelDatos.fo, PanelDatos.c, PanelDatos.a, PanelDatos.r);
        RE = new Resultados();
        FramePrincipal.PanelPrincipal.add(RE);
        RE.setBounds(0, 0, 1000, 720);
        FramePrincipal.PA.setVisible(false);
        FramePrincipal.PD.setVisible(false);
        PanelDatos.FO.setVisible(false);
        RE.setVisible(true);
    }//GEN-LAST:event_solucionDirectaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea FormaAmpliada;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnContinuar;
    private javax.swing.JTextArea formaNormal;
    private javax.swing.JTextArea infoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel multiple;
    private javax.swing.JButton solucionDirecta;
    // End of variables declaration//GEN-END:variables
}
