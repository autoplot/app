/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.autoplot.imagedatasource;

import javax.swing.JPanel;
import org.autoplot.datasource.DataSourceFormatEditorPanel;

/**
 *
 * @author jbf
 */
public class ImageDataSourceFormatEditorPanel extends javax.swing.JPanel implements DataSourceFormatEditorPanel {

    /**
     * Creates new form ImageDataSourceFormatEditorPanel
     */
    public ImageDataSourceFormatEditorPanel() {
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

        jLabel1.setText("<html>The data must be of the form:<ul><li>(m,n,4) for ARGB   <li>(3,m,n) RGB.  <li>(m,n,3) RGB. </ul>with values from 0 through 255.  This can be created with the \"bundle\" function in scripts.");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(123, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public JPanel getPanel() {
        return this;
    }

    String uri;
    
    @Override
    public void setURI(String uri) {
        this.uri= uri;
    }

    @Override
    public String getURI() {
        return this.uri;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
