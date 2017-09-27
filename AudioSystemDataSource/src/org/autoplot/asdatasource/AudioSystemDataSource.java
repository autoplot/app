
package org.autoplot.asdatasource;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import org.das2.datum.Units;
import org.das2.util.monitor.ProgressMonitor;
import org.das2.qds.buffer.BufferDataSet;
import org.das2.util.monitor.NullProgressMonitor;
import org.das2.qds.MutablePropertyDataSet;
import org.das2.qds.QDataSet;
import org.das2.qds.TagGenDataSet;
import org.autoplot.datasource.AbstractDataSource;
import org.autoplot.datasource.capability.Streaming;
import org.autoplot.datasource.capability.Updating;
import org.das2.qds.ops.Ops;

/**
 * provide a dataset by grabbing data from the desktop's audio system.
 * @author jbf
 */
public class AudioSystemDataSource extends AbstractDataSource implements Updating {

    public static final int SAMPLE_RATE = 8000; // SAMPLE_RATE * SAMPLE_LENGTH_SEC should be multiple of BUFSIZE

    public AudioSystemDataSource( URI uri ) {
        super(uri);
        String sspec= (String) getParams().get("spec");
        if ( sspec!=null ) spec= Integer.parseInt(sspec);
        addCapability( Updating.class, this );
        if ( spec==-1 ) {
            addCapability( Streaming.class, new AudioSystemStreamingSource() );
        }
    }
    
    ByteBuffer dataBuffer;
    ReadableByteChannel audioChannel;
    int nsamples;
    int spec=-1;

    @Override
    public QDataSet getDataSet(ProgressMonitor mon) throws Exception {

        double lenSeconds = Double.parseDouble( getParam( "len", "1.0") );

        nsamples = (int) (lenSeconds * SAMPLE_RATE);
        int len = nsamples * 4;
        int nchannels= 1;
        int bitsPerSample= 16;
        int frameSize= bitsPerSample / ( nchannels * 8 );

        dataBuffer = ByteBuffer.allocateDirect(len);

        TargetDataLine targetDataLine;
        AudioInputStream audioInputStream;

        AudioFormat audioFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                SAMPLE_RATE, bitsPerSample, nchannels, frameSize, SAMPLE_RATE, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);

        targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
        targetDataLine.open(audioFormat);

        audioInputStream = new AudioInputStream(targetDataLine);

        targetDataLine.start();

        audioChannel = Channels.newChannel(audioInputStream);

        mon.setTaskSize(len);
        mon.started();
        mon.setProgressMessage("recording from system audio");
        dataBuffer.limit( 2048 );
        fillBuffer(mon);

        targetDataLine.close();

        dataBuffer.order( ByteOrder.LITTLE_ENDIAN );

        TagGenDataSet t= new TagGenDataSet( nsamples, 1./SAMPLE_RATE, 0.0, Units.seconds );
        t.putProperty( QDataSet.LABEL, "Seconds Offset");
        //startUpdateTimer();
        
        MutablePropertyDataSet ds= BufferDataSet.makeDataSet( 1, 2, 0, nsamples, 1, 1, 1, dataBuffer, BufferDataSet.SHORT );
        ds.putProperty( QDataSet.DEPEND_0, t );
        
        if ( spec>-1 ) {
            ds= (MutablePropertyDataSet)Ops.fftPower( ds, spec, mon.getSubtaskMonitor("fftPower") );
        }

        mon.finished();

        return ds;
    }
    
    class AudioSystemStreamingSource implements Streaming{

        @Override
        public Iterator<QDataSet> streamDataSet( final ProgressMonitor mon) throws Exception {

            nsamples= 2048; // this is per record now.
            int len = nsamples * 2;
            int nchannels= 1;
            int bitsPerSample= 16;
            int frameSizeBytes= bitsPerSample / ( nchannels * 8 );

            dataBuffer = ByteBuffer.allocateDirect(len);

            final TargetDataLine targetDataLine;
            AudioInputStream audioInputStream;

            AudioFormat audioFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    SAMPLE_RATE, bitsPerSample, nchannels, frameSizeBytes, SAMPLE_RATE, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);

            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
            targetDataLine.open(audioFormat);

            audioInputStream = new AudioInputStream(targetDataLine);

            targetDataLine.start();

            audioChannel = Channels.newChannel(audioInputStream);

            mon.setTaskSize(-1);
            mon.started();
            mon.setProgressMessage("recording from system audio");
            //startUpdateTimer();
            
            return new Iterator<QDataSet>() {
                int length= 0;
                
                QDataSet result;
                
                @Override
                public boolean hasNext() {
                    try {
                        if ( mon.isCancelled() ) return false;
                        
                        dataBuffer.limit( nsamples*2 );
                        fillBuffer( new NullProgressMonitor() );
                        
                        dataBuffer.order( ByteOrder.LITTLE_ENDIAN );
                        
                        TagGenDataSet t= new TagGenDataSet( nsamples, 1./SAMPLE_RATE, 0.0, Units.seconds );
                        t.putProperty( QDataSet.LABEL, "Seconds Offset");
                                                
                        MutablePropertyDataSet ds= BufferDataSet.makeDataSet( 1, 2, 0, nsamples, 1, 1, 1, dataBuffer, BufferDataSet.SHORT );
                        ds.putProperty( QDataSet.DEPEND_0, t );
        
                        result= BufferDataSet.copy(ds);

                        dataBuffer.flip();                        
                        
                        length+=ds.length();
                        return true;
                        
                    } catch (IllegalArgumentException | IOException ex) {
                        return false;
                    }

                }

                @Override
                public QDataSet next() {
                    if ( result==null ) throw new NoSuchElementException();
                    QDataSet r= result;
                    result= null;
                    return r;
                }

                @Override
                public void remove() {

                }

            };

        }

    }


    PropertyChangeSupport pcs= new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    private void fillBuffer(ProgressMonitor mon) throws IllegalArgumentException, IOException {
        while ( !mon.isCancelled() && dataBuffer.position() < dataBuffer.capacity() ) {
            audioChannel.read(dataBuffer);
            dataBuffer.limit(Math.min(dataBuffer.position() + 2048, dataBuffer.capacity()));
            mon.setTaskProgress(dataBuffer.position());
        }
    }

    //TODO: add updating capability to replace function of code below.
//
//    private void shiftBuffer() throws IOException {
//        System.err.println("shiftBuffer"+dataBuffer);
//        dataBuffer.position( dataBuffer.capacity()/2 );
//        dataBuffer.compact();
//        fillBuffer( new NullProgressMonitor() );
//    }
//
//    private void startUpdateTimer() {
//        Runnable run= new Runnable() {
//            public void run() {
//                while ( true ) {
//                    try {
//                        shiftBuffer();
//                        QDataSet ds= BufferDataSet.makeDataSet( 1, 2, 0, nsamples, 1, 1, dataBuffer, BufferDataSet.SHORT );
//                        if ( spec>-1 ) {
//                           ds= Ops.fftWindow(ds, spec);
//                        }
//                        pcs.firePropertyChange(Updating.PROP_DATASET, null, ds );
//                    } catch ( IOException ex ) {
//                        Logger.getLogger(AudioSystemDataSource.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        };
//        new Thread( run, "updater for " + getURI() ).start();
//    }

}
