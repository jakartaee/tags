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

import org.apache.taglibs.standard.tag.common.core.SetSupport;

/**
 * <p>Tag handler for &lt;set&gt; in JSTL's rtexprvalue library.</p>
 *
 * @author Shawn Bayern
 */

public class SetTag extends SetSupport {

    //*********************************************************************
    // Accessors

    // for tag attribute
    public void setValue(Object value) {
        this.value = value;
	this.valueSpecified = true;
    }

    // for tag attribute
    public void setTarget(Object target) {
        this.target = target;
    }

    // for tag attribute
    public void setProperty(String property) {
        this.property = property;
    }
}
