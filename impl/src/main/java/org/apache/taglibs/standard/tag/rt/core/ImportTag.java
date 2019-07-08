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

import org.apache.taglibs.standard.tag.common.core.ImportSupport;

/**
 * <p>A handler for &lt;import&gt; that supports rtexprvalue-based
 * attributes.</p>
 *
 * @author Shawn Bayern
 */

public class ImportTag extends ImportSupport {

    //*********************************************************************
    // Accessor methods

    // for tag attribute
    public void setUrl(String url) throws JspTagException {
        this.url = url;
    }

    // for tag attribute
    public void setContext(String context) throws JspTagException {
        this.context = context;
    }

    // for tag attribute
    public void setCharEncoding(String charEncoding) throws JspTagException {
        this.charEncoding = charEncoding;
    }

}
