/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.tag.common.core;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <p>A handler for the &lt;remove&gt; tag, which removes the variable
 * identified by 'var' (and 'scope', if present).
 *
 * @author Shawn Bayern
 */
public class RemoveTag extends TagSupport {

    //*********************************************************************
    // Constants

    /* We support these 'scopes'. */

    private final String APPLICATION = "application";
    private final String SESSION = "session";
    private final String REQUEST = "request";
    private final String PAGE = "page";

    //*********************************************************************
    // Internal state

    private int scope;					// tag attribute
    private boolean scopeSpecified;			// ... by tag attribute
    private String var;					// tag attribute


    //*********************************************************************
    // Construction and initialization

    /**
     * Constructs a new handler.  As with TagSupport, subclasses should
     * not provide other constructors and are expected to call the
     * superclass constructor.
     */
    public RemoveTag() {
        super();
        init();
    }

    // resets local state
    private void init() {
        var = null;
        scope = PageContext.PAGE_SCOPE;
        scopeSpecified = false;
    }

    // Releases any resources we may have (or inherit)
    public void release() {
        super.release();
        init();
    }


    //*********************************************************************
    // Tag logic

    // removes the variable (from a specific scope, if specified)
    public int doEndTag() throws JspException {
        if (!scopeSpecified)
            pageContext.removeAttribute(var);
        else
            pageContext.removeAttribute(var, scope);
	return EVAL_PAGE;
    }


    //*********************************************************************
    // Accessor methods

    // for tag attribute
    public void setVar(String var) {
	this.var = var;
    }

    // for tag attribute
    public void setScope(String scope) {
        this.scope = Util.getScope(scope);
	scopeSpecified = true;
    }
}
