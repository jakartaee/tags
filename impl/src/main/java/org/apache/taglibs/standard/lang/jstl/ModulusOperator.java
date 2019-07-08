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
 * <p>The implementation of the modulus operator
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public class ModulusOperator
  extends BinaryOperator
{
  //-------------------------------------
  // Singleton
  //-------------------------------------

  public static final ModulusOperator SINGLETON =
    new ModulusOperator ();

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public ModulusOperator ()
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
    return "%";
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
    if (pLeft == null &&
	pRight == null) {
      if (pLogger.isLoggingWarning ()) {
	pLogger.logWarning
	  (Constants.ARITH_OP_NULL,
	   getOperatorSymbol ());
      }
      return PrimitiveObjects.getInteger (0);
    }

    if ((pLeft != null &&
	 (Coercions.isFloatingPointType (pLeft) ||
	  Coercions.isFloatingPointString (pLeft))) ||
	(pRight != null &&
	 (Coercions.isFloatingPointType (pRight) ||
	  Coercions.isFloatingPointString (pRight)))) {
      double left =
	Coercions.coerceToPrimitiveNumber (pLeft, Double.class, pLogger).
	doubleValue ();
      double right =
	Coercions.coerceToPrimitiveNumber (pRight, Double.class, pLogger).
	doubleValue ();

      try {
	return PrimitiveObjects.getDouble (left % right);
      }
      catch (Exception exc) {
	if (pLogger.isLoggingError ()) {
	  pLogger.logError
	    (Constants.ARITH_ERROR,
	     getOperatorSymbol (),
	     "" + left,
	     "" + right);
	}
	return PrimitiveObjects.getInteger (0);
      }
    }
    else {
      long left =
	Coercions.coerceToPrimitiveNumber (pLeft, Long.class, pLogger).
	longValue ();
      long right =
	Coercions.coerceToPrimitiveNumber (pRight, Long.class, pLogger).
	longValue ();

      try {
	return PrimitiveObjects.getLong (left % right);
      }
      catch (Exception exc) {
	if (pLogger.isLoggingError ()) {
	  pLogger.logError
	    (Constants.ARITH_ERROR,
	     getOperatorSymbol (),
	     "" + left,
	     "" + right);
	}
	return PrimitiveObjects.getInteger (0);
      }
    }
  }

  //-------------------------------------
}
