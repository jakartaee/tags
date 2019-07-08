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
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.taglibs.standard.resources.Resources;

/**
 * Support for tag handlers for &lt;param&gt;, the message argument
 * subtag in JSTL 1.0 which supplies an argument for parametric replacement
 * to its parent &lt;message&gt; tag.
 *
 * @see MessageSupport
 * @author Jan Luehe
 */

public abstract class ParamSupport extends BodyTagSupport {

    //*********************************************************************
    // Protected state

    protected Object value;                          // 'value' attribute
    protected boolean valueSpecified;	             // status


    //*********************************************************************
    // Constructor and initialization

    public ParamSupport() {
	super();
	init();
    }

    private void init() {
	value = null;
	valueSpecified = false;
    }


    //*********************************************************************
    // Tag logic

    // Supply our value to our parent <fmt:message> tag
    public int doEndTag() throws JspException {
	Tag t = findAncestorWithClass(this, MessageSupport.class);
	if (t == null) {
	    throw new JspTagException(Resources.getMessage(
                            "PARAM_OUTSIDE_MESSAGE"));
	}
	MessageSupport parent = (MessageSupport) t;

	/*
	 * Get argument from 'value' attribute or body, as appropriate, and
	 * add it to enclosing <fmt:message> tag, even if it is null or equal
	 * to "".
	 */
	Object input = null;
        // determine the input by...
	if (valueSpecified) {
	    // ... reading 'value' attribute
	    input = value;
	} else {
	    // ... retrieving and trimming our body (TLV has ensured that it's
	    // non-empty)
	    input = bodyContent.getString().trim();
	}
	parent.addParam(input);

	return EVAL_PAGE;
    }

    // Releases any resources we may have (or inherit)
    public void release() {
	init();
    }
}
