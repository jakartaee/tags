/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package javax.servlet.jsp.jstl.core;

import javax.el.ELContext;
import javax.el.ValueExpression;

/**
 * @author Kin-man Chung
 * @version $Id: IteratedValueExpression.java,v 1.2 2005/12/08 01:20:43 kchung Exp $
 */
public final class IteratedValueExpression extends ValueExpression {

    private static final long serialVersionUID = 1L;
    protected final int i;
    protected final IteratedExpression iteratedExpression;

    public IteratedValueExpression(IteratedExpression iteratedExpr, int i) {
        this.i = i;
        this.iteratedExpression = iteratedExpr;
    }

    public Object getValue(ELContext context) {
        return iteratedExpression.getItem(context, i);
    }

    public void setValue(ELContext context, Object value) {
    }

    public boolean isReadOnly(ELContext context) {
        return true;
    }

    public Class getType(ELContext context) {
        return null;
    }

    public Class getExpectedType() {
        return Object.class;
    }

    public String getExpressionString() {
        return iteratedExpression.getValueExpression().getExpressionString();
    }

    public boolean equals(Object obj) {
        return iteratedExpression.getValueExpression().equals(obj);
    }

    public int hashCode() {
        return iteratedExpression.getValueExpression().hashCode();
    }

    public boolean isLiteralText() {
        return false;
    }
}

