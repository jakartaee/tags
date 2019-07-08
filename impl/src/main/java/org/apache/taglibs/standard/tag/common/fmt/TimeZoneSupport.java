/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.tag.common.fmt;

import java.io.IOException;
import java.util.TimeZone;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

/**
 * Support for tag handlers for &lt;timeZone&gt;, the time zone tag in
 * JSTL 1.0.
 *
 * @author Jan Luehe
 */

public abstract class TimeZoneSupport extends BodyTagSupport {


    //*********************************************************************
    // Protected state

    protected Object value;                      // 'value' attribute
  

    //*********************************************************************
    // Private state

    private TimeZone timeZone;


    //*********************************************************************
    // Constructor and initialization

    public TimeZoneSupport() {
	super();
	init();
    }

    private void init() {
	value = null;
    }


    //*********************************************************************
    // Collaboration with subtags

    public TimeZone getTimeZone() {
	return timeZone;
    }


    //*********************************************************************
    // Tag logic

    public int doStartTag() throws JspException {

	if (value == null) {
	    timeZone = TimeZone.getTimeZone("GMT");
	} else if (value instanceof String) {
	    if (((String) value).trim().equals("")) {
		timeZone = TimeZone.getTimeZone("GMT");
	    } else {
		timeZone = TimeZone.getTimeZone((String) value);
	    }
	} else {
	    timeZone = (TimeZone) value;
	}

	return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {
	try {
	    pageContext.getOut().print(bodyContent.getString());
	} catch (IOException ioe) {
	    throw new JspTagException(ioe.toString(), ioe);
	}

	return EVAL_PAGE;
    }

    // Releases any resources we may have (or inherit)
    public void release() {
	init();
    }


    //*********************************************************************
    // Package-scoped utility methods

    /*
     * Determines and returns the time zone to be used by the given action.
     *
     * <p> If the given action is nested inside a &lt;timeZone&gt; action,
     * the time zone is taken from the enclosing &lt;timeZone&gt; action.
     *
     * <p> Otherwise, the time zone configuration setting
     * <tt>javax.servlet.jsp.jstl.core.Config.FMT_TIME_ZONE</tt>
     * is used.
     *
     * @param pageContext the page containing the action for which the
     * time zone needs to be determined
     * @param fromTag the action for which the time zone needs to be
     * determined
     *
     * @return the time zone, or <tt>null</tt> if the given action is not 
     * nested inside a &lt;timeZone&gt; action and no time zone configuration
     * setting exists
     */
    static TimeZone getTimeZone(PageContext pc, Tag fromTag) {
	TimeZone tz = null;

	Tag t = findAncestorWithClass(fromTag, TimeZoneSupport.class);
	if (t != null) {
	    // use time zone from parent <timeZone> tag
	    TimeZoneSupport parent = (TimeZoneSupport) t;
	    tz = parent.getTimeZone();
	} else {
	    // get time zone from configuration setting
	    Object obj = Config.find(pc, Config.FMT_TIME_ZONE);
	    if (obj != null) {
		if (obj instanceof TimeZone) {
		    tz = (TimeZone) obj;
		} else {
		    tz = TimeZone.getTimeZone((String) obj);
		}
	    }
	}

	return tz;
    }
}
