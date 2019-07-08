/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.extra.spath;


/**
 * <p>Represents a predicate expression concerning a single attribute.</p>
 *
 * @author Shawn Bayern
 */
public class AttributePredicate extends Predicate {

    private String attribute, target;

    /**
     * Constructs a new AttributePredicate, given an attribute name
     * and a target literal (with which to test equality).
     */
    public AttributePredicate(String attribute, String target) {
	if (attribute == null)
	    throw new IllegalArgumentException("non-null attribute needed");
	if (attribute.indexOf(":") != -1)
	    throw new IllegalArgumentException(
		"namespace-qualified attribute names are not currently " +
		"supported");
	this.attribute = attribute;

	if (target == null)
	    throw new IllegalArgumentException("non-null target needed");
	// strip quotation marks from target
	this.target = target.substring(1, target.length() - 1);
    }

    /**
     * Returns true if the given SAX AttributeList is suitable, given our
     * attribute name and target; returns false otherwise.
     */
    public boolean isMatchingAttribute(org.xml.sax.Attributes a) {
	String attValue = a.getValue("", attribute);
	return (attValue != null && attValue.equals(target));
    }
} 
