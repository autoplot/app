/*
 * DDataSet.java
 *
 * Created on April 24, 2007, 11:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.autoplot.cdf;

import java.util.HashMap;
import java.util.Map;
import org.das2.qds.AbstractDataSet;
import org.das2.qds.DataSetUtil;
import org.das2.qds.QDataSet;
import org.das2.qds.RankZeroDataSet;
import org.das2.qds.Slice0DataSet;
import org.das2.qds.WritableDataSet;

/**
 * hacked DDataSet implementation does transpose for column major files.
 * rank 1,2,or 3 dataset backed by double array.  Note this is not
 * simply a transpose of DDataSet, as the name implies.  The zeroth index is 
 * the same, and the remaining index are reversed.
 * 
 * Mutable datasets warning: No dataset should be mutable once it is accessible to the
 * rest of the system.  This would require clients make defensive copies which would 
 * seriously degrade performance.  
 *
 * @author jbf
 */
public final class TrFDataSet extends TrArrayDataSet implements WritableDataSet, RankZeroDataSet {

    float[] back;
    int rank;
    int len0;
    int len1;
    int len2;
    int len3;

    private static final boolean RANGE_CHECK = false;
    public static final String version = "20090605";

    public static TrFDataSet createRank1(int len0) {
        return new TrFDataSet(1, len0, 1, 1, 1);
    }

    public static TrFDataSet createRank2(int len0, int len1) {
        return new TrFDataSet(2, len0, len1, 1, 1);
    }

    public static TrFDataSet createRank3(int len0, int len1, int len2) {
        return new TrFDataSet(3, len0, len1, len2, 1);
    }

    public static TrFDataSet createRank4(int len0, int len1, int len2, int len3) {
        return new TrFDataSet(4, len0, len1, len2, len3);
    }

    /**
     * Makes an array from array of dimension sizes.  The result will have
     * rank qube.length(). 
     * @param qube array specifying the rank and size of each dimension
     * @return DDataSet
     */
    public static TrFDataSet create(int[] qube) {
        if ( qube.length==0 ) {
            return new TrFDataSet( 0, 1, 1, 1, 1 );
        } else if (qube.length == 1) {
            return TrFDataSet.createRank1(qube[0]);
        } else if (qube.length == 2) {
            return TrFDataSet.createRank2(qube[0], qube[1]);
        } else if (qube.length == 3) {
            return TrFDataSet.createRank3(qube[0], qube[1], qube[2]);
        } else if (qube.length == 4) {
            return TrFDataSet.createRank4(qube[0], qube[1], qube[2], qube[3]);
        } else {
            throw new IllegalArgumentException("bad qube");
        }
    }

    /**
     * Wraps an array from array of dimension sizes.  The result will have
     * rank qube.length(). 
     * @param data array containing the data, with the last dimension contiguous in memory.
     * @param qube array specifying the rank and size of each dimension
     * @return DDataSet
     */
    public static TrFDataSet wrap( float[] data, int[] qube ) {
        if (qube.length == 0 ) {
            return new TrFDataSet( 1, 1, 1, 1, 1, data );
        } else if (qube.length == 1) {
            return new TrFDataSet( 1, qube[0], 1, 1, 1, data );
        } else if (qube.length == 2) {
            return new TrFDataSet( 2, qube[0], qube[1], 1, 1, data );
        } else if (qube.length == 3) {
            return new TrFDataSet( 3, qube[0], qube[1], qube[2], 1, data );
        } else if (qube.length == 4) {
            return new TrFDataSet( 4, qube[0], qube[1], qube[2], qube[3], data);
        } else {
            throw new IllegalArgumentException("bad qube");
        }
    }

        
    /** Creates a new instance of DDataSet */
    private TrFDataSet(int rank, int len0, int len1, int len2, int len3) {
        this(rank, len0, len1, len2, len3, new float[len0 * len1 * len2 * len3]);
    }

    private TrFDataSet(int rank, int len0, int len1, int len2, int len3, float[] back) {
        if ( back==null ) throw new NullPointerException("back was null");
        this.back = back;
        this.rank = rank;
        this.len0 = len0;
        this.len1 = len1;
        this.len2 = len2;
        this.len3 = len3;
        DataSetUtil.addQube(this);
    }

    protected Object getBack() {
        return this.back;
    }


    public int rank() {
        return rank;
    }

    @Override
    public int length() {
        return len0;
    }

    @Override
    public int length(int i) {
        return len1;
    }

    @Override
    public int length(int i0, int i1) {
        return len2;        
    }

    @Override
    public int length(int i0, int i1, int i2) {
        return len3;
    }

    @Override
    public double value() {
        float v= back[0];
        return v==fill ? dfill : v;
    }

    @Override
    public double value(int i0) {
        if (RANGE_CHECK) {
            if (i0 < 0 || i0 >= len0) {
                throw new IndexOutOfBoundsException("i0=" + i0 + " " + this);
            }
        }
        float v= back[i0];
        return v==fill ? dfill : v;
    }

    @Override
    public double value(int i0, int i1) {
        if (RANGE_CHECK) {
            if (i0 < 0 || i0 >= len0) {
                throw new IndexOutOfBoundsException("i0=" + i0 + " " + this);
            }
            if (i1 < 0 || i1 >= len1) {
                throw new IndexOutOfBoundsException("i1=" + i1 + " " + this);
            }
        }
        float v= back[i0 * len1 + i1];
        return v==fill ? dfill : v;
    }

    @Override
    public double value(int i0, int i1, int i2) {
        if (RANGE_CHECK) {
            if (i0 < 0 || i0 >= len0) {
                throw new IndexOutOfBoundsException("i0=" + i0 + " " + this);
            }
            if (i1 < 0 || i1 >= len1) {
                throw new IndexOutOfBoundsException("i1=" + i1 + " " + this);
            }
            if (i2 < 0 || i2 >= len2) {
                throw new IndexOutOfBoundsException("i2=" + i2 + " " + this);
            }
        }
        float v= back[i0 * len1 * len2 + i2 * len1 + i1];
        return v==fill ? dfill : v;
    }

    @Override
    public double value(int i0, int i1, int i2, int i3) {
        if (RANGE_CHECK) {
            if (i0 < 0 || i0 >= len0) {
                throw new IndexOutOfBoundsException("i0=" + i0 + " " + this);
            }
            if (i1 < 0 || i1 >= len1) {
                throw new IndexOutOfBoundsException("i1=" + i1 + " " + this);
            }
            if (i2 < 0 || i2 >= len2) {
                throw new IndexOutOfBoundsException("i2=" + i2 + " " + this);
            }
            if (i3 < 0 || i3 >= len3) {
                throw new IndexOutOfBoundsException("i3=" + i3 + " " + this);
            }
        }
        float v= back[i0*len1*len2*len3 + i3*len2*len1 + i2*len1 +i1];
        return v==fill ? dfill : v;
    }

    public void putValue(double value) {
        back[0]= (float)value;
    }

    public void putValue(int i0, double value) {
        if (RANGE_CHECK) {
            if (i0 < 0 || i0 >= len0) {
                throw new IndexOutOfBoundsException("i0=" + i0 + " " + this);
            }
        }
        back[i0] = (float)value;
    }

    public void putValue(int i0, int i1, double value) {
        if (RANGE_CHECK) {
            if (i0 < 0 || i0 >= len0) {
                throw new IndexOutOfBoundsException("i0=" + i0 + " " + this);
            }
            if (i1 < 0 || i1 >= len1) {
                throw new IndexOutOfBoundsException("i1=" + i1 + " " + this);
            }
        }
        back[i0 * len1 + i1] = (float)value;
    }

    public void putValue(int i0, int i1, int i2, double value) {
        if (RANGE_CHECK) {
            if (i0 < 0 || i0 >= len0) {
                throw new IndexOutOfBoundsException("i0=" + i0 + " " + this);
            }
            if (i1 < 0 || i1 >= len1) {
                throw new IndexOutOfBoundsException("i1=" + i1 + " " + this);
            }
            if (i2 < 0 || i2 >= len2) {
                throw new IndexOutOfBoundsException("i2=" + i2 + " " + this);
            }
        }
        back[i0 * len1 * len2 + i2 * len1 + i1] = (float)value;
    }

    public void putValue(int i0, int i1, int i2, int i3, double value) {
        if (RANGE_CHECK) {
            if (i0 < 0 || i0 >= len0) {
                throw new IndexOutOfBoundsException("i0=" + i0 + " " + this);
            }
            if (i1 < 0 || i1 >= len1) {
                throw new IndexOutOfBoundsException("i1=" + i1 + " " + this);
            }
            if (i2 < 0 || i2 >= len2) {
                throw new IndexOutOfBoundsException("i2=" + i2 + " " + this);
            }
            if (i3 < 0 || i3 >= len3) {
                throw new IndexOutOfBoundsException("i3=" + i3 + " " + this);
            }
        }
        back[i0 *len1  *len2   *len3 + i3 *len1  *len2 +  i2*len1 +i1]= (float)value;
        
    }

    /**
     * Shorten the dataset by changing it's dim 0 length parameter.  The same backing array is used, 
     * so the element that remain ill be the same.
     * can only shorten!
     */
    public void putLength(int len) {
        if (len > len0) {
            throw new IllegalArgumentException("dataset cannot be lengthened");
        }
        len0 = len;
    }

    @Override
    public String toString() {
        return DataSetUtil.toString(this);
    }

    @Override
    public void putProperty(String name, Object value) {
        super.putProperty(name, value);
        if ( name.equals(QDataSet.FILL_VALUE) ) checkFill(); // because of rounding errors
    }
    
    /**
     * copies the properties, copying depend datasets as well.  
     * @see DataSetUtil.copyProperties, which is a shallow copy.
     */
    protected static Map copyProperties(QDataSet ds) {
        Map result = new HashMap();        
        Map srcProps= DataSetUtil.getProperties(ds);
        
        result.putAll(srcProps);
                
        for ( int i=0; i < ds.rank(); i++) {
            QDataSet dep = (QDataSet) ds.property("DEPEND_" + i);
            if (dep == ds) {
                throw new IllegalArgumentException("dataset is dependent on itsself!");
            }
            if (dep != null) {
                result.put("DEPEND_" + i, copy(dep));
            }
        }

        for (int i = 0; i < QDataSet.MAX_PLANE_COUNT; i++) {
            QDataSet plane0 = (QDataSet) ds.property("PLANE_" + i);
            if (plane0 != null) {
                result.put("PLANE_" + i, copy(plane0));
            } else {
                break;
            }
        }

        return result;
    }

    private static TrFDataSet ddcopy(TrFDataSet ds) {
        int dsLength = ds.len0 * ds.len1 * ds.len2 * ds.len3;

        float[] newback = new float[dsLength];

        System.arraycopy(ds.back, 0, newback, 0, dsLength);

        TrFDataSet result = new TrFDataSet(ds.rank, ds.len0, ds.len1, ds.len2, ds.len3, newback);
        result.properties.putAll(copyProperties(ds)); // TODO: problems... 
        if ( result.properties.containsKey(QDataSet.FILL_VALUE) ) {
            result.checkFill();
        }
        return result;
    }

    /**
     * Copy the dataset to a DDataSet only if the dataset is not already a DDataSet.
     * @param ds
     * @return
     */
    public static TrFDataSet maybeCopy( QDataSet ds ) {
        if ( ds instanceof TrFDataSet ) {
            return (TrFDataSet)ds;
        } else {
            return copy(ds);
        }
    }
    
    /**
     * copies the dataset into a writeable dataset, and all of its depend datasets as well.
     * An optimized copy is used when the argument is a DDataSet.
     */
    public static TrFDataSet copy(QDataSet ds) {
        if (ds instanceof TrFDataSet) {
            return ddcopy((TrFDataSet) ds);
        }
        int rank = ds.rank();
        TrFDataSet result;
        int len1,len2,len3;
        if ( !DataSetUtil.isQube(ds) ) {
            //throw new IllegalArgumentException("copy non-qube");
            logger.fine("copy of non-qube to DDataSet, which must be qube");
        }
        switch (rank) {
            case 0:
                result= createRank1(1);
                result.rank= 0;
                result.putValue(ds.value());
                break;
            case 1:
                result = createRank1(ds.length());
                for (int i = 0; i < ds.length(); i++) {
                    result.putValue(i, ds.value(i));
                }
                break;
            case 2:
                len1= ds.length() == 0 ? 0 : ds.length(0);
                result = createRank2(ds.length(), len1 );
                for (int i = 0; i < ds.length(); i++) {
                    for (int j = 0; j < ds.length(i); j++) {
                        result.putValue(i, j, ds.value(i, j));
                    }
                }
                break;
            case 3:
                len1= ds.length() == 0 ? 0 : ds.length(0) ;
                len2= len1 == 0 ? 0 : ds.length(0,0);
                result = createRank3(ds.length(), len1, len2 );
                for (int i = 0; i < ds.length(); i++) {
                    for (int j = 0; j < ds.length(i); j++) {
                        for (int k = 0; k < ds.length(i, j); k++) {
                            result.putValue(i, j, k, ds.value(i, j, k));
                        }
                    }
                }
                break;
            case 4:
                len1 = (ds.length()==0) ? 0 : ds.length(0);
                len2 = (len1==0) ? 0 : ds.length(0,0);
                len3 = (len2==0) ? 0 : ds.length(0,0,0);
                result = createRank4(ds.length(), len1, len2, len3);
                for (int i=0; i< ds.length(); i++)
                    for (int j=0; j<ds.length(i); j++)
                        for (int k=0; k<ds.length(i,j); k++)
                            for (int l=0; l<ds.length(i,j,k); l++)
                                result.putValue(i, j, k, l, ds.value(i, j, k, l));
                break;
            default:
                throw new IllegalArgumentException("bad rank");
        }
        result.properties.putAll(copyProperties(ds)); // TODO: problems...

        return result;
    }

    /**
     * creates a DDataSet by wrapping an existing double array.
     */
    public static TrFDataSet wrap(float[] back) {
        return new TrFDataSet(1, back.length, 1, 1, 1, back);
    }

    /**
     * creates a DDataSet by wrapping an existing array, and aliasing it to rank2.
     * Note the last index is packed closest in memory.
     * @param n1 the size of the second dimension.
     */
    public static TrFDataSet wrapRank2(float[] back, int n1) {
        return new TrFDataSet(2, back.length / n1, n1, 1, 1, back);
    }

    /**
     * creates a DDataSet by wrapping an existing array, and aliasing it to rank2.
     * Note the last index is packed closest in memory.  The first index length
     * is calculated from the size of the array.
     * @param n1 the size of the second index.
     * @param n2 the size of the third index.
     */
    public static TrFDataSet wrapRank3(float[] back, int n1, int n2) {
        return new TrFDataSet(3, back.length / (n1 * n2), n1, n2, 1, back);
    }

    /**
     * creates a DDataSet by wrapping an existing array, aliasing it to rank 2.
     */
    public static TrFDataSet wrap(float[] back, int nx, int ny) {
        return new TrFDataSet(2, nx, ny, 1, 1, back);
    }

    public static TrFDataSet wrap( float[] back, int rank, int len0, int len1, int len2 ) {
        return new TrFDataSet( rank, len0, len1, len2, 1, back );
    }

    public static TrFDataSet wrap( float[] back, int rank, int len0, int len1, int len2, int len3) {
        return new TrFDataSet( rank, len0, len1, len2, len3, back);
    }
    
    /**
     * join dep0 if found, join auxillary planes if found.
     */
    private void joinProperties(TrFDataSet ds) {
        Map result = new HashMap();
        for (int i = 0; i < 1; i++) {
            QDataSet dep1 = (QDataSet) ds.property("DEPEND_" + i);
            if (dep1 != null) {
                QDataSet dep0 = (QDataSet) this.property("DEPEND_" + i);
                TrFDataSet djoin = TrFDataSet.copy(dep0);
                TrFDataSet ddep1 = dep1 instanceof TrFDataSet ? (TrFDataSet) dep1 : TrFDataSet.copy(dep1);
                djoin.append(ddep1);
                result.put("DEPEND_" + i, djoin);
            }
        }

        for (int i = 0; i < QDataSet.MAX_PLANE_COUNT; i++) {
            QDataSet dep1 = (QDataSet) ds.property("PLANE_" + i);
            if (dep1 != null) {
                QDataSet dep0 = (QDataSet) this.property("PLANE_" + i);
                TrFDataSet djoin = TrFDataSet.copy(dep0); 
                TrFDataSet dd1 = dep1 instanceof TrFDataSet ? (TrFDataSet) dep1 : TrFDataSet.copy(dep1);
                djoin.append(dd1);  
                result.put("PLANE_" + i, djoin);
            } else {
                break;
            }
        }

        this.properties.putAll(result);
    }

    /**
     * copy elements of src DDataSet into dest DDataSet, with System.arraycopy.
     * src and dst must have the same geometry, except for dim 0.  Allows for
     * aliasing when higher dimension element count matches.
     * @param len number of records to copy.
     * @throws IllegalArgumentException if the higher rank geometry doesn't match
     * @throws IndexOutOfBoundsException
     */
    public static void copyElements(TrFDataSet src, int srcpos, TrFDataSet dest, int destpos, int len) {
        if ( src.len1 != dest.len1 || src.len2 != dest.len2 ) {
            throw new IllegalArgumentException("src and dest geometry don't match");
        }
        copyElements( src, srcpos, dest, destpos, len * src.len1 * src.len2, false); 
    }    
    
    /**
     * copy elements of src DDataSet into dest DDataSet, with System.arraycopy.
     * src and dst must have the same geometry, except for dim 0.  Allows for
     * aliasing when higher dimension element count matches.
     * @param src source dataset
     * @param srcpos source dataset first dimension index.
     * @param dest destination dataset
     * @param destpos destination dataset first dimension index.
     * @param len total number of elements to copy
     * @param checkAlias bounds for aliased write (same number of elements, different geometry.)
     * @throws IllegalArgumentException if the higher rank geometry doesn't match
     * @throws IndexOutOfBoundsException
     */
    public static void copyElements( TrFDataSet src, int srcpos, TrFDataSet dest, int destpos, int len, boolean checkAlias ) {
        if ( checkAlias && ( src.len1*src.len2 != dest.len1*dest.len2 ) ) {
            throw new IllegalArgumentException("src and dest geometry don't match");
        }
        int srcpos1 = srcpos * src.len1 * src.len2;
        int destpos1 = destpos * dest.len1 * dest.len2;
        int len1 = len;
        System.arraycopy( src.back, srcpos1, dest.back, destpos1, len1 );
    }

    /**
     * append the second dataset onto this dataset.  Not thread safe!!!
     * TODO: this really should return a new dataset.  Presumably this is to avoid copies, but currently it copies anyway!
     * TODO: this will be renamed "concatenate" or "append" since "join" is the anti-slice.
     * @deprecated use append instead.
     */
    public void join(TrFDataSet ds) {
        append(ds);
    }
    
    /**
     * append the second dataset onto this dataset.  Not thread safe!!!
     * TODO: this really should return a new dataset.  Presumably this is to avoid copies, but currently it copies anyway!
     */
    public void append( TrFDataSet ds ) {
        if (ds.rank() != rank) {
            throw new IllegalArgumentException("rank mismatch");
        }
        if (ds.len1 != len1) {
            throw new IllegalArgumentException("len1 mismatch");
        }
        if (ds.len2 != len2) {
            throw new IllegalArgumentException("len2 mismatch");
        }
        if (ds.len3 != len3) {
            throw new IllegalArgumentException("len3 mismatch");
        }

        int myLength = len0 * len1 * len2 * len3;
        int dsLength = ds.len0 * ds.len1 * ds.len2 * ds.len3;

        float[] newback = new float[myLength + dsLength];

        System.arraycopy(this.back, 0, newback, 0, myLength);
        System.arraycopy(ds.back, 0, newback, myLength, dsLength);

        len0 = this.len0 + ds.len0;
        this.back = newback;

        joinProperties(ds);
    }


    /**
     * trim operator copies the data into a new dataset.
     * @param start
     * @param end
     * @return
     */
    @Override
    public QDataSet trim(int start, int end) {
        int nrank = this.rank;
        int noff1= start * len1 * len2 * len3;
        int noff2= end * len1 * len2 * len3;
        float[] newback = new float[noff2-noff1];
        System.arraycopy( this.back, noff1, newback, 0, noff2-noff1 );
        TrFDataSet result= new TrFDataSet( nrank, end-start, len1, len2, len3, newback );
        Map<String,Object> props= DataSetUtil.getProperties(this);
        Map<String,Object> depProps= DataSetUtil.trimProperties( this, start, end );
        props.putAll(depProps);
        DataSetUtil.putProperties( props, result );
        return result;
    }

    /**
     * TODO: this is untested, but is left in to demonstrate how the capability
     * method should be implemented.  Clients should use this instead of
     * casting the class to the capability class.
     * @param <T>
     * @param clazz
     * @return
     */
    @Override
    public <T> T capability(Class<T> clazz) {
        if ( clazz==WritableDataSet.class ) {
            return (T) this;
        } else {
            return super.capability(clazz);
        }
    }


}
