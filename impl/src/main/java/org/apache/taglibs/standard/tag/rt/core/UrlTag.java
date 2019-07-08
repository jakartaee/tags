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

import org.apache.taglibs.standard.tag.common.core.UrlSupport;

/**
 * <p>A handler for &lt;urlEncode&gt; that supports rtexprvalue-based
 * attributes.</p>
 *
 * @author Shawn Bayern
 */

public class UrlTag extends UrlSupport {

    //*********************************************************************
    // Accessor methods

    // for tag attribute
    public void setValue(String value) throws JspTagException {
        this.value = value;
    }

    // for tag attribute
    public void setContext(String context) throws JspTagException {
        this.context = context;
    }

}
