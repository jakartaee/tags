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

import org.apache.taglibs.standard.tag.common.fmt.SetLocaleSupport;

/**
 * <p>A handler for &lt;setLocale&gt; that supports rtexprvalue-based
 * attributes.</p>
 *
 * @author Jan Luehe
 */

public class SetLocaleTag extends SetLocaleSupport {

    //*********************************************************************
    // Accessor methods

    // for tag attribute
    public void setValue(Object value) throws JspTagException {
        this.value = value;
    }

    // for tag attribute
    public void setVariant(String variant) throws JspTagException {
        this.variant = variant;
    }
}
