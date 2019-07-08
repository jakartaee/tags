/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.tag.common.fmt;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.tag.common.core.Util;

/**
 * Support for tag handlers for &lt;setBundle&gt;, the JSTL 1.0 tag that loads
 * a resource bundle and stores it in a scoped variable.
 *
 * @author Jan Luehe
 */

public abstract class SetBundleSupport extends TagSupport {

    
    //*********************************************************************
    // Protected state

    protected String basename;                  // 'basename' attribute


    //*********************************************************************
    // Private state

    private int scope;                          // 'scope' attribute
    private String var;                         // 'var' attribute


    //*********************************************************************
    // Constructor and initialization

    public SetBundleSupport() {
	super();
	init();
    }

    private void init() {
	basename = null;
	scope = PageContext.PAGE_SCOPE;
    }


    //*********************************************************************
    // Tag attributes known at translation time

    public void setVar(String var) {
        this.var = var;
    }

    public void setScope(String scope) {
	this.scope = Util.getScope(scope);
    }


    //*********************************************************************
    // Tag logic

    public int doEndTag() throws JspException {
	LocalizationContext locCtxt =
	    BundleSupport.getLocalizationContext(pageContext, basename);

	if (var != null) {
	    pageContext.setAttribute(var, locCtxt, scope);
	} else {
	    Config.set(pageContext, Config.FMT_LOCALIZATION_CONTEXT, locCtxt,
		       scope);
	}

	return EVAL_PAGE;
    }

    // Releases any resources we may have (or inherit)
    public void release() {
	init();
    }
}
