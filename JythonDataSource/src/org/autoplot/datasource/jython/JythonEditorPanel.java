
package org.autoplot.datasource.jython;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;
import org.das2.jythoncompletion.nbadapt.Utilities;
import org.das2.util.filesystem.FileSystem;
import org.das2.util.monitor.NullProgressMonitor;
import org.das2.util.monitor.ProgressMonitor;
import org.python.core.PyException;
import org.autoplot.datasource.DataSetURI;
import org.autoplot.datasource.DataSourceEditorPanel;
import org.autoplot.datasource.FileSystemUtil;
import org.autoplot.datasource.LogNames;
import org.autoplot.datasource.URISplit;
import org.autoplot.jythonsupport.JythonUtil;
import org.autoplot.jythonsupport.JythonUtil.ScriptDescriptor;
import org.autoplot.jythonsupport.ui.EditorAnnotationsSupport;
import org.autoplot.jythonsupport.ui.ParametersFormPanel;
import org.autoplot.jythonsupport.ui.ParametersFormPanel.FormData;
import org.autoplot.jythonsupport.ui.ScriptPanelSupport;
import org.das2.util.FileUtil;

/**
 * Editor for vap+jyds uris.  These URIs offer a couple of challenges,
 * such as the mode where the resourceURI is the name of the data file and
 * the script argument is used.  Also, we identify types and offer decent
 * GUI elements to control the types.
 * @author jbf
 */
public class JythonEditorPanel extends javax.swing.JPanel implements DataSourceEditorPanel {

    private static final Logger logger= Logger.getLogger( LogNames.APDSS_JYDS );

    ScriptPanelSupport support;
    String suri;
    File file;
    URI resourceUri;
    boolean hasVariables= false;
    ParametersFormPanel parametersFormPanel;

    /** Creates new form JythonEditorPanel */
    public JythonEditorPanel() {
        initComponents();
        tearoffTabbedPane1.hideMouseAdapter();

        support= new ScriptPanelSupport(textArea);
        support.addCaretLabel(caretPositionLabel);
        support.addFileLabel(fileNameLabel);
        support.setReadOnly();

        scriptScrollPane.getVerticalScrollBar().setUnitIncrement(scriptScrollPane.getFont().getSize()); 
        paramsScrollPane.getVerticalScrollBar().setUnitIncrement(scriptScrollPane.getFont().getSize());
        
        textArea.addMouseListener( new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int offs= textArea.viewToModel(e.getPoint());
                    int[] ii= Utilities.getIdentifierBlock( textArea, offs ) ;
                    if ( ii!=null ) {
                        String id= textArea.getDocument().getText(ii[0],ii[1]-ii[0]);
                        int idx= lookupResultVariableIndex(id);
                        if ( idx!=-1 ) variableComboBox.setSelectedIndex( idx );
                    }
                } catch (BadLocationException ex) {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
            
        });
    }
    
            
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        variableComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        tearoffTabbedPane1 = new org.das2.components.TearoffTabbedPane();
        scriptPanel = new javax.swing.JPanel();
        caretPositionLabel = new javax.swing.JLabel();
        fileNameLabel = new javax.swing.JLabel();
        scriptScrollPane = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        textArea = new org.autoplot.jythonsupport.ui.EditorTextPane();
        paramsScrollPane = new javax.swing.JScrollPane();
        paramsPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setName("jythonDataSourceEditorPanel"); // NOI18N

        variableComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(running script)" }));

        jLabel1.setText("Select variable:");
        jLabel1.setToolTipText("The dataset pointed to by the URI");

        caretPositionLabel.setText("1,1");

        fileNameLabel.setMinimumSize(new java.awt.Dimension(200, 16));

        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(textArea, java.awt.BorderLayout.CENTER);

        scriptScrollPane.setViewportView(jPanel1);

        org.jdesktop.layout.GroupLayout scriptPanelLayout = new org.jdesktop.layout.GroupLayout(scriptPanel);
        scriptPanel.setLayout(scriptPanelLayout);
        scriptPanelLayout.setHorizontalGroup(
            scriptPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, scriptPanelLayout.createSequentialGroup()
                .add(fileNameLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(caretPositionLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 56, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(scriptScrollPane)
        );
        scriptPanelLayout.setVerticalGroup(
            scriptPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, scriptPanelLayout.createSequentialGroup()
                .add(scriptScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(scriptPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(fileNameLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(caretPositionLabel)))
        );

        tearoffTabbedPane1.addTab("script", scriptPanel);

        paramsPanel.setLayout(new javax.swing.BoxLayout(paramsPanel, javax.swing.BoxLayout.Y_AXIS));
        paramsScrollPane.setViewportView(paramsPanel);

        tearoffTabbedPane1.addTab("params", paramsScrollPane);

        jLabel2.setText("Select from the variables calculated by the script, 'data' or 'result' is used by default:");
        jLabel2.setMinimumSize(new java.awt.Dimension(200, 17));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(variableComboBox, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(tearoffTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(tearoffTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(8, 8, 8)
                .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(variableComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1)))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel caretPositionLabel;
    protected javax.swing.JLabel fileNameLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel paramsPanel;
    private javax.swing.JScrollPane paramsScrollPane;
    private javax.swing.JPanel scriptPanel;
    private javax.swing.JScrollPane scriptScrollPane;
    private org.das2.components.TearoffTabbedPane tearoffTabbedPane1;
    private org.autoplot.jythonsupport.ui.EditorTextPane textArea;
    private javax.swing.JComboBox variableComboBox;
    // End of variables declaration//GEN-END:variables

    @Override
    public JPanel getPanel() {
        return this;
    }

    /**
     * return spacer of width 20.
     * @param size
     * @return 
     */
    private JComponent getSpacer() {
        JComponent spacer= new JLabel("  ");
        spacer.setSize( new Dimension(20,16) );
        spacer.setMinimumSize( new Dimension(20,16) );
        spacer.setPreferredSize( new Dimension(20,16) );
        return spacer;
    }
    
    /**
     * return spacer of width size.
     * @param size
     * @return 
     */
    private JComponent getSpacer( int size ) {
        JComponent spacer= new JLabel(" ");
        spacer.setSize( new Dimension(size,16) );
        spacer.setMinimumSize( new Dimension(size,16) );
        spacer.setPreferredSize( new Dimension(size,16) );
        return spacer;
    }
    
    /**
     * create a GUI from the params and the script.
     * @param f
     * @param params
     * @return 
     */
    private boolean doVariables( Map<String,Object> env, File f, Map<String,String> params ) throws PyException {

        boolean hasVars= false;
        
        try {

            String src= FileUtil.readFileToString(f);
            
            ParametersFormPanel p= new ParametersFormPanel();
            
            //paramsPanel.add( new JLabel("<html>This script has the following input parameters.  Buttons on the right show default values.<br><br></html>") );

            FormData fd= p.doVariables( env, src, params, paramsPanel );
            
            hasVars= fd.count>0;
            parametersFormPanel= p;
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return hasVars;

    }

    private Map<String, String> getParamsFromGui( ) throws IllegalArgumentException {
        
        URISplit split=  URISplit.parse(suri);
        
        Map<String,String> params= URISplit.parseParams(split.params);
        String param= (String)variableComboBox.getSelectedItem();
        int i= param.indexOf("<span");
        if ( i==-1 ) {
            params.put( "arg_0", param.trim() );
        } else {
            int j= param.startsWith("<html>") ? 6 : 0;
            params.put( "arg_0", param.substring(j,i).trim() );
        }
        
        if ( parametersFormPanel==null ) {
            return Collections.emptyMap();
        }
        
        FormData formData= parametersFormPanel.getFormData();
        ParametersFormPanel.resetVariables( formData, params );
        
        String resourceURI= params.get("resourceURI");
        if ( resourceURI!=null ) {
            if ( resourceURI.startsWith("'") ) resourceURI= resourceURI.substring(1);
            if ( resourceURI.endsWith("'") ) resourceURI= resourceURI.substring(0,resourceURI.length()-1);
            params.put( "resourceURI", resourceURI );
        }
        
        return params;
        
    }
    
    private String[] getScriptURI( URISplit split ) {
        Map<String,String> params= URISplit.parseParams(split.params);

        String furi;
        String resourceUri1;
        if ( params.containsKey(JythonDataSource.PARAM_SCRIPT) ) {
            furi= params.get(JythonDataSource.PARAM_SCRIPT);
            resourceUri1= split.resourceUri==null ? null : split.resourceUri.toString();
        } else {
            furi= split.resourceUri.toString();
            resourceUri1= null;
        }

        return new String[] { furi, resourceUri1 };
            
    }

    @Override
    public void setURI(String url) {
        try {
            this.suri= url;
            URISplit split= URISplit.parse(suri);

            String[] furir= getScriptURI( split );
            File f = DataSetURI.getFile( furir[0], new NullProgressMonitor() );
            file= f;

            Map<String,String> results= JythonDataSourceFactory.getResultParameters( f.toString(), new NullProgressMonitor() );
            String[] dropList= new String[results.size()+1];
            int i=0;
            int idx= -1;

            Map<String,String> params= URISplit.parseParams(split.params);

            String param= params.remove("arg_0");

            // populate the list of quanities calculated.
            dropList[0]= "";
            for ( Entry<String,String> ent: results.entrySet()  ) {
                dropList[i+1]= "<html>"+ent.getKey()+"<span color=#808080>: <i>"+ent.getValue()+"</i></span>";
                if ( param!=null && param.equals(ent.getKey()) ) {
                    idx= i+1;
                }
                i++;
            }
            variableComboBox.setModel( new DefaultComboBoxModel( dropList ) );
            if ( idx>=0 ) {
                variableComboBox.setSelectedIndex(idx);
            } else {
                variableComboBox.setSelectedIndex(0);
            }

            Map<String,Object> env= Collections.singletonMap( "PWD", (Object)split.path );

            // make the GUI for the title, description, and the parameters.
            Map<String,String> ffparams= new HashMap( params );

            if ( furir[1]!=null ) ffparams.put( JythonDataSource.PARAM_RESOURCE_URI, furir[1] );
            
            support.loadFile(f);

            if ( FileSystemUtil.isChildOf( FileSystem.settings().getLocalCacheDir(), support.getFile() ) || !support.getFile().canWrite() ) {
                support.setReadOnly();
            }

            try {
                hasVariables= doVariables( env, f, ffparams );
            } catch ( PyException e ) {
                hasVariables= false;
                try {
                    support.annotateError( e,0 );
                } catch (BadLocationException ex) {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                }
                paramsPanel.add( new JLabel("<html>Script contains errors.</html>") );
            }

            if ( hasVariables ) {
                tearoffTabbedPane1.setSelectedIndex(1);
            }
            
            List<String> errs= new ArrayList();
            if ( JythonUtil.pythonLint( f.toURI(), errs) ) {
                EditorAnnotationsSupport esa= textArea.getEditorAnnotationsSupport();
                for ( String s: errs ) {
                    String[] ss= s.split(":",2);
                    try {
                        String doc= ss[1];
                        doc= doc.replaceAll("<", "&lt;");
                        doc= doc.replaceAll(">", "&gt;");
                        esa.annotateLine(Integer.parseInt(ss[0]), EditorAnnotationsSupport.ANNO_WARNING, "Variable name is already used before execution: " + doc + "<br>Consider using a different name");
                    } catch (BadLocationException ex) {
                        logger.log(Level.SEVERE, ex.getMessage(), ex);
                    }
                }

            }



        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private int lookupResultVariableIndex( String var ) {
        for ( int jj=0; jj<variableComboBox.getItemCount(); jj++ ) {
            String it= (String)variableComboBox.getItemAt(jj);
            int i= it.indexOf("<span");
            if ( i==-1 ) {
                if ( var.equals(it) ) return jj;
            } else {
                int j= it.startsWith("<html>") ? 6 : 0;
                if ( var.equals( it.substring(j,i).trim() ) ) {
                    return jj;
                }
            }
        }
        return -1;
    }
    
    @Override
    public String getURI() {

        if ( support.isDirty() && support.getFile()!=null ) {
            OutputStream out=null;
            try {
                out = new FileOutputStream( support.getFile() );
                String text = textArea.getText();
                out.write(text.getBytes());
                support.setDirty(false);
            } catch ( IOException ex ) {
                
            } finally {
                if ( out!=null ) {
                    try {
                        out.close();
                    } catch ( IOException ex ) {
                        //nothing
                    }
                }
            }
        }

        URISplit split=  URISplit.parse(suri);
        
        Map<String, String> params = getParamsFromGui();
        
        if ( split.resourceUri!=null && params.get(JythonDataSource.PARAM_RESOURCE_URI)!=null ) {
            try {
                String script= params.get( JythonDataSource.PARAM_SCRIPT );
                if ( script==null ) script= split.resourceUri.toString();
                params.put( JythonDataSource.PARAM_SCRIPT, script );
                split.resourceUri= new URI( params.remove(JythonDataSource.PARAM_RESOURCE_URI) );
                split.file= split.resourceUri.toString();
                split.vapScheme="vap+jyds";
            } catch ( URISyntaxException ex ) {
                
            }
        } else {
            logger.fine("bugfix code used to go through here and do something that appeared to be incorrect.");
        }

        if ( support.isDirty() ) {
            try {
                FileWriter writer = new FileWriter(support.getFile());
                try {
                    writer.write( textArea.getText() );
                } finally {
                    writer.close();
                }
            } catch (IOException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        JythonDataSourceTimeSeriesBrowse tsb1;
        try {
            tsb1 = JythonDataSourceTimeSeriesBrowse.checkForTimeSeriesBrowse( suri, support.getFile() );
            if ( tsb1==null ) {
                params.remove("timerange");
                if ( "".equals( params.get(URISplit.PARAM_ARG_0) ) ) {
                    params.remove(URISplit.PARAM_ARG_0);
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        
        split.params= params.isEmpty() ? null : URISplit.formatParams(params);        
        
        String uri= URISplit.format(split);
        
        return uri;
    }

    @Override
    public boolean reject(String uri) throws Exception {
        URISplit split= URISplit.parse(uri);
        if ( split.file==null || split.file.length()==0 || split.file.equals("file:///") ) {
            Map<String,String> params= URISplit.parseParams(split.params);
            if ( params.containsKey(JythonDataSource.PARAM_SCRIPT) ) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    @Override
    public boolean prepare(String uri, Window parent, ProgressMonitor mon) throws Exception {

        URISplit split= URISplit.parse(uri);
        String[] furir= getScriptURI( split );
        DataSetURI.getFile( furir[0], mon );
        return true;
    }

    @Override
    public void markProblems(List<String> problems) {
        
    }

}
