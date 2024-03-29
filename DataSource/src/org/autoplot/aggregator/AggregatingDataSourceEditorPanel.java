/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AggregatingDataSourceEditorPanel.java
 *
 * Created on Apr 22, 2009, 8:37:48 AM
 */
package org.autoplot.aggregator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.autoplot.help.AutoplotHelpSystem;
import org.das2.datum.DatumRange;
import org.das2.datum.DatumRangeUtil;
import org.das2.datum.TimeUtil;
import org.das2.fsm.FileStorageModel;
import org.das2.util.LoggerManager;
import org.das2.util.monitor.NullProgressMonitor;
import org.das2.util.monitor.ProgressMonitor;
import org.autoplot.datasource.CompletionsDataSourceEditor;
import org.autoplot.datasource.DataSetSelector;
import org.autoplot.datasource.DataSetURI;
import org.autoplot.datasource.DataSourceEditorPanel;
import org.autoplot.datasource.DataSourceEditorPanelUtil;
import org.autoplot.datasource.SourceTypesBrowser;
import org.autoplot.datasource.TimeRangeTool;
import org.autoplot.datasource.URISplit;
import org.autoplot.datasource.capability.TimeSeriesBrowse;
import org.autoplot.datasource.ui.PromptComboBoxEditor;

/**
 *
 * @author jbf
 */
public class AggregatingDataSourceEditorPanel extends javax.swing.JPanel implements DataSourceEditorPanel {

    private static final Logger logger= LoggerManager.getLogger( "apdss.agg");
    
    /** Creates new form AggregatingDataSourceEditorPanel */
    public AggregatingDataSourceEditorPanel() {
        initComponents();
        timeRangeComboBox.setName("timeRangeEditor");
        timeRangeComboBox.setMinimumSize( new Dimension( 200,30) ); // long items in history cause problems.
        
        timeRangeComboBox.setPreferenceNode("timerange");
        timeRangeComboBox.setEditor( new PromptComboBoxEditor("Time range to view (e.g. 2010-01-01)") );
        timeRangeComboBox.setToolTipText("Recently entered time ranges");
        ((JComponent)timeRangeComboBox.getEditor().getEditorComponent()).setToolTipText("Time Range, right-click for examples");
        AutoplotHelpSystem.getHelpSystem().registerHelpID(this, "aggregator_main");
    }
    String uri;
    List<DatumRange> ranges;
    boolean updatingDropLists = false;  // avoid re-entry in droplists.
    boolean droplistIsDirty= false;

    boolean hasTimeFields= true;
    
    public void setDelegateEditorPanel(DataSourceEditorPanel edit) {
        delegateEditorPanel = edit;
    }

    private void copyTimes( int modifiers ) {
        String syr = String.valueOf(yearsComboBox.getSelectedItem()).trim();
        String smn = String.valueOf(monthsComboBox.getSelectedItem()).trim();
        String sday = String.valueOf(daysComboBox.getSelectedItem()).trim();
        String range = syr + " " + smn + " " + sday;
        range = range.trim();
        if ((modifiers & ActionEvent.SHIFT_MASK) == ActionEvent.SHIFT_MASK) {
            DatumRange dr1 = DatumRangeUtil.parseTimeRangeValid(range);
            DatumRange dr2;
            try {
                dr2 = DatumRangeUtil.parseTimeRange(timeRangeComboBox.getSelectedItem().toString());
                dr2 = DatumRangeUtil.union(dr1, dr2);
                timeRangeComboBox.setSelectedItem(dr2.toString());
            } catch (ParseException ex) {
                timeRangeComboBox.setSelectedItem(range);
            }
        } else {
            timeRangeComboBox.setSelectedItem(range);
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

        delegatePanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        outerRangeTextField = new javax.swing.JLabel();
        yearsComboBox = new javax.swing.JComboBox();
        monthsComboBox = new javax.swing.JComboBox();
        daysComboBox = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        delegateTextField = new javax.swing.JTextField();
        timeRangeToolButton = new javax.swing.JButton();
        reduceCB = new javax.swing.JCheckBox();
        availabilityCB = new javax.swing.JCheckBox();
        timeRangeComboBox = new org.autoplot.datasource.RecentComboBox();

        delegatePanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Time Range for Aggregation:");
        jLabel1.setToolTipText("<html>Enter the time range to over which to aggregate data. <br>\nData from files within this range will be combined by concatenating over the first dimension.</br>\n</html>\n\n");

        outerRangeTextField.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        outerRangeTextField.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/autoplot/aggregator/spinner_16.gif"))); // NOI18N
        outerRangeTextField.setText("listing to get available time ranges...");

        yearsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "..." }));
        yearsComboBox.setToolTipText("Select from available years");
        yearsComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                yearsComboBoxItemStateChanged(evt);
            }
        });
        yearsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearsComboBoxActionPerformed(evt);
            }
        });

        monthsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "...", " " }));
        monthsComboBox.setToolTipText("Select from available months");
        monthsComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                monthsComboBoxItemStateChanged(evt);
            }
        });
        monthsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthsComboBoxActionPerformed(evt);
            }
        });

        daysComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "...", " " }));
        daysComboBox.setToolTipText("Select from available days");
        daysComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                daysComboBoxActionPerformed(evt);
            }
        });

        jButton1.setText("Select This Time Range");
        jButton1.setToolTipText("<html>\nCopy the date into the time range field.<br>\nHolding shift down will add the selected time to the aggregation.<br>\n</html>");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/autoplot/datasource/help.png"))); // NOI18N
        jButton3.setToolTipText("Help for this component\n");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        delegateTextField.setEditable(false);
        delegateTextField.setFont(delegateTextField.getFont().deriveFont(delegateTextField.getFont().getSize()-4f));
        delegateTextField.setText("example file used for editing goes here.");
        delegateTextField.setToolTipText("this only indicates the delegate file used to edit the rest of the URI above");

        timeRangeToolButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/autoplot/datasource/calendar.png"))); // NOI18N
        timeRangeToolButton.setToolTipText("Time Range Tool");
        timeRangeToolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeRangeToolButtonActionPerformed(evt);
            }
        });

        reduceCB.setText("reduce");
        reduceCB.setToolTipText("Reduce data by averaging as it is loaded");

        availabilityCB.setText("availability");
        availabilityCB.setToolTipText("Show data availability instead of loading data.  This simply shows if granule files are found or not, so empty or near-empty granules still are marked as available.\n");
        availabilityCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                availabilityCBActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(timeRangeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 264, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(timeRangeToolButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jButton3))
            .add(jPanel1Layout.createSequentialGroup()
                .add(7, 7, 7)
                .add(yearsComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 93, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(monthsComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 88, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(daysComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton1)
                .addContainerGap(244, Short.MAX_VALUE))
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(delegateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .add(outerRangeTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(availabilityCB)
                            .add(reduceCB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 119, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(new java.awt.Component[] {monthsComboBox, yearsComboBox}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jButton3)
                    .add(timeRangeToolButton)
                    .add(timeRangeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(reduceCB)
                    .add(outerRangeTextField))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(yearsComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(monthsComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(daysComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jButton1))
                    .add(availabilityCB))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(delegateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(delegatePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(delegatePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jButton1.setEnabled(false);
        org.das2.util.LoggerManager.logGuiEvent(evt);
        copyTimes(evt.getModifiers());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void yearsComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_yearsComboBoxItemStateChanged
        if (!updatingDropLists) {
            updateDropLists(false, true);
        }
    }//GEN-LAST:event_yearsComboBoxItemStateChanged

    private void monthsComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_monthsComboBoxItemStateChanged
        if (!updatingDropLists) {
            updateDropLists(false, false);
        }
    }//GEN-LAST:event_monthsComboBoxItemStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);
        AutoplotHelpSystem.getHelpSystem().displayHelpFromEvent(evt,this);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void timeRangeToolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeRangeToolButtonActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);
        TimeRangeTool t=new TimeRangeTool();
        Object otr= timeRangeComboBox.getSelectedItem();
        String tr= otr==null ? "" : otr.toString();
        if ( tr.trim().length()==0 ) {
            tr= "2010-01-01"; // default
        }
        t.setSelectedRange( tr );
        if ( JOptionPane.OK_OPTION==JOptionPane.showConfirmDialog( this, t, "Select time range", JOptionPane.OK_CANCEL_OPTION ) ) {
            String str= t.getSelectedRange();
            timeRangeComboBox.setSelectedItem( DatumRangeUtil.parseTimeRangeValid(str).toString() );
        }
    }//GEN-LAST:event_timeRangeToolButtonActionPerformed

    private void availabilityCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_availabilityCBActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);
        // TODO add your handling code here:
    }//GEN-LAST:event_availabilityCBActionPerformed

    private void yearsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearsComboBoxActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);
        droplistIsDirty= true;
        jButton1.setEnabled(true);
    }//GEN-LAST:event_yearsComboBoxActionPerformed

    private void monthsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthsComboBoxActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);
        droplistIsDirty= true;
        jButton1.setEnabled(true);
    }//GEN-LAST:event_monthsComboBoxActionPerformed

    private void daysComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_daysComboBoxActionPerformed
        org.das2.util.LoggerManager.logGuiEvent(evt);
        droplistIsDirty= true;
        jButton1.setEnabled(true);
    }//GEN-LAST:event_daysComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox availabilityCB;
    private javax.swing.JComboBox daysComboBox;
    private javax.swing.JPanel delegatePanel;
    private javax.swing.JTextField delegateTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox monthsComboBox;
    private javax.swing.JLabel outerRangeTextField;
    private javax.swing.JCheckBox reduceCB;
    private org.autoplot.datasource.RecentComboBox timeRangeComboBox;
    private javax.swing.JButton timeRangeToolButton;
    private javax.swing.JComboBox yearsComboBox;
    // End of variables declaration//GEN-END:variables

    @Override
    public JPanel getPanel() {
        return this;
    }
    JComponent delegateComponent = null;
    DataSourceEditorPanel delegateEditorPanel = null;
    URISplit split;
    Map<String, String> params;

    private void setDelegateComponent(JComponent c) {
        if (delegateComponent != null) {
            delegatePanel.remove(delegateComponent);
        }
        delegateComponent = c;
        delegatePanel.add(c, BorderLayout.CENTER);
        delegatePanel.validate();
    }

    private void updateTimeRanges() {
        DatumRange dr = null;
        try {
            FileStorageModel fsm = AggregatingDataSourceFactory.getFileStorageModel(uri);
            String[] names = fsm.getNamesFor(null, new NullProgressMonitor());
            ranges = new ArrayList(names.length);
            for (int i = 0; i < names.length; i++) {
                ranges.add(i, fsm.getRangeFor(names[i]));
                if (dr == null) {
                    dr = ranges.get(i);
                } else {
                    dr = DatumRangeUtil.union(dr, ranges.get(i));
                }
            }
        } catch (IOException ex) {
            outerRangeTextField.setText(ex.toString());
            return;
        }
        if ( AggregatingDataSourceFactory.hasTimeFields(uri) ) {
            outerRangeTextField.setText("found files for " + dr);
            outerRangeTextField.setIcon(null);
        } else {
            outerRangeTextField.setText("aggregation doesn't have time fields");
            outerRangeTextField.setIcon(null);
        }
    }

    private void updateDropLists(boolean updateYear, boolean updateMonth) {

        List possible;
        List<DatumRange> result;
        ComboBoxModel model;

        updatingDropLists = true;

        DatumRange selectedRange = null;
        try {
            selectedRange = DatumRangeUtil.parseTimeRange(timeRangeComboBox.getSelectedItem().toString());
        } catch (ParseException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

        if (updateYear) {
            // years
            possible = DatumRangeUtil.generateList(DatumRangeUtil.parseTimeRangeValid("1800-2200"),
                    DatumRangeUtil.parseTimeRangeValid("1800"));
            result = DatumRangeUtil.intersection(possible, ranges, false);

            String[] yrLabels = new String[result.size() + 1];
            yrLabels[0] = "";
            int isel = result.size() == 1 ? 1 : 0;
            for (int i = 0; i < result.size(); i++) {
                yrLabels[i + 1] = result.get(i).toString();
                if (selectedRange != null && result.get(i).intersects(selectedRange)) {
                    isel = i + 1;
                }
            }
            model = new DefaultComboBoxModel(yrLabels);
            yearsComboBox.setModel(model);
            yearsComboBox.setSelectedIndex(isel);
        }

        String syr = String.valueOf(yearsComboBox.getSelectedItem()).trim();
        if (updateMonth) {
            if (syr.length() > 0) {
                possible = DatumRangeUtil.generateList(DatumRangeUtil.parseTimeRangeValid(syr),
                        DatumRangeUtil.parseTimeRangeValid(syr + " jan"));
                result = DatumRangeUtil.intersection(possible, ranges, false);
                String[] mnLabels = new String[result.size() + 1];
                mnLabels[0] = "";
                int isel = result.size() == 1 ? 1 : 0;
                for (int i = 0; i < result.size(); i++) {
                    mnLabels[i + 1] = result.get(i).toString().replace(syr, "").trim();
                    if (selectedRange != null && result.get(i).intersects(selectedRange) && selectedRange.width().le(result.get(i).width())) {
                        isel = i + 1;
                    }
                }
                model = new DefaultComboBoxModel(mnLabels);
                monthsComboBox.setModel(model);
                monthsComboBox.setSelectedIndex(isel);
            } else {
                monthsComboBox.setModel(new DefaultComboBoxModel(new String[]{""}));
            }
        }

        String smon = String.valueOf(monthsComboBox.getSelectedItem()).trim();
        if (syr.length() > 0 && smon.length() > 0) {
            possible = DatumRangeUtil.generateList(DatumRangeUtil.parseTimeRangeValid(syr + " " + smon),
                    DatumRangeUtil.parseTimeRangeValid(syr + " " + smon + " 1"));
            result = DatumRangeUtil.intersection(possible, ranges, false);
            String[] dayLabels = new String[result.size() + 1];
            dayLabels[0] = "";
            int isel = result.size() == 1 ? 1 : 0;
            for (int i = 0; i < result.size(); i++) {
                dayLabels[i + 1] = String.valueOf(TimeUtil.toTimeStruct(result.get(i).min()).day);
                if (selectedRange != null && result.get(i).intersects(selectedRange) && selectedRange.width().le(result.get(i).width())) {
                    isel = i + 1;
                }
            }
            model = new DefaultComboBoxModel(dayLabels);
            daysComboBox.setModel(model);
            daysComboBox.setSelectedIndex(isel);
        } else {
            daysComboBox.setModel(new DefaultComboBoxModel(new String[]{""}));
        }

        updatingDropLists = false;
        droplistIsDirty= false;
        jButton1.setEnabled(false);
    }

    @Override
    public boolean reject(String uri) throws Exception {
        String delegateUrl;
        
        //TODO: the following triggers web stuff on event thread!
        delegateUrl = AggregatingDataSourceFactory.getDelegateDataSourceFactoryUri(uri, new NullProgressMonitor() );

        if (delegateEditorPanel == null) {
            delegateEditorPanel = DataSourceEditorPanelUtil.getDataSourceEditorPanel( DataSetURI.toUri(delegateUrl) );
        }

        if (delegateEditorPanel == null) {
            return false;
        } else {
            return delegateEditorPanel.reject( delegateUrl );
        }
    }


    @Override
    public boolean prepare(String url, java.awt.Window parent, ProgressMonitor mon) throws IOException, URISyntaxException, Exception {
        String delegateUrl;
        delegateUrl = AggregatingDataSourceFactory.getDelegateDataSourceFactoryUri(url, new NullProgressMonitor() );

        Pattern p= Pattern.compile("(vap(\\+[a-z]+)?\\:)?([^\\?]*)(\\?.*)");
        Matcher m= p.matcher(delegateUrl);
        if ( m.matches() ) {
            delegateTextField.setText( "editing example URI "+ m.group(3) + " above");
        } else {
            delegateTextField.setText( "editing example URI "+ delegateUrl + " above");
        }
        
        if (delegateEditorPanel == null) {
            delegateEditorPanel = DataSourceEditorPanelUtil.getDataSourceEditorPanel( DataSetURI.toUri(delegateUrl) );
        }

        if (delegateEditorPanel == null) {
            delegateEditorPanel= new CompletionsDataSourceEditor();
            return delegateEditorPanel.prepare( delegateUrl, parent, mon );
            //return true;
        } else {
            return delegateEditorPanel.prepare( delegateUrl, parent, mon );
        }
    }

//    /**
//     * it's the delegate Data Source Editor is null and we have just an address 
//     * bar, then go ahead and do completions.
//     */
//    public void hintAtCompletion() {
//        if ( delegateDataSetSelector!=null ) {
//            String text= delegateDataSetSelector.getEditor().getText();
//            delegateDataSetSelector.getEditor().setCaretPosition(text.length());
//            //delegateDataSetSelector.showCompletions();
//            //TODO: bug 3046638: it would be nice if we didn't show "empty" when there are no completions.
//        }
//        
//        // set the focus to the timerange if that's all we need.
//        String delegateUrl;
//        try {
//            delegateUrl = AggregatingDataSourceFactory.getDelegateDataSourceFactoryUri( getURI(), null );
//            DataSourceFactory dsf= DataSetURI.getDataSourceFactory( DataSetURI.toUri(delegateUrl),new NullProgressMonitor() );
//            if ( !dsf.reject( delegateUrl, new ArrayList<String>() , new NullProgressMonitor()) ) {
//                if ( timeRangeTextField.getText().trim().length()==0 ) {
//                    timeRangeTextField.requestFocus();
//                }
//            }
//        } catch ( URISyntaxException ex ) {
//        } catch ( IOException ex ) {
//        } catch ( IllegalArgumentException ex ) {
//        }
//    }

    @Override
    public void setURI(String url) {
        split = URISplit.parse(url);
        params = URISplit.parseParams(split.params);
        this.uri = url;
        
        hasTimeFields= AggregatingDataSourceFactory.hasTimeFields(url);
        
        try {

            if ( hasTimeFields ) {
                String timeRange = params.get("timerange");
                if ( timeRange==null ) timeRange="";
                if ( timeRange.trim().length() > 0) {
                    timeRange= timeRange.replaceAll("\\+", " ");
                    try {
                        DatumRange dr = DatumRangeUtil.parseTimeRange(timeRange);
                    } catch (ParseException ex) {

                    }
                }
                timeRangeComboBox.setSelectedItem(timeRange);
                
                String reduce= params.get("reduce");
                reduceCB.setSelected( "T".equals(reduce) );

                String avail= params.get("avail");
                availabilityCB.setSelected( "T".equals(avail) );

            } else {
                jPanel1.setEnabled( false );
                timeRangeComboBox.setSelectedItem("");
                timeRangeComboBox.setEnabled(false);
                reduceCB.setEnabled(false);
                availabilityCB.setEnabled(false);
                yearsComboBox.setEnabled(false);
                monthsComboBox.setEnabled(false);
                daysComboBox.setEnabled(false);
                timeRangeToolButton.setEnabled(false);
            }
            
            String delegateUrl;
            delegateUrl = AggregatingDataSourceFactory.getDelegateDataSourceFactoryUri(url, null);

            if (delegateEditorPanel == null) {
                delegateEditorPanel = DataSourceEditorPanelUtil.getDataSourceEditorPanel( DataSetURI.toUri(delegateUrl) );
            }
            
            delegateEditorPanel.setURI(delegateUrl);
            setDelegateComponent(delegateEditorPanel.getPanel());

        } catch (IOException | IllegalArgumentException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            RuntimeException ex2 = new RuntimeException("Unable to create example file for aggregation", ex);
            throw ex2;
        }

        Runnable run = new Runnable() {

            @Override
            public void run() {
                updateTimeRanges();
                if ( ranges!=null ) {
                    updateDropLists(true, true);
                }
            }
        };

        new Thread(run).start();

    }

    public void setTimeRange( DatumRange timerange ) {
        timeRangeComboBox.setSelectedItem( timerange.toString() );
    }

    public DatumRange getTimeRange( ) {
        if ( timeRangeComboBox.getSelectedItem().toString().trim().equals("") ) {
            return null;
        } else {
            try {
                return DatumRangeUtil.parseTimeRange( timeRangeComboBox.getSelectedItem().toString().trim() );
            } catch (ParseException ex) {
                return null;
            }
        }
    }

    @Override
    public String getURI() {
        String delegateUrl;
        String vapScheme= split.vapScheme;
        if (delegateEditorPanel != null) {
            delegateUrl = delegateEditorPanel.getURI();
        } else {
            delegateUrl= ((SourceTypesBrowser)this.delegateComponent).getUri();
            vapScheme= null;
        }
        URISplit dsplit = URISplit.parse(delegateUrl);
        Map<String, String> allParams = new LinkedHashMap<>();
        
        if ( droplistIsDirty ) {
            copyTimes(0);
        }
        
        //allParams.putAll(params);
        allParams.putAll(URISplit.parseParams(dsplit.params));
        
        if ( hasTimeFields ) {
            Object otr= timeRangeComboBox.getSelectedItem();
            String tr = otr==null ? "" : otr.toString().trim();
            tr= tr.replaceAll(" ","+");
            String tr0= allParams.get("timerange"); // check that the delegate didn't insert a timerange
            if ( tr0!=null && tr0.length()>0 ) {
                if ( tr.trim().length()==0 ) {
                    logger.log( Level.FINE, "I didn't get timerange but delegate did");
                    tr= tr0;
                }
            }
            allParams.put("timerange", tr);
            if ( reduceCB.isSelected() ) {
                allParams.put("reduce","T"); // warning--this is going to become the default.
            } else {
                allParams.remove("reduce");
            }
            if ( availabilityCB.isSelected() ) {
                allParams.put("avail","T"); // warning--this is going to become the default.
            } else {
                allParams.remove("avail");
            }
        }
        
        split.params = URISplit.formatParams(allParams);
        if ( vapScheme==null ) {
            split.vapScheme= dsplit.vapScheme;
        }
        return URISplit.format(split);
    }

    @Override
    public void markProblems(List<String> problems) {
        List<String> p= new ArrayList(problems);
        JTextField timeRangeTextField= (JTextField)timeRangeComboBox.getEditor().getEditorComponent();
        if ( p.contains( TimeSeriesBrowse.PROB_NO_TIMERANGE_PROVIDED ) ) {
            p.remove( TimeSeriesBrowse.PROB_NO_TIMERANGE_PROVIDED );
            timeRangeTextField.setBackground( Color.YELLOW );
            jLabel1.setBackground( Color.YELLOW );
            jLabel1.setForeground( Color.RED );
            jLabel1.setToolTipText( jLabel1.getToolTipText() + "<br><b>"+TimeSeriesBrowse.PROB_NO_TIMERANGE_PROVIDED  );
            timeRangeComboBox.setToolTipText( TimeSeriesBrowse.PROB_NO_TIMERANGE_PROVIDED );
        } else if ( p.contains(TimeSeriesBrowse.PROB_PARSE_ERROR_IN_TIMERANGE ) ) {
            p.remove(TimeSeriesBrowse.PROB_PARSE_ERROR_IN_TIMERANGE );
            timeRangeTextField.setBackground( Color.YELLOW );
            jLabel1.setBackground( Color.YELLOW );
            jLabel1.setForeground( Color.RED );
            jLabel1.setToolTipText( jLabel1.getToolTipText() + "<br><br><b>"+TimeSeriesBrowse.PROB_PARSE_ERROR_IN_TIMERANGE  );
            timeRangeTextField.setToolTipText( TimeSeriesBrowse.PROB_PARSE_ERROR_IN_TIMERANGE );
        }

        //if ( p.size()==0 && problems.size()>0 ) {
        //    delegatePanel.setVisible(false);
        //}
    }
}
