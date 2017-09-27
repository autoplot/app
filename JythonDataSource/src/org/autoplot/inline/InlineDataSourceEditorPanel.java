/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.autoplot.inline;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.das2.datum.DatumRange;
import org.das2.datum.DatumRangeUtil;
import org.das2.jythoncompletion.ui.CompletionImpl;
import org.das2.util.LoggerManager;
import org.das2.util.monitor.ProgressMonitor;
import org.autoplot.datasource.DataSourceEditorPanel;
import org.autoplot.datasource.DataSourceUtil;
import org.autoplot.jythonsupport.Util;
import org.autoplot.jythonsupport.ui.DataMashUp;

/**
 * Editor panel for inline URIs.  This supports events lists and short 
 * Jython scripts with a simple editor.
 * @author jbf
 */
public class InlineDataSourceEditorPanel extends javax.swing.JPanel implements DataSourceEditorPanel {

    /**
     * true if the timerange field should be turned on.
     */
    private boolean needTimeRange=false;

    /**
     * Creates new form InlineDataSourceEditorPanel
     */
    public InlineDataSourceEditorPanel() {
    }

    private static final String SCHEME_EVENT_LIST= "eventList";
    private static final String SCHEME_Y_VS_T= "y_vs_t";
    private static final String SCHEME_EVENT_LIST_COLORS= "eventListColors";
    
    private String scheme= SCHEME_EVENT_LIST;
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        schemeComboBox = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        deleteSelectedButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        directionsLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        examplesButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        editorTextPane1 = new org.autoplot.jythonsupport.ui.EditorTextPane();
        jPanel3 = new javax.swing.JPanel();
        dataMashUp1 = new org.autoplot.jythonsupport.ui.DataMashUp();

        setName("inlineDataSourceEditorPanel"); // NOI18N

        jTabbedPane1.setToolTipText("jython tab allows short scripts to be constructed");
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        schemeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Event List", "Event List w/Colors", "Y(X)" }));
        schemeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schemeComboBoxActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane1.setViewportView(table);

        addButton.setText("Add...");
        addButton.setToolTipText("Add a record");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        deleteSelectedButton.setText("Delete Selected");
        deleteSelectedButton.setToolTipText("Delete selected records");
        deleteSelectedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSelectedButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Dataset Type:");

        directionsLabel.setText("<html><i>Enter a list of times or points</i></html>");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(directionsLabel)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(deleteSelectedButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(schemeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(schemeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(directionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteSelectedButton)
                        .addGap(0, 188, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("data", jPanel1);

        jLabel1.setText("<html>Enter lines of jython assignments and expressions.  Expressions are interpreted as the X values, then Y values, then Z values if specified.");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        examplesButton.setText("Examples...");
        examplesButton.setToolTipText("Example scripts");
        examplesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                examplesButtonActionPerformed(evt);
            }
        });

        editorTextPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                editorTextPane1FocusGained(evt);
            }
        });
        jScrollPane3.setViewportView(editorTextPane1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(examplesButton, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(examplesButton)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("jython", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dataMashUp1, javax.swing.GroupLayout.PREFERRED_SIZE, 616, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dataMashUp1, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("mash up", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private static ComboBoxModel getExamplesComboBoxModel( int icol, String scheme ) {
        if ( scheme.equals(SCHEME_EVENT_LIST_COLORS) ) {
            ComboBoxModel m;
            switch ( icol ) {
                case 0:
                    m= new DefaultComboBoxModel( new String[] { 
                        "2014-01-01T01:01Z",
                        "2014-01-01T01:01:01.000Z",
                    } );
                    break;
                case 1:
                    m= new DefaultComboBoxModel( new String[] { 
                        "2014-01-01T01:01Z",
                        "2014-01-01T01:01:01.000Z",
                    } );
                    break;
                case 2:
                    m= new DefaultComboBoxModel( new String[] {
                        "0x000000", "0xA0A0A0", "0xFFFFFF", "0xFF0000", "0x00FF00", "0x0000FF" 
                    } );
                    break;
                case 3:
                    m= new DefaultComboBoxModel( new String[] { 
                        "okay",
                        "error",
                    } );
                    break;
                default:
                    throw new IllegalArgumentException("bad column.");
            }
            return m;
                
        } else {
            if ( icol==0 ) {
                return new DefaultComboBoxModel( new String[] { 
                    "2014-01-01T01:01Z",
                    "2014-01-01T01:01:01.000Z",
                    "1.23"
                } );            
            } else {
                return new DefaultComboBoxModel( new String[] {
                    "1.23"
                });
            }
        }
    }
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        LoggerManager.logGuiEvent(evt);
        JPanel p= new JPanel();
        if ( tm.getColumnCount()>3 ) {
            p.setLayout( new BoxLayout(p,BoxLayout.Y_AXIS) );
        } else {
            p.setLayout( new FlowLayout() );
        }
        JTextField[] tfs= new JTextField[tm.getColumnCount()];
        for ( int i=0; i<tm.getColumnCount(); i++ ) {  // load up the last record so it can be edited to make new record
            JComboBox cb1= new JComboBox();
            cb1.setToolTipText("Examples");
            cb1.setModel( getExamplesComboBoxModel( i, scheme ) );
            cb1.setEditable(true);
            JTextField tf1= ((JTextField)cb1.getEditor().getEditorComponent());
            tf1.setAlignmentX(0.f);
            tf1.setColumns(20);
            //if ( i==0 ) tf1.requestFocusInWindow(); no effect on Linux, probably because of modal dialog.
            int ir= tm.getRowCount()-1;
            if ( tm.getRowCount()>0 ) {
                tf1.setText( String.valueOf( tm.getValueAt(ir,i) ) );
            }
            p.add(cb1);
            tfs[i]= tf1;
        }
        if ( JOptionPane.showConfirmDialog(schemeComboBox,p,"Enter Data Point", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION ) {
            String[] ss= new String[tm.getRowCount()+1];
            for ( int i=0; i<tm.getRowCount(); i++ ) {
                if ( tm.getColumnCount()>1 ) {
                    StringBuilder sb= new StringBuilder();
                    sb.append((String)tm.getValueAt(i,0));
                    for ( int j=1; j<tm.getColumnCount(); j++ ) {
                        sb.append(",").append((String)tm.getValueAt(i,j));
                    }
                    ss[i]= sb.toString();
                } else {
                    ss[i]= (String)tm.getValueAt(i,0);
                }
            }
            StringBuilder sval= new StringBuilder(tfs[0].getText());
            for ( int i=1; i<tm.getColumnCount(); i++ ) {
                sval.append(",").append(tfs[i].getText());
            }
            if ( tm.getColumnCount()>1 ) {
                ss[tm.getRowCount()]= sval.toString();                
                tm= toTableModel( DataSourceUtil.strjoin( Arrays.asList(ss), ";" ), 2 );
            } else {
                ss[tm.getRowCount()]= sval.toString();
                tm= toTableModel(ss);
            }
            setColumnLabels();
            table.setModel( tm );
            
        }
        
    }//GEN-LAST:event_addButtonActionPerformed

    private String getValueAt( TableModel tm, int i, int j ) {
        String ss= (String)tm.getValueAt(i,j);
        ss= ss.replaceAll(",","");
        ss= ss.replaceAll(";","");
        return ss;
    }

    
    private void deleteSelectedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSelectedButtonActionPerformed
        LoggerManager.logGuiEvent(evt);
        int[] rows= table.getSelectedRows();
        //String[] ss= new String[tm.getRowCount()-rows.length];
        StringBuilder sb= new StringBuilder();
        
        int irow= 0;
        int k= 0;
        
        if ( rows.length==0 ) {
            return;
        }
        
        boolean rank2Table= tm.getColumnCount()>1;
        
        for ( int i=0; i<tm.getRowCount(); i++ ) {
            if ( irow<rows.length && rows[irow]==i ) {
                irow++;
            } else {
                if ( k>0 ) sb.append( rank2Table ? ";" : ",");
                if ( rank2Table ) {
                    for ( int j=0; j<tm.getColumnCount(); j++ ) {
                        if ( j>0 ) sb.append(",");
                        sb.append( getValueAt(tm,i,j));
                    }
                } else {
                    sb.append( getValueAt(tm,i,0));
                }
                k++;
            }
        }
        int rank= tm.getColumnCount()>1 ? 2 : 1;
        tm= toTableModel(sb.toString(), rank );
        setColumnLabels();
        table.setModel( tm );
    }//GEN-LAST:event_deleteSelectedButtonActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        LoggerManager.logGuiEvent(evt);
        if ( jTabbedPane1.getSelectedIndex()==0 ) {
            if ( tm==null ) {
                tm= toTableModel( new String[0] );
            }
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void initializeScheme() {
        switch ( schemeComboBox.getSelectedIndex() ) {
            case 0:
                tm= toTableModel( new String[0] );
                directionsLabel.setText("<html><i>Enter a list of times or points</i></html>");
                scheme= SCHEME_EVENT_LIST;
                tm.setColumnIdentifiers( new String[] { "x" } );
                break;
            case 1:
                tm= toTableModel( 0, 4 );
                directionsLabel.setText("<html><i>Enter a list of times, colors (0xRRGGBB), and labels</i></html>");
                scheme= SCHEME_EVENT_LIST_COLORS;
                tm.setColumnIdentifiers( new String[] { "start", "end", "color", "message" } );
                break;
            case 2:
                tm= toTableModel( 0, 2 );
                directionsLabel.setText("<html><i>Enter a list X and Y values</i></html>");
                scheme= SCHEME_Y_VS_T;
                tm.setColumnIdentifiers( new String[] { "x", "y" } );
                break;
            default:
                throw new IllegalArgumentException("whoops");
        }
        table.setModel( tm );
    }
    
    private void schemeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schemeComboBoxActionPerformed
        LoggerManager.logGuiEvent(evt);
        initializeScheme();
    }//GEN-LAST:event_schemeComboBoxActionPerformed

    private void examplesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_examplesButtonActionPerformed
        LoggerManager.logGuiEvent(evt);
        final String t1= "linspace(0,5*PI,100)\nsin(linspace(0,5*PI,100))";
        final String t2= "t=linspace(0,5*PI,100)\nt,sin(t)";
        final String t3= "createEvent('2014-005',0x00FF00,'On')";
        final String t4= "ds=createEvent('2014-005T12:00/12:10',0x00FF00,'Instrument On')\nds=createEvent(ds,'2014-005T13:00/13:10',0xFF0000,'Instrument Off')\nds=createEvent(ds,'2014-005T14:00/14:10',0x00FF00,'Instrument On')\nds";
        final JTextArea tf1= new JTextArea(5,40);
        tf1.setText(t1);
        final JComboBox examples= new JComboBox( new DefaultComboBoxModel( new Object[] { "Sine","Sine With Variable","createEvent","multiple events" }) );
        examples.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (examples.getSelectedIndex()) {
                    case 0:
                        tf1.setText(t1);
                        break;
                    case 1:
                        tf1.setText(t2);
                        break;
                    case 2:
                        tf1.setText(t3);
                        break;
                    case 3:
                        tf1.setText(t4);
                        break;
                    default:
                        break;
                }
            }
        } );
        JPanel p= new JPanel();
        p.setLayout( new BorderLayout() );
        p.add( examples, BorderLayout.NORTH );
        p.add( tf1, BorderLayout.CENTER );
        if ( JOptionPane.OK_OPTION==JOptionPane.showConfirmDialog( examplesButton, p, "Example Inline Jython", JOptionPane.OK_CANCEL_OPTION ) ) {
            editorTextPane1.setText( tf1.getText() );
        }
    }//GEN-LAST:event_examplesButtonActionPerformed

    private void editorTextPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_editorTextPane1FocusGained
        CompletionImpl impl = CompletionImpl.get();
        impl.startPopup(editorTextPane1);
    }//GEN-LAST:event_editorTextPane1FocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private org.autoplot.jythonsupport.ui.DataMashUp dataMashUp1;
    private javax.swing.JButton deleteSelectedButton;
    private javax.swing.JLabel directionsLabel;
    private org.autoplot.jythonsupport.ui.EditorTextPane editorTextPane1;
    private javax.swing.JButton examplesButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox schemeComboBox;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    DefaultTableModel tm;
    
    String program;
    JTextField tf;
    String mashupUri= null;
    
    @Override
    public boolean reject(String uri) throws Exception {
        return false;
    }

    @Override
    public boolean prepare(String uri, Window parent, ProgressMonitor mon) throws Exception {
        return true;
    }

    private static DefaultTableModel toTableModel( final int nr, final int nc ) {
        DefaultTableModel tm= new DefaultTableModel( nr, nc );
        return tm;

    }
    
    /**
     *
     * @param s the value of s
     * @param rank the value of rank
     */
    private static DefaultTableModel toTableModel( final String s, int rank) {
        final String[] ss= s.split(";");
        if ( rank==1 ) {
            return toTableModel( s.split(",") );
        }
        final int nc= ss[0].split(",").length;
        
        DefaultTableModel tm= new DefaultTableModel(ss.length,nc) {

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                String[] sss= ss[rowIndex].split(",");
                if ( sss.length>columnIndex ) {
                    return sss[columnIndex];
                } else {
                    return "";
                }                
            }

            @Override
            public void setValueAt( Object v, int row, int col ) {
                String[] sss= ss[row].split(",");
                sss[col]= String.valueOf(v);
                StringBuilder b= new StringBuilder(sss[0].replaceAll(",",""));
                for ( int j=1; j<sss.length; j++ ) {
                    b.append(',').append(sss[j].replaceAll(",",""));
                }
                ss[row]= b.toString();
            }
        };
        return tm;
    }
    
    /**
     * set the column labels for the scheme.
     */
    private void setColumnLabels() {
        switch( scheme ) {
            case SCHEME_EVENT_LIST:
                tm.setColumnIdentifiers( new String[] { "x" } );
                break;
            case SCHEME_EVENT_LIST_COLORS:
                tm.setColumnIdentifiers( new String[] { "start", "stop", "color", "message" } );
                break;
            case SCHEME_Y_VS_T:
                tm.setColumnIdentifiers( new String[] { "x", "y" } );
                break;
            default:
                break;
        }
    }
    /**
     * create one-column table.
     * @param s array of all the values.
     * @return 
     */
    private static DefaultTableModel toTableModel( final String[] s ) {
        DefaultTableModel tm= new DefaultTableModel( s.length, 1 ) {

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return s[rowIndex];
            }

            @Override
            public void setValueAt( Object v, int row, int col ) {
                s[row]= String.valueOf(v).replaceAll(",","");
            }
        };
        return tm;
    }
    
    /**
     * look for a list of createEvent calls that build up a set of events, and
     * return the table if this is what's found, or null otherwise.
     * @param uri
     * @return 
     */
    private static DefaultTableModel detectRichEventsList( String uri ) {
        String[] ss= Util.guardedSplit( uri, '&', '\'', '\"' );
        DefaultTableModel mtm= new DefaultTableModel( ss.length-1, 4 );
        Pattern p= Pattern.compile("ds=createEvent\\((ds\\,)?\\'(.*)\\'\\,(.*)\\,\\'(.*)\\'\\)");
        for ( int i=0; i<ss.length; i++ ) {
            if ( i<ss.length-1 ) {
                Matcher m= p.matcher(ss[i]);
                if ( m.matches() ) {
                    String time= m.group(2);
                    try {
                        DatumRange tr= DatumRangeUtil.parseTimeRange(time);
                        mtm.setValueAt( tr.min().toString(), i, 0 );
                        mtm.setValueAt( tr.max().toString(), i, 1 );
                        mtm.setValueAt( m.group(3), i, 2 );
                        mtm.setValueAt( m.group(4), i, 3 );
                    } catch ( ParseException ex ) {
                        return null;
                    }
                }

            } else if (i==ss.length-1 ) {
                if ( !ss[i].equals("ds") ) {
                    return null;
                }
            }
        }
        return mtm;
    }
    
    @Override
    public void setURI(String uri) {
        if ( uri.startsWith("vap+inline:") ) {
            uri= uri.substring(11);
        }
        if ( DataMashUp.isDataMashupJythonInline( uri ) ) {
            mashupUri= uri;
        }
        
        DefaultTableModel mtm= detectRichEventsList(uri);
        
        if ( uri.length()==0 || Character.isDigit( uri.charAt(0) ) ) {
            int amp= uri.indexOf("&");
            if ( amp==-1 ) amp= uri.length();
            String lit= uri.substring(0,amp);
            if ( lit.contains(";") ) {
                this.tm= toTableModel(lit, 2 );
                scheme= SCHEME_Y_VS_T;
            } else {
                this.tm= toTableModel(lit, 1);
                scheme= SCHEME_EVENT_LIST;
            }
            setColumnLabels();
        } else if ( mtm!=null ) {
            tm= mtm;
            scheme= SCHEME_EVENT_LIST_COLORS;
            setColumnLabels();
            String[] ss= Util.guardedSplit( uri, '&', '\'', '\"' );
            //String[] ss= uri.split("&");
            StringBuilder t= new StringBuilder();
            for ( int i=0; i<ss.length; i++ ) {
                if ( i>0 ) t.append("\n");
                t.append(ss[i]);
            }
            program= t.toString(); // go ahead and set this as well.
        } else {
            String[] ss= Util.guardedSplit( uri, '&', '\'', '\"' );
            //String[] ss= uri.split("&");
            StringBuilder t= new StringBuilder();
            for ( int i=0; i<ss.length; i++ ) {
                if ( i>0 ) t.append("\n");
                t.append(ss[i]);
            }
            program= t.toString();
            this.tm= null;
        }
    }

    @Override
    public void markProblems(List<String> problems) {
        if ( problems.contains("no timerange provided") ) { //TODO: this needs to work in data sources.
            needTimeRange = true;
        }
    }

    @Override
    public JPanel getPanel() {
        DefaultTableModel ltm= tm;
        initComponents();

        if ( program!=null ) {
            editorTextPane1.setContentType("text/python");
            editorTextPane1.setText(program);
        }
        if ( ltm!=null ) {
            switch (tm.getColumnCount()) {
                case 2:
                    this.schemeComboBox.setSelectedIndex(2);
                    break;
                case 4:
                    this.schemeComboBox.setSelectedIndex(1);
                    break;
                default:
                    this.schemeComboBox.setSelectedIndex(0);
                    break;
            }
            tm= ltm; // kludgy way to get around goofy code.
            this.table.setModel(ltm); //schemeComboBox.setSelectedIndex resets the table.
            tf= new JTextField();
            tf.setEditable(true);
            this.table.setCellEditor(new DefaultCellEditor(tf) );
            
            int cellHeight = 21;  // c.getPreferredSize().height;

            table.setRowHeight( cellHeight );

        } else if ( mashupUri!=null ) {
            Runnable run= new Runnable() {
                @Override
                public void run() {
                    dataMashUp1.setAsJythonInline(mashupUri);
                }
            };
            new Thread( run ).start();
            if ( needTimeRange ) {
                dataMashUp1.enableTimeRange();
            }
            this.jTabbedPane1.setSelectedIndex(2);
            
        } else {
            this.schemeComboBox.setSelectedIndex(0);
            this.jTabbedPane1.setSelectedIndex(1);
        }
        return this;
    }

    @Override
    public String getURI() {
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
            {
                StringBuilder s= new StringBuilder( "vap+inline:" );
                if ( scheme.equals(SCHEME_EVENT_LIST_COLORS) ) {
                    for ( int i=0; i<tm.getRowCount(); i++ ) {
                        String str= String.format( "%s/%s", tm.getValueAt(i,0), tm.getValueAt(i,1) );
                        try {
                            DatumRange drtr= DatumRangeUtil.parseTimeRange(str);
                            str= drtr.toString().replaceAll(" ","+");
                        } catch (ParseException ex) {
                            // do nothing, just use the old format, which will fail and reject.
                        }
                        if ( i==0 ) {
                            s.append( String.format( "ds=createEvent('%s',%s,'%s')", str, tm.getValueAt(i,2), tm.getValueAt(i,3) ) );
                        } else {
                            s.append( String.format( "&ds=createEvent(ds,'%s',%s,'%s')", str, tm.getValueAt(i,2), tm.getValueAt(i,3) ) );
                        }
                    }
                    s.append("&ds");
                } else {
                    for ( int i=0; i<tm.getRowCount(); i++ ) {
                        if ( tm.getColumnCount()>1 ) {
                            for ( int j=0; j<tm.getColumnCount();j++ ) {
                                if ( j>0 ) s.append(",");
                                s.append(tm.getValueAt(i,j));
                            }
                            s.append(";");
                        } else {
                            if ( i>0 ) s.append(",");
                            s.append(tm.getValueAt(i,0));
                        }
                    }
                }
                if ( tm.getColumnCount()==1 ) {
                    s.append("&RENDER_TYPE=eventsBar");
                }
                return s.toString();
            }
            case 1:
            {
                StringBuilder s= new StringBuilder( "vap+inline:" );
                String t= editorTextPane1.getText();
                String[] ss= t.split("\n");
                for ( int i=0; i<ss.length; i++ ) {
                    if ( i>0 ) s.append("&");
                    s.append(ss[i]);
                }
                return s.toString();
            }
            default:
                return dataMashUp1.getAsJythonInline();
        }
    }
    
    public static void main( String[] args ) {
        //String uri= "vap+inline:ripples(20,20)";
        //String uri= "vap+inline:20,30,40";
        String uri= "vap+inline:ds=getDataSet('vap+inline:ripples(10)')&ff=getDataSet('vap+inline:ones(10)')&ds-ff";
        DataSourceEditorPanel ds= new InlineDataSourceEditorPanel();
        ds.setURI(uri);
        if ( JOptionPane.OK_OPTION== JOptionPane.showConfirmDialog( null, ds.getPanel(), "Test Inline Editor", JOptionPane.OK_CANCEL_OPTION ) ) {
            System.err.println(ds.getURI());
        }
        
    }
}
