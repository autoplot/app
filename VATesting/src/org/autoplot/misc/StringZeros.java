/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.autoplot.misc;

/**
 *
 * @author jbf
 */
public class StringZeros {
    public static void main( String[] args ) {
        byte[] bb= new byte[] { 49,49,49,0,49,49,49 };
        String s= new String(bb);
        System.err.println(s);
        System.err.println(s.length());
    }
}
