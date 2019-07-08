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

import java.util.TimeZone;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.tag.common.core.Util;

/**
 * Support for tag handlers for &lt;setTimeZone&gt;, the time zone setting tag
 * in JSTL 1.0.
 *
 * @author Jan Luehe
 */

public abstract class SetTimeZoneSupport extends TagSupport {

    
    //*********************************************************************
    // Protected state

    protected Object value;                      // 'value' attribute


    //*********************************************************************
    // Private state

    private int scope;                           // 'scope' attribute
    private String var;                          // 'var' attribute


    //*********************************************************************
    // Constructor and initialization

    public SetTimeZoneSupport() {
	super();
	init();
    }

    // resets local state
    private void init() {
	value = var = null;
	scope = PageContext.PAGE_SCOPE;
    }


   //*********************************************************************
    // Tag attributes known at translation time

    public void setScope(String scope) {
	this.scope = Util.getScope(scope);
    }

    public void setVar(String var) {
        this.var = var;
    }


    //*********************************************************************
    // Tag logic

    public int doEndTag() throws JspException {
	TimeZone timeZone = null;

	if (value == null) {
	    timeZone = TimeZone.getTimeZone("GMT");
	} else if (value instanceof String) {
	    if (((String) value).trim().equals("")) {
		timeZone = TimeZone.getTimeZone("GMT");
	    } else {
		timeZone = TimeZone.getTimeZone((String) value);
	    }
	} else {
	    timeZone = (TimeZone) value;
	}

	if (var != null) {
	    pageContext.setAttribute(var, timeZone, scope);
	} else {
	    Config.set(pageContext, Config.FMT_TIME_ZONE, timeZone, scope);
	}

	return EVAL_PAGE;
    }

    // Releases any resources we may have (or inherit)
    public void release() {
	init();
    }
}
