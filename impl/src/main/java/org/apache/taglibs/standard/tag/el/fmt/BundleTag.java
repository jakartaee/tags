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

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;

/**
 * <p>A handler for &lt;bundle&gt; that accepts attributes as Strings
 * and evaluates them as expressions at runtime.</p>
 *
 * @author Shawn Bayern
 * @author Jan Luehe
 */

public class BundleTag extends BundleSupport {

    //*********************************************************************
    // 'Private' state (implementation details)

    private String basename_;                    // stores EL-based property
    private String prefix_;		         // stores EL-based property


    //*********************************************************************
    // Constructor

    /**
     * Constructs a new BundleTag.  As with TagSupport, subclasses
     * should not provide other constructors and are expected to call
     * the superclass constructor
     */
    public BundleTag() {
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
    public void setBasename(String basename_) {
        this.basename_ = basename_;
    }

    // for EL-based attribute
    public void setPrefix(String prefix_) {
        this.prefix_ = prefix_;
    }


    //*********************************************************************
    // Private (utility) methods

    // (re)initializes state (during release() or construction)
    private void init() {
        // null implies "no expression"
	basename_ = prefix_ = null;
    }

    // Evaluates expressions as necessary
    private void evaluateExpressions() throws JspException {

	// 'basename' attribute (mandatory)
	basename = (String) ExpressionEvaluatorManager.evaluate(
	    "basename", basename_, String.class, this, pageContext);

	// 'prefix' attribute (optional)
	if (prefix_ != null) {
	    prefix = (String) ExpressionEvaluatorManager.evaluate(
	        "prefix", prefix_, String.class, this, pageContext);
	}
    }
}
