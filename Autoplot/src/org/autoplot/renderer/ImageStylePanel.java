/*
 * PlotStylePanel.java
 *
 * Created on July 27, 2007, 9:41 AM
 */
package org.autoplot.renderer;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
import org.das2.components.propertyeditor.ColorEditor;
import org.das2.graph.RGBImageRenderer;
import org.das2.graph.Renderer;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.autoplot.PlotStylePanel;
import org.autoplot.dom.PlotElement;

/**
 * controller for the Image plot type.
 * @author  jbf
 */
public class ImageStylePanel extends javax.swing.JPanel implements PlotStylePanel.StylePanel {

    ColorEditor colorEditor;
    BindingGroup elementBindingContext;
    
    /** Creates new form PlotStylePanel
     */
    public ImageStylePanel( ) {
        
        initComponents();

        validate();

    }

    Renderer renderer;
    
    private String control = "";

    public static final String PROP_CONTROL = "control";

    public String getControl() {
        return control;
    }


    /**
     * set the control string
     * @param control the control string, e.g. "nearestNeighborInterpolation=T"
     */
    public void setControl(String control) {
        String oldControl = this.control;
        this.control = control;
        this.renderer.setControl(control);
        updateGUI();
        firePropertyChange(PROP_CONTROL, oldControl, control);
    }
    
    @Override
    public void doElementBindings(PlotElement element) {
        this.renderer= element.getController().getRenderer();
        updateGUI( );
        
        BindingGroup bc = new BindingGroup();

        bc.addBinding( Bindings.createAutoBinding( AutoBinding.UpdateStrategy.READ_WRITE, 
                element, BeanProperty.create(  PlotElement.PROP_RENDERCONTROL ), 
                this, BeanProperty.create( Renderer.PROP_CONTROL ) ) );
        
        if ( elementBindingContext!=null ) {
            releaseElementBindings();
        }
        
        bc.bind();
        
        repaint();
        
        elementBindingContext= bc;

    }

    @Override
    public void releaseElementBindings() {
        if ( elementBindingContext!=null ) {
            elementBindingContext.unbind();
            elementBindingContext= null;
        }
        
    }
    
    private void updateGUI( ) {
        this.control= renderer.getControl();
        this.jCheckBox1.setSelected( renderer.getBooleanControl( RGBImageRenderer.PROP_NEARESTNEIGHBORINTERPOLATION, false ) );
    }
    
    private void update() {
        String oldValue= this.control;
        Map<String,String> controls= new LinkedHashMap();
        controls.put( RGBImageRenderer.PROP_NEARESTNEIGHBORINTERPOLATION, Renderer.encodeBooleanControl(jCheckBox1.isSelected()) );
        String c= Renderer.formatControl(controls);
        this.control= c;
        firePropertyChange( Renderer.PROP_CONTROL, oldValue, c );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lineStylePanel = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Image"));

        lineStylePanel.setLayout(new java.awt.BorderLayout());

        jCheckBox1.setText("Nearest Neighbor Interpolation");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(112, 112, 112)
                .add(lineStylePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jCheckBox1)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jCheckBox1)
                .add(68, 68, 68)
                .add(lineStylePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        update();
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel lineStylePanel;
    // End of variables declaration//GEN-END:variables
}
