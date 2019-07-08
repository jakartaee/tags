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
 * <p>This is the superclass for all relational operators (except ==
 * or !=)
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public abstract class RelationalOperator
  extends BinaryOperator
{
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
    return Coercions.applyRelationalOperator (pLeft, pRight, this, pLogger);
  }

  //-------------------------------------
  /**
   *
   * Applies the operator to the given double values
   **/
  public abstract boolean apply (double pLeft,
				 double pRight,
				 Logger pLogger);
  
  //-------------------------------------
  /**
   *
   * Applies the operator to the given long values
   **/
  public abstract boolean apply (long pLeft,
				 long pRight,
				 Logger pLogger);
  
  //-------------------------------------
  /**
   *
   * Applies the operator to the given String values
   **/
  public abstract boolean apply (String pLeft,
				 String pRight,
				 Logger pLogger);

  //-------------------------------------
}
