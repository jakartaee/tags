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
 * <p>The implementation of the greater than or equals operator
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public class GreaterThanOrEqualsOperator
  extends RelationalOperator
{
  //-------------------------------------
  // Singleton
  //-------------------------------------

  public static final GreaterThanOrEqualsOperator SINGLETON =
    new GreaterThanOrEqualsOperator ();

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public GreaterThanOrEqualsOperator ()
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
    return ">=";
  }

  //-------------------------------------
  /**
   *
   * Applies the operator to the given value
   **/
  public Object apply (Object pLeft,
		       Object pRight,
		       Object pContext,
		       Logger pLogger)
    throws ELException
  {
    if (pLeft == pRight) {
      return Boolean.TRUE;
    }
    else if (pLeft == null ||
	     pRight == null) {
      return Boolean.FALSE;
    }
    else {
      return super.apply (pLeft, pRight, pContext, pLogger);
    }
  }

  //-------------------------------------
  /**
   *
   * Applies the operator to the given double values
   **/
  public boolean apply (double pLeft,
			double pRight,
			Logger pLogger)
  {
    return pLeft >= pRight;
  }
  
  //-------------------------------------
  /**
   *
   * Applies the operator to the given long values
   **/
  public boolean apply (long pLeft,
			long pRight,
			Logger pLogger)
  {
    return pLeft >= pRight;
  }
  
  //-------------------------------------
  /**
   *
   * Applies the operator to the given String values
   **/
  public boolean apply (String pLeft,
			String pRight,
			Logger pLogger)
  {
    return pLeft.compareTo (pRight) >= 0;
  }

  //-------------------------------------
}
