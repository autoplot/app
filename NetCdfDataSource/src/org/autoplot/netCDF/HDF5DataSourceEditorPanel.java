
package org.autoplot.netCDF;

import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.das2.util.DasExceptionHandler;
import org.das2.util.LoggerManager;
import org.das2.util.filesystem.FileSystem;
import org.das2.util.monitor.NullProgressMonitor;
import org.das2.util.monitor.ProgressMonitor;
import org.autoplot.datasource.DataSetURI;
import org.autoplot.datasource.DataSourceEditorPanel;
import org.autoplot.datasource.URISplit;
import static org.autoplot.netCDF.NetCDFDataSourceFactory.checkMatlab;
import ucar.ma2.DataType;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Structure;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

/**
 * Editor panel for HDF5 files.
 * @author faden@cottagesystems.com
 */
public class HDF5DataSourceEditorPanel extends javax.swing.JPanel implements DataSourceEditorPanel {

    /**
     * Creates new form HDF5DataSourceEditorPanel
     */
    public HDF5DataSourceEditorPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        advancedPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        subsetComboBox = new javax.swing.JComboBox();
        whereCB = new javax.swing.JCheckBox();
        whereParamList = new javax.swing.JComboBox();
        whereOp = new javax.swing.JComboBox();
        whereTF = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        parameterTree = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        xParameterTree = new javax.swing.JTree();
        xCheckBox = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        yParameterTree = new javax.swing.JTree();
        yCheckBox = new javax.swing.JCheckBox();
        parameterInfoLabel = new javax.swing.JLabel();

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(0.5);

        advancedPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Advanced"));
        advancedPanel.setMaximumSize(new java.awt.Dimension(285, 32767));

        jLabel5.setText("Load subset of the data:");
        jLabel5.setToolTipText("<html>Load a subset of the data records, for example:<br>[0:100]  first 100 records<br> [-100:] last 100 records<br> [::10] every tenth record<br> </html>");

        subsetComboBox.setEditable(true);
        subsetComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "::10", "0:100", "-100:", "0:10000:5" }));
        subsetComboBox.setToolTipText("<html>Load a subset of the data records, for example:<br>[0:100]  first 100 records<br> [-100:] last 100 records<br> [::10] every tenth record<br> </html>");

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

        javax.swing.GroupLayout advancedPanelLayout = new javax.swing.GroupLayout(advancedPanel);
        advancedPanel.setLayout(advancedPanelLayout);
        advancedPanelLayout.setHorizontalGroup(
            advancedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(advancedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(whereParamList, 0, 161, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(whereOp, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(whereTF, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(advancedPanelLayout.createSequentialGroup()
                .addGroup(advancedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(whereCB)
                    .addGroup(advancedPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(subsetComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 135, Short.MAX_VALUE))
        );
        advancedPanelLayout.setVerticalGroup(
            advancedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(advancedPanelLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subsetComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(whereCB)
                .addGap(8, 8, 8)
                .addGroup(advancedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(whereParamList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(whereOp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(whereTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(245, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(advancedPanel);

        jTabbedPane1.setToolTipText("\"plot\" selects the dependent parameter for plotting.  \"x\" allows specification of an independent parameter upon which the \"plot\" parameter depends.");

        parameterTree.setRootVisible(false);
        parameterTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                parameterTreeValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(parameterTree);

        jTabbedPane1.addTab("select variable", jScrollPane1);

        xParameterTree.setRootVisible(false);
        xParameterTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                xParameterTreeValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(xParameterTree);

        xCheckBox.setText("Set Variable for X");
        xCheckBox.setToolTipText("Specify the parameter to use for the X tags, overriding any settings found in the file.\n");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xCheckBox)
                .addContainerGap(196, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(xCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("x", jPanel1);

        jPanel3.setAlignmentX(0.0F);

        yParameterTree.setRootVisible(false);
        yParameterTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                yParameterTreeValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(yParameterTree);

        yCheckBox.setText("Set Variable for Y");
        yCheckBox.setToolTipText("Specify the parameter to use for the X tags, overriding any settings found in the file. ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(yCheckBox)
                .addGap(0, 196, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(yCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("y", jPanel3);

        jSplitPane1.setLeftComponent(jTabbedPane1);

        jSplitPane2.setTopComponent(jSplitPane1);

        parameterInfoLabel.setText("jLabel1");
        parameterInfoLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        parameterInfoLabel.setMinimumSize(new java.awt.Dimension(49, 100));
        jSplitPane2.setRightComponent(parameterInfoLabel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void parameterTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_parameterTreeValueChanged
        TreePath tp= evt.getPath();
        parameter= String.valueOf(tp.getPathComponent(1));
        String longName= parameters.get(parameter);
        parameterInfoLabel.setText( longName );

        String dims= longName.substring(parameter.length());
        
        List<String> varnames= new ArrayList<>();
        for ( Entry<String,String> ps : parameters.entrySet() ) {
            String v= ps.getValue();
            int i= v.indexOf("[");
            if ( dims.startsWith( v.substring(i,v.length()-1) ) ) {
                varnames.add(ps.getKey());
            }
        }
        
        whereParamList.setModel( new DefaultComboBoxModel( varnames.toArray() ) );            
        
    }//GEN-LAST:event_parameterTreeValueChanged

    private void xParameterTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_xParameterTreeValueChanged
        xCheckBox.setSelected(true);
    }//GEN-LAST:event_xParameterTreeValueChanged

    private void yParameterTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_yParameterTreeValueChanged
        yCheckBox.setSelected(true);
    }//GEN-LAST:event_yParameterTreeValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel advancedPanel;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel parameterInfoLabel;
    private javax.swing.JTree parameterTree;
    private javax.swing.JComboBox subsetComboBox;
    private javax.swing.JCheckBox whereCB;
    private javax.swing.JComboBox whereOp;
    private javax.swing.JComboBox whereParamList;
    private javax.swing.JTextField whereTF;
    private javax.swing.JCheckBox xCheckBox;
    private javax.swing.JTree xParameterTree;
    private javax.swing.JCheckBox yCheckBox;
    private javax.swing.JTree yParameterTree;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private static final Logger logger= LoggerManager.getLogger("apdss.hdf5");
    
    URISplit split;
    
    /**
     * URI parameters, like arg_0.
     */
    Map<String,String> params;

    /**
     * parameter name to short descriptions of the parameter.
     */
    Map<String,String> parameters= new LinkedHashMap();
    
    /**
     * the parameter within the HDF5 file to be plotted.
     */
    String parameter;
    
    /**
     * the variables in the HDF5 file.
     */
    List<Variable> vars;

    @Override
    public boolean reject(String uri) throws Exception {
        split = URISplit.parse(uri); 

        if ( split.resourceUri==null ) {
            return true;
        }
        
        FileSystem fs = FileSystem.create( DataSetURI.toUri(split.path) );
        return fs.isDirectory( split.file.substring(split.path.length()) );
    }

    @Override
    public boolean prepare(String uri, Window parent, ProgressMonitor mon) throws Exception {
        split= URISplit.parse(uri);

        File cdfFile= DataSetURI.getFile( split.resourceUri, mon );
        DataSetURI.checkLength(cdfFile);

        return true;
    }
    
    private void fillTree( JTree parameterTree, Map<String,String> mm, String param ) {

        DefaultMutableTreeNode root= new DefaultMutableTreeNode("");

        List<TreePath> expand=new ArrayList(mm.size());
        
        TreePath selection=null;
        for ( Map.Entry<String,String> e: mm.entrySet() ) {

           try {
                DefaultMutableTreeNode node=  new DefaultMutableTreeNode( e.getKey() );
                root.add( node );
                if ( e.getKey().equals(param) ) {
                    selection= new TreePath( new Object[] { root, node } );
                }
            } catch ( Exception t ) {
                logger.log(Level.WARNING,t.getMessage(),t);

            }
        }

        DefaultTreeModel tm= new DefaultTreeModel( root );

        parameterTree.setModel(tm);

        if ( selection!=null ) {
            parameterTree.setSelectionPath(selection);
            parameterTree.scrollPathToVisible(selection);
        }
        
        for ( TreePath tp: expand ) {
            parameterTree.expandPath(tp);
        }

    }
    

    @Override
    public void setURI(String uri) {
        
        split= URISplit.parse(uri);
        params= URISplit.parseParams(split.params);

        try {

            File cdfFile = DataSetURI.getFile( split.resourceUri, new NullProgressMonitor() );
            
            String fileName= cdfFile.toString();

            logger.log(Level.FINE, "opening cdf file {0}", fileName);
            
            String resource= fileName;
            
            checkMatlab( resource );
            NetcdfFile f= NetcdfFile.open( resource );
            NetcdfDataset dataset= new NetcdfDataset( f );
            
            vars= (List<Variable>)dataset.getVariables();
            dataset.close();
            
            for (Variable v : vars) {
                if ( v.getDimensions().isEmpty() ) continue;
                if ( v instanceof Structure ) {
                    
                    for ( Variable v2: ((Structure) v).getVariables() ) {
                        if ( !v2.getDataType().isNumeric() ) continue;
                        StringBuilder description= new StringBuilder( v2.getName()+"[" );
                        for ( int k=0; k<v2.getDimensions().size(); k++ ) {
                            Dimension d= v2.getDimension(k);
                            if ( k>0 ) description.append(",");
                            try {
                                String n= d.getName();
                                if ( n!=null && !n.equals(v2.getName()) ) {
                                    description.append(d.getName()).append("=");
                                }
                                description.append(d.getLength());
                            } catch ( NullPointerException ex ) {
                                throw ex;
                            }
                        }
                        description.append("]");
                        
                        parameters.put( v2.getName(), description.toString() );
                    }
                    
                } else {
                
                    boolean isFormattedTime= v.getDataType()==DataType.CHAR && v.getRank()==2 && v.getShape(1)>=14 && v.getShape(1)<=30;
                    if ( !isFormattedTime && !v.getDataType().isNumeric() ) continue;
                    StringBuilder description= new StringBuilder( v.getName()+"[" );
                    for ( int k=0; k<v.getDimensions().size(); k++ ) {
                        Dimension d= v.getDimension(k);
                        if ( k>0 ) description.append(",");
                        try {
                            String n= d.getName();
                            if ( n!=null && !n.equals(v.getName()) ) {
                                description.append(d.getName()).append("=");
                            }
                            description.append(d.getLength());
                        } catch ( NullPointerException ex ) {
                            throw ex;
                        }
                    }
                    description.append("]");

                    parameters.put( v.getName(), description.toString() );
                    
                }
            }

            //String label= "Select Parameter (%d parameters):";
            
            int numData= parameters.size();

            //this.selectVariableLabel.setText( String.format( label, numData ) );

            this.jTabbedPane1.setTitleAt(0,"Select Variable (of "+numData+")");
            String param= params.get("arg_0");
            String subset= null;
            if ( param!=null ) {
                int i= param.indexOf("[");
                if ( i!=-1 ) {
                    subset= param.substring(i);
                    param= param.substring(0,i);
                }
            }
            
            fillTree( this.parameterTree, parameters, param );
            
            xParameterTree.setModel( this.parameterTree.getModel() );
            String sx= params.get( "x" );
            if ( sx!=null ) {
                fillTree( this.xParameterTree, parameters, sx ); //TODO: tree is filled twice.
            }
            
            yParameterTree.setModel( this.parameterTree.getModel() );
            String sy= params.get( "y" );
            if ( sy!=null ) {
                fillTree( this.yParameterTree, parameters, sy ); //TODO: tree is filled twice.
            }
                        
            logger.finest("close hdf");

            DefaultComboBoxModel cbmodel= new DefaultComboBoxModel();
            for ( String p: params.keySet() ) {
                cbmodel.addElement(p);
            }

            if ( param==null ) {
                if ( !params.isEmpty() ) {
                    parameter= params.entrySet().iterator().next().getKey();
                    param= parameter;
                    parameterInfoLabel.setText("");
                } else {
                    parameterInfoLabel.setText("(no plottable parameters)");
                }                
            }
            
            if ( param!=null ) {
                if ( subset!=null ) {
                    if ( subset.startsWith("[") ) subset= subset.substring(1); // note I think they always will.
                    if ( subset.endsWith("]") ) subset= subset.substring(0,subset.length()-1);                    
                    subsetComboBox.setSelectedItem( subset );
                } else {
                    subsetComboBox.setSelectedItem("");
                }
            } 
            
            if ( parameter!=null ) {
                parameter= parameter.replaceAll("%3D", "=");
            }
            
            List<String> varnames= new ArrayList<>();
            for ( Entry<String,String> p: parameters.entrySet() ) {
                varnames.add(p.getKey());
            }
            whereParamList.setModel( new DefaultComboBoxModel( varnames.toArray() ) );
            String where= params.get("where");
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
            
        } catch (IOException | IllegalArgumentException | NullPointerException ex) {
            DasExceptionHandler.handle( ex );
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    @Override
    public void markProblems(List<String> problems) {
        logger.log(Level.FINE, "markProblems: {0}", problems.toString());
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public String getURI() {
        String subset= subsetComboBox.getSelectedItem().toString().trim();
        if ( subset.length()>0 && subset.charAt(0)!='[' ) {
            subset= "["+subset+"]";
        }
        
        String p= parameter;
        if ( subset.length()>0 ) {
            p= p+subset;
        }
        params.put( "arg_0", p );
        
        if ( xCheckBox.isSelected() ) {
            TreePath xtp= xParameterTree.getSelectionPath();
            if ( xtp!=null ) {
                Object odep0= xtp.getLastPathComponent() ;
                params.put( "x", odep0.toString() ); // TODO weak code assumes toString works.
            }
        } else {
            params.remove("x");
        }

        if ( yCheckBox.isSelected() ) {
            TreePath ytp= yParameterTree.getSelectionPath();
            if ( ytp!=null ) {
                Object oy= ytp.getLastPathComponent() ;
                params.put( "y", oy.toString() ); // TODO weak code assumes toString works.
            }
        } else {
            params.remove("y");
        }
            
        if ( whereCB.isSelected() ) {
            params.put( "where", String.format( "%s%s(%s)", whereParamList.getSelectedItem(), whereOp.getSelectedItem(), whereTF.getText().replaceAll(" ","+") ) );
        } else {
            params.remove("where");
        }
        
        split.params= URISplit.formatParams(params);
        return URISplit.format(split);
    }
    
    public static void main( String[] args ) throws Exception {
        //String uri= "/home/jbf/ct/autoplot/data/hdf/20020908_CRRES_FluxAssimOut.h5";
        String uri= "/home/jbf/ct/autoplot/data/hdf/brian/FU1_ZEP_Counts_v1-5681_20110301_v1.0.0.h5";
        DataSourceEditorPanel ep= new HDF5DataSourceEditorPanel();
        
        if ( !ep.reject(uri) ) {
            ep.prepare( uri, null, new NullProgressMonitor() );
            ep.setURI( uri );
            if ( JOptionPane.showConfirmDialog( null, ep )==JOptionPane.OK_OPTION ) {
                System.err.println( ep.getURI() );
            }
            
        }
        
    }
}
