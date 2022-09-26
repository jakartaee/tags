/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation
 * Copyright (c) 1997-2018 Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFunctionException;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;

import org.apache.xml.dtm.DTM;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.jaxp.JAXPExtensionsProvider;
import org.apache.xpath.jaxp.JAXPPrefixResolver;
import org.apache.xpath.jaxp.JAXPVariableStack;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.res.XPATHErrorResources;
import org.apache.xpath.res.XPATHMessages;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * The JSTLXPathImpl class provides implementation for the methods defined  in
 * javax.xml.xpath.XPath interface. This provide simple access to the results
 * of an XPath expression.
 *
 * This class provides our own implementation of XPath, so that we can support
 * a generic Object type in returnType arguement for XPath's evaluate instance
 * method.
 *
 * Most of the implementation is exactly similar to what is already provided in
 * com.sun.org.apache.xpath.internal.jaxp.XPathImpl.java
 */
class JSTLXPathImpl implements javax.xml.xpath.XPath {

    private final XPathVariableResolver origVariableResolver;
    private final XPathFunctionResolver origFunctionResolver;

    private XPathVariableResolver variableResolver;
    private XPathFunctionResolver functionResolver;
    private NamespaceContext namespaceContext;
    private JAXPPrefixResolver prefixResolver;

    // By default Extension Functions are allowed in XPath Expressions. If
    // Secure Processing Feature is set on XPathFactory then the invocation of
    // extensions function need to throw XPathFunctionException
    private boolean featureSecureProcessing;

    JSTLXPathImpl(XPathVariableResolver vr, XPathFunctionResolver fr) {
        this.origVariableResolver = this.variableResolver = vr;
        this.origFunctionResolver = this.functionResolver = fr;
    }


    JSTLXPathImpl(XPathVariableResolver vr, XPathFunctionResolver fr, boolean featureSecureProcessing) {
        this.origVariableResolver = this.variableResolver = vr;
        this.origFunctionResolver = this.functionResolver = fr;
        this.featureSecureProcessing = featureSecureProcessing;
    }


    @Override
    public void reset() {
        this.variableResolver = this.origVariableResolver;
        this.functionResolver = this.origFunctionResolver;
        this.namespaceContext = null;
    }


    @Override
    public void setXPathVariableResolver(XPathVariableResolver resolver) {
        if (resolver == null) {
            String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                new Object[] {"XPathVariableResolver"});
            throw new NullPointerException(fmsg);
        }
        this.variableResolver = resolver;
    }


    @Override
    public XPathVariableResolver getXPathVariableResolver() {
        return variableResolver;
    }


    @Override
    public void setXPathFunctionResolver(XPathFunctionResolver resolver) {
        if (resolver == null) {
            String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                new Object[] {"XPathFunctionResolver"});
            throw new NullPointerException(fmsg);
        }
        this.functionResolver = resolver;
    }


    @Override
    public XPathFunctionResolver getXPathFunctionResolver() {
        return functionResolver;
    }


    @Override
    public void setNamespaceContext(NamespaceContext nsContext) {
        if (nsContext == null) {
            String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                new Object[] {"NamespaceContext"});
            throw new NullPointerException(fmsg);
        }
        this.namespaceContext = nsContext;
        this.prefixResolver = new JAXPPrefixResolver(nsContext);
    }


    @Override
    public NamespaceContext getNamespaceContext() {
        return namespaceContext;
    }


    @Override
    public XPathExpression compile(String expression)
        throws XPathExpressionException {
        // This is never used in JSTL
        if (expression == null) {
            String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                new Object[] {"XPath expression"});
            throw new NullPointerException(fmsg);
        }
        return null;
    }


    @Override
    public Object evaluate(String expression, Object item, QName returnType) throws XPathExpressionException {
        if (expression == null) {
            String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                new Object[] {"XPath expression"});
            throw new NullPointerException(fmsg);
        }
        if (returnType == null) {
            String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                new Object[] {"returnType"});
            throw new NullPointerException(fmsg);
        }
        // Checking if requested returnType is supported. returnType need to
        // be defined in XPathConstants or JSTLXPathConstants
        if (!isSupported(returnType)) {
            String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_UNSUPPORTED_RETURN_TYPE,
                new Object[] {returnType.toString()});
            throw new IllegalArgumentException(fmsg);
        }

        try {
            XObject resultObject = eval(expression, item);
            return getResultAsType(resultObject, returnType);
        } catch (NullPointerException npe) {
            // If VariableResolver returns null Or if we get
            // NullPointerException at this stage for some other reason
            // then we have to reurn XPathException
            throw new XPathExpressionException(npe);
        } catch (TransformerException te) {
            Throwable nestedException = te.getException();
            if (nestedException instanceof XPathFunctionException) {
                throw (XPathFunctionException) nestedException;
            }
            // For any other exceptions we need to throw
            // XPathExpressionException ( as per spec )
            throw new XPathExpressionException(te);
        }
    }


    @Override
    public String evaluate(String expression, Object item) throws XPathExpressionException {
        return (String) this.evaluate(expression, item, XPathConstants.STRING);
    }


    @Override
    public Object evaluate(String expression, InputSource source, QName returnType) throws XPathExpressionException {
        // Checking validity of different parameters
        if (source == null) {
            String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                new Object[] {"source"});
            throw new NullPointerException(fmsg);
        }
        if (expression == null) {
            String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                new Object[] {"XPath expression"});
            throw new NullPointerException(fmsg);
        }
        if (returnType == null) {
            String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                new Object[] {"returnType"});
            throw new NullPointerException(fmsg);
        }

        // Checking if requested returnType is supported.
        // returnType need to be defined in XPathConstants
        if (!isSupported(returnType)) {
            String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_UNSUPPORTED_RETURN_TYPE,
                new Object[] {returnType.toString()});
            throw new IllegalArgumentException(fmsg);
        }

        try {
            Document document = DocumentBuilderProvider.createDocumentBuilder().parse(source);
            XObject resultObject = eval(expression, document);
            return getResultAsType(resultObject, returnType);
        } catch (SAXException e) {
            throw new XPathExpressionException(e);
        } catch (IOException e) {
            throw new XPathExpressionException(e);
        } catch (TransformerException te) {
            Throwable nestedException = te.getException();
            if (nestedException instanceof XPathFunctionException) {
                throw (XPathFunctionException) nestedException;
            }
            throw new XPathExpressionException(te);
        }
    }


    @Override
    public String evaluate(String expression, InputSource source) throws XPathExpressionException {
        return (String) this.evaluate(expression, source, XPathConstants.STRING);
    }


    private XObject eval(String expression, Object contextItem) throws TransformerException {
        XPath xpath = new XPath(expression, null, prefixResolver, XPath.SELECT);
        final XPathContext xpathSupport;
        if (functionResolver != null) {
            JAXPExtensionsProvider jep = new JAXPExtensionsProvider(functionResolver, featureSecureProcessing);
            xpathSupport = new XPathContext(jep);
        } else {
            xpathSupport = new XPathContext();
        }

        xpathSupport.setVarStack(new JAXPVariableStack(variableResolver));

        // If item is null, then we will create a a Dummy contextNode
        if (contextItem instanceof Node) {
            return xpath.execute(xpathSupport, (Node) contextItem, prefixResolver);
        }
        return xpath.execute(xpathSupport, DTM.NULL, prefixResolver);
    }


    private boolean isSupported(QName returnType) {
        return returnType.equals(XPathConstants.STRING) || returnType.equals(XPathConstants.NUMBER)
            || returnType.equals(XPathConstants.BOOLEAN) || returnType.equals(XPathConstants.NODE)
            || returnType.equals(XPathConstants.NODESET) || returnType.equals(JSTLXPathConstants.OBJECT);
    }


    private Object getResultAsType(XObject resultObject, QName returnType) throws TransformerException {
        if (returnType.equals(XPathConstants.STRING)) {
            return resultObject.str();
        }
        if (returnType.equals(XPathConstants.NUMBER)) {
            return Double.valueOf(resultObject.num());
        }
        if (returnType.equals(XPathConstants.BOOLEAN)) {
            return Boolean.valueOf(resultObject.bool());
        }
        // XPathConstants.NODESET ---ORdered, UNOrdered???
        if (returnType.equals(XPathConstants.NODESET)) {
            return resultObject.nodelist();
        }
        if (returnType.equals(XPathConstants.NODE)) {
            NodeIterator ni = resultObject.nodeset();
            // Return the first node, or null
            return ni.nextNode();
        }
        if (returnType.equals(JSTLXPathConstants.OBJECT)) {
            if (resultObject instanceof XNodeSet) {
                return resultObject.nodelist();
            }
            return resultObject.object();
        }
        String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_UNSUPPORTED_RETURN_TYPE,
            new Object[] {returnType.toString()});
        throw new IllegalArgumentException(fmsg);
    }
}
