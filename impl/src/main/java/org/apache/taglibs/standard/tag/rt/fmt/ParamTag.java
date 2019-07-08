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

import org.apache.taglibs.standard.tag.common.fmt.ParamSupport;

/**
 * <p>A handler for &lt;param&gt; that supports rtexprvalue-based
 * message arguments.</p>
 *
 * @author Jan Luehe
 */

public class ParamTag extends ParamSupport {

    //*********************************************************************
    // Accessor methods

    // for tag attribute
    public void setValue(Object value) throws JspTagException {
        this.value = value;
	this.valueSpecified = true;
    }
}
