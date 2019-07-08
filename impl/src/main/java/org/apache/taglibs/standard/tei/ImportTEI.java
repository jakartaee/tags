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
 * &lt;c:import&gt;'s attributes</p>
 *
 * @author Shawn Bayern
 */
public class ImportTEI extends TagExtraInfo {

    final private static String VAR = "var";
    final private static String VAR_READER = "varReader";

    public boolean isValid(TagData us) {
	// don't allow both VAR and VAR_READER, together
	if (Util.isSpecified(us, VAR) && Util.isSpecified(us, VAR_READER))
	    return false;

        return true;
    }

}
