/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ImportBookmarksGui.java
 *
 * Created on Dec 11, 2010, 6:24:17 AM
 */

package org.autoplot;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 * Ask the user if they would like to import the a bookmarks file.  They can
 * leave it as a remote file as well, so we will keep checking for updates.
 * @author jbf
 */
public class ImportBookmarksGui extends javax.swing.JPanel {

    /** Creates new form ImportBookmarksGui */
    public ImportBookmarksGui() {
        initComponents();
    }

    public JLabel getBookmarksFilename() {
        return bookmarksFilename;
    }

    public JCheckBox getRemote() {
        return remote;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        bookmarksFilename = new javax.swing.JLabel();
        remote = new javax.swing.JCheckBox();

        jLabel1.setText("Import bookmarks file");

        bookmarksFilename.setText("insert name of bookmarks file here");

        remote.setText("remote bookmarks folder");
        remote.setToolTipText("<html>If checked, this bookmark folder will be synchronized with <br>the remote bookmarks file when Autoplot is started.  <br>If unchecked, the bookmarks in this remote bookmarks <br>file will not be updated if the remote file changes.");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(remote))
                    .add(bookmarksFilename, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                    .add(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(bookmarksFilename)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(remote)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bookmarksFilename;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JCheckBox remote;
    // End of variables declaration//GEN-END:variables

}
