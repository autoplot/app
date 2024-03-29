/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.das2.components.DasProgressPanel;
import org.das2.datum.DatumRange;
import org.das2.datum.Units;
import org.das2.util.monitor.NullProgressMonitor;
import org.das2.util.monitor.ProgressMonitor;
import org.autoplot.ScriptContext;
import org.das2.qds.QDataSet;
import org.das2.qds.examples.Schemes;
import org.das2.qds.ops.Ops;

/**
 *
 * @author mmclouth
 */
public class FFTMultiThreadExperiment {

    private static ProgressMonitor getMonitor(String label) {
        //DasProgressPanel p = DasProgressPanel.createFramed(label);
        //p.setVisible(true);
        return new NullProgressMonitorImpl(label);
        //return p;

    }


    public static void main(String[] args) throws InterruptedException {
        
        try {
            
            int size = 10;
            int numThreads = 8;
            args= new String[] { "multi" };
            

            
            if ( args.length==0 ) {
                System.err.println("args[0] should be four,eight or, multi");
                System.exit(1);
            }
            
            if (args[0].equals("multi")) {
                PrintWriter fw= new PrintWriter( new FileWriter("/tmp/multiThreadedFFT_TestData.txt"));
                //double[] speedArrayMulti = new double[size];
                for (int i = 0; i < size; i = i + 1) {
                    System.err.println("=== "+ i+ " ===");
                    double speed = fftMultiThread(numThreads);
                    //speedArrayMulti[i] = speed;
                    System.err.println("Multi threads: " + i + " " + speed );
                    QDataSet timesMulti = Ops.dataset(speed);
                        //for ( int j=0; j<10; j++ ) {
                    fw.println(numThreads + "   " + speed);       
                }
                numThreads = 4;
                for (int i = 0; i < size; i = i + 1) {
                    System.err.println("=== "+ i+ " ===");
                    double speed = fftMultiThread(numThreads);
                    //speedArrayMulti[i] = speed;
                    System.err.println("Multi threads: " + i + " " + speed );
                    QDataSet timesMulti = Ops.dataset(speed);
                    fw.println(numThreads + "   " + speed);       
                }
                numThreads = 1;
                for (int i = 0; i < size; i = i + 1) {
                    System.err.println("=== "+ i+ " ===");
                    double speed = fftMultiThread(numThreads);
                    //speedArrayMulti[i] = speed;
                    System.err.println("Multi threads: " + i + " " + speed );
                    QDataSet timesMulti = Ops.dataset(speed);
                    fw.println(numThreads + "   " + speed);       
                }
                numThreads = 12;
                for (int i = 0; i < size; i = i + 1) {
                    System.err.println("=== "+ i+ " ===");
                    double speed = fftMultiThread(numThreads);
                    //speedArrayMulti[i] = speed;
                    System.err.println("Multi threads: " + i + " " + speed );
                    QDataSet timesMulti = Ops.dataset(speed);
                    fw.println(numThreads + "   " + speed);       
                }numThreads = 16;
                for (int i = 0; i < size; i = i + 1) {
                    System.err.println("=== "+ i+ " ===");
                    double speed = fftMultiThread(numThreads);
                    //speedArrayMulti[i] = speed;
                    System.err.println("Multi threads: " + i + " " + speed );
                    QDataSet timesMulti = Ops.dataset(speed);
                    fw.println(numThreads + "   " + speed);       
                }
                fw.close();
                
            } else if (args[0].equals("two")) {
                //double[] speedArray2 = new double[size];
                for( int i = 0; i< size ; i = i + 1) {
                    double speed = doTwoThreads(); 
                    //speedArray2[i] = speed;
                    QDataSet timesTwo = Ops.dataset(speed);
                    ScriptContext.formatDataSet( timesTwo, "/tmp/twoThreads.txt" );
                }
              
            } else if (args[0].equals("four")) {
                //double[] speedArray4 = new double[size];
                for( int i = 0; i< size ; i = i + 1) {
                    double speed = doFourThreads();
                    QDataSet timesFour = Ops.dataset(speed);
                    ScriptContext.formatDataSet( timesFour, "/tmp/fourThreads.txt" );
                }
                 
            } else if (args[0].equals("eight")) {
                //double[] speedArray8 = new double[size];
                for (int i = 0; i < size; i = i + 1) {
                    double speed = doEightThreads();
                    //speedArray8[i] = speed;
                    QDataSet timesEight = Ops.dataset(speed);
                    ScriptContext.formatDataSet( timesEight, "/tmp/eightThreads.txt" );
                }
            } else {
                System.err.println("args[0] should be four,eight or, multi");
            }

            
            ScriptContext.setLayout(4, 1);

            
            ScriptContext.getDocumentModel().getPlots(2).getYaxis().setRange( DatumRange.newDatumRange(0,10,Units.dimensionless) );
                    
            //ScriptContext.plot(1, "Times 2", timesTwo);
            //ScriptContext.plot(0, "Times 4", timesFour);
            //ScriptContext.plot(1, "Times 8", timesEight);
            
            try {
                ScriptContext.writeToPng("/tmp/Experiment1.png");
            } catch (IOException ex) {
                Logger.getLogger(FFTMultiThreadExperiment.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(FFTMultiThreadExperiment.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static double doTwoThreads() throws InterruptedException {
        //ScriptContext.createGui();
        final QDataSet ds = Ops.ripplesWaveformTimeSeries(DATASET_SIZE);

        final ProgressMonitor mon0 = getMonitor("original task");
        long t0 = System.currentTimeMillis();
        QDataSet out = Ops.fftPower(ds, 512, mon0);

        while (!mon0.isFinished()) {
            Thread.sleep(200);
        }
        long time = System.currentTimeMillis() - t0;
        //System.err.println("Time for original task: " + time);

        ScriptContext.setLayout(3, 1);

        ScriptContext.plot(0, out);

        final ProgressMonitor mon1 = getMonitor("task 1");
        final ProgressMonitor mon2 = getMonitor("task 2");

        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                try {
                    QDataSet out1 = Ops.fftPower(ds.trim(0, DATASET_SIZE / 2), 512, mon1);
                    //ScriptContext.plot( 1, out1 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        Runnable run2 = new Runnable() {
            @Override
            public void run() {
                try {
                    QDataSet out2 = Ops.fftPower(ds.trim(DATASET_SIZE / 2, DATASET_SIZE), 512, mon2);
                    //ScriptContext.plot( 2, out2 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        t0 = System.currentTimeMillis();
        new Thread(run1).start();

        new Thread(run2).start();

        while (!mon1.isFinished() && !mon2.isFinished()) {
            Thread.sleep(200);
        }
        long time1 = System.currentTimeMillis() - t0;
        double speed = ((double) time) / time1;
        
        return speed;
    }
    private static final int DATASET_SIZE = 10000;

    private static double fftMultiThread(int threads) throws InterruptedException {
        final ArrayList<Runnable> run = new ArrayList<Runnable>();
        final ArrayList<ProgressMonitor> mon = new ArrayList<ProgressMonitor>();

        //run.clear();
        //mon.clear();

        final QDataSet ds = Ops.ripplesWaveformTimeSeries(DATASET_SIZE);

        final QDataSet[] out = new QDataSet[threads + 1];
        ProgressMonitor temp = getMonitor("original task");
        mon.add(temp);
        long t0 = System.currentTimeMillis();
        out[0] = Ops.fftPower(ds, 512, mon.get(0));

        while (!mon.get(0).isFinished()) {
            Thread.sleep(50);
        }
        
        // time is the time of the original task that only uses one thread in millis
        long time = System.currentTimeMillis() - t0;

        //ScriptContext.setLayout(threads + 1, 1);
        //ScriptContext.plot(0, out[0]);

        long time0= System.currentTimeMillis();
        for (int i = 0; i < threads; i = i + 1) {
            temp = getMonitor("task" + (i + 1));
            mon.add(temp);
            final int j = i + 1;
            final int thr = threads;
            Runnable run0 = new Runnable() {
                @Override
                public void run() {
                    try {
                        out[j] = Ops.fftPower(ds.trim(((j - 1) * DATASET_SIZE) / thr, (j * DATASET_SIZE) / thr), 512, mon.get(j));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
            run.add(run0);
            
            //if the first runnable was just created, start the timer before running it
            if (i == 0) {
                t0 = System.currentTimeMillis();
            }
            new Thread(run.get(i)).start();
        }
        //ScriptContext.plot(j, out[j]);
        System.err.println("time: "+(System.currentTimeMillis()-time0));

        while ( !isFinished(mon.toArray(new ProgressMonitor[mon.size()]) ) ) {
            Thread.sleep(50);
        }
        
        // time1 stores the time it takes to run the task using multiple threads
        long time1 = System.currentTimeMillis() - t0;
        
        // speed stores the relationship between time (one thread) and time1 (multiplethreads)
        // for example, if the multi-threaded task took half as much time as the single-threaded task, speed = 2
        double speed = ((double) time) / time1;

        
        //try {
        //    ScriptContext.writeToPng("/tmp/Experiment1_graphs.png");
        //} catch (IOException ex) {
        //    Logger.getLogger(Experiment1.class.getName()).log(Level.SEVERE, null, ex);
        //}
        
        return speed;
        
    }

    private static double doFourThreads() throws InterruptedException {
        //ScriptContext.createGui();
        final QDataSet ds = Ops.ripplesWaveformTimeSeries(DATASET_SIZE);

        final ProgressMonitor mon0 = getMonitor("original task");
        long t0 = System.currentTimeMillis();
        QDataSet out = Ops.fftPower(ds, 512, mon0);

        while (!mon0.isFinished()) {
            Thread.sleep(200);
        }
        long time = System.currentTimeMillis() - t0;
        //System.err.println("Time for original task: " + time);

        ScriptContext.setLayout(5, 1);

        //ScriptContext.plot( 0, out );
        final ProgressMonitor mon1 = getMonitor("task 1");
        final ProgressMonitor mon2 = getMonitor("task 2");
        final ProgressMonitor mon3 = getMonitor("task 3");
        final ProgressMonitor mon4 = getMonitor("task 4");

        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                try {
                    QDataSet out1 = Ops.fftPower(ds.trim(0, DATASET_SIZE / 4), 512, mon1);
                    //ScriptContext.plot( 1, out1 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        Runnable run2 = new Runnable() {
            @Override
            public void run() {
                try {
                    QDataSet out2 = Ops.fftPower(ds.trim(DATASET_SIZE / 4, (DATASET_SIZE * 2) / 4), 512, mon2);
                    //ScriptContext.plot( 2, out2 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        Runnable run3 = new Runnable() {
            @Override
            public void run() {
                try {
                    QDataSet out3 = Ops.fftPower(ds.trim((DATASET_SIZE * 2) / 4, (DATASET_SIZE * 3) / 4), 512, mon3);
                    //ScriptContext.plot( 3, out3 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        Runnable run4 = new Runnable() {
            @Override
            public void run() {
                try {
                    QDataSet out4 = Ops.fftPower(ds.trim((DATASET_SIZE * 3) / 4, DATASET_SIZE), 512, mon4);
                    //ScriptContext.plot( 4, out4 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        t0 = System.currentTimeMillis();
        new Thread(run1).start();
        new Thread(run2).start();
        new Thread(run3).start();
        new Thread(run4).start();

        while (!mon1.isFinished() && !mon2.isFinished() && !mon3.isFinished() && !mon4.isFinished()) {
            Thread.sleep(200);
        }
        long time1 = System.currentTimeMillis() - t0;
        double speed = ((double) time) / time1;
        
        return speed;
    }

    private static double doEightThreads() throws InterruptedException {
        //ScriptContext.createGui();
        final QDataSet ds = Ops.ripplesWaveformTimeSeries(DATASET_SIZE);

        final ProgressMonitor mon0 = getMonitor("original task");
        long t0 = System.currentTimeMillis();
        QDataSet out = Ops.fftPower(ds, 512, mon0);

        while (!mon0.isFinished()) {
            Thread.sleep(200);
        }
        long time = System.currentTimeMillis() - t0;
        //System.err.println("Time for original task: " + time);

        ScriptContext.setLayout(5, 1);

        //ScriptContext.plot( 0, out );
        final ProgressMonitor mon1 = getMonitor("task 1");
        final ProgressMonitor mon2 = getMonitor("task 2");
        final ProgressMonitor mon3 = getMonitor("task 3");
        final ProgressMonitor mon4 = getMonitor("task 4");
        final ProgressMonitor mon5 = getMonitor("task 5");
        final ProgressMonitor mon6 = getMonitor("task 6");
        final ProgressMonitor mon7 = getMonitor("task 7");
        final ProgressMonitor mon8 = getMonitor("task 8");

        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                try {
                    QDataSet out1 = Ops.fftPower(ds.trim(0, (DATASET_SIZE * 1) / 8), 512, mon1);
                    //ScriptContext.plot( 1, out1 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        Runnable run2 = new Runnable() {
            @Override
            public void run() {
                try {
                    QDataSet out2 = Ops.fftPower(ds.trim((DATASET_SIZE * 1) / 8, (DATASET_SIZE * 2) / 8), 512, mon2);
                    //ScriptContext.plot( 2, out2 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        Runnable run3 = new Runnable() {
            @Override
            public void run() {
                try {
                    QDataSet out3 = Ops.fftPower(ds.trim((DATASET_SIZE * 2) / 8, (DATASET_SIZE * 3) / 8), 512, mon3);
                    //ScriptContext.plot( 3, out3 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        Runnable run4 = new Runnable() {
            @Override
            public void run() {
                try {
                    QDataSet out4 = Ops.fftPower(ds.trim((DATASET_SIZE * 3) / 8, (DATASET_SIZE * 4) / 8), 512, mon4);
                    //ScriptContext.plot( 4, out4 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        Runnable run5 = new Runnable() {
            @Override
            public void run() {
                try {
                    QDataSet out5 = Ops.fftPower(ds.trim((DATASET_SIZE * 4) / 8, (DATASET_SIZE * 5) / 8), 512, mon5);
                    //ScriptContext.plot( 5, out5 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        Runnable run6 = new Runnable() {
            @Override
            public void run() {
                try {
                    QDataSet out6 = Ops.fftPower(ds.trim((DATASET_SIZE * 5) / 8, (DATASET_SIZE * 6) / 8), 512, mon6);
                    //ScriptContext.plot( 6, out6 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        Runnable run7 = new Runnable() {
            @Override
            public void run() {
                try {
                    QDataSet out7 = Ops.fftPower(ds.trim((DATASET_SIZE * 6) / 8, (DATASET_SIZE * 7) / 8), 512, mon7);
                    //ScriptContext.plot( 7, out7 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        Runnable run8 = new Runnable() {
            @Override
            public void run() {
                try {
                    QDataSet out8 = Ops.fftPower(ds.trim((DATASET_SIZE * 7) / 8, DATASET_SIZE), 512, mon8);
                    //ScriptContext.plot( 8, out8 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        t0 = System.currentTimeMillis();
        new Thread(run1).start();
        new Thread(run2).start();
        new Thread(run3).start();
        new Thread(run4).start();
        new Thread(run5).start();
        new Thread(run6).start();
        new Thread(run7).start();
        new Thread(run8).start();

        while ( !isFinished( new ProgressMonitor[] { mon1, mon2, mon3, mon4, mon5, mon6, mon7, mon8 } ) ) {
            Thread.sleep(200);
        }
        
        long time1 = System.currentTimeMillis() - t0;
        double speed = ((double) time) / time1;
        
        return speed;
    }
    
    private static boolean isFinished( ProgressMonitor[] mons ) {
        boolean finished= true;
        
        for ( ProgressMonitor m: mons ) {
            if ( m.isFinished()==false ) finished= false;
        }
        return finished;
    }

    private static class NullProgressMonitorImpl extends NullProgressMonitor {

        long t0= System.currentTimeMillis();
        String label;
        
        public NullProgressMonitorImpl( String label ) {
            this.label= label;
        }

        @Override
        public void setTaskProgress(long position) throws IllegalArgumentException {
            long t= System.currentTimeMillis();
            if ( t-t0 > 500 ) {
                t0= t;
                System.err.println( label + ": " + position + " of " + getTaskSize() );
            }
        }
        
        
    }
}
