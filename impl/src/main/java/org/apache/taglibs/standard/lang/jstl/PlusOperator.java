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
 * <p>The implementation of the plus operator
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public class PlusOperator
  extends ArithmeticOperator
{
  //-------------------------------------
  // Singleton
  //-------------------------------------

  public static final PlusOperator SINGLETON =
    new PlusOperator ();

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public PlusOperator ()
  {
  }

  //-------------------------------------
  // Expression methods
  //-------------------------------------
  /**
   *
   * Returns the symbol representing the operator
   **/
  public String getOperatorSymbol ()
  {
    return "+";
  }

  //-------------------------------------
  /**
   *
   * Applies the operator to the given double values, returning a double
   **/
  public double apply (double pLeft,
		       double pRight,
		       Logger pLogger)
  {
    return pLeft + pRight;
  }
  
  //-------------------------------------
  /**
   *
   * Applies the operator to the given double values, returning a double
   **/
  public long apply (long pLeft,
		     long pRight,
		     Logger pLogger)
  {
    return pLeft + pRight;
  }
  
  //-------------------------------------
}
