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
 * <x:parse>'s attributes</p>
 *
 * @author Shawn Bayern
 */
public class XmlParseTEI extends TagExtraInfo {

    final private static String VAR = "var";
    final private static String VAR_DOM = "varDom";
    final private static String SCOPE = "scope";
    final private static String SCOPE_DOM = "scopeDom";

    public boolean isValid(TagData us) {
	// must have no more than one of VAR and VAR_DOM ...
	if (Util.isSpecified(us, VAR) && Util.isSpecified(us, VAR_DOM))
	    return false;

	// ... and must have no less than one of VAR and VAR_DOM
	if (!(Util.isSpecified(us, VAR) || Util.isSpecified(us, VAR_DOM)))
	    return false;

	// When either 'scope' is specified, its 'var' must be specified
	if (Util.isSpecified(us, SCOPE) && !Util.isSpecified(us, VAR))
	    return false;
	if (Util.isSpecified(us, SCOPE_DOM) && !Util.isSpecified(us, VAR_DOM))
	    return false;

        return true;
    }

}
