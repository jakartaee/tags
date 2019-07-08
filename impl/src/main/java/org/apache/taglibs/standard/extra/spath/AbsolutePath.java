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

import java.util.List;

/**
 * <p>Represents an absolute SPath expression.  Essentially a marker
 * class.</p>
 *
 * @author Shawn Bayern
 */
public class AbsolutePath extends Path {

    private boolean all;
    private RelativePath base;

    /**
     * Constructs a new AbsolutePath object based on a RelativePath.
     * An absolute path is the same as a relative path, except that it
     * begins with '/' or '//' (which one, of those two, can be
     * determined by the first Step returned from getSteps()).
     */
    public AbsolutePath(RelativePath base) {
	if (base == null)
	    throw new IllegalArgumentException("non-null base required");
	this.base = base;
    }

    // inherit JavaDoc comment
    public List getSteps() {
	// simply return our base's Step objects
	return base.getSteps();
    }
}
