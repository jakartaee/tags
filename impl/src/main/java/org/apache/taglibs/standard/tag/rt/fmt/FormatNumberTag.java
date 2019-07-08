/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.tag.rt.fmt;

import javax.servlet.jsp.JspTagException;

import org.apache.taglibs.standard.tag.common.fmt.FormatNumberSupport;

/**
 * <p>A handler for &lt;formatNumber&gt; that supports rtexprvalue-based
 * attributes.</p>
 *
 * @author Jan Luehe
 */

public class FormatNumberTag extends FormatNumberSupport {

    //*********************************************************************
    // Accessor methods

    // 'value' attribute
    public void setValue(Object value) throws JspTagException {
        this.value = value;
	this.valueSpecified = true;
    }

    // 'type' attribute
    public void setType(String type) throws JspTagException {
        this.type = type;
    }

    // 'pattern' attribute
    public void setPattern(String pattern) throws JspTagException {
        this.pattern = pattern;
    }

    // 'currencyCode' attribute
    public void setCurrencyCode(String currencyCode) throws JspTagException {
        this.currencyCode = currencyCode;
    }

    // 'currencySymbol' attribute
    public void setCurrencySymbol(String currencySymbol)
	throws JspTagException {
        this.currencySymbol = currencySymbol;
    }

    // 'groupingUsed' attribute
    public void setGroupingUsed(boolean isGroupingUsed)
	throws JspTagException {
        this.isGroupingUsed = isGroupingUsed;
	this.groupingUsedSpecified = true;
    }

    // 'maxIntegerDigits' attribute
    public void setMaxIntegerDigits(int maxDigits) throws JspTagException {
        this.maxIntegerDigits = maxDigits;
	this.maxIntegerDigitsSpecified = true;
    }

    // 'minIntegerDigits' attribute
    public void setMinIntegerDigits(int minDigits) throws JspTagException {
        this.minIntegerDigits = minDigits;
	this.minIntegerDigitsSpecified = true;
    }

    // 'maxFractionDigits' attribute
    public void setMaxFractionDigits(int maxDigits) throws JspTagException {
        this.maxFractionDigits = maxDigits;
	this.maxFractionDigitsSpecified = true;
    }

    // 'minFractionDigits' attribute
    public void setMinFractionDigits(int minDigits) throws JspTagException {
        this.minFractionDigits = minDigits;
	this.minFractionDigitsSpecified = true;
    }
}
