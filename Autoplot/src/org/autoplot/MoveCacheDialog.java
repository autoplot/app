/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MoveCacheDialog.java
 *
 * Created on Sep 30, 2010, 3:29:43 PM
 */

package org.autoplot;

import java.io.File;
import javax.swing.JFileChooser;
import org.autoplot.datasource.AutoplotSettings;

/**
 *
 * @author jbf
 */
public class MoveCacheDialog extends javax.swing.JPanel {

    /** Creates new form MoveCacheDialog */
    public MoveCacheDialog() {
        initComponents();
        this.oldDir.setText( AutoplotSettings.settings().resolveProperty( AutoplotSettings.PROP_FSCACHE ) );
        this.newDir.setText( oldDir.getText() );
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        oldDir = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        newDir = new javax.swing.JTextField();
        pickButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        jLabel1.setText("From current location, which is:");

        oldDir.setEditable(false);
        oldDir.setText("jTextField1");

        jLabel2.setText("To:");

        newDir.setText("jTextField2");

        pickButton.setText("pick");
        pickButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pickButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("<html>This will permanently move the files found in the data cache to the new folder.  For example, this can be used to move data to a larger disk.  The disk should be local to the machine Autoplot runs on for optimal performance.");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(oldDir)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(newDir, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(pickButton))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jLabel1))
                        .add(0, 0, Short.MAX_VALUE))
                    .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 74, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(oldDir, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(newDir, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(pickButton))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void pickButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pickButtonActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);
        JFileChooser chooser= new JFileChooser( new File( getNewDir().getText()) );
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if ( chooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION ) {
            getNewDir().setText( String.valueOf(chooser.getSelectedFile()) );
        }
    }//GEN-LAST:event_pickButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField newDir;
    private javax.swing.JTextField oldDir;
    private javax.swing.JButton pickButton;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the newDir
     */
    public javax.swing.JTextField getNewDir() {
        return newDir;
    }

}