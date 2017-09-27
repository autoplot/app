/*
 * PlotStylePanel.java
 *
 * Created on July 27, 2007, 9:41 AM
 */
package org.autoplot.renderer;

import org.das2.components.propertyeditor.ColorEditor;
import java.awt.BorderLayout;
import java.awt.Color;
import org.autoplot.help.AutoplotHelpSystem;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.autoplot.PlotStylePanel;
import org.autoplot.dom.PlotElement;
import org.autoplot.dom.PlotElementStyle;

/**
 *
 * @author  jbf
 */
public class HugeScatterStylePanel extends javax.swing.JPanel implements PlotStylePanel.StylePanel {

    ColorEditor colorEditor;
    BindingGroup elementBindingContext;
    
    /** Creates new form PlotStylePanel
     */
    public HugeScatterStylePanel( ) {
        
        initComponents();

        colorEditor = new ColorEditor();
        colorEditor.setValue( Color.BLACK );
        colorPanel.add(colorEditor.getSmallEditor(), BorderLayout.CENTER);

        validate();

    }

    public void releaseElementBindings() {
        if ( elementBindingContext!=null ) {
            elementBindingContext.unbind();
            elementBindingContext= null;
        }
        AutoplotHelpSystem.getHelpSystem().registerHelpID(this, PlotStylePanel.STYLEPANEL_HELP_ID );
    }

    public synchronized void doElementBindings(PlotElement element) {
        PlotElementStyle style= element.getStyle();
        BindingGroup bc = new BindingGroup();

        bc.addBinding(Bindings.createAutoBinding( UpdateStrategy.READ_WRITE, style, BeanProperty.create( "color" ), colorEditor, BeanProperty.create("value")));
        
        if ( elementBindingContext!=null ) releaseElementBindings();
        bc.bind();
        
        repaint();
        
        elementBindingContext= bc;

        AutoplotHelpSystem.getHelpSystem().registerHelpID(this, PlotStylePanel.STYLEPANEL_HELP_ID );

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        colorPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lineStylePanel = new javax.swing.JPanel();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Huge Scatter [?]"));

        colorPanel.setLayout(new java.awt.BorderLayout());

        jLabel6.setText("Color:");
        jLabel6.setToolTipText("color of the line and plot symbols");

        lineStylePanel.setLayout(new java.awt.BorderLayout());

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel6)
                .add(67, 67, 67)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(lineStylePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .add(colorPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(new java.awt.Component[] {colorPanel, lineStylePanel}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, colorPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(73, 73, 73)
                .add(lineStylePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(new java.awt.Component[] {colorPanel, jLabel6}, org.jdesktop.layout.GroupLayout.VERTICAL);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel colorPanel;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel lineStylePanel;
    // End of variables declaration//GEN-END:variables
}