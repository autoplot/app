/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.das2.qstream;

import org.w3c.dom.Element;

/**
 *
 * @author jbf
 */
public class PacketDescriptorFactory implements DescriptorFactory {

    public Descriptor create(Element element) {
        return new PacketDescriptor(element);
    }

}
