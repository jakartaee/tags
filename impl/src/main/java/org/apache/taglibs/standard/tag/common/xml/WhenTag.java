/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.tag.common.xml;

import javax.servlet.jsp.JspTagException;

import org.apache.taglibs.standard.tag.common.core.WhenTagSupport;

/**
 * <p>Tag handler for &lt;if&gt; in JSTL's XML library.</p>
 *
 * @author Shawn Bayern
 */

public class WhenTag extends WhenTagSupport {

    //*********************************************************************
    // Constructor and lifecycle management

    // initialize inherited and local state
    public WhenTag() {
        super();
        init();
    }

    // Releases any resources we may have (or inherit)
    public void release() {
        super.release();
        init();
    }


    //*********************************************************************
    // Supplied conditional logic

    protected boolean condition() throws JspTagException {
        XPathUtil xu = new XPathUtil(pageContext);
        return (xu.booleanValueOf(XPathUtil.getContext(this), select));
    }

    //*********************************************************************
    // Private state

    private String select;               // the value of the 'test' attribute


    //*********************************************************************
    // Attribute accessors

    public void setSelect(String select) {
        this.select = select;
    }


    //*********************************************************************
    // Private utility methods

    // resets internal state
    private void init() {
        select = null;
    }
}
