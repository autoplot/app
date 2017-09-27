/*
 * PlotStylePanel.java
 *
 * Created on July 27, 2007, 9:41 AM
 */
package org.autoplot.renderer;

import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import org.das2.components.DatumEditor;
import org.das2.components.propertyeditor.ColorEditor;
import org.das2.components.propertyeditor.EnumerationEditor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.beans.PropertyChangeListener;
import javax.swing.SpinnerNumberModel;
import org.autoplot.help.AutoplotHelpSystem;
import org.das2.graph.DasColorBar;
import org.das2.graph.DefaultPlotSymbol;
import org.das2.graph.PsymConnector;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.autoplot.PlotStylePanel;
import org.autoplot.dom.Application;
import org.autoplot.dom.ApplicationController;
import org.autoplot.dom.PlotElement;
import org.autoplot.dom.PlotElementStyle;

/**
 *
 * @author  jbf
 */
public class ColorScatterStylePanel extends javax.swing.JPanel implements PlotStylePanel.StylePanel {
    
    EnumerationEditor psymEditor;
    EnumerationEditor lineEditor;
    EnumerationEditor edit;
    EnumerationEditor rebin;
    ColorEditor colorEditor;
    ColorEditor fillColorEditor;
    DatumEditor referenceEditor;
    BindingGroup elementBindingContext;

    /** Creates new form PlotStylePanel */
    public ColorScatterStylePanel( ) {
                
        initComponents();

        edit = new EnumerationEditor();
        edit.setValue( DasColorBar.Type.GRAYSCALE );
        colortableTypePanel.add(edit.getCustomEditor(), BorderLayout.CENTER);

        symSizeSpinner.setModel(new SpinnerNumberModel(2.0f, 0.09f, 10.f, 0.2f));

        psymEditor = new EnumerationEditor();
        psymEditor.setValue( DefaultPlotSymbol.BOX );
        psymPanel.add(psymEditor.getCustomEditor(), BorderLayout.CENTER);

        lineEditor = new EnumerationEditor();
        lineEditor.setValue( PsymConnector.SOLID );
        lineStylePanel.add(lineEditor.getCustomEditor(), BorderLayout.CENTER);

        lineThickSpinner.setModel(new SpinnerNumberModel(1.0f, 0.09f, 10.f, 0.2f));

        colorEditor = new ColorEditor();
        colorEditor.setValue( Color.BLACK );
        colorPanel.add(colorEditor.getSmallEditor(), BorderLayout.CENTER);

        fillColorEditor = new ColorEditor();

        fillColorEditor.setValue( Color.DARK_GRAY );
        fillColorPanel.add(fillColorEditor.getSmallEditor(), BorderLayout.CENTER);

        referenceEditor = new DatumEditor();
        java.awt.Component refedit= referenceEditor.getCustomEditor();
        refedit.addFocusListener( new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                referenceEditor.stopCellEditing();
            }
        });
        referenceValuePanel.add(refedit);

        validate();

    }

    public void releaseElementBindings() {
        if ( elementBindingContext!=null ) {
            elementBindingContext.unbind();
            elementBindingContext= null;
        }
        AutoplotHelpSystem.getHelpSystem().unregisterHelpID(this, PlotStylePanel.STYLEPANEL_HELP_ID );
    }

    public synchronized void doElementBindings(PlotElement element) {
        PlotElementStyle style= element.getStyle();
        BindingGroup bc = new BindingGroup();

        bc.addBinding(Bindings.createAutoBinding( UpdateStrategy.READ_WRITE, style, BeanProperty.create( "colortable" ), edit, BeanProperty.create("value")));

        bc.addBinding(Bindings.createAutoBinding( UpdateStrategy.READ_WRITE, style,BeanProperty.create(  "symbolSize" ), symSizeSpinner, BeanProperty.create("value")) );
        bc.addBinding(Bindings.createAutoBinding( UpdateStrategy.READ_WRITE, style, BeanProperty.create( "plotSymbol" ), psymEditor,BeanProperty.create( "value")));
        bc.addBinding(Bindings.createAutoBinding( UpdateStrategy.READ_WRITE, style, BeanProperty.create( "lineWidth" ), lineThickSpinner, BeanProperty.create("value")));
        bc.addBinding(Bindings.createAutoBinding( UpdateStrategy.READ_WRITE, style, BeanProperty.create( "symbolConnector" ), lineEditor, BeanProperty.create("value")));

        bc.addBinding(Bindings.createAutoBinding( UpdateStrategy.READ_WRITE, style, BeanProperty.create( "color" ), colorEditor, BeanProperty.create("value")));
        bc.addBinding(Bindings.createAutoBinding( UpdateStrategy.READ_WRITE, style, BeanProperty.create( "fillToReference" ), fillToReferenceCheckBox, BeanProperty.create("selected")));
        bc.addBinding(Bindings.createAutoBinding( UpdateStrategy.READ_WRITE, style, BeanProperty.create( "fillColor" ), fillColorEditor, BeanProperty.create("value")));
        bc.addBinding(Bindings.createAutoBinding( UpdateStrategy.READ_WRITE, style, BeanProperty.create( "reference" ), referenceEditor, BeanProperty.create("value")));
        
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
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lineThickSpinner = new javax.swing.JSpinner();
        symSizeSpinner = new javax.swing.JSpinner();
        colorPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fillColorPanel = new javax.swing.JPanel();
        fillToReferenceCheckBox = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        referenceValuePanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        psymPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lineStylePanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        colortableTypePanel = new javax.swing.JPanel();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Color Scatter [?]"));

        jLabel3.setText("line thickness:");
        jLabel3.setToolTipText("thickness of the plot trace");

        jLabel2.setText("symbol size:");
        jLabel2.setToolTipText("size of the plot symbols");

        lineThickSpinner.setToolTipText("thickness of the plot trace");

        symSizeSpinner.setToolTipText("size of the plot symbols");

        colorPanel.setLayout(new java.awt.BorderLayout());

        jLabel6.setText("line color:");
        jLabel6.setToolTipText("color of the line and plot symbols");

        jLabel7.setText("fill color:");
        jLabel7.setToolTipText("Fill with this color");

        fillColorPanel.setToolTipText("fill with this color");
        fillColorPanel.setLayout(new java.awt.BorderLayout());

        fillToReferenceCheckBox.setText("fill to reference");
        fillToReferenceCheckBox.setToolTipText("Fill from the plot trace to a reference value");
        fillToReferenceCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        fillToReferenceCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fillToReferenceCheckBoxActionPerformed(evt);
            }
        });

        jLabel8.setText("reference value:");
        jLabel8.setToolTipText("Fill to this value");

        referenceValuePanel.setToolTipText("fill to this value");
        referenceValuePanel.setLayout(new java.awt.BorderLayout());

        jLabel9.setText("plot symbol:");
        jLabel9.setToolTipText("type of symbol, or none.");

        psymPanel.setLayout(new java.awt.BorderLayout());

        jLabel10.setText("line style:");
        jLabel10.setToolTipText("style of the plot trace, or none");

        lineStylePanel.setLayout(new java.awt.BorderLayout());

        jLabel4.setText("colortable:");
        jLabel4.setToolTipText("colortable for spectrograms");

        colortableTypePanel.setToolTipText("colortable for spectrograms\n");
        colortableTypePanel.setLayout(new java.awt.BorderLayout());

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel3)
                            .add(jLabel10))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(lineThickSpinner, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 61, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(lineStylePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(fillToReferenceCheckBox)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel9)
                            .add(jLabel2)
                            .add(jLabel6)
                            .add(jLabel4))
                        .add(23, 23, 23)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, colortableTypePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, colorPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, psymPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, symSizeSpinner, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(177, 177, 177))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jLabel7)
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .add(63, 63, 63)
                                        .add(referenceValuePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 89, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .add(41, 41, 41)
                                        .add(fillColorPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 126, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                            .add(jLabel8)))))
        );

        jPanel2Layout.linkSize(new java.awt.Component[] {colorPanel, colortableTypePanel, fillColorPanel, lineStylePanel, psymPanel}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel2Layout.linkSize(new java.awt.Component[] {lineThickSpinner, symSizeSpinner}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(colortableTypePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(psymPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel9))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(symSizeSpinner, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(colorPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel10)
                    .add(lineStylePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(lineThickSpinner, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(fillToReferenceCheckBox)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel7)
                    .add(fillColorPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel8)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, referenceValuePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(new java.awt.Component[] {fillColorPanel, jLabel7}, org.jdesktop.layout.GroupLayout.VERTICAL);

        jPanel2Layout.linkSize(new java.awt.Component[] {jLabel10, jLabel3, lineStylePanel}, org.jdesktop.layout.GroupLayout.VERTICAL);

        jPanel2Layout.linkSize(new java.awt.Component[] {colorPanel, jLabel2, jLabel6, jLabel9, psymPanel}, org.jdesktop.layout.GroupLayout.VERTICAL);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void fillToReferenceCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fillToReferenceCheckBoxActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);                
    }//GEN-LAST:event_fillToReferenceCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel colorPanel;
    private javax.swing.JPanel colortableTypePanel;
    private javax.swing.JPanel fillColorPanel;
    private javax.swing.JCheckBox fillToReferenceCheckBox;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel lineStylePanel;
    private javax.swing.JSpinner lineThickSpinner;
    private javax.swing.JPanel psymPanel;
    private javax.swing.JPanel referenceValuePanel;
    private javax.swing.JSpinner symSizeSpinner;
    // End of variables declaration//GEN-END:variables
}
