/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.tag.rt.core;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.LoopTag;
import javax.servlet.jsp.tagext.IterationTag;

import org.apache.taglibs.standard.tag.common.core.ForTokensSupport;

/**
 * <p>A handler for &lt;forTokens&gt; that supports rtexprvalue-based
 * attributes.</p>
 *
 * @author Shawn Bayern
 */

public class ForTokensTag
    extends ForTokensSupport
    implements LoopTag, IterationTag
{

    //*********************************************************************
    // Accessor methods

    // for tag attribute
    public void setBegin(int begin) throws JspTagException {
        this.beginSpecified = true;
        this.begin = begin;
        validateBegin();
    }

    // for tag attribute
    public void setEnd(int end) throws JspTagException {
        this.endSpecified = true;
        this.end = end;
        validateEnd();
    }

    // for tag attribute
    public void setStep(int step) throws JspTagException {
        this.stepSpecified = true;
        this.step = step;
        validateStep();
    }

    // stores the 'items' String we're passed
    public void setItems(Object s) throws JspTagException {
        items = s;
	// use the empty string to indicate "no iteration"
        if (s == null)
	    items = "";
    }

    // stores the 'delims' String we're passed
    public void setDelims(String s) throws JspTagException {
        delims = s;
	// use the empty string to cause monolithic tokenization
        if (s == null)
	    delims = "";
    }

}
