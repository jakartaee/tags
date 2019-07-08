/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.tag.rt.xml;

import javax.servlet.jsp.JspTagException;
import javax.xml.transform.Result;

import org.apache.taglibs.standard.tag.common.xml.TransformSupport;

/**
 * <p>A handler for &lt;transform&gt; that supports rtexprvalue-based
 * attributes.</p>
 *
 * @author Shawn Bayern
 */

public class TransformTag extends TransformSupport {

    //*********************************************************************
    // Accessor methods

    // Deprecated as of JSTL 1.1
    // for tag attribute
    public void setXml(Object xml) throws JspTagException {
        this.xml = xml;
    }

    // 'doc' replaces 'xml' as of JSTL 1.1
    public void setDoc(Object xml) throws JspTagException {
        this.xml = xml;
    }

    // Deprecated as of JSTL 1.1
    // for tag attribute
    public void setXmlSystemId(String xmlSystemId) throws JspTagException {
        this.xmlSystemId = xmlSystemId;
    }

    // 'docSystemId' replaces 'xmlSystemId' as of JSTL 1.1
    public void setDocSystemId(String xmlSystemId) throws JspTagException {
        this.xmlSystemId = xmlSystemId;
    }

    // for tag attribute
    public void setXslt(Object xslt) throws JspTagException {
        this.xslt = xslt;
    }

    // for tag attribute
    public void setXsltSystemId(String xsltSystemId) throws JspTagException {
        this.xsltSystemId = xsltSystemId;
    }

    // for tag attribute
    public void setResult(Result result) throws JspTagException {
        this.result = result;
    }

}
