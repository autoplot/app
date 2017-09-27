/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GuiExceptionHandlerSubmitForm.java
 *
 * Created on Sep 25, 2009, 10:10:53 AM
 */

package org.autoplot.scriptconsole;

import javax.swing.JTextArea;

/**
 *
 * @author jbf
 */
public class GuiExceptionHandlerSubmitForm extends javax.swing.JPanel {

    GuiExceptionHandler guiExceptionHandler;

    /** Creates new form GuiExceptionHandlerSubmitForm */
    public GuiExceptionHandlerSubmitForm() {
        initComponents();
    }

    public JTextArea getDataTextArea() {
        return dataTextArea;
    }


    public JTextArea getUserTextArea() {
        return userTextArea;
    }

    /**
     * @return the emailTextField
     */
    public javax.swing.JTextField getEmailTextField() {
        return emailTextField;
    }
    
    /**
     * @return the usernameTextField
     */
    public javax.swing.JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public boolean isAllowDom() {
        return submitDOMCB.isSelected();
    }

    public boolean isAllowScreenshot() {
        return screenshotCB.isSelected();
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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        dataTextArea = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        emailTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        submitDOMCB = new javax.swing.JCheckBox();
        updateButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        screenshotCB = new javax.swing.JCheckBox();

        jLabel1.setText("<html>Press \"Submit\" to submit stack traces, application state, and undo information to the server.  Optionally, add comments briefly describing actions preceding the event.  Your usename is automatically detected, and provide an email address if you'd like to be contacted.</html> ");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel2.setText("Preview of data that will be submitted:");

        userTextArea.setColumns(20);
        userTextArea.setLineWrap(true);
        userTextArea.setRows(5);
        jScrollPane1.setViewportView(userTextArea);

        dataTextArea.setEditable(false);
        dataTextArea.setColumns(20);
        dataTextArea.setFont(dataTextArea.getFont().deriveFont(dataTextArea.getFont().getSize()-2f));
        dataTextArea.setRows(5);
        jScrollPane2.setViewportView(dataTextArea);

        jLabel3.setText("Your Email Address (optional):");
        jLabel3.setToolTipText("<html>Your email address is kept private, and will only provide a means<br>\nfor us to contact you if we have additional questions.  <br>\nAny feedback is helpful, but we are often unable reproduce <br>\nerrors when data is not available.\n");

        jLabel4.setText("Your Username (optional):");
        jLabel4.setToolTipText("Your username is read from the system properties.");

        submitDOMCB.setSelected(true);
        submitDOMCB.setText("Submit .vap and Undo Info");
        submitDOMCB.setToolTipText("<html>Submit the DOM which is the current application state, <br>which contains references to data you are using.  Undo info contains <br>changes which lead to the current state, and may contain references to data.   <br>The preview area below shows exactly what will be submitted.\n");

        updateButton.setText("Update Preview");
        updateButton.setToolTipText("Update data preview area below, showing exactly what data is submitted.");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Comments (optional):");

        screenshotCB.setText("Submit Application Screenshot");
        screenshotCB.setToolTipText("<html>Grab a screenshot and embed it within the report.  <br>\nTo see this content, save exception report to a local file, then <br>\nuse uudeview on Linux to extract the png image.  ");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel4)
                                    .add(jLabel3))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(emailTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 349, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(usernameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 133, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(layout.createSequentialGroup()
                                .add(submitDOMCB)
                                .add(62, 62, 62)
                                .add(screenshotCB)))
                        .add(0, 139, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(updateButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel5)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 73, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(usernameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(emailTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(4, 4, 4)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(submitDOMCB)
                    .add(screenshotCB))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(updateButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        dataTextArea.setText( guiExceptionHandler.updateText( this, userTextArea.getText() ) );
    }//GEN-LAST:event_updateButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea dataTextArea;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JCheckBox screenshotCB;
    private javax.swing.JCheckBox submitDOMCB;
    private javax.swing.JButton updateButton;
    private javax.swing.JTextArea userTextArea;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables

    void setGuiExceptionHandler(GuiExceptionHandler h) {
        this.guiExceptionHandler= h;
    }


}