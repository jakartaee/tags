/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.tag.common.core;

import javax.servlet.jsp.JspTagException;

import org.apache.taglibs.standard.resources.Resources;

/**
 * <p>NullAttributeException is a JspTagException that will be thrown
 * by the JSTL RI handlers when a tag attribute illegally evaluates
 * to 'null'.</p>
 *
 * @author Shawn Bayern
 */

public class NullAttributeException extends JspTagException {

    /**
     * Constructs a NullAttributeException with appropriate information.
     *
     * @param tag The name of the tag in which the error occurred.
     * @param att The attribute value for which the error occurred.
     */
    public NullAttributeException(String tag, String att) {
	super(Resources.getMessage("TAG_NULL_ATTRIBUTE", att, tag));
    }
}
