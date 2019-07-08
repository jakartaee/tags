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
import java.util.Vector;

/**
 * <p>Represents a relative SPath expression.</p>
 *
 * @author Shawn Bayern
 */
public class RelativePath extends Path {

    private RelativePath next;
    private Step step;

    /**
     * Constructs a new RelativePath object, based on a Step and another
     * (possibly null) RelativePath.  If 'all' is true, then the path
     * matches all instances of 'next' underneath 'step'; otherwise;
     * 'next' must be an immediate child of 'step'.
     */
    public RelativePath(Step step, RelativePath next) {
	if (step == null)
	    throw new IllegalArgumentException("non-null step required");
	this.step = step;
	this.next = next;
    }

    // inherit JavaDoc comment
    public List getSteps() {
	// simply merge our 'step' with our 'next'
	List l;
	if (next != null)
	    l = next.getSteps();
	else
	    l = new Vector();
	l.add(0, step);
	return l;
    }
}
