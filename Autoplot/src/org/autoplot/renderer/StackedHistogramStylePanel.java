
package org.autoplot.renderer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.Map;
import org.das2.components.propertyeditor.EnumerationEditor;
import org.das2.graph.Renderer;
import org.das2.graph.StackedHistogramRenderer;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.autoplot.PlotStylePanel.StylePanel;
import org.autoplot.dom.PlotElement;

/**
 * specialized GUI for controlling the renderer.
 * @author jbf
 */
public class StackedHistogramStylePanel extends javax.swing.JPanel implements StylePanel {

    /**
     * Creates new form StackedHistogramStylePane
     */
    public StackedHistogramStylePanel() {
        initComponents();
        peaksIndicatorEditor= new EnumerationEditor();
        peaksIndicatorEditor.setValue( StackedHistogramRenderer.PeaksIndicator.GrayPeaks );
        peaksIndicatorPanel.add( peaksIndicatorEditor.getCustomEditor() );
        this.revalidate();
        peaksIndicatorEditor.addPropertyChangeListener( new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                update();
            }
        });   
    }
    
    private void update() {
        String oldValue= this.control;
        Map<String,String> controls= new LinkedHashMap();
        controls.put( "peaksIndicator", peaksIndicatorEditor.getAsText() );
        String c= Renderer.formatControl(controls);
        this.control= c;
        firePropertyChange( Renderer.PROP_CONTROL, oldValue, c );
    }    
    
    BindingGroup elementBindingContext;
    
    Renderer renderer;
    
    public static final String PROP_CONTROL = "control";
    
    String control;
    
    public String getControl() {
        return "peaksIndicator="+peaksIndicatorEditor.getValue().toString();
    }
    
    public void setControl( String control ) {
        String oldControl = this.control;
        this.control = control;
        this.renderer.setControl(control);
        firePropertyChange( PROP_CONTROL, oldControl, control);
    }
    
    EnumerationEditor peaksIndicatorEditor;
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        peaksIndicatorPanel = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Stacked Histogram")));

        jLabel1.setText("Peaks Indicator:");

        peaksIndicatorPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(peaksIndicatorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 265, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(peaksIndicatorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(233, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel peaksIndicatorPanel;
    // End of variables declaration//GEN-END:variables

    private void updateGUI( Renderer renderer ) {
        this.control= renderer.getControl();
        if ( renderer.hasControl( "peaksIndicator" ) ) {
            String v= renderer.getControl( "peaksIndicator", "GreyPeaks" );
            peaksIndicatorEditor.setAsText( v );
        }
    }    
    
    @Override
    public void doElementBindings(PlotElement element) {
        this.renderer= element.getController().getRenderer();
        updateGUI( renderer );
        
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
}
