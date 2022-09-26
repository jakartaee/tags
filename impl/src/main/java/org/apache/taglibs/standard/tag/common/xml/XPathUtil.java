/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation
 * Copyright (c) 1997-2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2020 Payara Services Ltd.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.taglibs.standard.tag.common.xml;

import java.util.List;

import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.Tag;
import jakarta.servlet.jsp.tagext.TagSupport;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathVariableResolver;
import org.apache.taglibs.standard.resources.Resources;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Support for tag handlers that evaluate XPath expressions.
 *
 * @author Shawn Bayern
 * @author Ramesh Mandava ( ramesh.mandava@sun.com )
 * @author Pierre Delisle ( pierre.delisle@sun.com )
 * @author Dongbin Nie
 * @author David Matejcek
 */
public class XPathUtil {

    private static final String PAGE_NS_URL = "http://java.sun.com/jstl/xpath/page";
    private static final String REQUEST_NS_URL = "http://java.sun.com/jstl/xpath/request";
    private static final String SESSION_NS_URL = "http://java.sun.com/jstl/xpath/session";
    private static final String APP_NS_URL = "http://java.sun.com/jstl/xpath/app";
    private static final String PARAM_NS_URL = "http://java.sun.com/jstl/xpath/param";
    private static final String INITPARAM_NS_URL = "http://java.sun.com/jstl/xpath/initParam";
    private static final String COOKIE_NS_URL = "http://java.sun.com/jstl/xpath/cookie";
    private static final String HEADER_NS_URL = "http://java.sun.com/jstl/xpath/header";

    private static final XPathFactory XPATH_FACTORY = new JSTLXPathFactory();
    private static final JSTLXPathNamespaceContext JSTL_XPATH_NS_CTX = initXPathNamespaceContext();

    private final PageContext pageContext;

    /**
     * Constructs a new XPathUtil object associated with the given
     * PageContext.
     */
    public XPathUtil(PageContext pc) {
        pageContext = pc;
    }


    /**
     * Evaluate an XPath expression to a String value.
     */
    public String valueOf(Node contextNode, String xpathString) throws JspTagException {
        XPath xpath = XPATH_FACTORY.newXPath();
        xpath.setNamespaceContext(JSTL_XPATH_NS_CTX);
        xpath.setXPathVariableResolver(new JSTLXPathVariableResolver(pageContext));
        try {
            return xpath.evaluate(xpathString, contextNode);
        } catch (XPathExpressionException ex) {
            throw new JspTagException(ex.toString(), ex);
        }
    }


    /**
     * Evaluate an XPath expression to a boolean value.
     */
    public boolean booleanValueOf(Node contextNode, String xpathString) throws JspTagException {
        XPath xpath = XPATH_FACTORY.newXPath();
        xpath.setNamespaceContext(JSTL_XPATH_NS_CTX);
        xpath.setXPathVariableResolver(new JSTLXPathVariableResolver(pageContext));
        try {
            return ((Boolean) xpath.evaluate(xpathString, contextNode, XPathConstants.BOOLEAN)).booleanValue();
        } catch (XPathExpressionException ex) {
            throw new JspTagException(Resources.getMessage("XPATH_ERROR_XOBJECT", ex.toString()), ex);
        }
    }


    /**
     * Evaluate an XPath expression to a List of nodes.
     */
    public List<Object> selectNodes(Node contextNode, String xpathString) throws JspTagException {
        try {
            XPath xpath = XPATH_FACTORY.newXPath();
            xpath.setNamespaceContext(JSTL_XPATH_NS_CTX);
            xpath.setXPathVariableResolver(new JSTLXPathVariableResolver(pageContext));
            Object nl = xpath.evaluate(xpathString, contextNode, JSTLXPathConstants.OBJECT);
            return new JSTLNodeList(nl);
        } catch (XPathExpressionException ex) {
            throw new JspTagException(ex.toString(), ex);
        }
    }


    /**
     * Evaluate an XPath expression to a single node.
     */
    public Node selectSingleNode(Node contextNode, String xpathString) throws JspTagException {
        XPathVariableResolver jxvr = new JSTLXPathVariableResolver(pageContext);
        try {
            XPath xpath = XPATH_FACTORY.newXPath();
            xpath.setNamespaceContext(JSTL_XPATH_NS_CTX);
            xpath.setXPathVariableResolver(jxvr);
            return (Node) xpath.evaluate(xpathString, contextNode, XPathConstants.NODE);
        } catch (XPathExpressionException ex) {
            throw new JspTagException(ex.toString(), ex);
        }
    }


    public static Node getContext(Tag t) throws JspTagException {
        ForEachTag xt = (ForEachTag) TagSupport.findAncestorWithClass(t, ForEachTag.class);
        if (xt == null) {
            return newEmptyDocument();
        }
        return xt.getContext();
    }


    /**
     * Initialize globally useful data.
     */
    private static JSTLXPathNamespaceContext initXPathNamespaceContext() {
        // register supported namespaces
        JSTLXPathNamespaceContext context = new JSTLXPathNamespaceContext();
        context.addNamespace("pageScope", PAGE_NS_URL);
        context.addNamespace("requestScope", REQUEST_NS_URL);
        context.addNamespace("sessionScope", SESSION_NS_URL);
        context.addNamespace("applicationScope", APP_NS_URL);
        context.addNamespace("param", PARAM_NS_URL);
        context.addNamespace("initParam", INITPARAM_NS_URL);
        context.addNamespace("header", HEADER_NS_URL);
        context.addNamespace("cookie", COOKIE_NS_URL);
        return context;
    }

    /**
     * Create a new empty document.
     *
     * This method always allocates a new document as its root node might be
     * exposed to other tags and potentially be mutated.
     *
     * @return a new empty document
     */
    private static Document newEmptyDocument() {
        return DocumentBuilderProvider.createDocumentBuilder().newDocument();
    }
}




