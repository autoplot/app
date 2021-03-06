/*
 * JythonEditorPanel.java
 *
 * Created on Jul 14, 2009, 10:44:41 AM
 */

package org.autoplot.datasource.jython;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import org.das2.datum.DatumRangeUtil;
import org.das2.jythoncompletion.nbadapt.Utilities;
import org.das2.util.filesystem.FileSystem;
import org.das2.util.monitor.NullProgressMonitor;
import org.das2.util.monitor.ProgressMonitor;
import org.python.core.PyException;
import org.autoplot.datasource.DataSetSelector;
import org.autoplot.datasource.DataSetURI;
import org.autoplot.datasource.DataSourceEditorPanel;
import org.autoplot.datasource.FileSystemUtil;
import org.autoplot.datasource.LogNames;
import org.autoplot.datasource.TimeRangeTool;
import org.autoplot.datasource.URISplit;
import org.autoplot.jythonsupport.JythonUtil;
import org.autoplot.jythonsupport.ui.EditorAnnotationsSupport;
import org.autoplot.jythonsupport.ui.ScriptPanelSupport;
import org.autoplot.jythonsupport.ui.Util;

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
    List<JComponent> tflist;
    List<String> paramsList;
    List<String> deftsList;
    List<Character> typesList; 

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
    
    private void redoVariables() {
        paramsPanel.removeAll();
        Map<String, String> params = getParamsFromGui();
        try {
            if ( doDocumentation(file) ) {
                paramsPanel.add( new JLabel("<html><br></html>") );
            }
            doVariables( file, params );
            paramsPanel.revalidate();
        } catch ( PyException ex ) {
            JOptionPane.showMessageDialog(this,"<html>Error:<br>"+ex);   
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
     * TODO: params is object?
     * @param parms
     * @return
     */
    private boolean isBoolean( List<Object> parms ) {
        if ( parms.size()==2 && parms.contains("T") && parms.contains("F") ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * add JLabels if documentation is available.
     * @param f
     * @return true if some documentation was found.
     */
    private boolean doDocumentation( File f ) {
        BufferedReader reader=null;
        boolean hasDoc= false;
        try {
            reader = new BufferedReader( new FileReader(f) );
            Map<String,String> doc= JythonUtil.getDocumentation( reader );

            String title= doc.get("TITLE");
            if ( title!=null ) {
                 paramsPanel.add( new JLabel("<html><b>"+title+"</b></html>") );
                 hasDoc= true;
            }
            String description= doc.get("DESCRIPTION");
            if ( description!=null ) {
                 paramsPanel.add( new JLabel("<html>"+description+"</html>") );
                 hasDoc= true;
            }

        } catch (IOException ex ) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            if ( reader!=null ) try {
                reader.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        return hasDoc;
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
     * See org.autoplot.jythonsupport.ui.Util.createForm
     * See org.autoplot.jythonsupport.ui.Util.doVariables which is a copy.
     * @param f
     * @param params
     * @return 
     */
    private boolean doVariables( File f, Map<String,String> params ) throws PyException {

        Map<String,JythonUtil.Param> parms;

        boolean hasVars= false;
        tflist= new ArrayList();
        paramsList= new ArrayList();
        deftsList= new ArrayList();
        typesList= new ArrayList();
        
        try {
            parms= JythonDataSourceFactory.getParams( f.toURI(), params, new NullProgressMonitor() );

            paramsPanel.add( new JLabel("<html>This script has the following input parameters.  Buttons on the right show default values.<br><br></html>") );

            for ( Entry<String,JythonUtil.Param> e: parms.entrySet() ) {
                //String s= e.getKey();
                JythonUtil.Param parm= e.getValue();
                
                String vname= parm.name;                
                String label;

                JComponent ctf;

                boolean isBool= parm.enums!=null && isBoolean( parm.enums );

                String colon= isBool ? "" : ":";

                if ( parm.doc==null ) {
                    label= vname+ colon;
                } else {
                    String doc= parm.doc;
                    if ( doc.startsWith("'") ) doc= doc.substring(1,doc.length()-1);// pop off the quotes
                    if ( !parm.label.equals(parm.name) ) {
                        doc= doc + " ("+parm.label+" inside the script)";
                    }
                    label= "<html>" + parm.name + ", <i>" + doc + "</i>"+colon+"</html>";
                }      
                
                if ( !isBool ) {
                    JPanel labelPanel= new JPanel();
                    labelPanel.setLayout( new BoxLayout( labelPanel, BoxLayout.X_AXIS ) );
                    JLabel l= new JLabel( label );
                    labelPanel.add( getSpacer() );
                    labelPanel.add( l );
                    labelPanel.setAlignmentX( JComponent.LEFT_ALIGNMENT );
                    paramsPanel.add( labelPanel );
                } else {
                    paramsPanel.add( Box.createVerticalStrut(paramsPanel.getFont().getSize()/2 ) ); //TODO: other code that writes the GUI needs this too.
                }

                JPanel valuePanel= new JPanel(  );
                valuePanel.setLayout( new BoxLayout( valuePanel, BoxLayout.X_AXIS ) );
                if ( !isBool ) valuePanel.add( getSpacer() );

                if ( parm.type=='R' ) { // ResourceURI
                    final DataSetSelector sel= new DataSetSelector();
                    sel.setPlotItButtonVisible(false);
                    sel.setEnableDataSource(false);
                    sel.setSuggestFiles(true);
                    sel.setSuggestFsAgg(true);
                    String val;
                    if (params.get(vname)!=null ) {
                        val= params.get(vname);
                        if ( val.startsWith("'") ) val= val.substring(1);
                        if ( val.endsWith("'") ) val= val.substring(0,val.length()-1);
                    } else {
                        val= String.valueOf( parm.deft );
                        params.put( vname, val );
                    }
                    sel.setRecent( DataSetSelector.getDefaultRecent() );
                    sel.setValue( val );
                    
                    valuePanel.add( getSpacer(7) );  // kludge.  Set on Jeremy's home Ubuntu
                    valuePanel.add( sel );
                    sel.setMaximumSize( new Dimension(Integer.MAX_VALUE,100) );
                    sel.setValue( val );
                    valuePanel.add( getSpacer(10) ); // put a little space in after the selector as well.
                            
                    ctf= sel;
                    
                } else if ( parm.type=='U' ) {
                    final DataSetSelector sel= new DataSetSelector();
                    sel.setPlotItButtonVisible(false);
                    JythonUtil.Param timerange= parms.get("timerange");
                    if ( timerange!=null ) {
                        try {
                            sel.setTimeRange( DatumRangeUtil.parseTimeRange(String.valueOf(timerange.deft)) );
                        } catch ( ParseException ex ) {
                            logger.log(Level.WARNING, "unable to parse as time range: {0}", timerange.deft);
                        }
                    }
                    String val;
                    if (params.get(vname)!=null ) {
                        val= params.get(vname);
                        if ( val.startsWith("'") ) val= val.substring(1);
                        if ( val.endsWith("'") ) val= val.substring(0,val.length()-1);
                    } else {
                        val= String.valueOf( parm.deft );
                        params.put( vname, val );
                    }
                    if ( timerange!=null ) {
                        String blurVal= DataSetURI.blurTsbUri(val);
                        if ( blurVal!=null ) {
                            val= blurVal;
                            logger.finer("blurring URI because timerange is used.");
                        }
                    }
                    sel.setRecent( DataSetSelector.getDefaultRecent() );
                    sel.setMaximumSize( new Dimension(Integer.MAX_VALUE,100) );
                    sel.setValue( val );
                    
                    valuePanel.add( getSpacer(7) );  // kludge.  Set on Jeremy's home Ubuntu
                    valuePanel.add( sel );
                    sel.setValue( val );
                    valuePanel.add( getSpacer(10) ); // put a little space in after the selector as well.
                            
                    ctf= sel;
                    
                } else if ( parm.type=='T' ) {
                    String val;
                    if ( params.get(vname)!=null ) {
                        val= params.get(vname);
                        if ( val.startsWith("'") ) val= val.substring(1);
                        if ( val.endsWith("'") ) val= val.substring(0,val.length()-1);
                    } else {
                        val= String.valueOf( parm.deft );
                        params.put( vname, val );
                    }
                    final JTextField tf= new JTextField();
                    Dimension x= tf.getPreferredSize();
                    x.width= Integer.MAX_VALUE;
                    tf.setMaximumSize(x);
                    tf.setAlignmentX( JComponent.LEFT_ALIGNMENT );

                    tf.setText( val );
                    ctf= tf;
                    
                    Icon fileIcon= new javax.swing.ImageIcon( Util.class.getResource("/org/autoplot/datasource/calendar.png"));
                    JButton button= new JButton( fileIcon );
                    button.addActionListener( new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            org.das2.util.LoggerManager.logGuiEvent(e);
                            TimeRangeTool tt= new TimeRangeTool();
                            tt.setSelectedRange(tf.getText());
                            int r= JOptionPane.showConfirmDialog( paramsPanel, tt, "Select Time Range", JOptionPane.OK_CANCEL_OPTION );
                            if ( r==JOptionPane.OK_OPTION) {
                                tf.setText(tt.getSelectedRange());
                            }
                        }
                    });
                    button.setToolTipText("Time Range Tool");
                    valuePanel.add( ctf );
                    button.setAlignmentX( JComponent.LEFT_ALIGNMENT );
                    valuePanel.add( button );
                    
                } else {
                    String val;
                    if ( params.get(vname)!=null ) {
                        val= params.get(vname);
                        if ( val.startsWith("'") ) val= val.substring(1);
                        if ( val.endsWith("'") ) val= val.substring(0,val.length()-1);
                    } else {
                        val= String.valueOf( parm.deft );
                    }
                    if ( parm.enums!=null && parm.enums.size()>0 ) {
                        if ( isBoolean( parm.enums ) ) {
                            JCheckBox jcb= new JCheckBox( label );
                            jcb.setSelected( val.equals("T") );
                            ctf= jcb;
                            jcb.addActionListener( new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    org.das2.util.LoggerManager.logGuiEvent(e);
                                    redoVariables();
                                }
                            });
                        } else {
                            JComboBox jcb= new JComboBox(parm.enums.toArray());
                            jcb.setEditable(false);
                            Object oval;
                            if ( parm.deft instanceof Long ) {
                                oval = Long.valueOf(val);
                            } else if ( parm.deft instanceof Integer ) {
                                oval = Integer.valueOf(val);
                            } else if ( parm.deft instanceof Double ) {
                                oval = Double.valueOf(val);
                            } else if ( parm.deft instanceof Float ) {
                                oval = Float.valueOf(val);
                            } else {
                                oval = val;
                            }
                            jcb.setSelectedItem(oval);
                            jcb.addItemListener( new ItemListener() {
                                @Override
                                public void itemStateChanged(ItemEvent e) {
                                    redoVariables();
                                }
                            });
                            
                            ctf= jcb;
                            Dimension x= ctf.getPreferredSize();
                            x.width= Integer.MAX_VALUE;
                            ctf.setMaximumSize(x);
                            ctf.setAlignmentX( JComponent.LEFT_ALIGNMENT );
                        }
                    } else {
                        JTextField tf= new JTextField();
                        Dimension x= tf.getPreferredSize();
                        x.width= Integer.MAX_VALUE;
                        tf.setMaximumSize(x);
                        tf.setAlignmentX( JComponent.LEFT_ALIGNMENT );

                        tf.setText( val );
                        ctf= tf;
                    }
                    
                    valuePanel.add( ctf );
                }

                boolean shortLabel= ( parm.type=='R' || String.valueOf(parm.deft).length()>22 ) ;
                final String fdeft= shortLabel ? "default" : String.valueOf(parm.deft);
                final String fvalue= String.valueOf(parm.deft);
                final JComponent ftf= ctf;
                JButton defaultButton= new JButton( new AbstractAction( fdeft ) {
                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        if ( ftf instanceof DataSetSelector ) {
                            ((DataSetSelector)ftf).setValue(fvalue);
                        } else if ( ftf instanceof JComboBox ) {
                            JComboBox jcb= ((JComboBox)ftf);
                            for ( int i=0; i<jcb.getItemCount(); i++ ) {
                                if ( fvalue.equals( jcb.getItemAt(i).toString() ) ) {
                                    jcb.setSelectedIndex(i);
                                }
                            }
                        } else if ( ftf instanceof JCheckBox ) {
                            ((JCheckBox)ftf).setSelected( fvalue.equals("T") );
                        } else {
                            ((JTextField)ftf).setText(fvalue);
                        }
                    }
                });
                defaultButton.setToolTipText( shortLabel ? String.valueOf(parm.deft) : "Click to reset to default" );
                valuePanel.add( defaultButton );
                valuePanel.add( getSpacer() );
                valuePanel.setAlignmentX( JComponent.LEFT_ALIGNMENT );

                paramsPanel.add( valuePanel );
                tflist.add(ctf);

                paramsList.add( parm.name );
                deftsList.add( String.valueOf( parm.deft ) );
                typesList.add( parm.type );

            }
                
            hasVars= parms.size()>0;

            if ( !hasVars ) {
                paramsPanel.add( new JLabel("<html><i>no input parameters</i></html>") );
            }

            paramsPanel.add( Box.createVerticalStrut(paramsPanel.getFont().getSize()*2 ) );
            
            paramsPanel.revalidate();

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
        for ( int j=0; j<paramsList.size(); j++ ) {
            String name= paramsList.get(j);
            JComponent jc= tflist.get(j);
            String value;
            if ( jc instanceof JTextField ) {
                value= ((JTextField)jc).getText();
            } else if ( jc instanceof DataSetSelector ) {
                value= ((DataSetSelector)jc).getValue();
                if ( params.get("timerange")!=null ) {
                    String blurredValue= DataSetURI.blurTsbUri(value);
                    if ( blurredValue!=null ) { // if the value URI was not a TSB.
                        value= blurredValue;
                    }
                }
            } else if ( jc instanceof JComboBox ) {
                value= String.valueOf( ((JComboBox)jc).getSelectedItem() );
            } else if ( jc instanceof JCheckBox ) {
                value= ((JCheckBox)jc).isSelected() ? "T" : "F";
            } else {
                throw new IllegalArgumentException("the code needs attention: component for "+name+" not supported ");
            }
            
            String deft= deftsList.get(j);
            char type= typesList.get(j);

            if ( name.equals( JythonDataSource.PARAM_TIMERANGE ) || ! value.equals(deft) || params.containsKey(name) ) {
                if ( type=='A' ) {
                    value= value.replaceAll("\'", "");
                    //The value is escaped when the URI is formatted, so there's no reason to enquote it here.
                    //boolean containsSpaces= value.contains(" ");
                    //if ( containsSpaces && !( value.startsWith("'") && value.endsWith("'") ) ) {
                    //    value=  "'" + value + "'";
                    //}
                    params.put( name, value );
                } else if ( type=='R' ) {
                    URISplit ruriSplit= URISplit.parse(value);
                    if ( !params.containsKey(JythonDataSource.PARAM_SCRIPT) ) {
                        params.put( JythonDataSource.PARAM_SCRIPT, split.resourceUri.toString() );
                        params.put( JythonDataSource.PARAM_RESOURCE_URI, ruriSplit.resourceUri.toString() );
                    }
                    split.resourceUri= ruriSplit.resourceUri;
                    split.scheme= ruriSplit.scheme;
                    split.authority= ruriSplit.authority;
                    split.path= ruriSplit.path;
                    split.file= ruriSplit.file;
                    if ( split.vapScheme==null ) split.vapScheme= "vap+jyds";
                    params.put( JythonDataSource.PARAM_RESOURCE_URI, split.file  );
                } else {
                    params.put( name, value );
                }
            } else if ( type=='R' && value.equals(deft) && ( split.resourceUri==null || !split.resourceUri.toString().equals(deft) ) ) {
                URISplit ruriSplit= URISplit.parse(value); //TODO: consider removing script=param.
                if (params.get( JythonDataSource.PARAM_SCRIPT )==null ) {
                    params.put( JythonDataSource.PARAM_SCRIPT, split.resourceUri.toString() );
                    params.put( JythonDataSource.PARAM_RESOURCE_URI, ruriSplit.resourceUri.toString() );
                }
                split.resourceUri= ruriSplit.resourceUri;
                split.scheme= ruriSplit.scheme;
                split.authority= ruriSplit.authority;
                split.path= ruriSplit.path;
                split.file= ruriSplit.file;
                if ( split.vapScheme==null ) split.vapScheme= "vap+jyds"; // DANGER--this ought-not to be hard coded, but this is all I can do for now...
            }
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

            if ( doDocumentation(f) ) {
                paramsPanel.add( new JLabel("<html><br></html>") );
            }

            Map<String,String> ffparams= new HashMap( params );

            if ( furir[1]!=null ) ffparams.put( JythonDataSource.PARAM_RESOURCE_URI, furir[1] );
            
            support.loadFile(f);

            if ( FileSystemUtil.isChildOf( FileSystem.settings().getLocalCacheDir(), support.getFile() ) || !support.getFile().canWrite() ) {
                support.setReadOnly();
            }

            try {
                hasVariables= doVariables( f,ffparams );
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
