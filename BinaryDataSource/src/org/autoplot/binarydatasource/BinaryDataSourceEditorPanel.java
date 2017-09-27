
/*
 * BinaryDataSourceEditorPanel.java
 *
 * Created on Nov 3, 2009, 3:35:49 PM
 */

package org.autoplot.binarydatasource;

import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.table.TableModel;
import org.das2.util.monitor.NullProgressMonitor;
import org.das2.util.monitor.ProgressMonitor;
import org.das2.qds.QDataSet;
import org.autoplot.datasource.DataSetURI;
import org.autoplot.datasource.DataSource;
import org.autoplot.datasource.DataSourceEditorPanel;
import org.das2.qds.util.QDataSetTableModel;
import java.lang.Short; // because of Short object in this package.
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import org.das2.util.filesystem.FileSystem;
import org.autoplot.datasource.URISplit;

/**
 *
 * @author jbf
 */
public class BinaryDataSourceEditorPanel extends javax.swing.JPanel implements DataSourceEditorPanel {

    private static final Logger logger= Logger.getLogger("apdss.binary");

    /**
     * Creates new form BinaryDataSourceEditorPanel
     */
    public BinaryDataSourceEditorPanel() {
        initComponents();
        InputMap im = paramsTextArea1.getInputMap();
        KeyStroke tab = KeyStroke.getKeyStroke("TAB");
        paramsTextArea1.getActionMap().put(im.get(tab), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton1ActionPerformed(e);
                try {
                    Rectangle r = paramsTextArea1.modelToView(paramsTextArea1.getCaretPosition());
                    paramsTextArea1.showPopup(r.x, r.y);
                } catch (BadLocationException ex) {

                }
            }
        });

        jTable1.getTableHeader().setReorderingAllowed(false);

    }

    String suri;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        paramsTextArea1 = new org.autoplot.datasource.ui.ParamsTextArea();
        jLabel1 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        paramsTextArea1.setColumns(20);
        paramsTextArea1.setRows(5);
        paramsTextArea1.setMaximumSize(new java.awt.Dimension(1000, 1000));
        jScrollPane1.setViewportView(paramsTextArea1);

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 0, 8)); // NOI18N
        jLabel1.setText("<html>right-click to get completions.  Click update to see how it parses.</html>");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButton1)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jButton1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 52, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String suri1 = getURI();
            DataSource dss = DataSetURI.getDataSource(suri1);
            QDataSet ds= dss.getDataSet( new NullProgressMonitor() );
            TableModel model= new QDataSetTableModel(ds);
            this.jTable1.setModel(model);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

	@Override
    public JPanel getPanel() {
        return this;
    }

	@Override
    public void setURI(String uri) {
        try {
            this.suri= uri;
            URISplit split = URISplit.parse(uri);
            DataSetURI.getFile(new URL(split.file), new NullProgressMonitor());
            Map<String, String> params= URISplit.parseParams( split.params );
            paramsTextArea1.setParams(params);
            paramsTextArea1.setFactory( new BinaryDataSourceFactory(), new ArrayList<String>() );
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

	@Override
    public void markProblems(List<String> problems) {
    
    }

	@Override
    public String getURI() {
        URISplit split = URISplit.parse(suri);
        Map<String,String> params= paramsTextArea1.getParams();
        split.params= URISplit.formatParams(params);
        return URISplit.format(split);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable jTable1;
    public org.autoplot.datasource.ui.ParamsTextArea paramsTextArea1;
    // End of variables declaration//GEN-END:variables

	@Override
    public boolean reject( String url ) throws IOException, URISyntaxException {
        URISplit split = URISplit.parse(url);
        FileSystem fs = FileSystem.create( DataSetURI.getWebURL( DataSetURI.toUri(split.path) ).toURI() );
        if ( fs.isDirectory( split.file.substring(split.path.length()) ) ) {
            return true;
        }
        return false;
    }

	@Override
    public boolean prepare(String uri, Window parent, ProgressMonitor mon) throws Exception {
        URISplit split = URISplit.parse(uri);
        DataSetURI.getFile(new URL(split.file), mon );
        return true;
    }

}