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
 * <p>Represents a simple path (SPath) expression.  A path is an ordered
 * list of Steps.
 *
 * @author Shawn Bayern
 */
public abstract class Path {

    /**
     * Retrives an ordered list of Step objects representing this
     * expression.  The result is safely modifiable by the caller and
     * must support List.add(Object) and List.add(int, Object).
     */
    public abstract List getSteps();

}
