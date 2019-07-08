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


/**
 * <p>Tag handler for &lt;otherwise&gt; in JSTL.</p>
 *
 * @author Shawn Bayern
 */

public class OtherwiseTag extends WhenTagSupport {

    /*
     * <otherwise> is just a <when> that always tries to evaluate its body
     * if it has permission from its parent tag.
     */

    // Don't let the condition stop us... :-)
    protected boolean condition() {
        return true;
    }
}
