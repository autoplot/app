/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SourceTypesBrowser.java
 *
 * Created on Sep 10, 2009, 6:09:09 AM
 */

package org.autoplot.datasource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 * Dialog for explicitly selecting data source type.
 * @author jbf
 */
public class SourceTypesBrowser extends javax.swing.JPanel {

    List<String> types;

    /** Creates new form SourceTypesBrowser */
    public SourceTypesBrowser() {
        initComponents();
        initTypes();
        dataSetSelector1.setHidePlayButton(true);
        dataSetSelector1.setDisableDataSources(true);
    }

    public DataSetSelector getDataSetSelector() {
        return dataSetSelector1;
    }

    private static String getDescriptionFor( String ext ) {
        if ( ext.equals("dat") || ext.equals("txt" ) ) {
            return "Ascii table";
        } else if ( ext.equals("csv") ) {
            return "Comma Separated Values Ascii Table";
        } else if ( ext.equals("cdf") ) {
            return "NASA Common Data Format";
        } else if ( ext.equals("cdfj") ) {
            return "NASA Common Data Format (Java reader)";
        } else if ( ext.equals("cdaweb") ) {
            return "NASA CDAWeb database";
        } else if ( ext.equals("nc") || ext.equals("ncml") ) {
            return "NetCDF";
        } else if ( ext.equals("h5") || ext.equals("hdf5") ) {
            return "HDF5 data model file";
        } else if ( ext.equals("jyds") ) {
            return "Autoplot Jython Script";
        } else if ( ext.equals("inline") ) {
            return "Array literals and Jython code defining datasets";
        } else if ( ext.equals("htm") ) {
            return "Tables within HTML files";
        } else {
            return "";
        }
    }
    
    
    private void initTypes() {
        DefaultComboBoxModel model= new DefaultComboBoxModel();
        List<CompletionContext> cc= DataSourceRegistry.getPlugins();
        types= new ArrayList(cc.size());
        for ( int i=0; i<cc.size(); i++ ) {
            String label= cc.get(i).completable;
            label= label.substring(4,label.length()-1);//vap+
            String desc= getDescriptionFor(label);
            if ( !desc.equals("") ) {
                label= label+": "+desc;
            }
            types.add(i,label );
        }
        Collections.sort(types);
        for ( int i=0; i<types.size(); i++ ) {
            model.insertElementAt(types.get(i),i);
        }
        this.sourceTypesComboBox.setModel(model);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sourceTypesComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dataSetSelector1 = new org.autoplot.datasource.DataSetSelector();
        jLabel3 = new javax.swing.JLabel();

        sourceTypesComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Select Data Source Type:");

        jLabel2.setText("Select File:");

        jLabel3.setText("<html>Autoplot has data source plugins that are used to read in data, and the plug-in can not be identified from the file extention.  Select the data source type for the file.  ");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(dataSetSelector1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel1)
                            .add(jLabel2))
                        .add(0, 0, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, sourceTypesComboBox, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(15, 15, 15)
                .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 62, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sourceTypesComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(20, 20, 20)
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(dataSetSelector1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.autoplot.datasource.DataSetSelector dataSetSelector1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JComboBox sourceTypesComboBox;
    // End of variables declaration//GEN-END:variables

    public String getUri() {
        if ( sourceTypesComboBox.getSelectedIndex()==-1 ) {
            return dataSetSelector1.getValue();
        } else {
            String s= types.get( sourceTypesComboBox.getSelectedIndex() );
            int i= s.indexOf(':');
            if ( i>-1 ) s= s.substring(0,i);
            return "vap+"+ s + ":" + dataSetSelector1.getValue();
        }
    }

}
