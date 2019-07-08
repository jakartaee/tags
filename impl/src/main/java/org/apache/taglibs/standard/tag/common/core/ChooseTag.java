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
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.resources.Resources;

/**
 * <p>Tag handler for &lt;choose&gt; in JSTL.</p>
 * 
 * <p>&lt;choose&gt; is a very simple tag that acts primarily as a container;
 * it always includes its body and allows exactly one of its child
 * &lt;when&gt; tags to run.  Since this tag handler doesn't have any
 * attributes, it is common.core to both the rtexprvalue and expression-
 * evaluating versions of the JSTL library.
 *
 * @author Shawn Bayern
 */

public class ChooseTag extends TagSupport {

    //*********************************************************************
    // Constructor and lifecycle management

    // initialize inherited and local state
    public ChooseTag() {
        super();
        init();
    }

    // Releases any resources we may have (or inherit)
    public void release() {
        super.release();
        init();
    }


    //*********************************************************************
    // Private state

    private boolean subtagGateClosed;      // has one subtag already executed?


    //*********************************************************************
    // Public methods implementing exclusivity checks

    /**
     * Returns status indicating whether a subtag should run or not.
     *
     * @return <tt>true</tt> if the subtag should evaluate its condition
     *         and decide whether to run, <tt>false</tt> otherwise.
     */
    public synchronized boolean gainPermission() {
        return (!subtagGateClosed);
    }

    /**
     * Called by a subtag to indicate that it plans to evaluate its
     * body.
     */
    public synchronized void subtagSucceeded() {
        if (subtagGateClosed)
            throw new IllegalStateException(
		Resources.getMessage("CHOOSE_EXCLUSIVITY"));
        subtagGateClosed = true;
    }


    //*********************************************************************
    // Tag logic

    // always include body
    public int doStartTag() throws JspException {
        subtagGateClosed = false;	// when we start, no children have run
        return EVAL_BODY_INCLUDE;
    }


    //*********************************************************************
    // Private utility methods

    private void init() {
        subtagGateClosed = false;                          // reset flag
    }
}
