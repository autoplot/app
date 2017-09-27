
package org.autoplot.renderer;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.Map;
import org.das2.graph.Renderer;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.autoplot.PlotStylePanel;
import org.autoplot.dom.PlotElement;

/**
 * Style panel for orbit renderer
 * @author jbf
 */
public class BoundsStylePanel extends javax.swing.JPanel implements PlotStylePanel.StylePanel {

    /**
     * Creates new form DigitalStylePanel
     */
    public BoundsStylePanel() {
        initComponents();
        //note the colorPanel must have its layout set to BorderLayout.
        colorEditor1.setValue(Color.BLACK);
        colorPanel.add( colorEditor1.getSmallEditor() );
        colorEditor1.addPropertyChangeListener( new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                update();
            }
        });
        validate();
    }

    Renderer renderer;
    BindingGroup elementBindingContext;
        
    private String control = "";

    public static final String PROP_CONTROL = "control";

    public String getControl() {
        return control;
    }


    /**
     * set the control string
     * @param control the control string, e.g. "color=red&lineThick=0.2"
     */
    public void setControl(String control) {
        String oldControl = this.control;
        this.control = control;
        this.renderer.setControl(control);
        updateGUI(renderer);
        firePropertyChange(PROP_CONTROL, oldControl, control);
    }

    private void update() {
        String oldValue= this.control;
        Map<String,String> controls= new LinkedHashMap();
        controls.put( Renderer.CONTROL_KEY_COLOR, Renderer.encodeColorControl( (Color)colorEditor1.getValue() ) );
        //controls.put( "fillLabel", fillLabelTF.getText() );
        String c= Renderer.formatControl(controls);
        this.control= c;
        firePropertyChange( Renderer.PROP_CONTROL, oldValue, c );
    }
    private void updateGUI( Renderer renderer ) {
        this.control= renderer.getControl();
        colorEditor1.setValue( renderer.getColorControl( Renderer.CONTROL_KEY_COLOR, (Color)colorEditor1.getValue() ) );
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        colorEditor1 = new org.das2.components.propertyeditor.ColorEditor();
        jLabel2 = new javax.swing.JLabel();
        colorPanel = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Bounds"));

        jLabel2.setText("Color:");

        colorPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(colorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(134, 134, 134)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addComponent(colorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(127, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.das2.components.propertyeditor.ColorEditor colorEditor1;
    private javax.swing.JPanel colorPanel;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
