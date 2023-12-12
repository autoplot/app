/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.autoplot;

import java.util.logging.Level;
import javax.swing.JComboBox;
import org.autoplot.dom.Options;

/**
 * Custom GUI for editing options.  This should be
 * presented in a modal dialog.
 * @author jbf
 */
public class OptionsDialog extends javax.swing.JPanel {

    /**
     * Creates new form OptionsDialog
     */
    public OptionsDialog() {
        initComponents();
    }

    /**
     * select the current item, ignoring everything after the colon.
     * @param cb
     * @param item 
     */
    private static void dowow( JComboBox cb, String theValue ) {
        for ( int i=0; i<cb.getModel().getSize(); i++ ) {
            String item = String.valueOf( cb.getModel().getElementAt(i) );
            if ( item.startsWith( theValue ) ) {
                cb.setSelectedIndex(i);
            }
        }
    }
    
    /**
     * clas must be an enumeration
     * @param val
     * @param clas 
     */
    private static Object undowow( String val, Class clas ) {
        if ( clas.equals( Level.class ) ) {
            int i= val.indexOf(':');
            return Level.parse(val.substring(0,i));
        } else {
            Object[] objs= clas.getEnumConstants();
            for ( Object o : objs ) {
                if ( val.startsWith( o.toString() ) ) {
                    return o;
                }
            }
        }
        throw new IllegalArgumentException("what happened...");
    }
    
    public void setOptions( Options options ) {
        autorangeTypeTF.setText( options.getAutorangeType() );
        flipColorbarLabelCB.setSelected( options.isFlipColorbarLabel() );
        dowow( mouseModuleCB, options.getMouseModule().name() );
        dowow( printingLogLevelCB, options.getPrintingLogLevel().getName() );
        dowow( displayLogLevelCB, options.getDisplayLogLevel().getName() );
        logTimeoutSecFTF.setValue(options.getLogMessageTimeoutSec() );
        printingTagTF.setText( options.getPrintingTag() );
        sliceRebinnedDataCB.setSelected( options.isSliceRebinnedData() );
        tickLengthTF.setText( options.getTicklen() );
        enableScan.setSelected( options.isScanEnabled() );
        oppositeAxisVisibleCB.setSelected( options.isOppositeAxisVisible() );
        lineThicknessTF.setText( options.getLineThickness() );
    }
    
    public void copyOptions( Options options ) {
        options.setAutorangeType( autorangeTypeTF.getText() );
        options.setFlipColorbarLabel( flipColorbarLabelCB.isSelected() );
        options.setMouseModule( (MouseModuleType)undowow( mouseModuleCB.getSelectedItem().toString(), MouseModuleType.class ) );
        options.setPrintingLogLevel( (Level)undowow( printingLogLevelCB.getSelectedItem().toString(), Level.class ) );
        options.setDisplayLogLevel( (Level)undowow( displayLogLevelCB.getSelectedItem().toString(), Level.class ) );
        options.setLogMessageTimeoutSec( (Integer) logTimeoutSecFTF.getValue() );
        options.setPrintingTag( printingTagTF.getText() );
        options.setSliceRebinnedData( sliceRebinnedDataCB.isSelected() );
        options.setTicklen( tickLengthTF.getText() );
        options.setScanEnabled( enableScan.isSelected() );
        options.setOppositeAxisVisible( oppositeAxisVisibleCB.isSelected() );
        options.setLineThickness( lineThicknessTF.getText() );
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
        autorangeTypeTF = new javax.swing.JTextField();
        flipColorbarLabelCB = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        mouseModuleCB = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        printingLogLevelCB = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        printingTagTF = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        sliceRebinnedDataCB = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tickLengthTF = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        displayLogLevelCB = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        logTimeoutSecFTF = new javax.swing.JFormattedTextField();
        enableScan = new javax.swing.JCheckBox();
        jLabel17 = new javax.swing.JLabel();
        oppositeAxisVisibleCB = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lineThicknessTF = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();

        jLabel1.setText("Autorange Type");

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()-1f));
        jLabel2.setForeground(new java.awt.Color(128, 128, 128));
        jLabel2.setText("\"reluctant\" will enable the experimental reluctant mode");

        flipColorbarLabelCB.setText("Flip Colorbar Label");
        flipColorbarLabelCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flipColorbarLabelCBActionPerformed(evt);
            }
        });

        jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getSize()-1f));
        jLabel3.setForeground(new java.awt.Color(128, 128, 128));
        jLabel3.setText("if checked, the colorbar label will be flipped to match Y-axis label");

        jLabel4.setText("Default Mouse Module:");

        mouseModuleCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "boxZoom: drag out a box to zoom in on the region", "crosshairDigitizer: click to read X and Y coordinates.", "zoomX: zoom in X only" }));

        jLabel5.setText("Printing Log Level:");

        jLabel6.setFont(jLabel6.getFont().deriveFont(jLabel6.getFont().getSize()-1f));
        jLabel6.setForeground(new java.awt.Color(128, 128, 128));
        jLabel6.setText("New plots will have this mouse module enabled");

        printingLogLevelCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "OFF: print no message bubbles", "WARNING: print only warning messages", "INFO: print informational message bubbles", "ALL: print all message bubbles" }));

        jLabel7.setFont(jLabel7.getFont().deriveFont(jLabel7.getFont().getSize()-1f));
        jLabel7.setForeground(new java.awt.Color(128, 128, 128));
        jLabel7.setText("Suppress warning bubbles (e.g. no data) when printing to PDF or PNG, or the printer\n");

        jLabel8.setText("Printing Tag (Time stamp):");

        printingTagTF.setText("jTextField2");

        jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getSize()-1f));
        jLabel9.setForeground(new java.awt.Color(128, 128, 128));
        jLabel9.setText("Stamp this tag, which can include $Y$m$d,etc for local time");

        sliceRebinnedDataCB.setText("Slice Rebinned Data");
        sliceRebinnedDataCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sliceRebinnedDataCBActionPerformed(evt);
            }
        });

        jLabel10.setFont(jLabel10.getFont().deriveFont(jLabel10.getFont().getSize()-1f));
        jLabel10.setForeground(new java.awt.Color(128, 128, 128));
        jLabel10.setText("Vertical and Horizontal slices are of pixels, not original measurements");

        jLabel11.setText("Tick Length:");

        tickLengthTF.setText("jTextField3");

        jLabel12.setFont(jLabel12.getFont().deriveFont(jLabel12.getFont().getSize()-1f));
        jLabel12.setForeground(new java.awt.Color(128, 128, 128));
        jLabel12.setText("Positive ems is outward, negative is inward, units are em or px");

        jLabel13.setText("Display Log Level");

        displayLogLevelCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "OFF: print no message bubbles", "WARNING: print only warning messages", "INFO: print informational message bubbles", "ALL: print all message bubbles" }));

        jLabel14.setFont(jLabel14.getFont().deriveFont(jLabel14.getFont().getSize()-1f));
        jLabel14.setForeground(new java.awt.Color(128, 128, 128));
        jLabel14.setText("Suppress warning bubbles (e.g. no data) on the display\n\n");

        jLabel15.setText("Display Log Timeout (sec):");

        jLabel16.setFont(jLabel16.getFont().deriveFont(jLabel16.getFont().getSize()-1f));
        jLabel16.setForeground(new java.awt.Color(128, 128, 128));
        jLabel16.setText("Duration to display message bubbles on plot, >100 means no limit");

        logTimeoutSecFTF.setText("jFormattedTextField1");

        enableScan.setText("Enable Scan");

        jLabel17.setFont(jLabel17.getFont().deriveFont(jLabel17.getFont().getSize()-1f));
        jLabel17.setForeground(new java.awt.Color(128, 128, 128));
        jLabel17.setText("Scan the data to the next available chunk of data, instead of stepping to next interval");

        oppositeAxisVisibleCB.setText("Opposite Axis Visible");

        jLabel18.setFont(jLabel18.getFont().deriveFont(jLabel18.getFont().getSize()-1f));
        jLabel18.setForeground(new java.awt.Color(128, 128, 128));
        jLabel18.setText("Axes are drawn on both sides of the plot");

        jLabel19.setText("Line Thickness:");

        lineThicknessTF.setText("jTextField3");

        jLabel20.setFont(jLabel20.getFont().deriveFont(jLabel20.getFont().getSize()-1f));
        jLabel20.setForeground(new java.awt.Color(128, 128, 128));
        jLabel20.setText("Thickness used for lines, where \"1px\" is one pixel \".1em\" is relative to font size");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(autorangeTypeTF))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mouseModuleCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(printingTagTF))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(printingLogLevelCB, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(displayLogLevelCB, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(enableScan, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2))
                            .addComponent(flipColorbarLabelCB, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sliceRebinnedDataCB, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tickLengthTF, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(logTimeoutSecFTF, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(oppositeAxisVisibleCB, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lineThicknessTF, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(autorangeTypeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(flipColorbarLabelCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(mouseModuleCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(printingTagTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(printingLogLevelCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(displayLogLevelCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(logTimeoutSecFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addGap(9, 9, 9)
                .addComponent(sliceRebinnedDataCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(tickLengthTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addGap(3, 3, 3)
                .addComponent(oppositeAxisVisibleCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(lineThicknessTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enableScan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void flipColorbarLabelCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flipColorbarLabelCBActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt); 
        //TODO: this will probably change, because we need to listen to the bindings, I think
    }//GEN-LAST:event_flipColorbarLabelCBActionPerformed

    private void sliceRebinnedDataCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sliceRebinnedDataCBActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);                
    }//GEN-LAST:event_sliceRebinnedDataCBActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField autorangeTypeTF;
    private javax.swing.JComboBox displayLogLevelCB;
    private javax.swing.JCheckBox enableScan;
    private javax.swing.JCheckBox flipColorbarLabelCB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField lineThicknessTF;
    private javax.swing.JFormattedTextField logTimeoutSecFTF;
    private javax.swing.JComboBox mouseModuleCB;
    private javax.swing.JCheckBox oppositeAxisVisibleCB;
    private javax.swing.JComboBox printingLogLevelCB;
    private javax.swing.JTextField printingTagTF;
    private javax.swing.JCheckBox sliceRebinnedDataCB;
    private javax.swing.JTextField tickLengthTF;
    // End of variables declaration//GEN-END:variables
}
