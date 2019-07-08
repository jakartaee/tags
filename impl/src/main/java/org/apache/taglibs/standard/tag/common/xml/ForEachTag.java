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

import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.LoopTagSupport;

import org.apache.taglibs.standard.resources.Resources;

/**
 * <p>Support for the XML library's &lt;forEach&gt; tag.</p>
 *
 * @see javax.servlet.jsp.jstl.core.LoopTagSupport
 * @author Shawn Bayern
 */
public class ForEachTag extends LoopTagSupport {

    //*********************************************************************
    // Private state

    private String select;				// tag attribute
    private List nodes;					// XPath result
    private int nodesIndex;				// current index
    private org.w3c.dom.Node current;			// current node

    //*********************************************************************
    // Iteration control methods

    // (We inherit semantics and Javadoc from LoopTagSupport.) 

    protected void prepare() throws JspTagException {
        nodesIndex = 0;
        XPathUtil xu = new XPathUtil(pageContext);
        nodes = xu.selectNodes(XPathUtil.getContext(this), select);
    }

    protected boolean hasNext() throws JspTagException {
        return (nodesIndex < nodes.size());
    }

    protected Object next() throws JspTagException {
	Object o = nodes.get(nodesIndex++);
	if (!(o instanceof org.w3c.dom.Node))
	    throw new JspTagException(
		Resources.getMessage("FOREACH_NOT_NODESET"));
	current = (org.w3c.dom.Node) o;
        return current;
    }


    //*********************************************************************
    // Tag logic and lifecycle management

    // Releases any resources we may have (or inherit)
    public void release() {
	init();
        super.release();
    }


    //*********************************************************************
    // Attribute accessors

    public void setSelect(String select) {
	this.select = select;
    }

    public void setBegin(int begin) throws JspTagException {
        this.beginSpecified = true;
        this.begin = begin;
        validateBegin();
    }

    public void setEnd(int end) throws JspTagException {
        this.endSpecified = true;
        this.end = end;
        validateEnd();
    }

    public void setStep(int step) throws JspTagException {
        this.stepSpecified = true;
        this.step = step;
        validateStep();
    }
    
    //*********************************************************************
    // Public methods for subtags

    /* Retrieves the current context. */
    public org.w3c.dom.Node getContext() throws JspTagException {
	// expose the current node as the context
        return current;
    }


    //*********************************************************************
    // Private utility methods

    private void init() {
	select = null;
	nodes = null;
	nodesIndex = 0;
	current = null;
    }	
}

