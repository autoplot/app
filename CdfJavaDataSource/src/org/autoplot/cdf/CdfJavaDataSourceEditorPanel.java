
package org.autoplot.cdf;

import gov.nasa.gsfc.spdf.cdfj.CDFException;
import gov.nasa.gsfc.spdf.cdfj.CDFReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.autoplot.help.AutoplotHelpSystem;
import org.das2.util.DasExceptionHandler;
import org.das2.util.filesystem.FileSystem;
import org.das2.util.monitor.NullProgressMonitor;
import org.das2.util.monitor.ProgressMonitor;
import org.das2.qds.QDataSet;
import org.autoplot.datasource.DataSetSelector;
import org.autoplot.datasource.DataSetURI;
import org.autoplot.datasource.DataSourceEditorPanel;
import org.autoplot.datasource.URISplit;

/**
 * Editor panel for CDF files.  The "Java" part of the name comes from this is a
 * second implementation of the CDF reader, where the first was a native reader
 * interfaced to Autoplot via Java Native Interface.
 * @author jbf
 */
public class CdfJavaDataSourceEditorPanel extends javax.swing.JPanel implements DataSourceEditorPanel {
    public static final String NO_PLOTTABLE_PARAMETERS_MSG = "<html><i>No plottable parameters</i></html>";

    /** the maximum number of DEPEND_1 channels where we should show option for depend_1. */
    private static final int MAX_SLICE1_OFFER = 32;

    private final static Logger logger= Logger.getLogger( "apdss.cdf" );

    private boolean isValidCDF= false;
    
    /** Creates new form AggregatingDataSourceEditorPanel */
    public CdfJavaDataSourceEditorPanel() {
        initComponents();
        parameterTree.getSelectionModel().setSelectionMode( TreeSelectionModel.SINGLE_TREE_SELECTION );
        jPanel3.setVisible(false);
        if ( AutoplotHelpSystem.getHelpSystem()!=null ) { // to help with debugging, check for null so we needn't initialize all of Autoplot to debug.
            AutoplotHelpSystem.getHelpSystem().registerHelpID(this, "cdf_main");
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel1 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        subsetComboBox = new javax.swing.JComboBox();
        interpretMetadataLabel = new javax.swing.JLabel();
        noInterpMeta = new javax.swing.JCheckBox();
        noDep = new javax.swing.JCheckBox();
        showAllVarTypeCB = new javax.swing.JCheckBox();
        whereCB = new javax.swing.JCheckBox();
        whereParamList = new javax.swing.JComboBox();
        whereOp = new javax.swing.JComboBox();
        whereTF = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        parameterTree = new javax.swing.JTree();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        parameterTree1 = new javax.swing.JTree();
        xCheckBox = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        parameterTree2 = new javax.swing.JTree();
        yCheckBox = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        paramInfo = new javax.swing.JLabel();

        setName("cdfDataSourceEditorPanel"); // NOI18N
        setPreferredSize(new java.awt.Dimension(615, 452));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(615, 452));

        jSplitPane2.setDividerLocation(230);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jSplitPane1.setDividerLocation(320);
        jSplitPane1.setResizeWeight(1.0);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Advanced"));
        jPanel3.setMaximumSize(new java.awt.Dimension(285, 32767));

        jLabel4.setText("Load subset of the data:");
        jLabel4.setToolTipText("<html>Load a subset of the data records, for example:<br>[0:100]  first 100 records<br> [-100:] last 100 records<br> [::10] every tenth record<br> </html>");

        subsetComboBox.setEditable(true);
        subsetComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "::10", "0:100", "-100:", "0:10000:5" }));
        subsetComboBox.setToolTipText("<html>Load a subset of the data records, for example:<br>[0:100]  first 100 records<br> [-100:] last 100 records<br> [::10] every tenth record<br> </html>");

        interpretMetadataLabel.setText("Interpret Metadata:");

        noInterpMeta.setText("no ISTP");
        noInterpMeta.setToolTipText("Don't interpret metadata to get titles and units.");

        noDep.setText("no dependencies");
        noDep.setToolTipText("Ignore connections between variables like \"DEPEND_0\"\n");

        showAllVarTypeCB.setText("show all");
        showAllVarTypeCB.setToolTipText("show all parameters, even if ISTP VAR_TYPE is not \"data\"");
        showAllVarTypeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllVarTypeCBActionPerformed(evt);
            }
        });

        whereCB.setText("Only load data where:");
        whereCB.setToolTipText("return only the records where the condition is true");

        whereParamList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, whereCB, org.jdesktop.beansbinding.ELProperty.create("${selected}"), whereParamList, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        whereOp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ".eq", ".gt", ".lt", ".ne", ".within" }));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, whereCB, org.jdesktop.beansbinding.ELProperty.create("${selected}"), whereOp, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        whereTF.setText("0");
        whereTF.setToolTipText("enter the value, or \"mode\" for the most frequently occuring value.");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, whereCB, org.jdesktop.beansbinding.ELProperty.create("${selected}"), whereTF, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(whereParamList, 0, 209, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(whereOp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 84, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(whereTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 53, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel4)
                    .add(interpretMetadataLabel)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(subsetComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(showAllVarTypeCB)
                            .add(jPanel3Layout.createSequentialGroup()
                                .add(noInterpMeta)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(noDep))))
                    .add(whereCB))
                .add(0, 147, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(subsetComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(whereCB)
                .add(8, 8, 8)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(whereParamList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(whereOp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(whereTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(interpretMetadataLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(noInterpMeta)
                    .add(noDep))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(showAllVarTypeCB)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel3);

        parameterTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                parameterTreeValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(parameterTree);

        jTabbedPane1.addTab("Data", jScrollPane3);

        parameterTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                parameterTree1ValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(parameterTree1);

        xCheckBox.setText("Set Variable for X");
        xCheckBox.setToolTipText("Specify the parameter to use for the X tags, overriding any settings found in the file.");
        xCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xCheckBoxActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(xCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
            .add(jScrollPane4)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(xCheckBox)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("X", jPanel2);

        parameterTree2.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                parameterTree2ValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(parameterTree2);

        yCheckBox.setText("Set Variable for Y");
        yCheckBox.setToolTipText("Specify the parameter to use for the Y tags, overriding any settings found in the file.");

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(yCheckBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
            .add(jScrollPane5)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(yCheckBox)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Y", jPanel4);

        jSplitPane1.setLeftComponent(jTabbedPane1);

        jSplitPane2.setTopComponent(jSplitPane1);

        jScrollPane2.setMaximumSize(new java.awt.Dimension(1000, 600));

        paramInfo.setText("Variable");
        paramInfo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        paramInfo.setMaximumSize(new java.awt.Dimension(1000, 4000));
        paramInfo.setPreferredSize(new java.awt.Dimension(600, 100));
        paramInfo.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jScrollPane2.setViewportView(paramInfo);

        jSplitPane2.setRightComponent(jScrollPane2);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jSplitPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jSplitPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void parameterTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_parameterTree1ValueChanged
        xCheckBox.setSelected(true);
    }//GEN-LAST:event_parameterTree1ValueChanged

    private void parameterTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_parameterTreeValueChanged
        TreePath tp= evt.getPath();
        if ( isValidCDF ) {
            parameter= String.valueOf(tp.getPathComponent(1));
            updateMetadata();
        }
    }//GEN-LAST:event_parameterTreeValueChanged

    private void showAllVarTypeCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllVarTypeCBActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);
        setURI( getURI() );
    }//GEN-LAST:event_showAllVarTypeCBActionPerformed

    private void parameterTree2ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_parameterTree2ValueChanged
        yCheckBox.setSelected(true);
    }//GEN-LAST:event_parameterTree2ValueChanged

    private void xCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_xCheckBoxActionPerformed

    private void updateMetadata() {
       String longName= parameterInfo.get(parameter);
       paramInfo.setText( longName );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel interpretMetadataLabel;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JCheckBox noDep;
    private javax.swing.JCheckBox noInterpMeta;
    private javax.swing.JLabel paramInfo;
    private javax.swing.JTree parameterTree;
    private javax.swing.JTree parameterTree1;
    private javax.swing.JTree parameterTree2;
    private javax.swing.JCheckBox showAllVarTypeCB;
    private javax.swing.JComboBox subsetComboBox;
    private javax.swing.JCheckBox whereCB;
    private javax.swing.JComboBox whereOp;
    private javax.swing.JComboBox whereParamList;
    private javax.swing.JTextField whereTF;
    private javax.swing.JCheckBox xCheckBox;
    private javax.swing.JCheckBox yCheckBox;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    @Override
    public JPanel getPanel() {
        if (showAllInitially) {
            SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                    showAllVarTypeCB.setSelected(true);
                    setURI( getURI() );
                }
            });
        }     
        return this;
    }


    JComponent delegateComponent = null;
    DataSetSelector delegateDataSetSelector=null;
    DataSourceEditorPanel delegateEditorPanel= null;
    
    /**
     * extra URI parameters that are not supported in the dialog.
     */
    Map<String,String> params;
    
    String vapScheme;
    
    /**
     * the location of the CDF file.
     */
    URI resourceUri;
    
    /**
     * short descriptions of the parameters
     */
    Map<String,String> parameterDescriptions;

    /**
     * long descriptions html formatted metadata about each parameter.
     */
    Map<String,String> parameterInfo;

    String parameter;

    boolean showAllInitially= false;
    
    /**
     * can I reuse the slice?  Only if the maxRec doesn't change.
     */
    long subsetMaxRec=-1;

    File cdfFile;
    CDFReader cdf;
    Throwable cdfException;

    /**
     * allow more abstract sources, namely cdaweb, to turn off these controls.
     * @param v
     */
    public void setShowAdvancedSubpanel( boolean v ) {
        interpretMetadataLabel.setVisible(v);
        noDep.setVisible(v);
        noInterpMeta.setVisible(v);
        showAllVarTypeCB.setVisible(v);
    }
    
    @Override
    public boolean reject( String url ) throws IOException, URISyntaxException {
        URISplit split = URISplit.parse(url); 

        if ( split.resourceUri==null ) {
            return true;
        }
        
        FileSystem fs = FileSystem.create( DataSetURI.getWebURL( DataSetURI.toUri(split.path) ).toURI() );
        return fs.isDirectory( split.file.substring(split.path.length()) );
    }

    @Override
    public boolean prepare( String url,  java.awt.Window parent, ProgressMonitor mon) throws Exception {
        URISplit split= URISplit.parse(url);

        cdfFile= DataSetURI.getFile( split.resourceUri, mon );
        DataSetURI.checkLength(cdfFile);

        logger.log(Level.FINE, "opening cdf file {0}", cdfFile.toString());
        try {
            CdfDataSource.checkCdf(cdfFile);
            cdf = CdfDataSource.getCdfFile(cdfFile.toString());
            if ( cdf==null ) {
                throw new IllegalArgumentException("file is not a CDF file");
            }
            cdfException= null;
        } catch ( IOException | IllegalArgumentException ex ) {
            cdfException= ex;
        }
        return true;
    }

    @Override
    public void setURI(String url) {
        URISplit split= URISplit.parse(url);
        
        vapScheme= split.vapScheme;
        
        Map<String,String> lparams= URISplit.parseParams(split.params);

        try {
            resourceUri= split.resourceUri;
            
            cdfFile= DataSetURI.getFile( split.resourceUri, new NullProgressMonitor() );
            DataSetURI.checkLength(cdfFile);
            
            String fileName= cdfFile.toString();

            logger.log(Level.FINE, "opening cdf file {0}", fileName);
            if ( cdf==null && cdfException==null ) {
                try {
                    cdf = CdfDataSource.getCdfFile(fileName);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
    
            if ( cdfException!=null ) {
                this.parameterTree.setModel( new DefaultTreeModel( new DefaultMutableTreeNode("") ) );
                this.paramInfo.setText( "<html>Unable to read CDF file:<br>"+cdfException.getMessage() );
                return;
            }
            
            logger.finest("inspect cdf for plottable parameters");
            
            boolean isMaster= fileName.contains("MASTERS");
            parameterDescriptions= org.autoplot.cdf.CdfUtil.getPlottable( cdf, !this.showAllVarTypeCB.isSelected(), QDataSet.MAX_RANK, false, false );
            
            Map<String,String> allParameterInfo= org.autoplot.cdf.CdfUtil.getPlottable( cdf, false, QDataSet.MAX_RANK, true, isMaster );
            Map<String,String> dataParameterInfo= org.autoplot.cdf.CdfUtil.getPlottable( cdf, true, QDataSet.MAX_RANK, true, isMaster );
            Map<String,String> whereParameterInfo= org.autoplot.cdf.CdfUtil.getPlottable( cdf, false, 2, false, isMaster );

            if ( dataParameterInfo.isEmpty() ) {
                this.showAllVarTypeCB.setSelected(true);
                parameterDescriptions= org.autoplot.cdf.CdfUtil.getPlottable( cdf, !this.showAllVarTypeCB.isSelected(), QDataSet.MAX_RANK, false, false );
                this.showAllVarTypeCB.setEnabled(false);
            }
            
            String label;
            if ( this.showAllVarTypeCB.isSelected() ) {
                parameterInfo= allParameterInfo;
                label= "Select CDF Variable (%d data, %d support):";
            } else {
                parameterInfo= dataParameterInfo;
                label= "Select CDF Variable (%d data, %d support not shown):";
            }
            
            this.isValidCDF= true;
            jPanel3.setVisible(true);
            
            int numData= dataParameterInfo.size();
            int numSupport= allParameterInfo.size() - numData;

            //this.selectVariableLabel.setText( String.format( label, numData, numSupport ) );
            if ( this.showAllVarTypeCB.isSelected() ) {
                this.jTabbedPane1.setTitleAt( 0, String.format( "Select CDF Variable (of %d)", numData+numSupport ) );
            } else {
                this.jTabbedPane1.setTitleAt( 0, String.format( "Select CDF Variable (of %d)", numData ) );
            }
            
            this.jTabbedPane1.setToolTipText( String.format( label, numData, numSupport ) );
            if ( this.showAllVarTypeCB.isSelected() ) {
                this.showAllVarTypeCB.setText("show all ("+numSupport+" support shown)");
            } else {
                this.showAllVarTypeCB.setText("show all ("+numSupport+" support not shown)");
            }
            
            Pattern slice1pattern= Pattern.compile("\\[\\:\\,(\\d+)\\]");
            String slice1= lparams.remove("slice1"); // legacy

            String param= lparams.remove("arg_0");
            String subset= null;
            if ( param!=null ) {
                int i= param.indexOf("[");
                if ( i!=-1 ) {
                    subset= param.substring(i);
                    param= param.substring(0,i);
                    Matcher m= slice1pattern.matcher(subset);
                    if ( m.matches() ) {
                        slice1= m.group(1);
                        subset= null;
                    }
                }
            }

            if ( allParameterInfo.containsKey(param) ) {
                if ( !dataParameterInfo.containsKey(param) ) {
                    showAllInitially= true;
                }
            }
            
            fillTree( this.parameterTree, parameterDescriptions, cdf, param, slice1 );
            
            Map<String,String> parameterDescriptions2= org.autoplot.cdf.CdfUtil.getPlottable( cdf, false, QDataSet.MAX_RANK, false, false );
            String xparam= lparams.remove("depend0");
            String xslice1= null;
            if ( xparam==null ) xparam= lparams.remove("x");
            if ( xparam!=null ) {
                int i= xparam.indexOf("[");
                if ( i!=-1 ) {
                    String xsubset= xparam.substring(i);
                    xparam= xparam.substring(0,i);
                    Matcher m= slice1pattern.matcher(xsubset);
                    if ( m.matches() ) {
                        xslice1= m.group(1);
                    }
                }
            }
            fillTree( this.parameterTree1, parameterDescriptions2, cdf, xparam, xslice1 );
            
            String yparam= lparams.remove("y");
            String yslice1= null;
            if ( yparam!=null ) {
                int i= yparam.indexOf("[");
                if ( i!=-1 ) {
                    String ysubset= yparam.substring(i);
                    yparam= yparam.substring(0,i);
                    Matcher m= slice1pattern.matcher(ysubset);
                    if ( m.matches() ) {
                        yslice1= m.group(1);
                    }
                }
            }
            fillTree( this.parameterTree2, parameterDescriptions2, cdf, yparam, yslice1 );
            
            logger.finest("close cdf");

            DefaultComboBoxModel cbmodel= new DefaultComboBoxModel();
            for ( String p: parameterDescriptions.keySet() ) {
                cbmodel.addElement(p);
            }

            if ( param!=null ) {
                if ( subset!=null ) {
                    if ( subset.startsWith("[") ) subset= subset.substring(1);
                    if ( subset.endsWith("]") ) subset= subset.substring(0,subset.length()-1);
                    subsetComboBox.setSelectedItem( subset );
                } else {
                    subsetComboBox.setSelectedItem("");
                }
            } else {
                if ( !parameterDescriptions.isEmpty() ) {
                    parameter= parameterDescriptions.entrySet().iterator().next().getKey();
                    subsetComboBox.setSelectedItem("");
                    param= parameter;
                    paramInfo.setText("");
                } else {
                    param= "";
                    if ( this.parameterTree.getRowCount()==0 && numSupport>0 && numData==0 && !this.showAllVarTypeCB.isSelected() ) {
                        paramInfo.setText("(all parameters are marked as support data, select \"show all\" above)");
                    } else {
                        paramInfo.setText("(no plottable parameters)");
                    }
                }                
            }
            parameter= param.replaceAll("%3D", "=");

            if ( "no".equals( lparams.remove("interpMeta")) ) {
                noInterpMeta.setSelected(true);
            }

            if ( "no".equals( lparams.remove("doDep" ) ) ) {
                noDep.setSelected(true);
            }

            whereParamList.setModel( new DefaultComboBoxModel( whereParameterInfo.keySet().toArray() ) );
            String where= lparams.remove("where");
            if ( where!=null && where.length()>0 ) {
                whereCB.setSelected(true);
                int i= where.indexOf(".");
                if ( i>-1 ) {
                    whereParamList.setSelectedItem(where.substring(0,i)); 
                    int i0= where.indexOf("(");
                    int i1= where.indexOf(")",i0);
                    whereOp.setSelectedItem(where.substring(i,i0));
                    whereTF.setText( where.substring(i0+1,i1).replaceAll("\\+"," "));
                }
            } else {
                whereCB.setSelected(false);
            }
            
        } catch (IOException ex) {
            DasExceptionHandler.handle( ex );
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IllegalArgumentException ex) {
            DasExceptionHandler.handle( ex );
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (Exception ex) {
            DasExceptionHandler.handle( ex );
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

        this.params= lparams;
        
    }

    @Override
    public String getURI() {
        
        URISplit split= URISplit.parse(resourceUri);
        
        split.vapScheme= this.vapScheme;
                
        String subset= subsetComboBox.getSelectedItem().toString().trim();
        if ( subset.length()>0 && subset.charAt(0)!='[' ) {
            subset= "["+subset+"]";
        }
        
        Map<String,String> lparams= this.params;
        if ( lparams!=null ) {
            lparams= new HashMap(lparams);
        } else {
            lparams= new HashMap();
        }
        
        if ( isValidCDF ) {
            TreePath treePath= parameterTree.getSelectionPath();
            if ( treePath==null ) {
                logger.fine("param was null");
            } else if ( treePath.getPathCount()==3 ) {
                String p= String.valueOf( treePath.getPathComponent(1) );
                p= p.replaceAll("=", "%3D");
                lparams.put( "arg_0", p + subset );
                String val=  String.valueOf( treePath.getPathComponent(2) );
                int idx= val.indexOf(":");
                lparams.put( "slice1", val.substring(0,idx).trim() );
            } else {
                String p= String.valueOf( treePath.getPathComponent(1) );
                p= p.replaceAll("=", "%3D");
                lparams.put( "arg_0", p + subset );
            }
            
            if ( xCheckBox.isSelected() ) {
                TreePath depend0Path= parameterTree1.getSelectionPath();
                if ( depend0Path!=null ) {
                    if ( depend0Path.getPathCount()==3 ) {
                        String p= String.valueOf( depend0Path.getPathComponent(1) );
                        p= p.replaceAll("=", "%3D");
                        String val=  String.valueOf( depend0Path.getPathComponent(2) );
                        int idx= val.indexOf(":");
                        lparams.put( "x", p +"[:,"+val.substring(0,idx).trim()+"]" );
                    } else {
                        String p= String.valueOf( depend0Path.getPathComponent(1) );
                        p= p.replaceAll("=", "%3D");
                        lparams.put( "x", p );
                    }
                }
            }

            if ( yCheckBox.isSelected() ) {
                TreePath yPath= parameterTree2.getSelectionPath();
                if ( yPath!=null ) {
                    if ( yPath.getPathCount()==3 ) {
                        String p= String.valueOf( yPath.getPathComponent(1) );
                        p= p.replaceAll("=", "%3D");
                        String val=  String.valueOf( yPath.getPathComponent(2) );
                        int idx= val.indexOf(":");
                        lparams.put( "y", p +"[:,"+val.substring(0,idx).trim()+"]" );
                    } else {
                        String p= String.valueOf( yPath.getPathComponent(1) );
                        p= p.replaceAll("=", "%3D");
                        lparams.put( "y", p );
                    }                
                }
            }
            
            if ( noDep.isSelected() ) {
                lparams.put("doDep","no");
            } 
            
            if ( noInterpMeta.isSelected() ) {
                lparams.put("interpMeta", "no");
            } 

            if ( whereCB.isSelected() ) {
                lparams.put( "where", String.format( "%s%s(%s)", whereParamList.getSelectedItem(), whereOp.getSelectedItem(), whereTF.getText().replaceAll(" ","+") ) );
            }
        }

        split.params= URISplit.formatParams(lparams);
        if ( split.params!=null && split.params.length()==0 ) split.params= null;
        return URISplit.format(split);
    }

    @Override
    public void markProblems(List<String> problems) {
        
    }

    private void fillTree( JTree parameterTree, Map<String,String> mm, gov.nasa.gsfc.spdf.cdfj.CDFReader cdf, String param, String slice1 ) {

        DefaultMutableTreeNode root= new DefaultMutableTreeNode("");

        List<TreePath> expand=new ArrayList(mm.size());
        
        TreePath selection=null;
        for ( Entry<String,String> e: mm.entrySet() ) {

           try {
                Object oattr= cdf.getAttribute( e.getKey(), "LABL_PTR_1");
                String lablPtr1=null;
                if ( oattr!=null && oattr instanceof List ) {
                    List voattr= (List)oattr;
                    if ( voattr.size()>0 ) {
                        lablPtr1= (String)((List)oattr).get(0);
                    } else {
                        oattr= null;
                    }
                }

                int[] dimensions= cdf.getDimensions( e.getKey() );
                boolean doComponents= oattr!=null && dimensions.length==1 && dimensions[0]<=MAX_SLICE1_OFFER;
                if ( doComponents ) {
                    String s= lablPtr1;
                    DefaultMutableTreeNode node= new DefaultMutableTreeNode( e.getKey() );
                    try {
                        Object o = cdf.get(s);
                        String[] rec;
                        if ( o.getClass().isArray() && String.class.isAssignableFrom( o.getClass().getComponentType() ) ) {
                            rec= (String[])o;
                        } else {
                            Object oo= Array.get(o,0);
                            if ( !oo.getClass().isArray() || !( String.class.isAssignableFrom( oo.getClass().getComponentType() ) ) ) {
                               logger.log(Level.FINE, "Expected string array in element: {0}", s);
                               continue;
                            }
                            rec= (String[])Array.get(o,0);
                        }
                        for ( int i=0; i<rec.length; i++ ) {
                            String snode=  String.format("%d: %s", i, rec[i] ) ;
                            DefaultMutableTreeNode child= new DefaultMutableTreeNode( snode );
                            node.add( child );
                            if ( e.getKey().equals(param) ) {
                                if ( slice1!=null ) {
                                    if ( String.valueOf(i).equals(slice1) ) {
                                        selection= new TreePath( new Object[] { root, node, child } );
                                    }
                                } else {
                                    selection= new TreePath( new Object[] { root, node } );
                                }
                            }
                        }
                        root.add( node );
                        if ( rec.length<4 ) {
                            expand.add( new TreePath( new Object[] { root, node } ) );
                        }
                        
                    } catch (CDFException.ReaderError | ArrayIndexOutOfBoundsException | IllegalArgumentException ex ) {
                        logger.log(Level.WARNING,"parameter name found: "+s+" referred to by " +e.getKey(),ex);
                        root.add( node );
                    }

                } else {
                    DefaultMutableTreeNode node=  new DefaultMutableTreeNode( e.getKey() );
                    root.add( node );
                    if ( e.getKey().equals(param) ) {
                        selection= new TreePath( new Object[] { root, node } );
                    }
                }
            } catch ( CDFException.ReaderError t ) {
                logger.log(Level.WARNING,t.getMessage(),t);

            }
        }

        DefaultTreeModel tm= new DefaultTreeModel( root );

        parameterTree.setRootVisible(false);
        parameterTree.setModel(tm);

        if ( selection!=null ) {
            parameterTree.setSelectionPath(selection);
            parameterTree.scrollPathToVisible(selection);
        }
        
        for ( TreePath tp: expand ) {
            parameterTree.expandPath(tp);
        }

    }

}
