/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.tag.common.xml;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <p>Tag handler for &lt;expr&gt; in JSTL's XML library.</p>
 *
 * @author Shawn Bayern
 */

public abstract class ExprSupport extends TagSupport {

    //*********************************************************************
    // Internal state

    private String select;                       // tag attribute
    protected boolean escapeXml;		 // tag attribute

    //*********************************************************************
    // Construction and initialization

    /**
     * Constructs a new handler.  As with TagSupport, subclasses should
     * not provide other constructors and are expected to call the
     * superclass constructor.
     */
    public ExprSupport() {
        super();
        init();
    }

    // resets local state
    private void init() {
	select = null;
        escapeXml = true;
    }


    //*********************************************************************
    // Tag logic

    // applies XPath expression from 'select' and prints the result
    public int doStartTag() throws JspException {
        try {
	    XPathUtil xu = new XPathUtil(pageContext);
	    String result = xu.valueOf(XPathUtil.getContext(this), select);
	    org.apache.taglibs.standard.tag.common.core.OutSupport.out(
              pageContext, escapeXml, result);
	    return SKIP_BODY;
        } catch (java.io.IOException ex) {
	    throw new JspTagException(ex.toString(), ex);
        }
    }

    // Releases any resources we may have (or inherit)
    public void release() {
        super.release();
        init();
    }


    //*********************************************************************
    // Attribute accessors

    public void setSelect(String select) {
	this.select = select;
    }
}
