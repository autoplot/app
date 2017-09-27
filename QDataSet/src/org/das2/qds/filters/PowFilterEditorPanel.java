/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.das2.qds.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author jbfaden
 */
public class PowFilterEditorPanel extends AbstractFilterEditorPanel implements FilterEditorPanel {

    public PowFilterEditorPanel() {
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

        jLabel2 = new javax.swing.JLabel();
        scalar = new javax.swing.JTextField();

        jLabel2.setText("Raise to the power:");

        scalar.setText("1.0");
        scalar.setMaximumSize(new java.awt.Dimension(75, 27));
        scalar.setMinimumSize(new java.awt.Dimension(75, 27));
        scalar.setPreferredSize(new java.awt.Dimension(75, 27));
        scalar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scalarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scalar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(125, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(scalar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void scalarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scalarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_scalarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField scalar;
    // End of variables declaration//GEN-END:variables
    @Override
    public String getFilter() {
        return "|pow("+scalar.getText()+")";
    }

    @Override
    public void setFilter(String filter) {
        Pattern p= Pattern.compile("\\|pow\\((.*)\\)");
        Matcher m= p.matcher(filter);
        if ( m.matches() ) {
            scalar.setText(m.group(1));
        } else {
            scalar.setText("1.");
        }
        
    }
}
