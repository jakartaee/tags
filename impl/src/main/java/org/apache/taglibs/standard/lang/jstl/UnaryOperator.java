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
 * <p>This is the superclass for all unary operators
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public abstract class UnaryOperator
{
  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public UnaryOperator ()
  {
  }

  //-------------------------------------
  // Expression methods
  //-------------------------------------
  /**
   *
   * Returns the symbol representing the operator
   **/
  public abstract String getOperatorSymbol ();

  //-------------------------------------
  /**
   *
   * Applies the operator to the given value
   **/
  public abstract Object apply (Object pValue,
				Object pContext,
				Logger pLogger)
    throws ELException;

  //-------------------------------------
}
