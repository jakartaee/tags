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
 * ForEachTag's attributes</p>
 *
 * @author Shawn Bayern
 */
public class ForEachTEI extends TagExtraInfo {

    final private static String ITEMS = "items";
    final private static String BEGIN = "begin";
    final private static String END = "end";

    /*
     * Currently implements the following rules:
     * 
     * - If 'items' is not specified, 'begin' and 'end' must be
     */
    public boolean isValid(TagData us) {
        if (!Util.isSpecified(us, ITEMS))
            if (!Util.isSpecified(us, BEGIN) || !(Util.isSpecified(us, END)))
                return false;
        return true;
    }

}
