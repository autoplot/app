/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.io.File;
import java.io.IOException;
import org.das2.util.monitor.NullProgressMonitor;
import org.das2.qds.QDataSet;
import org.autoplot.datasource.DataSetURI;
import org.das2.qds.ops.Ops;

import static org.autoplot.ScriptContext.*;

/**
 * code to work out <a href='https://sourceforge.net/p/autoplot/bugs/360/'>sourceforge ticket</a>
 * @author jbf
 */
public class ExerciseTest001 {

    static void test001() throws Exception {
            getDocumentModel().getOptions().setAutolayout(false);
            getDocumentModel().getCanvases(0).getMarginColumn().setRight("100%-10em");
            //QDataSet ds= Util.getDataSet( "http://www.autoplot.org/data/fireworks.wav" );

            setCanvasSize( 800, 600 );

            reset();
            plot( "file:/home/jbf/ct/hudson/data.backup/xls/2008-lion and tiger summary.xls?sheet=Samantha+tiger+lp+lofreq&firstRow=53&column=Complex_Modulus&depend0=Frequency" );
            // this causes bad things as of 2009-08-12.
            plot( "file:/home/jbf/ct/hudson/data.backup/qds/hist.qds" );
            writeToPng( "test001_003.png" );

    }
    public static void main(String[] args) throws InterruptedException, IOException, Exception {
        String pwd= new File("").getAbsolutePath();
        int ok=0;
        int fail=0;
        for ( int i=0; i<100; i++ ) {
            test001();
            QDataSet ds= DataSetURI.getDataSource( pwd + "/test001_003.png" ).getDataSet(new NullProgressMonitor());
            double d= Ops.total(ds);
            QDataSet ds2= getDocumentModel().getDataSourceFilters(0).getController().getFillDataSet();
            String s= String.valueOf(ds2);
            if ( d!=3.58733967E8 ) {
                System.err.println("here!!");
                fail++;
                File f=  new File( pwd + "/test001_003.png" );
                String sfn= "test001_003."+i+".png";
                if ( !f.renameTo( new File( pwd + "/" + sfn ) ) ) {
                    System.err.println("rename failed: "+f + " to " + sfn );
                }
                Thread.sleep(1000);
                writeToPng( "test001_003."+i+"test.png" );
            } else {
                ok++;
            }
            System.err.println( "okay:"+ok + "  fail:" + fail + "  total:"+ d  + "   ds2:"+ds2 );
        }
    }
}
