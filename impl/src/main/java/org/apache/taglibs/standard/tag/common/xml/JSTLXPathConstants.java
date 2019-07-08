/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.tag.common.xml;


import javax.xml.namespace.QName;

/**
 * This class is added to provide support for a generic Object type in return type
 * arguement for XPath's evaluate instance method.
 *
 * @author dhirup
 */
public class JSTLXPathConstants {
    
    /**
     * <p>Private constructor to prevent instantiation.</p>
     */
    private JSTLXPathConstants() {
    }
    
    // To support generic Object types
    public static final QName OBJECT = new QName("http://www.w3.org/1999/XSL/Transform", "OBJECT");

    
}
