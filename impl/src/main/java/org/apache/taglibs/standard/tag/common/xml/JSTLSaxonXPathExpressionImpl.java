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

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import net.sf.saxon.expr.Atomizer;
import net.sf.saxon.expr.EarlyEvaluationContext;
import net.sf.saxon.expr.Expression;
import net.sf.saxon.expr.JPConverter;
import net.sf.saxon.expr.PJConverter;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.expr.XPathContextMajor;
import net.sf.saxon.expr.instruct.Executable;
import net.sf.saxon.expr.instruct.SlotManager;
import net.sf.saxon.functions.Number_1;
import net.sf.saxon.om.GroundedValue;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.om.SequenceTool;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.tree.wrapper.VirtualNode;
import net.sf.saxon.type.AtomicType;
import net.sf.saxon.value.AtomicValue;
import net.sf.saxon.value.DoubleValue;
import net.sf.saxon.value.NumericValue;
import net.sf.saxon.xpath.XPathExpressionImpl;

public class JSTLSaxonXPathExpressionImpl extends XPathExpressionImpl {

    private final Executable executable;

    private Expression atomizer;

    protected JSTLSaxonXPathExpressionImpl(Expression exp, Executable exec) {
        super(exp, exec);
        // TODO Auto-generated constructor stub
        this.executable = exec;
    }

    @Override
    protected void setStackFrameMap(SlotManager map) {
        // TODO Auto-generated method stub
        super.setStackFrameMap(map);
    }

    public Object evaluate(Object node, QName name) throws XPathExpressionException {
        Item contextItem;
        GroundedValue val;
        JPConverter converter = JPConverter.allocate(node.getClass(), null, getConfiguration());
        try {
            val = converter.convert(node, (XPathContext) new EarlyEvaluationContext(getConfiguration()));
        } catch (XPathException e) {
            throw new XPathExpressionException("Failure converting a node of class " + node
                    .getClass().getName() + ": "
                    + e
                            .getMessage());
        }
        if (val.getLength() == 0) {
            contextItem = null;
        } else {
            if (val.getLength() > 1)
                throw new XPathExpressionException("Supplied context item is a sequence of " + val
                        .getLength() + " items");
            Item item = val.head();
            if (item instanceof NodeInfo) {
                if (!((NodeInfo) item).getConfiguration().isCompatible(getConfiguration()))
                    throw new XPathExpressionException(
                            "Supplied node must be built using the same or a compatible Configuration");
                if (((NodeInfo) item).getTreeInfo().isTyped() && !this.executable.isSchemaAware())
                    throw new XPathExpressionException(
                            "The expression was compiled to handled untyped data, but the input is typed");
            }
            contextItem = item;
        }
        XPathContextMajor context = new XPathContextMajor(contextItem, this.executable);
        context.openStackFrame(getStackFrameMap());
        QName qName = name;
        if(qName.equals(JSTLXPathConstants.OBJECT)){
            qName = getInternalExpression().getItemType().isAtomicType() ? 
                ((AtomicType)getInternalExpression().getItemType()).getPrimitiveAtomicType().isNumericType() ? XPathConstants.NUMBER :
                JSTLXPathConstants.LOCAL_TO_XPATH_CONSTANT(((AtomicType)getInternalExpression().getItemType()).getStructuredQName().toJaxpQName().getLocalPart()) : 
                XPathConstants.NODESET;
        }
        try {
            if (qName.equals(XPathConstants.BOOLEAN))
                return Boolean.valueOf(getInternalExpression().effectiveBooleanValue((XPathContext) context));
            if (qName.equals(XPathConstants.STRING)) {
                SequenceIterator iter = getInternalExpression().iterate((XPathContext) context);
                Item first = iter.next();
                if (first == null)
                    return "";
                return first.getStringValue();
            }
            if (qName.equals(XPathConstants.NUMBER)) {
                if (this.atomizer == null)
                    this.atomizer = Atomizer.makeAtomizer(getInternalExpression(), null);
                SequenceIterator iter = this.atomizer.iterate((XPathContext) context);
                Item first = iter.next();
                if (first == null)
                    return Double.valueOf(Double.NaN);
                if (first instanceof NumericValue)
                    return Double.valueOf(((NumericValue) first).getDoubleValue());
                DoubleValue v = Number_1.convert((AtomicValue) first, getConfiguration());
                return Double.valueOf(v.getDoubleValue());
            }
            if (qName.equals(XPathConstants.NODE)) {
                SequenceIterator iter = getInternalExpression().iterate((XPathContext) context);
                Item first = iter.next();
                if (first instanceof VirtualNode)
                    return ((VirtualNode) first).getRealNode();
                if (first == null || first instanceof NodeInfo)
                    return first;
                throw new XPathExpressionException("Expression result is not a node");
            }
            if (qName.equals(XPathConstants.NODESET)) {
                context.openStackFrame(getStackFrameMap());
                SequenceIterator iter = getInternalExpression().iterate((XPathContext) context);
                GroundedValue extent = SequenceTool.toGroundedValue(iter);
                PJConverter pj = PJConverter.allocateNodeListCreator(getConfiguration(), node);
                return pj.convert((Sequence) extent, Object.class, (XPathContext) context);
            }
            throw new IllegalArgumentException("qName: Unknown type for expected result");
        } catch (XPathException e) {
            throw new XPathExpressionException(e);
        }
    }

}