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

/**
 * <p>Utilities in support of TagExtraInfo classes.</p>
 *
 * @author Shawn Bayern
 */
public class Util {

    /**
     * Returns true if the given attribute name is specified, false otherwise.
     */
    public static boolean isSpecified(TagData data, String attributeName) {
        return (data.getAttribute(attributeName) != null);
    }

}
