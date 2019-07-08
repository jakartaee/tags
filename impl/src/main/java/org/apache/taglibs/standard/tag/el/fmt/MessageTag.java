/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.tag.el.fmt;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.fmt.MessageSupport;

/**
 * <p>A handler for &lt;message&gt; that accepts attributes as Strings
 * and evaluates them as expressions at runtime.</p>
 *
 * @author Jan Luehe
 */

public class MessageTag extends MessageSupport {

    //*********************************************************************
    // Private state (implementation details)

    private String key_;                         // stores EL-based property
    private String bundle_;		         // stores EL-based property


    //*********************************************************************
    // Constructor

    /**
     * Constructs a new MessageTag.  As with TagSupport, subclasses
     * should not provide other constructors and are expected to call
     * the superclass constructor
     */
    public MessageTag() {
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

    // for EL-based attribute
    public void setKey(String key_) {
        this.key_ = key_;
	this.keySpecified = true;
    }

    // for EL-based attribute
    public void setBundle(String bundle_) {
        this.bundle_ = bundle_;
        this.bundleSpecified = true;
    }


    //*********************************************************************
    // Private (utility) methods

    // (re)initializes state (during release() or construction)
    private void init() {
        // null implies "no expression"
	key_ = bundle_ = null;
    }

    // Evaluates expressions as necessary
    private void evaluateExpressions() throws JspException {
        /* 
         * Note: we don't check for type mismatches here; we assume
         * the expression evaluator will return the expected type
         * (by virtue of knowledge we give it about what that type is).
         * A ClassCastException here is truly unexpected, so we let it
         * propagate up.
         */

	if (keySpecified) {
	    keyAttrValue = (String) ExpressionEvaluatorManager.evaluate(
	        "key", key_, String.class, this, pageContext);
	}

	if (bundleSpecified) {
	    bundleAttrValue = (LocalizationContext)
		ExpressionEvaluatorManager.evaluate(
	            "bundle", bundle_, LocalizationContext.class, this,
		    pageContext);
	} 
    }
}
