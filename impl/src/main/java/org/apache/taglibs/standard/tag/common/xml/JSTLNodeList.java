/*
 * Copyright (c) 2022 Eclipse Foundation and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.apache.taglibs.standard.tag.common.xml;

import java.util.Vector;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class JSTLNodeList extends Vector<Object> implements NodeList   {

    private static final long serialVersionUID = -1217630367839271134L;

    Vector<Object> nodeVector;

    JSTLNodeList(Vector<Object> nodeVector) {
        this.nodeVector = nodeVector;
    }


    JSTLNodeList(NodeList nl) {
        nodeVector = new Vector<>();
        // p("[JSTLNodeList] nodelist details");
        for (int i = 0; i < nl.getLength(); i++) {
            Node currNode = nl.item(i);
            // XPathUtil.printDetails ( currNode );
            nodeVector.add(i, currNode);
        }
    }


    JSTLNodeList(Node n) {
        nodeVector = new Vector<>();
        nodeVector.addElement(n);
    }


    JSTLNodeList(Object o) {
        nodeVector = new Vector<>();

        if (o instanceof NodeList) {
            NodeList nl = (NodeList) o;
            for (int i = 0; i < nl.getLength(); i++) {
                Node currNode = nl.item(i);
                // XPathUtil.printDetails ( currNode );
                nodeVector.add(i, currNode);
            }
        } else {
            nodeVector.addElement(o);
        }
    }


    @Override
    public Node item(int index) {
        return (Node) nodeVector.elementAt(index);
    }


    @Override
    public Object elementAt(int index) {
        return nodeVector.elementAt(index);
    }


    @Override
    public Object get(int index) {
        return nodeVector.get(index);
    }


    @Override
    public int getLength() {
        return nodeVector.size();
    }


    @Override
    public int size() {
        return nodeVector.size();
    }

    // Can implement other Vector methods to redirect those methods to
    // the vector in the variable param. As we are not using them as part
    // of this implementation we are not doing that here. If this changes
    // then we need to override those methods accordingly
}