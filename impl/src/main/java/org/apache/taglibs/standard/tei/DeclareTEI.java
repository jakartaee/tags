/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.tei;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * <p>An implementation of TagExtraInfo provided for &lt;declare&gt;.
 * We simply set up a scripting variable for the ID and value that
 * &lt;declare&gt; already stored.  For EA2, DefineTEI *always* declares
 * the variable; no option is given via a tag attribute.  Visibility is
 * always AT_END.</p>
 *
 * @author Shawn Bayern
 */
public class DeclareTEI extends TagExtraInfo {

    // purposely inherit JavaDoc and semantics from TagExtraInfo
    public VariableInfo[] getVariableInfo(TagData data) {
        // construct the relevant VariableInfo object
        VariableInfo id = new VariableInfo(
            data.getAttributeString("id"),
            data.getAttributeString("type") == null ?
		"java.lang.Object" : data.getAttributeString("type"),
            true,
            VariableInfo.AT_END);
        return new VariableInfo[] { id };
    }
}
