/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VapSchemeChooser.java
 *
 * Created on Jul 21, 2011, 3:52:03 PM
 */

package org.autoplot;

/**
 *
 * @author jbf
 */
public class VapSchemeChooser extends javax.swing.JPanel {

    /** Creates new form VapSchemeChooser */
    public VapSchemeChooser() {
        initComponents();
        if ( "true".equals(System.getProperty("allowEmbedData","true")) ) {
            embedDataCheckBox.setVisible(true);
        } else {
            embedDataCheckBox.setVisible(false);
        }
    }

    /**
     * return the scheme to target.
     * @return 
     */
    public String getScheme() {
        if ( currentVersionCB.isSelected() ) {
            return "";
        } else if ( v1_09CB.isSelected() ) {
            return "1.09";            
        } else if ( v1_08CB.isSelected() ) {
            return "1.08";
        } else if ( v1_07CB.isSelected() ) {
            return "1.07";
        } else if ( v1_06CB.isSelected() ) {
            return "1.06";
        } else {
            new Exception("Selected version not implemented").printStackTrace();
            return "1.08";
        }
    }

    /**
     * return true if the scientist wants to embed data within the
     * vap.
     * @return 
     */
    public boolean isEmbedData() {
        return this.embedDataCheckBox.isSelected();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        v1_06CB = new javax.swing.JRadioButton();
        currentVersionCB = new javax.swing.JRadioButton();
        embedDataCheckBox = new javax.swing.JCheckBox();
        v1_07CB = new javax.swing.JRadioButton();
        v1_08CB = new javax.swing.JRadioButton();
        v1_09CB = new javax.swing.JRadioButton();

        buttonGroup1.add(v1_06CB);
        v1_06CB.setText("v1.06 (Autoplot 2010)");

        buttonGroup1.add(currentVersionCB);
        currentVersionCB.setSelected(true);
        currentVersionCB.setText("Save as current version");
        currentVersionCB.setToolTipText("Save as v1.07 by default.");

        embedDataCheckBox.setText("Embed data within .vap file");
        embedDataCheckBox.setToolTipText("Create zip file containing dom configuration in default.vap, as well as file resources used.");

        buttonGroup1.add(v1_07CB);
        v1_07CB.setText("v1.07 (Autoplot 2013)");
        v1_07CB.setToolTipText("Autoranging disabled");

        buttonGroup1.add(v1_08CB);
        v1_08CB.setText("v1.08 (Autoplot 2014)");
        v1_08CB.setToolTipText("v1.08 restores autorange flag within the vap file, to support servers and timerange modifier.");

        buttonGroup1.add(v1_09CB);
        v1_09CB.setText("v1.09 (Autoplot 2017)");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(v1_06CB)
                    .add(currentVersionCB)
                    .add(embedDataCheckBox)
                    .add(v1_08CB)
                    .add(v1_09CB)
                    .add(v1_07CB))
                .addContainerGap(140, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(13, 13, 13)
                .add(currentVersionCB)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(v1_09CB)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(v1_08CB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(v1_07CB)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(v1_06CB)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 36, Short.MAX_VALUE)
                .add(embedDataCheckBox)
                .add(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton currentVersionCB;
    private javax.swing.JCheckBox embedDataCheckBox;
    private javax.swing.JRadioButton v1_06CB;
    private javax.swing.JRadioButton v1_07CB;
    private javax.swing.JRadioButton v1_08CB;
    private javax.swing.JRadioButton v1_09CB;
    // End of variables declaration//GEN-END:variables

}
