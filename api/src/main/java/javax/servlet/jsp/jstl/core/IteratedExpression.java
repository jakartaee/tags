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

import java.io.Serializable;
import java.util.Iterator;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;

import javax.servlet.jsp.JspTagException;

/**
 * @author Kin-man Chung
 * @version $Id: IteratedExpression.java,v 1.6 2006/11/17 19:48:41 jluehe Exp $
 */
public final class IteratedExpression /*implements Serializable*/ {

    private static final long serialVersionUID = 1L;
    protected final ValueExpression orig;
    protected final String delims;

    private Object base;
    private int index;
    private Iterator iter;

    public IteratedExpression(ValueExpression orig, String delims) {
        this.orig = orig;
        this.delims = delims;
    }

    /**
     * Evaluates the stored ValueExpression and return the indexed item.
     * @param context The ELContext used to evaluate the ValueExpression
     * @param i The index of the item to be retrieved
     */
    public Object getItem(ELContext context, int i) {

        if (base == null) {
            base = orig.getValue(context);
            if (base == null) {
                return null;
            }
            iter = toIterator(base);
            index = 0;
        }
        if (index > i) {
            // Restart from index 0
            iter = toIterator(base);
            index = 0;
        }
        while (iter.hasNext()) {
            Object item = iter.next();
            if (index++ == i) {
                return item;
            }
        }
        return null;
    }

    public ValueExpression getValueExpression() {
        return orig;
    }

    private Iterator toIterator(final Object obj) {

        Iterator iter;
        if (obj instanceof String) {
            iter = toIterator(new StringTokenizer((String)obj, delims));
        }
        else if (obj instanceof Iterator) {
            iter = (Iterator)obj;
        }
        else if (obj instanceof Collection) {
            iter = toIterator(((Collection) obj).iterator());
        }
        else if (obj instanceof Enumeration) {
            iter = toIterator((Enumeration)obj);
        }
        else if (obj instanceof Map) {
            iter = ((Map)obj).entrySet().iterator();
        } else {
            throw new ELException("Don't know how to iterate over supplied "
                                  + "items in forEach");
        }
        return iter;
    }

    private Iterator toIterator(final Enumeration obj) {
        return new Iterator() {
            public boolean hasNext() {
                return obj.hasMoreElements();
            }
            public Object next() {
                return obj.nextElement();
            }
            public void remove() {}
        };
    }
}

