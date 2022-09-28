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

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;

import net.sf.saxon.xpath.XPathEvaluator;
import net.sf.saxon.xpath.XPathFactoryImpl;

public class JSTLSaxonXPathFactory extends XPathFactoryImpl{

    private XPathVariableResolver variableResolver;

    private XPathFunctionResolver functionResolver;

    @Override
    public void setXPathVariableResolver(XPathVariableResolver xPathVariableResolver) {
        this.variableResolver = xPathVariableResolver;
    }

    @Override
    public void setXPathFunctionResolver(XPathFunctionResolver xPathFunctionResolver) {
        this.functionResolver = xPathFunctionResolver;
    }

    @Override
    public XPath newXPath() {
        XPathEvaluator xpath = new JSTLSaxonXPathImpl(getConfiguration());
        xpath.setXPathFunctionResolver(functionResolver);
        xpath.setXPathVariableResolver(variableResolver);
        return xpath;
    }


}
