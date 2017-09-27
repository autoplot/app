/* File: DasAxisEvent.java
 * Copyright (C) 2002-2003 The University of Iowa
 * Created by: Jeremy Faden <jbf@space.physics.uiowa.edu>
 *             Jessica Swanner <jessica@space.physics.uiowa.edu>
 *             Edward E. West <eew@space.physics.uiowa.edu>
 *
 * This file is part of the das2 library.
 *
 * das2 is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.das2.graph.event;

import java.util.EventObject;

/**
 *
 * @author  eew
 */
public class DasAxisEvent extends EventObject
{
    
    private double minimum;
    private double maximum;
    
    /** Creates a new instance of DasAxisEvent */
    public DasAxisEvent(Object source, double minimum, double maximum)
    {
        super(source);
        this.minimum = minimum;
        this.maximum = maximum;
    }
    
    public double getDataMinimum()
    {
        return minimum;
    }
    
    public double getDataMaximum()
    {
        return maximum;
    }
    
}
