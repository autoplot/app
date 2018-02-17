/*
 * CdfUtil.java
 *
 * Created on July 24, 2007, 12:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.autoplot.cdf;

import gov.nasa.gsfc.spdf.cdfj.AttributeEntry;
import gov.nasa.gsfc.spdf.cdfj.CDFException;
import gov.nasa.gsfc.spdf.cdfj.CDFReader;
import java.util.logging.Level;
import org.das2.datum.DatumRange;
import org.das2.datum.EnumerationUnits;
import org.das2.datum.Units;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;
import org.das2.datum.InconvertibleUnitsException;
import org.das2.datum.UnitsConverter;
import org.das2.datum.UnitsUtil;
import org.das2.util.LoggerManager;
import org.das2.util.monitor.ProgressMonitor;
import org.das2.qds.buffer.BufferDataSet;
import org.das2.qds.ArrayDataSet;
import org.das2.qds.DDataSet;
import org.das2.qds.DataSetUtil;
import org.das2.qds.QDataSet;
import org.das2.qds.MutablePropertyDataSet;
import org.das2.qds.Slice0DataSet;
import org.autoplot.datasource.DataSourceUtil;
import org.das2.qds.ops.Ops;

/**
 * static methods supporting CdfFileDataSource
 *
 * @author jbf
 */
public class CdfUtil {

    private final static Logger logger= LoggerManager.getLogger("apdss.cdf");

    /**
     * return the Java type used to store the CDF data type.
     * @param type 45, 44, or 51
     * @return String like double, float or string
     */
    private static String getTargetType(int type) {
        switch (type) {
            case (int)CDFConstants.CDF_DOUBLE:
            case (int)CDFConstants.CDF_REAL8:
            case (int)CDFConstants.CDF_EPOCH:
                return "double";
            case (int)CDFConstants.CDF_EPOCH16:
                return "double";
            case (int)CDFConstants.CDF_FLOAT:
            case (int)CDFConstants.CDF_REAL4:
                return "float";
            case (int)CDFConstants.CDF_UINT4:
                return "double";
            case (int)CDFConstants.CDF_INT8:
            case (int)CDFConstants.CDF_TT2000:
                return "long";
            case (int)CDFConstants.CDF_INT4:
            case (int)CDFConstants.CDF_UINT2:
                return "int";
            case (int)CDFConstants.CDF_INT2:
            case (int)CDFConstants.CDF_UINT1:
                return "short";
            case (int)CDFConstants.CDF_INT1:
            case (int)CDFConstants.CDF_BYTE:
                return "byte";
            case (int)CDFConstants.CDF_CHAR:
            case (int)CDFConstants.CDF_UCHAR:
                return "string";
            default:
                throw new IllegalArgumentException("unsupported type: "+type);
        }
    }
    
    private static Object byteBufferType( int type ) {
        switch (type) {
            case (int)CDFConstants.CDF_DOUBLE:
            case (int)CDFConstants.CDF_REAL8:
            case (int)CDFConstants.CDF_EPOCH:
                return BufferDataSet.DOUBLE;
            case (int)CDFConstants.CDF_FLOAT:
            case (int)CDFConstants.CDF_REAL4:
                return BufferDataSet.FLOAT;
            case (int)CDFConstants.CDF_UINT4:
                return BufferDataSet.DOUBLE;
            case (int)CDFConstants.CDF_INT8:
            case (int)CDFConstants.CDF_TT2000:
                return BufferDataSet.LONG;
            case (int)CDFConstants.CDF_INT4:
            case (int)CDFConstants.CDF_UINT2:
                return BufferDataSet.INT;
            case (int)CDFConstants.CDF_INT2:
            case (int)CDFConstants.CDF_UINT1:
                return BufferDataSet.SHORT;
            case (int)CDFConstants.CDF_INT1:
            case (int)CDFConstants.CDF_BYTE:
                return BufferDataSet.BYTE;
            case (int)CDFConstants.CDF_CHAR:        
                return BufferDataSet.BYTE; // determined experimentally: vap+cdfj:file:///home/jbf/ct/hudson/data.backup/cdf/ac_k0_mfi_20080602_v01.cdf?BGSEc
            case (int)CDFConstants.CDF_UCHAR:
                return BufferDataSet.BYTE; // TODO: I think...
            case (int)CDFConstants.CDF_EPOCH16:
                return BufferDataSet.DOUBLE;
            default:
                throw new IllegalArgumentException("unsupported type: "+type);
        }
    }
    
    /**
     * column major files require a transpose of each record.  This makes a copy of the input, because I'm nervous
     * that this might be backed by a writable cdf file.
     * @param recLenBytes length of each record in bytes.  (qube=2,3 bbType=float, then this is 2*4=8.)
     * @param qube dimensions, a 0,1,..,4 element array.
     * @param byteBuffer
     * @param bbType
     * @return the byte buffer.
     */
    private static ByteBuffer transpose( int recLenBytes, int[] qube, ByteBuffer byteBuffer, Object bbType ) {
        if ( qube.length<3 ) {
            return byteBuffer;
        }
        
        ByteBuffer temp= ByteBuffer.allocate(recLenBytes);
        ByteBuffer result= ByteBuffer.allocate(recLenBytes * qube[0]);
        result.order(byteBuffer.order());
                
        int fieldBytes= BufferDataSet.byteCount(bbType);
        switch (qube.length) {
            case 3:
                {
                    int len1= qube[1];
                    int len2= qube[2];
                    for ( int i0=0; i0<qube[0]; i0++ ) {
                        for ( int i1=0; i1<qube[1]; i1++ ) {
                            for ( int i2=0; i2<qube[2]; i2++ ) {
                                int iin= fieldBytes * ( i1 * len2 + i2  );
                                int iout= fieldBytes * ( i0 * len1 * len2 + i2 * len1 + i1 );
                                for ( int j=0; j<fieldBytes; j++ ) {
                                    temp.put( iin + j, byteBuffer.get(iout+j) );
                                }
                            }
                        }
                        result.put(temp);
                        temp.flip();
                    }       break;
                }
            case 4:
                {
                    int len1= qube[1];
                    int len2= qube[2];
                    int len3= qube[3];
                    for ( int i0=0; i0<qube[0]; i0++ ) {
                        for ( int i1=0; i1<qube[1]; i1++ ) {
                            for ( int i2=0; i2<qube[2]; i2++ ) {
                                for ( int i3=0; i3<qube[3]; i3++ ) {
                                    int iin= fieldBytes * ( i1*len2*len3 + i2*len3 +i3 );
                                    int iout= fieldBytes * ( i0*len1*len2*len3 + i3*len2*len1 + i2*len1 +i1 );
                                    for ( int j=0; j<fieldBytes; j++ ) {
                                        temp.put( iin + j, byteBuffer.get( iout + j ) );
                                    }
                                }
                            }
                        }
                        result.put(temp);
                        temp.flip();
                    }       break;
                }
            default:
                throw new IllegalArgumentException("number of dimensions must be less than 5: "+qube.length );
        }
        result.flip();
        
        return result;
    }

    /**
     * 
     * @param cdf the cdf file.
     * @param svariable the variable name
     * @param recStart the first index to read
     * @param recStop the exclusive index
     * @param recInterval the interval to read
     * @return a ByteBuffer of the variable type.
     * @throws gov.nasa.gsfc.spdf.cdfj.CDFException.ReaderError 
     */
    private static ByteBuffer myGetBuffer( CDFReader cdf, String svariable, long recStart, int recStop, int recInterval ) throws CDFException.ReaderError {
        String stype= getTargetType( cdf.getType(svariable) );
        Object buff3= cdf.getSampled( svariable, (int)recStart, (int)(recStop-1), (int)recInterval, stype, true );
        
        ByteBuffer result;
        int type= cdf.getType(svariable);
        switch ( type ) {
            case (int)CDFConstants.CDF_DOUBLE:
            case (int)CDFConstants.CDF_EPOCH:
            case (int)CDFConstants.CDF_EPOCH16:
            case (int)CDFConstants.CDF_REAL8:
            case (int)CDFConstants.CDF_UINT4:
                double[] array= (double[])buff3;
                result= ByteBuffer.allocate( 8* array.length );
                for ( double a: array ) result.putDouble(a);
                break;
            case (int)CDFConstants.CDF_FLOAT:
            case (int)CDFConstants.CDF_REAL4:
                float[] farray= (float[])buff3;
                result= ByteBuffer.allocate( 4* farray.length );
                for ( float a: farray ) result.putFloat(a);
                break;
            case (int)CDFConstants.CDF_INT8:
            case (int)CDFConstants.CDF_TT2000:
                long[] larray= (long[])buff3;
                result= ByteBuffer.allocate( 8* larray.length );
                for ( long a: larray ) result.putLong(a);
                break;
            case (int)CDFConstants.CDF_INT4:
            case (int)CDFConstants.CDF_UINT2:
                int[] iarray= (int[])buff3;
                result= ByteBuffer.allocate( 4* iarray.length );
                for ( int a: iarray ) result.putInt(a);
                break;
            case (int)CDFConstants.CDF_INT2:
            case (int)CDFConstants.CDF_UINT1:
                short[] sarray= (short[])buff3;
                result= ByteBuffer.allocate( 2* sarray.length );
                for ( short a: sarray ) result.putShort(a);
                break;
            case (int)CDFConstants.CDF_INT1:                
            case (int)CDFConstants.CDF_BYTE:
            case (int)CDFConstants.CDF_CHAR:
            case (int)CDFConstants.CDF_UCHAR:
                byte[] barray= (byte[])buff3;
                result= ByteBuffer.allocate( 1* barray.length );
                for ( byte a: barray ) result.put(a);                
                break;
            default:
                throw new IllegalArgumentException("not implemented: "+type);
        }
        result.flip();
        return result;
    }
    
    /**
     * Creates a new instance of CdfUtil
     */
    public CdfUtil() {
    }

    /**
     * returns the Entry that is convertible to double as a double.
     * @throws NumberFormatException for strings Double.parseDouble
     */
    private static double doubleValue(Object o) {
        if (o instanceof Float) {
            return ((Float) o).doubleValue();
        } else if (o instanceof Double) {
            return ((Double) o);
        } else if (o instanceof Integer) {
            return ((Integer) o).doubleValue();
        } else if (o instanceof Short) {
            return ((Short) o).doubleValue();
        } else if (o instanceof String) {
            return Double.parseDouble((String) o);
        } else {
            throw new RuntimeException("Unsupported Data Type: " + o.getClass().getName());
        }
    }

    /**
     * returns the range of the data by looking for the SCALEMIN/SCALEMAX params,
     * or the required VALIDMIN/VALIDMAX parameters.  This is not used.
     * @param attrs the properties for the variable
     * @return the range
     */
    public static DatumRange getRange(HashMap attrs) {
        DatumRange range;
        if (attrs.containsKey("SCALEMIN") && attrs.containsKey("SCALEMAX")) {
            range = new DatumRange(doubleValue(attrs.get("SCALEMIN")),
                    doubleValue(attrs.get("SCALEMAX")), Units.dimensionless);
        } else {
            range = new DatumRange(doubleValue(attrs.get("VALIDMIN")),
                    doubleValue(attrs.get("VALIDMAX")), Units.dimensionless);
        }
        return range;
    }

    public static String getScaleType(HashMap attrs) {
        String type = "linear";
        if (attrs.containsKey("SCALETYP")) {
            type = (String) attrs.get("SCALETYP");
        }
        return type;
    }

    /**
     * add the valid range only if it looks like it is correct.  It must contain some of the data.
     * @param props the properties for the variable
     * @param ds the dataset to which the valid range would be added.
     */
    public static void maybeAddValidRange( Map<String,Object> props, MutablePropertyDataSet ds ) {

        Units pu= (Units) props.get(QDataSet.UNITS);
        Units u= (Units) ds.property( QDataSet.UNITS );

        UnitsConverter uc;
        if ( pu==null || u==null ) {
            uc= UnitsConverter.IDENTITY;
        } else if ( u==Units.cdfEpoch ) {
            uc= UnitsConverter.IDENTITY;
        } else if ( pu==Units.microseconds && u==Units.us2000 ) { // epoch16
            uc= UnitsConverter.IDENTITY;
        } else {
            if ( pu==u ) {
                uc= UnitsConverter.IDENTITY;
            } else if ( UnitsUtil.isOrdinalMeasurement(u) || UnitsUtil.isOrdinalMeasurement(pu) ) {
                return;
            } else {
                try {
                    uc= UnitsConverter.getConverter( pu, u );
                } catch ( InconvertibleUnitsException ex ) { // PlasmaWave group Polar H7 files
                    uc= UnitsConverter.IDENTITY;
                }
            }
        }

        double dmin=Double.NEGATIVE_INFINITY;
        double dmax=Double.POSITIVE_INFINITY;
        if ( ds.rank()==1 && ds.length()>0 ) {
            QDataSet range= Ops.extent(ds,null,null);
            dmin= uc.convert(range.value(0));
            dmax= uc.convert(range.value(1));
        }

        Number nmin= (Number)props.get(QDataSet.VALID_MIN);
        double vmin= nmin==null ?  Double.POSITIVE_INFINITY : nmin.doubleValue();
        Number nmax= (Number)props.get(QDataSet.VALID_MAX);
        double vmax= nmax==null ?  Double.POSITIVE_INFINITY : nmax.doubleValue();

        boolean intersects= false;
        if ( dmax>vmin && dmin<vmax ) {
            intersects= true;
        }

        if ( u instanceof EnumerationUnits  )  {
            EnumerationUnits eu= (EnumerationUnits)u;
            if ( nmax!=null && nmax.intValue()<=eu.getHighestOrdinal() ) {
                nmax= eu.getHighestOrdinal()+1; // rbsp-e_L1_mageisLOW-sp_20110922_V.06.0.0.cdf?channel_num
            }
        }
        
        if ( intersects || dmax==dmin || dmax<-1e30 || dmin>1e30 )  { //bugfix 3235447: all data invalid
            if ( nmax!=null ) ds.putProperty(QDataSet.VALID_MAX, uc.convert(nmax) );
            if ( nmin!=null ) ds.putProperty(QDataSet.VALID_MIN, uc.convert(nmin) );
        }

        String t= (String) props.get(QDataSet.SCALE_TYPE);
        if ( t!=null ) ds.putProperty( QDataSet.SCALE_TYPE, t );

    }

    /**
     * returns the size of the data type in bytes.
     * @param itype type of data, such as CDFConstants.CDF_FLOAT
     * @return the size the data atom in bytes 
     * TODO: this needs to be verified.  Unsigned numbers may come back as next larger size.
     */
    protected static int sizeOf( long itype ) {
        int sizeBytes;
        if ( itype==CDFConstants.CDF_EPOCH16 ) {
            sizeBytes= 16;
        } else if(itype == CDFConstants.CDF_DOUBLE || itype == CDFConstants.CDF_REAL8 || itype == CDFConstants.CDF_EPOCH || itype==CDFConstants.CDF_TT2000 || itype==CDFConstants.CDF_INT8 || itype==CDFConstants.CDF_UINT4 ) {
            sizeBytes= 8;
        } else if( itype == CDFConstants.CDF_FLOAT || itype == CDFConstants.CDF_REAL4 || itype==CDFConstants.CDF_INT4 || itype == CDFConstants.CDF_UINT2 ) {
            sizeBytes=4; //sizeBytes= 4;
        } else if( itype == CDFConstants.CDF_INT2 || itype == CDFConstants.CDF_UINT1 || itype==CDFConstants.CDF_UCHAR ) {
            sizeBytes=2; //sizeBytes= 2;
        } else if( itype == CDFConstants.CDF_INT1 || itype==CDFConstants.CDF_BYTE  || itype==CDFConstants.CDF_CHAR ) {
            sizeBytes=1; //sizeBytes= 1;
        } else {
            throw new IllegalArgumentException("didn't code for type");
        }
        return sizeBytes;
    }
    
    /**
     * returns the size of the variable in bytes.
     * @param dims number of dimensions in each record
     * @param dimSizes dimensions of each record
     * @param itype type of data, such as CDFConstants.CDF_FLOAT
     * @param rc number of records (rec count)
     * @return the size the variable in bytes 
     */
    private static long sizeOf( int dims, int[] dimSizes, long itype, long rc ) {
        long size= dims==0 ? rc : rc * DataSetUtil.product( dimSizes );
        size= size*sizeOf(itype);
        return size;
    }

    /**
     * returns effective rank.  Nand's code looks for 1-element dimensions, which messes up Seth's file rbspb_pre_ect-mageisHIGH.
     * See files:<ul> 
     * <li>vap+cdfj:ftp://cdaweb.gsfc.nasa.gov/pub/data/geotail/lep/2011/ge_k0_lep_20111016_v01.cdf?V0
     * <li>vap+cdfj:file:///home/jbf/ct/autoplot/data.backup/examples/cdf/seth/rbspb_pre_ect-mageisHIGH-sp-L1_20130709_v1.0.0.cdf?Histogram_prot
     * </ul>
     */
    protected static int getEffectiveRank( boolean[] varies ) {
        int rank = 0;
        for (int i = 0; i < varies.length; i++) {
            if (!varies[i]) continue;
            rank++;
        }
        return rank;
    }
    
    /**
     * implements slice1 by packing all the remaining elements towards the front and trimming.
     * @param buf the byte buffer, which can be read-only.
     * @param varType the variable type, see sizeOf(varType)
     * @param qube the dimensions of the unsliced dataset
     * @param slice1 the index to slice 
     * @param rowMajority true if the buffer is row majority.
     * @return a copy containing just the slice1 of the input buffer.
     */
    private static ByteBuffer doSlice1( ByteBuffer buf, long varType, int[] qube, int slice1, boolean rowMajority ) {
        int recSizeBytes= DataSetUtil.product(qube) / qube[0] * sizeOf(varType);
        ByteBuffer result= ByteBuffer.allocate( recSizeBytes / qube[1] * qube[0] );
        result.order(buf.order());
        if ( rowMajority ) { // one of these two is wrong.
            int p1= slice1 * recSizeBytes / qube[1];
            int p2= ( slice1 * recSizeBytes / qube[1] + recSizeBytes / qube[1] );
            for ( int irec=0; irec<qube[0]; irec++ ) {
                buf.limit(irec*recSizeBytes + p2 );
                buf.position(irec*recSizeBytes + p1);
                ByteBuffer b= buf.slice();
                result.put(b);
            }
        } else {
            int varSize= sizeOf(varType);
            for ( int irec=0; irec<qube[0]; irec++ ) {
                for ( int j=0; j<recSizeBytes; j++ ) {
                    if ( (j/varSize) % qube[1] == slice1 ) {
                        result.put( buf.get( irec*recSizeBytes + j ) );                        
                    } else {
                        // skip to slice
                    }
                }
            }            
        }
        result.flip();
        buf.position(0);
        buf.limit(recSizeBytes*qube[0]);
        return result;
    }
    
    public static synchronized MutablePropertyDataSet wrapCdfData(
        CDFReader cdf, String svariable, long recStart, long recCount, long recInterval, 
        int slice1, ProgressMonitor mon) throws Exception {
        return wrapCdfData(cdf, svariable, recStart, recCount, recInterval, slice1, false, mon);
    }
    
    /**
     * Return the named variable as a QDataSet.  This does not look at the
     * metadata for DEPEND_0, etc, and only adds metadata to represent time units
     * (e.g. the data is in TT2000) and ordinal data.
     * @param cdf the value of CDF
     * @param svariable name of the variable
     * @param recStart the first record to retrieve (0 is the first record in the file).
     * @param recCount the number of records to retrieve, -1 means the record is flag for slice
     * @param recInterval the number of records to increment, typically 1 (e.g. 2= every other record).
     * @param slice1 if non-negative, return the slice at this point.
     * @param depend if false, don't do the non-varying expansion.
     * @param mon progress monitor (currently not used), or null.
     * @return the dataset
     * @throws Exception
     */
    public static synchronized MutablePropertyDataSet wrapCdfData(
            CDFReader cdf, String svariable, long recStart, long recCount, long recInterval, 
            int slice1, boolean depend, ProgressMonitor mon) throws Exception {

        if ( recCount==0 ) {
            throw new IllegalArgumentException("recCount must be greater than 0 or -1");
        }
        if ( recCount<-1 ) throw new IllegalArgumentException("recCount must be greater than 0 or -1");
        
        logger.log( Level.FINE, "wrapCdfData {0}[{1}:{2}:{3}]", new Object[] { svariable, String.valueOf(recStart), // no commas in {1}
                 ""+(recCount+recStart), recInterval } );
        
        //MutablePropertyDataSet cresult= maybeGetCached( cdf, svariable, recStart, recCount, recInterval );
        //if ( cresult!=null ) return cresult;
        
        long varType = cdf.getType(svariable);
        
        int[] dimSizes = cdf.getDimensions(svariable);
        boolean[] dimVaries= cdf.getVarys(svariable);
        int[] repeatDimensions= new int[dimVaries.length]; // number of times to repeat because we didn't remove the dimension.
        for ( int i=0; i<repeatDimensions.length; i++ ) repeatDimensions[i]= 1;

        int dims;
        if (dimSizes == null) {
            dims = 0;
            dimSizes= new int[0]; // to simplify code
        } else {
            dims = dimSizes.length;
        }

          if ( getEffectiveRank(dimVaries) != dimSizes.length ) { // vap+cdfj:ftp://cdaweb.gsfc.nasa.gov/pub/data/geotail/lep/2011/ge_k0_lep_20111016_v01.cdf?V0
            int[] dimSizes1= new int[ cdf.getEffectiveRank(svariable) ];
            boolean[] varies= cdf.getVarys(svariable);
            int[] dimensions= cdf.getDimensions(svariable);
            int k=0;
            for ( int i=0; i<varies.length; i++ ) {
                if ( varies[i] && dimensions[i] != 1 ) {
                    dimSizes1[k]= dimSizes[i];
                    k++;
                } else {
                    repeatDimensions[i]= dimSizes[i]; 
                }
            }
            dimSizes= dimSizes1;
        }
        
        if (dims > 3 ) {
            if (recCount != -1) {
                throw new IllegalArgumentException("rank 5 not implemented");
            }
        }

         int varRecCount= cdf.getNumberOfValues(svariable);
         if ( recCount==-1 && recStart>0 && varRecCount==1 ) { // another kludge for Rockets, where depend was assigned variance
             recStart= 0;
         }
    
        if ( recCount>1 ) {    // check for length limit
            int bytesPerRecord= DataSetUtil.product(dimSizes) * sizeOf(varType);
            int limit= (int)(Integer.MAX_VALUE)/1000; // KB
            if ( limit<(recCount/1000/recInterval*bytesPerRecord) ) {
                int newRecCount= (int)( limit * recInterval * 1000 / bytesPerRecord );
                String suggest;
                if ( recInterval>1 ) {
                    suggest= "[0:"+newRecCount+":"+recInterval+"]";
                } else {
                    suggest= "[0:"+newRecCount+"]";
                }
                throw new IllegalArgumentException("data read would result in more than 2GB read, which is not yet supported.  Use "+svariable+suggest+" to read first records.");
            }
        }
        
        long rc= recCount;
        if ( rc==-1 ) rc= 1;  // -1 is used as a flag for a slice, we still really read one record.

        logger.log( Level.FINEST, "size of {0}: {1}MB  type: {2}", new Object[]{svariable, sizeOf(dims, dimSizes, varType, rc) / 1024. / 1024., varType});
        
        String stype = getTargetType( cdf.getType(svariable) );
        ByteBuffer buff2;

        long t0= System.currentTimeMillis();
        logger.entering("gov.nasa.gsfc.spdf.cdfj.CDFReader", "getBuffer" );
        
        if ( recInterval==1 ) {
            try {
                boolean preserve= true;
                if ( stype.equals("string") ) {
                    buff2= null;
                } else {
                    buff2= cdf.getBuffer( svariable, stype, new int[] { (int)recStart,(int)(recStart+recInterval*(rc-1)) }, preserve  );
                }
            } catch ( CDFException ex ) {
                buff2= myGetBuffer( cdf, svariable, (int)recStart, (int)(recStart+rc*recInterval), (int)recInterval  );
            }
        } else {
            buff2= myGetBuffer( cdf, svariable, (int)recStart, (int)(recStart+rc*recInterval), (int)recInterval  );
        }
        
        logger.exiting("gov.nasa.gsfc.spdf.cdfj.CDFReader", "getBuffer" );
        logger.log(Level.FINE, "read variable {0} in (ms): {1}", new Object[]{svariable, System.currentTimeMillis()-t0});

        ByteBuffer[] buf;
        
        buf= new ByteBuffer[] { buff2 };

        Object bbType= byteBufferType( cdf.getType(svariable) );
        
        int recLenBytes= BufferDataSet.byteCount(bbType);
        if ( dimSizes.length>0 ) recLenBytes= recLenBytes * DataSetUtil.product( dimSizes );            
        
        MutablePropertyDataSet result;
        
        int[] qube;
        qube= new int[ 1+dimSizes.length ];
        for ( int i=0; i<dimSizes.length; i++ ) {
            qube[i+1]= (int)dimSizes[i];
        }
        if ( recCount==-1 ) {
            qube[0]= 1;
        } else {
            qube[0]= (int)recCount;
        }
        
        if ( stype.equals("string") ) {
            result = readStringData(svariable, recInterval, cdf, recCount, qube );
            return result;
        }

        if ( buf.length>1 ) {
            throw new IllegalArgumentException("multiple buffers not yet implemented");
        }
        
        if ( slice1>-1 && qube.length>1 ) {
            buf[0]= doSlice1( buf[0], varType, qube, slice1, cdf.rowMajority() );
            if ( recCount==-1 ) throw new IllegalArgumentException("recCount==-1 and slice1>-1");
            int[] nqube= new int[qube.length-1];
            nqube[0]= qube[0];
            for ( int i=2;i<qube.length;i++ ) {
                nqube[i-1]= qube[i];
            }
            recLenBytes= recLenBytes/qube[1];
            qube= nqube;
        }
        
        if ( varType == CDFConstants.CDF_EPOCH && qube.length>0 ) {  // vap+cdfj:file:///home/jbf/ct/hudson/data.backup/cdf/c4_cp_fgm_spin_20030102_v01.cdf?B_vec_xyz_gse__C4_CP_FGM_SPIN
            boolean reform= true; 
            for ( int i=1; i<qube.length; i++ ) {
                if ( qube[i]!=1 ) {
                    reform= false;
                }
            }
            if ( reform ) {
                qube= Arrays.copyOf(qube,1);
            }
        }
        
        if ( cdf.rowMajority()  ) {

            if ( recCount==-1 ) {
                result= BufferDataSet.makeDataSet( qube.length, recLenBytes, 0, 
                        qube,
                        buf[0], bbType );
                result= (MutablePropertyDataSet)result.slice(0);
                
            } else {
                result= BufferDataSet.makeDataSet(qube.length, recLenBytes, 0, 
                        qube,
                        buf[0], bbType );
            }
        } else {
            if ( recCount==-1 ) {
                buf[0]= transpose(recLenBytes,qube,buf[0],bbType );
                
                result= BufferDataSet.makeDataSet( qube.length, recLenBytes, 0, 
                        qube,
                        buf[0], bbType );
                result= (MutablePropertyDataSet)result.slice(0);
            } else {
                buf[0]= transpose(recLenBytes,qube,buf[0],bbType );

                result= BufferDataSet.makeDataSet(qube.length, recLenBytes, 0, 
                        qube,
                        buf[0], bbType );
            }
        }
        
        
        //TODO: we need to figure out why the native library would implement dimvary=false
        //See https://sourceforge.net/p/autoplot/bugs/1351/
//        if ( false && depend && repeatDimensions.length==qube.length ) { 
//            //for ( int i=1; i<repeatDimensions.length; i++ ) {
//            int i= repeatDimensions.length-1;
//                if ( repeatDimensions[i]>1 ) {
//                    result= new RepeatIndexDataSet( result, i+1, repeatDimensions[i] );
//                }
//            //}
//        }

        if ( varType == CDFConstants.CDF_CHAR || varType==CDFConstants.CDF_UCHAR ) {
            throw new IllegalArgumentException("We shouldn't get here because stype=string");

        } else if ( varType == CDFConstants.CDF_EPOCH ) {
            result.putProperty(QDataSet.UNITS, Units.cdfEpoch);
            result.putProperty(QDataSet.VALID_MIN, 1.); // kludge for Timas, which has zeros.
            
        } else if ( varType==CDFConstants.CDF_EPOCH16 ) {

            result.putProperty(QDataSet.UNITS, Units.cdfEpoch);
            result.putProperty(QDataSet.VALID_MIN, 1.); // kludge for Timas, which has zeros.
            
            DDataSet result1= DDataSet.createRank1(result.length());
            for ( int i=0; i<result.length(); i++ ) {
                double t2000 = result.value(i,0) - 6.3113904e+10; // seconds since midnight 2000
                result1.putValue( i, t2000 * 1e6 + result.value(i,1) / 1000000. );
            }
            result1.putProperty( QDataSet.UNITS, Units.us2000 );
            result= result1;
            
            // adapt to das2 by translating to Units.us2000, which should be good enough.
            // note when this is not good enough, new units types can be introduced, along with conversions.


        } else if ( varType==CDFConstants.CDF_TT2000 ) {
            result.putProperty( QDataSet.UNITS, Units.cdfTT2000 );

        }

//logger.fine( "jvmMemory (MB): "+jvmMemory(result)/1024/1024 );
        if ( varType==CDFConstants.CDF_EPOCH || varType==CDFConstants.CDF_EPOCH16 || varType==CDFConstants.CDF_TT2000 ) {
            String cdfFile;
            cdfFile= CdfDataSource.cdfCacheFileForReader(cdf);
            if ( cdfFile!=null ) {
                String uri= cdfFile + "?" + svariable;
                if ( recStart!=0 || recCount!=cdf.getNumberOfValues(svariable) || recInterval>1 ) {
                    uri= uri + "["+recStart+":"+(recStart+recCount)+":"+recInterval+"]";
                }
                CdfDataSource.dsCachePut( uri, result );
            }
        }
                
        return result;
        
    }

    private static MutablePropertyDataSet readStringData(String svariable, long recInterval, CDFReader cdf, long recCount, int[] qube ) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, CDFException.ReaderError {
        EnumerationUnits units = EnumerationUnits.create(svariable);
        Object o;
        if ( recInterval>1 ) throw new IllegalArgumentException("recInterval>1 not supported here");
        o = cdf.get(svariable);
        Object o0= Array.get(o,0);
        String[] sdata;
        if ( o0.getClass().isArray() ) {
            sdata= new String[ Array.getLength(o0) ];
            for ( int j=0; j<Array.getLength(o0); j++ ) {
                sdata[j]= (String) Array.get(o0, j);
            }
        } else if ( o0.getClass()==String.class ) {
            //sdata= new String[ 1 ]; //vap+cdaweb:ds=ALOUETTE2_AV_LIM&id=freq_mark&timerange=1967-01-15+12:59:00+to+12:59:01
            //sdata[0]= String.valueOf( o0 );
            sdata= ((String[])o);
        } else {
            throw new IllegalArgumentException("not handled single array where expected double array");
        }
        int[] back = new int[sdata.length];
        for (int i = 0; i < sdata.length; i++) {
            back[i] = (int)( units.createDatum(sdata[i]).doubleValue(units) );
        }
        boolean[] varies= cdf.getVarys(svariable);
        boolean canSlice= recCount==-1;
        if ( canSlice ) {
            for ( int i=1; i<varies.length; i++ ) canSlice= canSlice && !varies[i];
        }
        if ( canSlice ) {
            qube= new int[] { qube[1] };
        }
        MutablePropertyDataSet result;
        result= ArrayDataSet.wrap( back, qube, false );
        result.putProperty(QDataSet.UNITS, units);
        return result;
    }
    
    
    /**
     * returns the amount of JVM memory in bytes occupied by the dataset. This is an approximation,
     * calculated by taking the element type size (e.g. float=4 bytes) times the number of elements for
     * the dataset.  This does not include the memory consumed by DEPEND_0, etc.
     * @param ds the ArrayDataSet, or TrArrayDataSet, or BufferDataSet.
     * @return the approximate memory consumption in bytes
     */
    public static int jvmMemory( QDataSet ds ) {
        if ( ds instanceof ArrayDataSet ) {
            return ((ArrayDataSet)ds).jvmMemory();
        } else if ( ds instanceof TrArrayDataSet ) {
            return ((TrArrayDataSet)ds).jvmMemory();
        } else if ( ds instanceof Slice0DataSet ) {
            return 0; // TODO: not worth chasing after.  TODO: really?  these are often backed by the original data.
        } else if ( ds instanceof BufferDataSet ) {
           return ((BufferDataSet)ds).jvmMemory();
        } else {
            throw new IllegalArgumentException("not supported type of QDataSet: "+ds);
        }
    }

    /**
     * return the data type for the encoding.  From
     * ftp://cdaweb.gsfc.nasa.gov/pub/cdf/doc/cdf33/cdf33ifd.pdf  page 33.
     * @param type integer type, such as 44 for CDF_FLOAT
     * @return string like "CDF_FLOAT"
     */
    public static String getStringDataType( int type ) {
        switch ( type ) {
            case  1: return "CDF_INT1";
            case  2: return "CDF_INT2";
            case  4: return "CDF_INT4";
            case  8: return "CDF_INT8";
            case 11: return "CDF_UINT1";
            case 12: return "CDF_UINT2";
            case 14: return "CDF_UINT4";
            case 41: return "CDF_BYTE";
            case 21: return "CDF_REAL4";
            case 22: return "CDF_REAL8";
            case 44: return "CDF_FLOAT";
            case 45: return "CDF_DOUBLE";
            case 31: return "CDF_EPOCH";
            case 32: return "CDF_EPOCH16";
            case 33: return "CDF_TT2000";
            case 51 :return "CDF_CHAR";
            default: return String.valueOf(type);
        }
    }

    /**
     * returns null or the attribute.
     * @param cdf the cdf file reader
     * @param var the variable name
     * @param attrname the attribute name.
     * @return null if there was a problem
     */
    private static Object getAttribute( CDFReader cdf, String var, String attrname ) {
        try {
            Object att= cdf.getAttribute( var,attrname );
            if ( att==null ) return null;
            if ( ((Vector)att).isEmpty() ) return null;
            att= ((Vector)att).get(0);
            return att;
        } catch ( CDFException ex ) {
            logger.fine(ex.getMessage());
            return null;
        }
    }

    /** 
     * return true if the attribute is set for the variable.
     * @param cdf the cdf file reader
     * @param var the variable name
     * @param attrname the attribute name.
     * @return true if the attribute is set for the variable.
     */
    public static boolean hasAttribute( CDFReader cdf, String var, String attrname ) {
        try {
            Object att= cdf.getAttribute( var,attrname );
            return !( att==null || ((Vector)att).isEmpty() );
        } catch ( CDFException ex ) {
            return false;
        }
    }

    //container for description of depend
    private static class DepDesc {
        String dep;
        String labl;
        long nrec;
        boolean rank2;
    }

    /**
     * factor out common code that gets the properties for each dimension.
     * @param cdf
     * @param var
     * @param rank
     * @param dims
     * @param dim
     * @param warn
     * @return
     */
    private static DepDesc getDepDesc( CDFReader cdf, String svar, int rank, int[] dims, int dim, List<String> warn ) {
        DepDesc result= new DepDesc();

        result.nrec=-1;
        
        try {
            if ( hasAttribute( cdf, svar, "DEPEND_"+dim ) ) {  // check for metadata for DEPEND_1
                Object att= getAttribute( cdf, svar, "DEPEND_"+dim );
                if ( att!=null && rank>1 ) {
                    logger.log(Level.FINE, "get attribute DEPEND_"+dim+" entry for {0}", svar );
                    result.dep = String.valueOf(att);
                    if ( cdf.getDimensions( result.dep ).length>0 && cdf.getNumberOfValues( result.dep )>1 && cdf.recordVariance( result.dep ) ) {
                        result.rank2= true;
                        result.nrec = cdf.getDimensions( result.dep )[0];
                        warn.add( "NOTE: " + result.dep + " is record varying" );
                    } else {
                        result.nrec = cdf.getNumberOfValues( result.dep );
                        if (result.nrec == 1) {
                            result.nrec = cdf.getDimensions( result.dep )[0];
                        }
                    }
                    if ( dims.length>(dim-2) && (result.nrec)!=dims[dim-1] ) {
                        warn.add("DEPEND_"+dim+" length ("+result.nrec+") is inconsistent with length ("+dims[dim-1]+")" );
                    }
                }
            }
        } catch ( CDFException e) {
            warn.add( "problem with DEPEND_"+dim+": " + e.getMessage() );//e.printStackTrace();
        } catch ( Exception e) {
            warn.add( "problem with DEPEND_"+dim+": " + e.getMessage() );//e.printStackTrace();
        }

        try {
             if (result.nrec==-1 && hasAttribute( cdf, svar, "LABL_PTR_"+dim ) ) {  // check for metadata for LABL_PTR_1
                Object att= getAttribute( cdf, svar, "LABL_PTR_"+dim );
                if ( att!=null && rank>1  ) {
                    logger.log(Level.FINE, "get attribute LABL_PTR_"+dim+" entry for {0}", svar );
                    result.labl = String.valueOf(att);
                    if ( !cdf.existsVariable(result.labl) ) throw new Exception("No such variable: "+String.valueOf(att));
                    result.nrec = cdf.getNumberOfValues( result.labl );
                    if (result.nrec == 1) {
                        result.nrec = cdf.getDimensions(svar)[0];
                    }
                    if ( dims.length>(dim-2) && (result.nrec)!=dims[dim-1] ) {
                        warn.add("LABL_PTR_"+dim+" length is inconsistent with length ("+dims[dim-1]+")" );
                    }
                }
            } else if ( hasAttribute( cdf, svar, "LABL_PTR_"+dim ) ) { // check that the LABL_PTR_i is the right length as well.
                Object att= getAttribute( cdf, svar, "LABL_PTR_"+dim );
                if ( att!=null && rank>1  ) {
                    logger.log(Level.FINE, "get attribute LABL_PTR_"+dim+" entry for {0}", svar );
                    result.labl= String.valueOf(att);
                    int nrec = cdf.getNumberOfValues(result.labl);
                    if ( nrec == 1 ) {
                        nrec = cdf.getDimensions(result.labl)[0];
                    }
                    if ( dims.length>(dim-2) && (nrec)!=dims[dim-1] ) {
                        warn.add("LABL_PTR_"+dim+" length is inconsistent with length ("+dims[dim-1]+")" );
                    }
                }
            }
        } catch (CDFException e) {
            warn.add( "problem with LABL_PTR_"+dim+": " + e.getMessage() );//e.printStackTrace();
        } catch (Exception e) {
            warn.add( "problem with LABL_PTR_"+dim+": " + e.getMessage() );//e.printStackTrace();
        }
        return result;
    }

    private static boolean hasVariable( CDFReader cdf, String var ) {
        List<String> names= Arrays.asList( cdf.getVariableNames() );
        return names.contains(var);
    }    
    
    /**
     * Return a map where keys are the names of the variables, and values are descriptions.
     * @param cdf the cdf reader reference.
     * @param dataOnly show only the DATA and not SUPPORT_DATA.  Note I reclaimed this parameter because I wasn't using it.
     * @param rankLimit show only variables with no more than this rank.
     * @return map of parameter name to short description
     * @throws Exception
     */
    public static Map<String, String> getPlottable(CDFReader cdf, boolean dataOnly, int rankLimit) throws Exception {
        return getPlottable(cdf, dataOnly, rankLimit, false, false);
    }
    
    /**
     * Return a map where keys are the names of the variables, and values are descriptions.  This 
     * allows for a deeper query, getting detailed descriptions within the values, and also supports the
     * mode where the master CDFs (used by the CDAWeb plugin) don't contain data and record counts should
     * not be supported.
     * @param cdf
     * @param dataOnly show only the DATA and not SUPPORT_DATA.  Note I reclaimed this parameter because I wasn't using it.
     * @param rankLimit show only variables with no more than this rank.
     * @param deep return more detailed descriptions in HTML
     * @param master cdf is a master cdf, so don't indicate record counts.
     * @return map of parameter name to short description
     * @throws Exception
     */
    public static Map<String, String> getPlottable(CDFReader cdf, boolean dataOnly, int rankLimit, boolean deep, boolean master) throws Exception {

        Map<String, String> result = new LinkedHashMap<String, String>();
        Map<String, String> dependent= new LinkedHashMap<String, String>();

        boolean isMaster= master; //cdf.getName().contains("MASTERS"); // don't show of Epoch=0, just "Epoch"

        logger.fine("getting CDF variables");
        String[] v = cdf.getVariableNames();
        logger.log(Level.FINE, "got {0} variables", v.length);

        logger.fine("getting CDF attributes");

        boolean[] isData= new boolean[v.length];
        int i=-1;
        
        int skipCount=0;
        for (String svar : v) {
            i=i+1;
            if ( dataOnly ) {
                Object attr= getAttribute(cdf, svar, "VAR_TYPE" );
                if ( attr==null ) {
                    for ( String s: cdf.variableAttributeNames(svar) ) {
                        if ( s.equalsIgnoreCase("VAR_TYPE") ) {
                            attr= getAttribute(cdf,svar,s);
                        }
                    }
                    if ( attr!=null ) {
                        logger.log(Level.INFO, "Wrong-case VAR_TYPE attribute found, should be \"VAR_TYPE\"");
                    }
                }
                if ( attr!=null && "data".equalsIgnoreCase(attr.toString()) ) {
                    if ( !attr.equals("data") ) {
                        logger.log(Level.INFO, "var_type is case-sensitive, should be \"data\", not {0}", attr);
                        attr= "data";
                    }
                }
                if ( attr==null || !attr.equals("data") ) {
                    skipCount++;
                    isData[i]= false;
                } else {
                    isData[i]= true;
                }
            }
        }
        //if ( skipCount==v.length ) {
        //    logger.fine( "turning off dataOnly because it rejects everything");
        //    dataOnly= false;
        //}

        i=-1;
        for (String v1 : v) {
            i=i+1;
            String svar=null;
            List<String> warn= new ArrayList();
            String xDependVariable=null;
            boolean isVirtual= false;
            long xMaxRec = -1;
            long maxRec= -1;
            long recCount= -1;
            String scatDesc = null;
            String svarNotes = null;                
            StringBuilder vdescr=null;
            int rank=-1;
            int[] dims=new int[0];
            int varType=0;
            try {
                svar = v1;
                try {
                    varType= cdf.getType(svar);
                } catch ( CDFException ex ) {
                    throw new RuntimeException(ex);
                }
                // reject variables that are ordinal data that do not have DEPEND_0.
                boolean hasDep0= hasAttribute( cdf, svar, "DEPEND_0" );
                if ( ( varType==CDFConstants.CDF_CHAR || varType==CDFConstants.CDF_UCHAR ) && ( !hasDep0 ) ) {
                    logger.log(Level.FINER, "skipping because ordinal and no depend_0: {0}", svar );
                    continue;
                }
                maxRec = cdf.getNumberOfValues(svar); 
                recCount= maxRec;
                dims = cdf.getDimensions(svar);
                boolean[] dimVary= cdf.getVarys(svar);
                // cdf java Nand
                if ( dimVary.length>0 && dimVary[0]==false ) {
                    dims= new int[0];
                }
                if (dims == null) {
                    rank = 1;
                } else {
                    rank = dims.length + 1;
                }
                if (rank > rankLimit) {
                    continue;
                }
                if ( svar.equals("Time_PB5") ) {
                    logger.log(Level.FINE, "skipping {0} because we always skip Time_PB5", svar );
                    continue;
                }
                if ( dataOnly ) {
                    if ( !isData[i] ) continue;
                }
                Object att= getAttribute( cdf, svar, "VIRTUAL" );
                if ( att!=null ) {
                    logger.log(Level.FINE, "get attribute VIRTUAL entry for {0}", svar );
                    if ( String.valueOf(att).toUpperCase().equals("TRUE") ) {
                        String funct= (String)getAttribute( cdf, svar, "FUNCTION" );
                        if ( funct==null ) funct= (String) getAttribute( cdf, svar, "FUNCT" ) ; // in alternate_view in IDL: 11/5/04 - TJK - had to change FUNCTION to FUNCT for IDL6.* compatibili
                        if ( !CdfVirtualVars.isSupported(funct) ) {
                            if ( !funct.startsWith("comp_themis") ) {
                                logger.log(Level.FINE, "virtual function not supported: {0}", funct);
                            }
                            continue;
                        } else {
                            vdescr= new StringBuilder(funct);
                            vdescr.append( "( " );
                            int icomp=0;
                            String comp= (String)getAttribute( cdf, svar, "COMPONENT_"+icomp );
                            if ( comp!=null ) {
                                vdescr.append( comp );
                                icomp++;
                            }
                            for ( ; icomp<5; icomp++ ) {
                                comp= (String)getAttribute( cdf, svar, "COMPONENT_"+icomp );
                                if ( comp!=null ) {
                                    vdescr.append(", ").append(comp);
                                } else {
                                    break;
                                }
                            }
                            vdescr.append(" )");
                        }
                        isVirtual= true;
                    }
                }
            }catch (CDFException e) {
                logger.fine(e.getMessage());
            }catch (Exception e) {
                logger.fine(e.getMessage());
            }
            try {
                if ( hasAttribute( cdf, svar, "DEPEND_0" )) {  // check for metadata for DEPEND_0
                    Object att= getAttribute( cdf, svar, "DEPEND_0" );
                    if ( att!=null ) {
                        logger.log(Level.FINE, "get attribute DEPEND_0 entry for {0}", svar);
                        xDependVariable = String.valueOf(att);
                        if ( !hasVariable(cdf,xDependVariable ) ) throw new Exception("No such variable: "+String.valueOf(att));
                        xMaxRec = cdf.getNumberOfValues( xDependVariable );
                        if ( xMaxRec!=maxRec && vdescr==null && cdf.recordVariance(svar) ) {
                            if ( maxRec==-1 ) maxRec+=1; //why?
                            if ( maxRec==0 ) {
                                warn.add("data contains no records" );
                            } else {
                                warn.add("depend0 length is inconsistent with length ("+(maxRec)+")" );
                            }
                            //TODO: warnings are incorrect for Themis data.
                        }
                    } else {
                        if ( dataOnly ) {
                            continue; // vap+cdaweb:ds=GE_K0_PWI&id=freq_b&timerange=2012-06-12
                        }
                    }
                }
            } catch (CDFException e) {
                warn.add( "problem with DEPEND_0: " + e.getMessage() );
            } catch (Exception e) {
                warn.add( "problem with DEPEND_0: " + e.getMessage() );
            }
            DepDesc dep1desc= getDepDesc( cdf, svar, rank, dims, 1, warn );
            DepDesc dep2desc= getDepDesc( cdf, svar, rank, dims, 2, warn );
            DepDesc dep3desc= getDepDesc( cdf, svar, rank, dims, 3, warn );
            if (deep) {
                Object o= (Object) getAttribute( cdf, svar, "CATDESC" );
                if ( o != null && o instanceof String ) {
                    logger.log(Level.FINE, "get attribute CATDESC entry for {0}", svar );
                    scatDesc = (String)o ;
                }
                o=  getAttribute( cdf, svar, "VAR_NOTES" );
                if ( o!=null  && o instanceof String ) {
                    logger.log(Level.FINE, "get attribute VAR_NOTES entry for {0}", svar );
                    svarNotes = (String)o ;
                }
            }
            String desc = svar;
            if (xDependVariable != null) {
                desc += "(" + xDependVariable;
                if ( ( xMaxRec>0 || !isMaster ) && xMaxRec==maxRec ) { // small kludge for CDAWeb, where we expect masters to be empty.
                    desc+= "=" + (xMaxRec);
                }
                if ( dep1desc.dep != null) {
                    desc += "," + dep1desc.dep + "=" + dep1desc.nrec + ( dep1desc.rank2 ? "*": "" );
                    if ( dep2desc.dep != null) {
                        desc += "," + dep2desc.dep + "=" + dep2desc.nrec + ( dep2desc.rank2 ? "*": "" );
                        if (dep3desc.dep != null) {
                            desc += "," + dep3desc.dep + "=" + dep3desc.nrec + ( dep3desc.rank2 ? "*": "" );
                        }
                    }
                } else if ( rank>1 ) {
                    desc += ","+DataSourceUtil.strjoin( dims, ",");
                }
                desc += ")";
            }
            if (deep) {
                StringBuilder descbuf = new StringBuilder("<html><b>" + desc + "</b><br>");

                int itype= -1;
                try { 
                    //assert svar is valid.
                    itype= cdf.getType(svar);
                } catch ( CDFException ex ) {}
                
                String recDesc= ""+ CdfUtil.getStringDataType( itype );
                if ( dims!=null ) {
                    recDesc= recDesc+"["+ DataSourceUtil.strjoin( dims, ",") + "]";
                }
                if (maxRec != xMaxRec)
                    if ( isVirtual ) {
                        descbuf.append("").append("(virtual function ").append(vdescr).append( ")<br>");
                    } else {
                        descbuf.append("").append( recCount ).append(" records of ").append(recDesc).append("<br>");
                    }
                if (scatDesc != null)
                    descbuf.append("").append(scatDesc).append("<br>");
                if (svarNotes !=null ) {
                    descbuf.append("<br><p><small>").append(svarNotes).append("</small></p>");
                }
                
                descbuf.append("<br><br><small>CDF data type is ").append(CdfUtil.getStringDataType(varType)).append("</small>");
                        
                for ( String s: warn ) {
                    if ( s.startsWith("NOTE") ) {
                        descbuf.append("<br>").append(s);
                    } else {
                        descbuf.append("<br>WARNING: ").append(s);
                    }
                }

                descbuf.append("</html>");
                if ( xDependVariable!=null ) {
                    dependent.put(svar, descbuf.toString());
                } else {
                    result.put(svar, descbuf.toString());
                }
            } else {
                if ( xDependVariable!=null ) {
                    dependent.put(svar, desc);
                } else {
                    result.put(svar, desc);
                }
            }
        } // for

        logger.fine("done, get plottable ");

        dependent.putAll(result);

        return dependent;
    }

}
