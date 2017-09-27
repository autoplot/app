/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.autoplot.dom;

import org.das2.datum.Datum;
import org.das2.datum.Units;
import org.das2.graph.DasColorBar;

/**
 *
 * @author jbf
 */
public class CloneTest {
    public static void main(String[] args) {
        PlotElementStyle style= new PlotElementStyle();
        style.setReference(Datum.create(4.0, Units.kiloHertz ));

        PlotElementStyle cln= (PlotElementStyle) style.copy();

        System.err.println( "CloneTest: " + cln.getReference());

        PlotElement myPanel= new PlotElement();

        myPanel.getStyle().setColortable( DasColorBar.Type.GRAYSCALE );

        PlotElement clonePanel= (PlotElement) myPanel.copy();
        System.err.println( "colortable: " + clonePanel.getStyle().getColortable() );
    }
}
