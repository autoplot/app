/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.autoplot;

/**
 *
 * @author jbf
 */
public class PdfOptionsPanel extends javax.swing.JPanel {

    /**
     * Creates new form PdfOptionsPanel
     */
    public PdfOptionsPanel() {
        initComponents();
    }

    /**
     * return the pixels per point (a float or int), or "" if there is no
     * preference.
     * @return 
     */
    public String getPixelsPerInch() {
        if ( pixelsPerPointCB.isSelected() ) {
            return (String)pixelsPerInchComboBox.getSelectedItem();
        } else {
            return "";
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        fontsAsShapesCB = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        manualWidthCB = new javax.swing.JCheckBox();
        widthTF = new javax.swing.JTextField();
        unitsComboBox = new javax.swing.JComboBox();
        pixelsPerPointCB = new javax.swing.JCheckBox();
        pixelsPerInchComboBox = new javax.swing.JComboBox<>();

        fontsAsShapesCB.setText("Fonts as shapes");

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()-2f));
        jLabel1.setText("<html>Write the characters out as shapes.  This makes a portable PDF and all characters render, but the labels cannot be edited. ");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        manualWidthCB.setText("Set Canvas Width");
        manualWidthCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manualWidthCBActionPerformed(evt);
            }
        });

        widthTF.setText("8.5");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, manualWidthCB, org.jdesktop.beansbinding.ELProperty.create("${selected}"), widthTF, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        unitsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "inches", "centimeters", "points" }));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, manualWidthCB, org.jdesktop.beansbinding.ELProperty.create("${selected}"), unitsComboBox, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        pixelsPerPointCB.setText("Set Pixels Per Inch");
        pixelsPerPointCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pixelsPerPointCBActionPerformed(evt);
            }
        });

        pixelsPerInchComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "72", "100", "144", "400" }));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, pixelsPerPointCB, org.jdesktop.beansbinding.ELProperty.create("${selected}"), pixelsPerInchComboBox, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fontsAsShapesCB)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(widthTF, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(unitsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(manualWidthCB)
                            .addComponent(pixelsPerPointCB)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(pixelsPerInchComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 24, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontsAsShapesCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manualWidthCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(widthTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unitsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pixelsPerPointCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pixelsPerInchComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {fontsAsShapesCB, manualWidthCB});

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void manualWidthCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualWidthCBActionPerformed
        if ( manualWidthCB.isSelected() ) pixelsPerPointCB.setSelected(false);
    }//GEN-LAST:event_manualWidthCBActionPerformed

    private void pixelsPerPointCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pixelsPerPointCBActionPerformed
        if ( pixelsPerPointCB.isSelected() ) manualWidthCB.setSelected(false);
    }//GEN-LAST:event_pixelsPerPointCBActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBox fontsAsShapesCB;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JCheckBox manualWidthCB;
    private javax.swing.JComboBox<String> pixelsPerInchComboBox;
    private javax.swing.JCheckBox pixelsPerPointCB;
    public javax.swing.JComboBox unitsComboBox;
    public javax.swing.JTextField widthTF;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
