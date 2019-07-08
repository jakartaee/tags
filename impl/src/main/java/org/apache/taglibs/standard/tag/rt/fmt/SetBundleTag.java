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

import org.apache.taglibs.standard.tag.common.fmt.SetBundleSupport;

/**
 * <p>A handler for &lt;setBundle&gt; that supports rtexprvalue-based
 * attributes.</p>
 *
 * @author Jan Luehe
 */

public class SetBundleTag extends SetBundleSupport {

    //*********************************************************************
    // Accessor methods

    // for tag attribute
    public void setBasename(String basename) throws JspTagException {
        this.basename = basename;
    }
}
