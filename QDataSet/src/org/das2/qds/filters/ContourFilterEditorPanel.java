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
 * @author mmclouth
 */
public class ContourFilterEditorPanel extends AbstractFilterEditorPanel {

    /**
     * Creates new form ContourFilterEditorPanel
     */
    public ContourFilterEditorPanel() {
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

        contourPtsTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        contourPtsTF.setText("1,5,10");
        contourPtsTF.setPreferredSize(new java.awt.Dimension(150, 27));

        jLabel1.setText("Convert to contours at:");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contourPtsTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(contourPtsTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jLabel1))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField contourPtsTF;
    public javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setFilter(String filter) {
        Pattern p= Pattern.compile("\\|contour\\((.*)\\)");
        Matcher m= p.matcher(filter);
        if ( m.matches() ) {
            contourPtsTF.setText( m.group(1) );
        }
        else {
            contourPtsTF.setText("1");
        }
    }

    @Override
    public String getFilter() {
        String pts = contourPtsTF.getText().replaceAll("\\s","");
        return "|contour(" + pts + ")";
    }
}
