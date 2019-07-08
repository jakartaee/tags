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

import java.util.Date;

import javax.servlet.jsp.JspTagException;

import org.apache.taglibs.standard.tag.common.fmt.FormatDateSupport;

/**
 * <p>A handler for &lt;formatDate&gt; that supports rtexprvalue-based
 * attributes.</p>
 *
 * @author Jan Luehe
 */

public class FormatDateTag extends FormatDateSupport {

    //*********************************************************************
    // Accessor methods

    // 'value' attribute
    public void setValue(Date value) throws JspTagException {
        this.value = value;
    }

    // 'type' attribute
    public void setType(String type) throws JspTagException {
        this.type = type;
    }

    // 'dateStyle' attribute
    public void setDateStyle(String dateStyle) throws JspTagException {
        this.dateStyle = dateStyle;
    }

    // 'timeStyle' attribute
    public void setTimeStyle(String timeStyle) throws JspTagException {
        this.timeStyle = timeStyle;
    }

    // 'pattern' attribute
    public void setPattern(String pattern) throws JspTagException {
        this.pattern = pattern;
    }

    // 'timeZone' attribute
    public void setTimeZone(Object timeZone) throws JspTagException {
        this.timeZone = timeZone;
    }
}
