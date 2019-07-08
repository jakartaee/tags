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

import org.apache.taglibs.standard.tag.common.core.NullAttributeException;
import org.apache.taglibs.standard.tag.common.core.SetSupport;

/**
 * <p>A handler for &lt;set&gt;, which redirects the browser to a
 * new URL.
 *
 * @author Shawn Bayern
 */

public class SetTag extends SetSupport {

    //*********************************************************************
    // 'Private' state (implementation details)

    private String value_;			// stores EL-based property
    private String target_;			// stores EL-based property
    private String property_;			// stores EL-based property


    //*********************************************************************
    // Constructor

    public SetTag() {
        super();
        init();
    }


    //*********************************************************************
    // Tag logic

    // evaluates expression and chains to parent
    public int doStartTag() throws JspException {

        // evaluate any expressions we were passed, once per invocation
        evaluateExpressions();

	// chain to the parent implementation
	return super.doStartTag();
    }


    // Releases any resources we may have (or inherit)
    public void release() {
        super.release();
        init();
    }


    //*********************************************************************
    // Accessor methods

    public void setValue(String value_) {
        this.value_ = value_;
	this.valueSpecified = true;
    }

    public void setTarget(String target_) {
        this.target_ = target_;
    }

    public void setProperty(String property_) {
        this.property_ = property_;
    }


    //*********************************************************************
    // Private (utility) methods

    // (re)initializes state (during release() or construction)
    private void init() {
        // null implies "no expression"
	value_ = target_ = property_ = null;
    }

    /* Evaluates expressions as necessary */
    private void evaluateExpressions() throws JspException {
        /* 
         * Note: we don't check for type mismatches here; we assume
         * the expression evaluator will return the expected type
         * (by virtue of knowledge we give it about what that type is).
         * A ClassCastException here is truly unexpected, so we let it
         * propagate up.
         */

	// 'value'
	try {
	    value = ExpressionUtil.evalNotNull(
	        "set", "value", value_, Object.class, this, pageContext);
	} catch (NullAttributeException ex) {
	    // explicitly let 'value' be null
	    value = null;
	}

	// 'target'
	target = ExpressionUtil.evalNotNull(
	    "set", "target", target_, Object.class, this, pageContext);

	// 'property'
	try {
	    property = (String) ExpressionUtil.evalNotNull(
	         "set", "property", property_, String.class, this, pageContext);
        } catch (NullAttributeException ex) {
            // explicitly let 'property' be null
            property = null;
        }
    }
}
