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

import javax.xml.xpath.XPathException;

/**
 * <meta name="usage" content="general"/>
 * Derived from XPathException in order that XPath processor
 * exceptions may be specifically caught.
 */
public class UnresolvableException extends XPathException {
    /**
     * Create an UnresolvableException object that holds
     * an error message.
     * @param message The error message.
     */
    public UnresolvableException(String message) {
        super(message);
    }
    
    /**
     * Create an UnresolvableException object that holds
     * an error message, and another exception
     * that caused this exception.
     * @param cause The exception that caused this exception.
     */
    public UnresolvableException(Throwable cause) {
        super(cause);
    }
}
