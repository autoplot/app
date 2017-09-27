/*
 * FontAndColorsDialog.java
 *
 * Created on August 6, 2007, 10:49 AM
 */
package org.autoplot;

import org.autoplot.ApplicationModel;
import org.das2.graph.DasCanvas;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.das2.graph.GraphUtil;
import org.autoplot.dom.DomUtil;
import org.autoplot.dom.PlotElement;

/**
 *
 * @author  jbf
 */
public class FontAndColorsDialog extends javax.swing.JDialog {

    ApplicationModel app;
    Color[] fores, backs;
    private final static int ICON_SIZE=16;
    
    /** Creates new form FontAndColorsDialog */
    public FontAndColorsDialog( java.awt.Frame parent, boolean modal, ApplicationModel app) {
        super(parent, modal);
        setTitle("Font and Colors");
        
        initComponents();

        this.app = app;
        DasCanvas c = app.getCanvas();

        fores = new Color[]{Color.BLACK, Color.WHITE, Color.WHITE};
        backs = new Color[]{Color.WHITE, Color.BLACK, Color.BLUE.darker()};
        String[] names = {"black on white", "white on black", "white on blue", "custom"};
        final Font f = app.getCanvas().getFont();
        fontLabel.setText(DomUtil.encodeFont(f));
        
        Runnable run= new Runnable() {
            @Override
            public void run() {
                Font f= Font.decode(fontLabel.getText());
                canEmbedFont(f);
            }
        };
        new Thread( run, "canEmbedFont" ).start();
        
        //guiFontLabel.setText( parent.getFont().toString());
        int index = 3; // custom
        for (int i = 0; i < fores.length; i++) {
            if (fores[i].equals(c.getForeground()) && backs[i].equals(c.getBackground())) {
                index = i;
            }
        }

        jComboBox1.setModel(new DefaultComboBoxModel(names));
        if ( index!=-1 ) jComboBox1.setSelectedIndex(index);

        foregroundColorButton.setIcon( GraphUtil.colorIcon( c.getForeground(), ICON_SIZE, ICON_SIZE ) );
        backgroundColorButton.setIcon( GraphUtil.colorIcon( c.getBackground(), ICON_SIZE, ICON_SIZE ) );
        
        setLocationRelativeTo(parent);
        
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        foregroundColorButton = new javax.swing.JButton();
        backgroundColorButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        pickFontButton = new javax.swing.JButton();
        dismissButton = new javax.swing.JButton();
        fontLabel = new javax.swing.JLabel();
        canEmbedFontTF = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Fore/Back Colors:");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Foreground:");

        jLabel3.setText("Background:");

        foregroundColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foregroundColorButtonActionPerformed(evt);
            }
        });

        backgroundColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backgroundColorButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Font:");

        pickFontButton.setText("Pick");
        pickFontButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pickFontButtonActionPerformed(evt);
            }
        });

        dismissButton.setText("OK");
        dismissButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dismissButtonActionPerformed(evt);
            }
        });

        fontLabel.setText("jLabel5");

        canEmbedFontTF.setFont(canEmbedFontTF.getFont().deriveFont(canEmbedFontTF.getFont().getSize()-1f));
        canEmbedFontTF.setText("   ");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(canEmbedFontTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(10, 10, 10)
                                .add(jLabel2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(foregroundColorButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jLabel1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel3)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(backgroundColorButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 173, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(0, 0, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(fontLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(pickFontButton))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(dismissButton)))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {backgroundColorButton, foregroundColorButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(jLabel3)
                    .add(backgroundColorButton)
                    .add(foregroundColorButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(pickFontButton)
                    .add(fontLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(canEmbedFontTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 103, Short.MAX_VALUE)
                .add(dismissButton)
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {backgroundColorButton, foregroundColorButton}, org.jdesktop.layout.GroupLayout.VERTICAL);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dismissButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dismissButtonActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);
        this.dispose();
    }//GEN-LAST:event_dismissButtonActionPerformed

    private void pickFontButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pickFontButtonActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);
        Font f= GuiSupport.pickFont( (JFrame) SwingUtilities.getWindowAncestor(this), app );
        if ( f!=null ) {
            fontLabel.setText( DomUtil.encodeFont(f));
            canEmbedFont(f);
        }
    }//GEN-LAST:event_pickFontButtonActionPerformed

    private void backgroundColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backgroundColorButtonActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);
        Color c = JColorChooser.showDialog(this, "background color", backgroundColorButton.getBackground());
        if ( c!=null ) {
            jComboBox1.setSelectedIndex(fores.length);
            backgroundColorButton.setIcon( GraphUtil.colorIcon( c, ICON_SIZE, ICON_SIZE ) );
            app.getCanvas().setBackground(c);
            app.getDocumentModel().getOptions().setBackground(c);
        }
        
    }//GEN-LAST:event_backgroundColorButtonActionPerformed

    private void foregroundColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foregroundColorButtonActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);
        Color c = JColorChooser.showDialog(this, "foreground color", foregroundColorButton.getBackground());
        if ( c!=null ) {
            jComboBox1.setSelectedIndex(fores.length);
            List<PlotElement> pe= Arrays.asList( app.dom.getPlotElements() );
            for ( PlotElement p: pe ) {
                if ( p.getStyle().getColor().equals(app.getCanvas().getForeground())) {
                    p.getStyle().setColor(c);
                }
            }
            foregroundColorButton.setIcon( GraphUtil.colorIcon( c, ICON_SIZE, ICON_SIZE ) );
            app.getCanvas().setForeground(c);
            app.getDocumentModel().getOptions().setForeground(c);
            app.getDocumentModel().getOptions().setColor(c);
        }
    }//GEN-LAST:event_foregroundColorButtonActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);
        int i = jComboBox1.getSelectedIndex();
        if (i < fores.length) {
            foregroundColorButton.setIcon( GraphUtil.colorIcon( fores[i], ICON_SIZE, ICON_SIZE ) );
            backgroundColorButton.setIcon( GraphUtil.colorIcon( backs[i], ICON_SIZE, ICON_SIZE ) );
            List<PlotElement> pe= Arrays.asList( app.dom.getPlotElements() );
            for ( PlotElement p: pe ) {
                if (p.getStyle().getColor().equals(app.getCanvas().getForeground())) {
                    p.getStyle().setColor(fores[i]);
                }
            }
            app.getDocumentModel().getOptions().setForeground(fores[i]);
            app.getDocumentModel().getOptions().setColor(fores[i]);
            app.getDocumentModel().getOptions().setBackground(backs[i]);
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backgroundColorButton;
    private javax.swing.JLabel canEmbedFontTF;
    private javax.swing.JButton dismissButton;
    private javax.swing.JLabel fontLabel;
    private javax.swing.JButton foregroundColorButton;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton pickFontButton;
    // End of variables declaration//GEN-END:variables

    private void canEmbedFont(Font f) {
        String s= org.das2.util.awt.PdfGraphicsOutput.ttfFromName(f);
        if ( s!=null ) {
            canEmbedFontTF.setText("PDF okay");
            canEmbedFontTF.setToolTipText("font should work in PDF files");
        } else {
            canEmbedFontTF.setText("font can not be embedded in PDF");
            canEmbedFontTF.setToolTipText("TTF file not found or licensing restricts use");
        }
    }
}
