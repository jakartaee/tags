/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.tag.el.core;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.core.NullAttributeException;
import org.apache.taglibs.standard.tag.common.core.WhenTagSupport;

/**
 * <p>Tag handler for &lt;when&gt; in JSTL's expression-evaluating
 * library.</p>
 *
 * @author Shawn Bayern
 */

public class WhenTag extends WhenTagSupport {

    //*********************************************************************
    // Constructor and lifecycle management

    // initialize inherited and local state
    public WhenTag() {
        super();
        init();
    }

    // Releases any resources we may have (or inherit)
    public void release() {
        super.release();
        init();
    }


    //*********************************************************************
    // Supplied conditional logic

    protected boolean condition() throws JspTagException {
        try { 
            Object r = ExpressionEvaluatorManager.evaluate(
                "test", test, Boolean.class, this, pageContext);
            if (r == null)
	        throw new NullAttributeException("when", "test");
            else
                return (((Boolean) r).booleanValue());
	} catch (JspException ex) {
	    throw new JspTagException(ex.toString(), ex);
	}
    }


    //*********************************************************************
    // Private state

    private String test;               // the value of the 'test' attribute


    //*********************************************************************
    // Accessors

    // receives the tag's 'test' attribute
    public void setTest(String test) {
        this.test = test;
    }


    //*********************************************************************
    // Private utility methods

    // resets internal state
    private void init() {
        test = null;
    }
}
