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
 * <p>The implementation of the not equals operator
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public class NotEqualsOperator
  extends EqualityOperator
{
  //-------------------------------------
  // Singleton
  //-------------------------------------

  public static final NotEqualsOperator SINGLETON =
    new NotEqualsOperator ();

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public NotEqualsOperator ()
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
    return "!=";
  }

  //-------------------------------------
  /**
   *
   * Applies the operator given the fact that the two elements are
   * equal.
   **/
  public boolean apply (boolean pAreEqual,
			Logger pLogger)
  {
    return !pAreEqual;
  }
  
  //-------------------------------------
}
