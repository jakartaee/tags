/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.resources;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <p>Provides locale-neutral access to string resources.  Only the
 * documentation and code are in English. :-)
 *
 * <p>The major goal, aside from globalization, is convenience.
 * Access to resources with no parameters is made in the form:</p>
 * <pre>
 *     Resources.getMessage(MESSAGE_NAME);
 * </pre>
 *
 * <p>Access to resources with one parameter works like</p>
 * <pre>
 *     Resources.getMessage(MESSAGE_NAME, arg1);
 * </pre>
 *
 * <p>... and so on.</p>
 *
 * @author Shawn Bayern
 */
public class Resources {

    //*********************************************************************
    // Static data

    /** The location of our resources. */
    private static final String RESOURCE_LOCATION
	= "org.apache.taglibs.standard.resources.Resources";

    /** Our class-wide ResourceBundle. */
    private static ResourceBundle rb =
	ResourceBundle.getBundle(RESOURCE_LOCATION);


    //*********************************************************************
    // Public static methods

    /** Retrieves a message with no arguments. */
    public static String getMessage(String name)
	    throws MissingResourceException {
	return rb.getString(name);
    }

    /** Retrieves a message with arbitrarily many arguments. */
    public static String getMessage(String name, Object[] a)
	    throws MissingResourceException {
	String res = rb.getString(name);
	return MessageFormat.format(res, a);
    }

    /** Retrieves a message with one argument. */
    public static String getMessage(String name, Object a1)
	    throws MissingResourceException {
	return getMessage(name, new Object[] { a1 });
    }

    /** Retrieves a message with two arguments. */
    public static String getMessage(String name, Object a1, Object a2)
	    throws MissingResourceException {
	return getMessage(name, new Object[] { a1, a2 });
    }

    /** Retrieves a message with three arguments. */
    public static String getMessage(String name,
				    Object a1,
				    Object a2,
				    Object a3)
	    throws MissingResourceException {
	return getMessage(name, new Object[] { a1, a2, a3 });
    }

    /** Retrieves a message with four arguments. */
    public static String getMessage(String name,
			 	    Object a1,
				    Object a2,
				    Object a3,
				    Object a4)
	    throws MissingResourceException {
	return getMessage(name, new Object[] { a1, a2, a3, a4 });
    }

    /** Retrieves a message with five arguments. */
    public static String getMessage(String name,
				    Object a1,
				    Object a2,
				    Object a3,
				    Object a4,
				    Object a5)
	    throws MissingResourceException {
	return getMessage(name, new Object[] { a1, a2, a3, a4, a5 });
    }

    /** Retrieves a message with six arguments. */
    public static String getMessage(String name,
				    Object a1,
				    Object a2,
				    Object a3,
				    Object a4,
				    Object a5,
				    Object a6)
	    throws MissingResourceException {
	return getMessage(name, new Object[] { a1, a2, a3, a4, a5, a6 });
    }

}
