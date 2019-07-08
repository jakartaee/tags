/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.extra.spath;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <p>Tag handler that exposes SPath functionality.</p>
 *
 * @author Shawn Bayern
 */

public class SPathTag extends TagSupport {

    //*********************************************************************
    // Internal state

    private String select;                       // tag attribute
    private String var;				 // tag attribute

    //*********************************************************************
    // Construction and initialization

    /**
     * Constructs a new handler.  As with TagSupport, subclasses should
     * not provide other constructors and are expected to call the
     * superclass constructor.
     */
    public SPathTag() {
        super();
        init();
    }

    // resets local state
    private void init() {
	select = var = null;
    }


    //*********************************************************************
    // Tag logic

    // applies XPath expression from 'select' and exposes a filter as 'var'
    public int doStartTag() throws JspException {
      try {
	SPathFilter s = new SPathFilter(new SPathParser(select).expression());
	pageContext.setAttribute(var, s);
	return SKIP_BODY;
      } catch (ParseException ex) {
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

    public void setVar(String var) {
	this.var = var;
    }
}
