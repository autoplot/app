
package org.autoplot.jythonsupport.ui;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.ChangeDelta;
import com.github.difflib.patch.DeleteDelta;
import com.github.difflib.patch.InsertDelta;
import com.github.difflib.patch.Patch;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import org.das2.DasApplication;
import org.das2.util.LoggerManager;
import org.das2.util.filesystem.FileSystem;
import org.python.core.PyException;
import org.autoplot.datasource.FileSystemUtil;

/**
 * @see ScriptPanelSupport in Autoplot project.  TODO: clean this up.
 * @author jbf
 */
public class ScriptPanelSupport {

    private static final Logger logger= LoggerManager.getLogger("jython.editor");
    
    private final EditorTextPane editor;
    private final EditorAnnotationsSupport annotationsSupport;
    private JLabel fileNameLabel;
    private boolean readOnly= false;

    private DocumentListener dirtyListener;

    public ScriptPanelSupport( final EditorTextPane editor ) {
        this.editor= editor;

        dirtyListener= new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setDirty(true);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                setDirty(true);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };

        this.editor.getDocument().addDocumentListener(dirtyListener);
        this.editor.addPropertyChangeListener( "document", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ( evt.getOldValue()!=null ) {
                    ((Document)evt.getOldValue()).removeDocumentListener(dirtyListener);
                }
                ((Document)evt.getNewValue()).addDocumentListener(dirtyListener);
            }
            
        });

        this.annotationsSupport = editor.getEditorAnnotationsSupport();
    }

    protected boolean dirty = false;
    public static final String PROP_DIRTY = "dirty";

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        boolean oldDirty = this.dirty;
        this.dirty = dirty;
        updateFileNameLabel();
        propertyChangeSupport.firePropertyChange(PROP_DIRTY, oldDirty, dirty);
    }
    protected File file = null;
    public static final String PROP_FILE = "file";

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        File oldFile = this.file;
        this.file = file;
        updateFileNameLabel();
        propertyChangeSupport.firePropertyChange(PROP_FILE, oldFile, file);
    }

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void save( File file ) throws FileNotFoundException, IOException {
        if ( file==null || FileSystemUtil.isChildOf( FileSystem.settings().getLocalCacheDir(), file ) ) {
            saveAs();
            return;
        }
        try (OutputStream out = new FileOutputStream(file)) {
            String text = editor.getText();
            out.write(text.getBytes());
            setDirty(false);
        }
    }

    public void loadFile(File file) throws IOException, FileNotFoundException {
        logger.log(Level.FINE, "loadFile({0})", file);
        loadFileInternal( file );
        setFile(file);
        setDirty(false);
    }

    protected void loadFileInternal( File file ) throws IOException {
        InputStream in= new FileInputStream(file);
        BufferedReader r = null;
        final StringBuilder buf= new StringBuilder();
        try {
            r = new BufferedReader(new InputStreamReader(in));
            String s = r.readLine();
            while (s != null) {
                buf.append(s).append("\n");
                s = r.readLine();
            }
        } finally {
            if (r != null) r.close();
        }
        Runnable run= new Runnable() {
            @Override
            public void run() {
                try {
                    Document d = editor.getDocument();
                    d.remove(0, d.getLength());
                    d.insertString(0, buf.toString(), null);
                    setDirty(false);

                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        if ( SwingUtilities.isEventDispatchThread() ) {
            run.run();
        } else {
            SwingUtilities.invokeLater(run);
        }
    }

    /**
     * mark the error in the editor by looking at the python exception to get the line number.
     * @param ex the python exception
     * @param offset line offset from beginning of file where execution began.
     * @throws javax.swing.text.BadLocationException
     */
    public void annotateError(PyException ex, int offset) throws BadLocationException {
        annotationsSupport.annotateError(ex,offset);
    }

    /**
     * show insertions and deletions.  Bad locations will be reflected on stderr, 
     * and are not verified here.
     * @param patch patch generated by DiffUtils
     * @see DiffUtils
     */
    public void annotatePatch( Patch<String> patch ) {
        annotationsSupport.clearAnnotations();
        
        int scrollToOffset= -1;
        
        for ( AbstractDelta<String> d : patch.getDeltas() ) {
            
            List<Integer> ll = d.getSource().getChangePosition();
            if ( ll==null ) {
                ll= new ArrayList<>(d.getSource().size());
                for ( int i=0; i<d.getSource().size(); i++ ) {
                    ll.add( d.getSource().getPosition()+1+i );
                }
            }
            List<Integer> ss = d.getTarget().getChangePosition();
            if ( ss==null ) {
                ss= new ArrayList<>(d.getTarget().size());
                for ( int i=0; i<d.getTarget().size(); i++ ) {
                    ss.add( d.getTarget().getPosition()+1+i );
                }
            }
            int[] lp0,lp1;
            
            String sourceText = String.join( "<br>", d.getSource().getLines() );
            
            if ( d instanceof ChangeDelta ) {
                sourceText= "<html><i>Text has been changed:</i><br>"+sourceText;
                for ( int i : ss ) {
                    lp0 = annotationsSupport.getLinePosition(i);    
                    lp1 = annotationsSupport.getLinePosition(i); 
                    annotationsSupport.annotateChars(lp0[0],lp1[1],EditorAnnotationsSupport.ANNO_CHANGE,sourceText,null);
                    if ( scrollToOffset==-1 ) scrollToOffset= lp0[0];
                }
            } else if ( d instanceof DeleteDelta ) {
                sourceText= "<html><i>Text has been deleted:</i><br>"+sourceText;
                for ( int i : ll ) {
                    lp0 = annotationsSupport.getLinePosition(i);    
                    lp1 = annotationsSupport.getLinePosition(i); 
                    annotationsSupport.annotateChars(lp0[0],lp1[1],EditorAnnotationsSupport.ANNO_DELETE,sourceText,null);
                    if ( scrollToOffset==-1 ) scrollToOffset= lp0[0];
                }
            } else if ( d instanceof InsertDelta ) {
                for ( int i : ss ) {
                    lp0 = annotationsSupport.getLinePosition(i);    
                    lp1 = annotationsSupport.getLinePosition(i);    
                    annotationsSupport.annotateChars(lp0[0],lp1[1],EditorAnnotationsSupport.ANNO_INSERT,"<html><i>Text has been inserted.</i>",null);
                    if ( scrollToOffset==-1 ) scrollToOffset= lp0[0];
                }
            }        
        }
        
        if ( scrollToOffset>-1 ) {
            try {
                annotationsSupport.scrollToOffset( scrollToOffset );
            } catch ( BadLocationException ex ) {
                logger.log( Level.WARNING, ex.getMessage(), ex );
            }
        }
        
    }
    
    /**
     * show the save as dialog and return the result.  This was recently public, but
     * no one appears to be using it and it seems like private is more appropriate.
     * @return the result of showSaveDialog, e.g. JFileChooser.APPROVE_OPTION, etc.
     * @throws IOException 
     */
    private int getSaveFile() throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter( new FileNameExtensionFilter( "python and jython scripts", "jy", "jyds", "py" ) );
        if (file != null && ! FileSystemUtil.isChildOf( FileSystem.settings().getLocalCacheDir(), file ) ) {
            chooser.setSelectedFile(file);
        }
        int r = chooser.showSaveDialog(editor);
        if (r == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            if (!(file.toString().endsWith(".jy") || file.toString().endsWith(".py") || file.toString().endsWith(".jyds"))) {
                file = new File(file.toString() + ".jy");
                //TODO NOW: .jyds
            }
        }
        return r;
    }

    private void saveAs() {
        OutputStream out = null;
        try {
            if ( getSaveFile() == JFileChooser.APPROVE_OPTION) {
                out = new FileOutputStream(file);
                String text = editor.getText();
                out.write(text.getBytes());
                setDirty(false);
                setFile(file);
            }

        } catch (IOException iOException) {
            DasApplication.getDefaultApplication().getExceptionHandler().handle(iOException);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    /**
     * set the save status to read only.
     */
    public void setReadOnly() {
        this.editor.setEditable(false);
        this.readOnly= true;
        updateFileNameLabel();
    }

    /**
     * used by script panel.
     * @param caretPositionLabel 
     */
    public void addCaretLabel(final JLabel caretPositionLabel) {

       editor.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                int pos = editor.getCaretPosition();
                Element root = editor.getDocument().getDefaultRootElement();
                int irow = root.getElementIndex(pos);
                int icol = pos - root.getElement(irow).getStartOffset();
                String text = "" + (1 + irow) + "," + (1 + icol);
                int isel = editor.getSelectionEnd() - editor.getSelectionStart();
                int iselRow0 = root.getElementIndex(editor.getSelectionStart());
                int iselRow1 = root.getElementIndex(editor.getSelectionEnd());
                if (isel > 0) {
                    if (iselRow1 > iselRow0) {
                        text = "[" + isel + "ch," + (1 + iselRow1 - iselRow0) + "lines]";
                    } else {
                        text = "[" + isel + "ch]";
                    }
                }

                caretPositionLabel.setText(text);
            }
        });

    }

    /**
     * used by the script panel.
     * @param fileNameLabel 
     */
    public void addFileLabel( final JLabel fileNameLabel ) {
        this.fileNameLabel= fileNameLabel;
        updateFileNameLabel();
    }

    private void updateFileNameLabel() {
        if ( this.fileNameLabel!=null ) {
            fileNameLabel.setText( String.valueOf( this.getFile() ) + " " + ( this.isDirty() ? "*" : "" ) + ( this.readOnly ? "(Read Only)" : "" ) );
        }
    }
}

