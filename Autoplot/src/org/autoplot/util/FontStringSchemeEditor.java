/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.autoplot.util;

import ZoeloeSoft.projects.JFontChooser.JFontChooser;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import static org.autoplot.GuiSupport.setFont;
import org.das2.util.Entities;
import org.das2.util.StringSchemeEditor;
import org.das2.util.awt.PdfGraphicsOutput;

/**
 *
 * @author jbf
 */
public class FontStringSchemeEditor extends javax.swing.JPanel implements StringSchemeEditor {

    /**
     * Creates new form FontStringSchemeEditor
     */
    public FontStringSchemeEditor() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of
     * this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        jLabel1.setText("Font:");

        jTextField1.setText("jTextField1");

        jButton1.setText("Pick");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFontChooser chooser = new JFontChooser( (JFrame) SwingUtilities.getWindowAncestor(this) );
        String sci= Entities.decodeEntities("2 &times; 10E7  &aacute;");
        String greek= Entities.decodeEntities("Greek Symbols: &Alpha; &Beta; &Delta; &alpha; &beta; &delta; &pi; &rho; &omega;");
        String math= Entities.decodeEntities("Math Symbols: &sum; &plusmn;");

        chooser.setExampleText("Electron Differential Energy Flux\n2001-01-10 12:00\nExtended ASCII: "+sci+"\n"+greek+"\n"+math);
        chooser.setFontCheck( new JFontChooser.FontCheck() {
            @Override
            public String checkFont(Font c) {
                Object font= PdfGraphicsOutput.ttfFromNameInteractive(c);
                if ( font==PdfGraphicsOutput.READING_FONTS ) {
                    return "Checking which fonts are embeddable...";
                } else if ( font!=null ) {
                    return "PDF okay";
                } else {                    
                    return "Can not be embedded in PDF";
                }
            }
        });
        chooser.setFont( Font.decode(jTextField1.getText()) );
        chooser.setLocationRelativeTo(this);
        if (chooser.showDialog() == JFontChooser.OK_OPTION) {
            this.jTextField1.setText(chooser.getFont().getFontName());
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setValue(String v) {
        this.jTextField1.setText(v);
    }

    @Override
    public String getValue() {
        return jTextField1.getText();
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void setContext(Object o) {
        
    }
}
