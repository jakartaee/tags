/*
 * Copyright (c) 1997-2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.taglibs.standard.tag.common.xml;


import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;

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

    public static QName LOCAL_TO_XPATH_CONSTANT(String local){
        String lowerCaseLocal = local.toLowerCase();
        if(lowerCaseLocal.equals(XPathConstants.BOOLEAN.getLocalPart().toLowerCase()))
            return XPathConstants.BOOLEAN;
        if(lowerCaseLocal.equals(XPathConstants.NUMBER.getLocalPart().toLowerCase()))
            return XPathConstants.NUMBER;
        if(lowerCaseLocal.equals(XPathConstants.STRING.getLocalPart().toLowerCase()))
            return XPathConstants.STRING;
        if(lowerCaseLocal.equals(XPathConstants.NODE.getLocalPart().toLowerCase()))
            return XPathConstants.NODE;
        if(lowerCaseLocal.equals(XPathConstants.NODESET.getLocalPart().toLowerCase()))
            return XPathConstants.NODESET;
        return QName.valueOf(local);

    }
    
}
