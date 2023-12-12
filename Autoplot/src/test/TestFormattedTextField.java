
package test;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

/**
 *
 * @author jbf
 */
public class TestFormattedTextField extends javax.swing.JPanel {

    private static class ColorKeyListener implements KeyListener {
        JFormattedTextField formattedTextField;
            
        public ColorKeyListener( JFormattedTextField tf ) {
            this.formattedTextField= tf;
        }
        
        private void action( ) {
            InputVerifier verifier= this.formattedTextField.getInputVerifier();
            if ( verifier.verify(formattedTextField) ) {
                formattedTextField.setBackground( Color.WHITE );
            } else {
                formattedTextField.setBackground( Color.YELLOW );
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            action();
        }
        @Override
        public void keyPressed(KeyEvent e) {
            action();
        }
        @Override
        public void keyReleased(KeyEvent e) {
            action();
        }  
        
    }
    
    private static class RegexInputVerifier extends InputVerifier {
        final JFormattedTextField jf;
        final Pattern p;
        public RegexInputVerifier( JFormattedTextField jf, Pattern p ) {
            this.jf= jf;
            this.p= p;
        }
        @Override
        public boolean verify( JComponent input) {
            return p.matcher( jf.getText() ).matches();
        }
    }

    /**
     * Creates new form TestFormattedTextField
     */
    public TestFormattedTextField() {
        initComponents();
        
        final Pattern p1= Pattern.compile( "\\d\\d\\d\\d-[01]\\d-[0123]\\d" );
                    
        jftf1.setInputVerifier( new RegexInputVerifier( jftf1, p1 ) );
        jftf1.addKeyListener( new ColorKeyListener(jftf1) );

        final Pattern p2= Pattern.compile( "a|b" );
        
        jftf2.setInputVerifier( new RegexInputVerifier( jftf2, p2 ) );        
        jftf2.addKeyListener( new ColorKeyListener(jftf2) );
        
    }

    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of
     * this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jftf1 = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jftf2 = new javax.swing.JFormattedTextField();

        jftf1.setText("2022-03-05");

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getSize()+2f));
        jLabel1.setText("Enter Time Range:");

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+2f));
        jLabel2.setText("RBSP Spacecraft:");

        jftf2.setText("a");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftf1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jftf2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(235, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jftf1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jftf2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(181, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    public static void main( String[] args ) {
        JOptionPane.showConfirmDialog( null, new TestFormattedTextField() );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JFormattedTextField jftf1;
    private javax.swing.JFormattedTextField jftf2;
    // End of variables declaration//GEN-END:variables
}