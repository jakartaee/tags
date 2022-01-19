/*
 * Copyright (c) 1997-2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 * Copyright (c) 2021-2021 Contributors to the Eclipse Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jakarta.servlet.jsp.jstl.core;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.TagSupport;

/**
 * <p>
 * Abstract class that facilitates implementation of conditional actions where the boolean result is exposed as a JSP
 * scoped variable. The boolean result may then be used as the test condition in a &lt;c:when&gt; action.
 * </p>
 *
 * <p>
 * This base class provides support for:
 * </p>
 *
 * <ul>
 * <li>Conditional processing of the action's body based on the returned value of the abstract method
 * <code>condition()</code>.</li>
 * <li>Storing the result of <code>condition()</code> as a <code>Boolean</code> object into a JSP scoped variable identified by
 * attributes <code>var</code> and <code>scope</code>.
 * </ul>
 *
 * @author Shawn Bayern
 */
public abstract class ConditionalTagSupport extends TagSupport {
    // *********************************************************************
    // Abstract methods

    /**
     * <p>
     * Subclasses implement this method to compute the boolean result of the conditional action. This method is invoked once
     * per tag invocation by <code>doStartTag()</code>.
     *
     * @return a boolean representing the condition that a particular subclass uses to drive its conditional logic.
     * @throws JspTagException on failure
     */
    protected abstract boolean condition() throws JspTagException;

    // *********************************************************************
    // Constructor

    /**
     * Base constructor to initialize local state. As with <code>TagSupport</code>, subclasses should not implement constructors
     * with arguments, and no-argument constructors implemented by subclasses must call the superclass constructor.
     */
    public ConditionalTagSupport() {
        super();
        init();
    }

    // *********************************************************************
    // Lifecycle management and implementation of conditional behavior

    /**
     * Includes its body if <code>condition()</code> evaluates to true.
     */
    @Override
    public int doStartTag() throws JspException {

        // execute our condition() method once per invocation
        result = condition();

        // expose variables if appropriate
        exposeVariables();

        // handle conditional behavior
        if (result) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    /**
     * Releases any resources this ConditionalTagSupport may have (or inherit).
     */
    @Override
    public void release() {
        super.release();
        init();
    }

    // *********************************************************************
    // Private state

    private boolean result; // the saved result of condition()
    private String var; // scoped attribute name
    private int scope; // scoped attribute scope

    // *********************************************************************
    // Accessors

    /**
     * Sets the 'var' attribute.
     *
     * @param var Name of the exported scoped variable storing the result of <code>condition()</code>.
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * Sets the 'scope' attribute.
     *
     * @param scope Scope of the 'var' attribute
     */
    public void setScope(String scope) {
        if (scope.equalsIgnoreCase("page")) {
            this.scope = PageContext.PAGE_SCOPE;
        } else if (scope.equalsIgnoreCase("request")) {
            this.scope = PageContext.REQUEST_SCOPE;
        } else if (scope.equalsIgnoreCase("session")) {
            this.scope = PageContext.SESSION_SCOPE;
        } else if (scope.equalsIgnoreCase("application")) {
            this.scope = PageContext.APPLICATION_SCOPE;
            // TODO: Add error handling? Needs direction from spec.
        }
    }

    // *********************************************************************
    // Utility methods

    // expose attributes if we have a non-null 'var'
    private void exposeVariables() {
        if (var != null) {
            pageContext.setAttribute(var, Boolean.valueOf(result), scope);
        }
    }

    // initializes internal state
    private void init() {
        result = false; // not really necessary
        var = null;
        scope = PageContext.PAGE_SCOPE;
    }
}
