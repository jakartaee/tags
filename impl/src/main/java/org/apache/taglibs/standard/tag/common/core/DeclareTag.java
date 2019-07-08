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

import javax.servlet.jsp.tagext.TagSupport;

/**
 * <p>Tag handler for &lt;declaregt; in JSTL.  This tag handler is
 * essentially a runtime no-op as far as tag logic is concerned; the
 * only important functionality of the tag is to expose a scripting
 * variable for an existing scoped attribute.</p>
 * 
 * @author Shawn Bayern
 */

public class DeclareTag extends TagSupport {

    /*
     * We're not identical to TagSupport only because we need to
     * accept an additional "type" attribute.
     */
    public void setType(String x) { }
}
