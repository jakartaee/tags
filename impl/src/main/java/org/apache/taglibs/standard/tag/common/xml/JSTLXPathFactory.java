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

import org.apache.xpath.jaxp.XPathFactoryImpl;

/**
 * This factory class is added to provide access to our own implementation
 * of XPath, so that we can support a generic Object type in return type
 * arguement for XPath's evaluate instance method. 
 * 
 * @author dhirup
 */
public class JSTLXPathFactory extends XPathFactoryImpl {
    
    public javax.xml.xpath.XPath newXPath() {
        return new org.apache.taglibs.standard.tag.common.xml.JSTLXPathImpl(null, null);
    }    
}
