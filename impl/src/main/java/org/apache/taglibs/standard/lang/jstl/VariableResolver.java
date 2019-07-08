/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.lang.jstl;

/**
 *
 * <p>This class is used to customize the way the evaluator resolves
 * variable references.  For example, instances of this class can
 * implement their own variable lookup mechanisms, or introduce the
 * notion of "implicit variables" which override any other variables.
 * An instance of this class should be passed to the evaluator's
 * constructor.
 *
 * <p>Whenever the evaluator is invoked, it is passed a "context"
 * Object from the application.  For example, in a JSP environment,
 * the "context" is a PageContext.  That context object is eventually
 * passed to this class, so that this class has a context in which to
 * resolve variables.
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public interface VariableResolver
{
  //-------------------------------------
  /**
   *
   * Resolves the specified variable within the given context.
   * Returns null if the variable is not found.
   **/
  public Object resolveVariable (String pName,
				 Object pContext)
    throws ELException;
					
  //-------------------------------------
}
