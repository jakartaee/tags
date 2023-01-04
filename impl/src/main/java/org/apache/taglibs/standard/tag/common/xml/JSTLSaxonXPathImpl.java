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
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import net.sf.saxon.Configuration;
import net.sf.saxon.expr.parser.ContextItemStaticInfo;
import net.sf.saxon.expr.parser.ExpressionTool;
import net.sf.saxon.expr.parser.ExpressionVisitor;
import net.sf.saxon.expr.Expression;
import net.sf.saxon.expr.StaticContext;
import net.sf.saxon.expr.instruct.Executable;
import net.sf.saxon.expr.instruct.SlotManager;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.type.Type;
import net.sf.saxon.xpath.XPathEvaluator;

public class JSTLSaxonXPathImpl extends XPathEvaluator {

    public JSTLSaxonXPathImpl(Configuration config) {
        super(config);
    }

    public XPathExpression compile(String expr) throws XPathExpressionException {
        if (expr == null)
          throw new NullPointerException("expr");  
        try {
          Executable exec = new Executable(getConfiguration());
          exec.setSchemaAware(getStaticContext().getPackageData().isSchemaAware());
          Expression exp = ExpressionTool.make(expr, (StaticContext)getStaticContext(), 0, -1, null);
          ExpressionVisitor visitor = ExpressionVisitor.make((StaticContext)getStaticContext());
          ContextItemStaticInfo contextItemType = getConfiguration().makeContextItemStaticInfo(Type.ITEM_TYPE, true);
          exp = exp.typeCheck(visitor, contextItemType).optimize(visitor, contextItemType);
          SlotManager map = getStaticContext().getConfiguration().makeSlotManager();
          ExpressionTool.allocateSlots(exp, 0, map);
          JSTLSaxonXPathExpressionImpl xpe = new JSTLSaxonXPathExpressionImpl(exp, exec);
          xpe.setStackFrameMap(map);
          return xpe;
        } catch (XPathException e) {
          throw new XPathExpressionException(e);
        } 
      }

    public Object evaluate(String expr, Object node, QName qName) throws XPathExpressionException {
        JSTLSaxonXPathExpressionImpl exp = (JSTLSaxonXPathExpressionImpl)compile(expr);
        return exp.evaluate(node, qName);
    }

}
