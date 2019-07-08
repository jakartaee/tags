/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.tag.common.core;

/**
 * <p>Interface for tag handlers implementing valid parent tags for
 * &lt;c:param&gt;.</p>
 *
 * @author Shawn Bayern
 */

public interface ParamParent {

    /**
     * Adds a parameter to this tag's URL.  The intent is that the
     * &lt;param&gt; subtag will call this to register URL parameters.
     * Assumes that 'name' and 'value' are appropriately encoded and do
     * not contain any meaningful metacharacters; in order words, escaping
     * is the responsibility of the caller.
     *
     * @see ParamSupport
     */
    void addParameter(String name, String value);

}
