package org.autoplot.jythonsupport.ui;

import javax.swing.JOptionPane;
import org.das2.util.ColorUtil;

/**
 *
 * @author jbf@iowa.uiowa.edu
 */
public class ScriptColorsPanel extends javax.swing.JPanel {

    /**
     * Creates new form ScriptColorsPanel
     */
    public ScriptColorsPanel() {
        initComponents();
        backgroundColorEditor.setValue( ColorUtil.ALICE_BLUE );
        backgroundColorPanel.add(backgroundColorEditor.getSmallEditor());
        caretColorEditor.setValue(ColorUtil.CADET_BLUE );
        caretColorPanel.add(caretColorEditor.getSmallEditor());
        //org.das2.components.propertyeditor.ColorEditor
    }
    
    

    public static void main( String[] args ) {
        JOptionPane.showMessageDialog( null, new ScriptColorsPanel() );
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundColorEditor = new org.das2.components.propertyeditor.ColorEditor();
        caretColorEditor = new org.das2.components.propertyeditor.ColorEditor();
        jLabel1 = new javax.swing.JLabel();
        backgroundColorPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        caretColorPanel = new javax.swing.JPanel();

        jLabel1.setText("background");

        backgroundColorPanel.setLayout(new java.awt.BorderLayout());

        jLabel2.setText("caret");

        caretColorPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(backgroundColorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(caretColorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(187, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {backgroundColorPanel, caretColorPanel});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(backgroundColorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(caretColorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addContainerGap(240, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.das2.components.propertyeditor.ColorEditor backgroundColorEditor;
    private javax.swing.JPanel backgroundColorPanel;
    private org.das2.components.propertyeditor.ColorEditor caretColorEditor;
    private javax.swing.JPanel caretColorPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

    String config="";
    
    public void setConfiguration(String config) {
        System.err.println("setConfig");
        this.config= config;            
    }
    
    public String getConfiguration( ) {
        return this.config;
    }
}