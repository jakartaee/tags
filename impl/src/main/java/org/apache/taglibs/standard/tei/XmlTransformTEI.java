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

/**
 * <p>An implementation of TagExtraInfo that implements validation for
 * &lt;x:transform&gt;'s attributes</p>
 *
 * @author Shawn Bayern
 */
public class XmlTransformTEI extends TagExtraInfo {

    final private static String XSLT = "xslt";
    final private static String RESULT = "result";
    final private static String VAR = "var";

    public boolean isValid(TagData us) {
	// require XSLT
	if (!Util.isSpecified(us, XSLT))
	    return false;

	// disallow both VAR and RESULT
	if (Util.isSpecified(us, VAR) && Util.isSpecified(us, RESULT))
	    return false;
        return true;
    }

}
