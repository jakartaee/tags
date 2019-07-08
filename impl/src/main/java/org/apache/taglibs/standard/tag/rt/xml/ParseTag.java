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

import org.apache.taglibs.standard.tag.common.xml.ParseSupport;
import org.xml.sax.XMLFilter;

/**
 * <p>A handler for &lt;parse&gt; that supports rtexprvalue-based
 * attributes.</p>
 *
 * @author Shawn Bayern
 */

public class ParseTag extends ParseSupport {

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

    public void setSystemId(String systemId) throws JspTagException {
	this.systemId = systemId;
    }

    // for tag attribute
    public void setFilter(XMLFilter filter) throws JspTagException {
	this.filter = filter;
    }

}
