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
 * <p>An expression representing a literal value
 * 
 * @author Nathan Abramson - Art Technology Group
 * @author Shawn Bayern
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public abstract class Literal
  extends Expression
{
  //-------------------------------------
  // Properties
  //-------------------------------------
  // property value

  Object mValue;
  public Object getValue ()
  { return mValue; }
  public void setValue (Object pValue)
  { mValue = pValue; }

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public Literal (Object pValue)
  {
    mValue = pValue;
  }

  //-------------------------------------
  // Expression methods
  //-------------------------------------
  /**
   *
   * Evaluates to the literal value
   **/
  public Object evaluate (Object pContext,
			  VariableResolver pResolver,
			  Map functions,
			  String defaultPrefix,
			  Logger pLogger)
    throws ELException
  {
    return mValue;
  }

  //-------------------------------------
}
