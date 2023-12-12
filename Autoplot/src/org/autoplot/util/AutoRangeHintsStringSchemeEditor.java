/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.autoplot.util;

import java.awt.Component;
import java.util.Map;
import org.autoplot.datasource.URISplit;
import org.das2.util.StringSchemeEditor;

/**
 * Editor for the AutoRangeHints.
 * @author jbf
 */
public class AutoRangeHintsStringSchemeEditor extends javax.swing.JPanel implements StringSchemeEditor {

    /**
     * Creates new form AutoRangeHintsStringSchemeEditor
     */
    public AutoRangeHintsStringSchemeEditor() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of
     * this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        includeZeroCB = new javax.swing.JCheckBox();
        widthCheckBox = new javax.swing.JCheckBox();
        widthTextField = new javax.swing.JTextField();
        widthsCheckBox = new javax.swing.JCheckBox();
        widthsTextField = new javax.swing.JTextField();
        logCB = new javax.swing.JCheckBox();
        logTrueRB = new javax.swing.JRadioButton();
        logFalseRB = new javax.swing.JRadioButton();
        centerCB = new javax.swing.JCheckBox();
        centerTextField = new javax.swing.JTextField();
        minValueCB = new javax.swing.JCheckBox();
        minValueTF = new javax.swing.JTextField();
        maxValueCB = new javax.swing.JCheckBox();
        maxValueTF = new javax.swing.JTextField();
        extendByPercentCB = new javax.swing.JCheckBox();
        extendByPercentTF = new javax.swing.JTextField();

        jLabel1.setText("Auto Range Hints");

        includeZeroCB.setText("Ensure that zero is within range");

        widthCheckBox.setText("Constrain the axis range to a particular width");

        widthTextField.setText("10");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, widthCheckBox, org.jdesktop.beansbinding.ELProperty.create("${selected}"), widthTextField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        widthsCheckBox.setText("Constrain the axis range to a set of widths");

        widthsTextField.setText("10,300,10000");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, widthsCheckBox, org.jdesktop.beansbinding.ELProperty.create("${selected}"), widthsTextField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        logCB.setText("Force log or linear");

        buttonGroup1.add(logTrueRB);
        logTrueRB.setText("true");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, logCB, org.jdesktop.beansbinding.ELProperty.create("${selected}"), logTrueRB, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        buttonGroup1.add(logFalseRB);
        logFalseRB.setText("false");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, logCB, org.jdesktop.beansbinding.ELProperty.create("${selected}"), logFalseRB, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        centerCB.setText("Center the axis at this value");

        centerTextField.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, centerCB, org.jdesktop.beansbinding.ELProperty.create("${selected}"), centerTextField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        minValueCB.setText("Min Value");

        minValueTF.setText("0");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, minValueCB, org.jdesktop.beansbinding.ELProperty.create("${selected}"), minValueTF, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        maxValueCB.setText("Max Value");

        maxValueTF.setText("100");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, maxValueCB, org.jdesktop.beansbinding.ELProperty.create("${selected}"), maxValueTF, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        extendByPercentCB.setText("Extend by Percent");

        extendByPercentTF.setText("10");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, extendByPercentCB, org.jdesktop.beansbinding.ELProperty.create("${selected}"), extendByPercentTF, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(widthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(widthsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(logTrueRB)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(logFalseRB))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(minValueTF, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(minValueCB)
                                .addGap(96, 96, 96)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(maxValueTF, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(maxValueCB)
                                        .addContainerGap(266, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(extendByPercentCB)
                                    .addComponent(includeZeroCB)
                                    .addComponent(logCB)
                                    .addComponent(widthCheckBox)
                                    .addComponent(widthsCheckBox)
                                    .addComponent(centerCB)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(centerTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                            .addComponent(extendByPercentTF, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {centerTextField, widthTextField});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(includeZeroCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(widthCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(widthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(widthsCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(widthsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(logCB)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logTrueRB)
                    .addComponent(logFalseRB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(centerCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(centerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minValueCB)
                    .addComponent(maxValueCB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minValueTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxValueTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(extendByPercentCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(extendByPercentTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents
    
    @Override
    public void setValue(String v) {
        Map<String,String> params= URISplit.parseParams(v);
        if ( params.containsKey("includeZero") ) {
            includeZeroCB.setSelected( params.get("includeZero").toUpperCase().startsWith("T") );
        } 
        if ( params.containsKey("width") ) {
            widthCheckBox.setSelected(true);
            widthTextField.setText(params.get("width"));
        } else {
            widthCheckBox.setSelected(false);
        }
        if ( params.containsKey("widths") ) {
            widthsCheckBox.setSelected(true);
            widthsTextField.setText(params.get("widths"));
        } else {
            widthsCheckBox.setSelected(false);
        }
        if ( params.containsKey("log") ) {
            logCB.setSelected(true);
            logTrueRB.setSelected(params.get("log").toUpperCase().startsWith("T"));
        } else {
            logCB.setSelected(false);
        }
        if ( params.containsKey("center") ) {
            centerCB.setSelected(true);
            centerTextField.setText(params.get("center"));
        } else {
            centerCB.setSelected(false);
        }
        if ( params.containsKey("min") ) {
            minValueCB.setSelected(true);
            minValueTF.setText(params.get("min"));
        }
        if ( params.containsKey("max") ) {
            maxValueCB.setSelected(true);
            maxValueTF.setText(params.get("max"));
        }
        if ( params.containsKey("extend") ) {
            extendByPercentCB.setSelected(true);
            extendByPercentTF.setText(params.get("extend"));
        }
            
    }

    @Override
    public String getValue() {
        StringBuilder b= new StringBuilder();
        if ( includeZeroCB.isSelected() ) {
            b.append( "&includeZero=T");
        }
        if ( widthCheckBox.isSelected() ) {
            b.append("&width=").append(widthTextField.getText().trim());
        }
        if ( widthsCheckBox.isSelected() ) {
            b.append("&widths=").append(widthsTextField.getText().trim());
        }
        if ( logCB.isSelected() ) {
            b.append("&log=").append( logTrueRB.isSelected() ? "T" : "F" );
        }
        if ( centerCB.isSelected() ) {
            b.append("&center=").append( centerTextField.getText().trim() );
        }
        if ( minValueCB.isSelected() ) {
            b.append("&min=").append(minValueTF.getText().trim());
        }
        if ( maxValueCB.isSelected() ) {
            b.append("&max=").append(maxValueTF.getText().trim());
        }
        if ( extendByPercentCB.isSelected() ) {
            b.append("&extend=").append(extendByPercentTF.getText().trim());
        }
        return b.length()==0 ? "" : b.substring(1);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void setContext(Object o) {
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox centerCB;
    private javax.swing.JTextField centerTextField;
    private javax.swing.JCheckBox extendByPercentCB;
    private javax.swing.JTextField extendByPercentTF;
    private javax.swing.JCheckBox includeZeroCB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JCheckBox logCB;
    private javax.swing.JRadioButton logFalseRB;
    private javax.swing.JRadioButton logTrueRB;
    private javax.swing.JCheckBox maxValueCB;
    private javax.swing.JTextField maxValueTF;
    private javax.swing.JCheckBox minValueCB;
    private javax.swing.JTextField minValueTF;
    private javax.swing.JCheckBox widthCheckBox;
    private javax.swing.JTextField widthTextField;
    private javax.swing.JCheckBox widthsCheckBox;
    private javax.swing.JTextField widthsTextField;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}