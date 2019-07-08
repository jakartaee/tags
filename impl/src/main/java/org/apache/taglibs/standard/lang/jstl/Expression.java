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

import java.util.Map;

/**
 *
 * <p>The abstract class from which all expression types
 * derive.
 * 
 * @author Nathan Abramson - Art Technology Group
 * @author Shawn Bayern
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public abstract class Expression
{
  //-------------------------------------
  // Member variables
  //-------------------------------------

  //-------------------------------------
  /**
   *
   * Returns the expression in the expression language syntax
   **/
  public abstract String getExpressionString ();

  //-------------------------------------
  /**
   *
   * Evaluates the expression in the given context
   **/
  public abstract Object evaluate (Object pContext,
				   VariableResolver pResolver,
				   Map functions,
				   String defaultPrefix,
				   Logger pLogger)
    throws ELException;

  //-------------------------------------

}
