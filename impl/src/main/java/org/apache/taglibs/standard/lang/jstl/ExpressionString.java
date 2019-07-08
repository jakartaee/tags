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
 * <p>Represents an expression String consisting of a mixture of
 * Strings and Expressions.
 * 
 * @author Nathan Abramson - Art Technology Group
 * @author Shawn Bayern
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public class ExpressionString
{
  //-------------------------------------
  // Properties
  //-------------------------------------
  // property elements

  Object [] mElements;
  public Object [] getElements ()
  { return mElements; }
  public void setElements (Object [] pElements)
  { mElements = pElements; }

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public ExpressionString (Object [] pElements)
  {
    mElements = pElements;
  }

  //-------------------------------------
  /**
   *
   * Evaluates the expression string by evaluating each element,
   * converting it to a String (using toString, or "" for null values)
   * and concatenating the results into a single String.
   **/
  public String evaluate (Object pContext,
			  VariableResolver pResolver,
			  Map functions,
			  String defaultPrefix,
			  Logger pLogger)
    throws ELException
  {
    StringBuffer buf = new StringBuffer ();
    for (int i = 0; i < mElements.length; i++) {
      Object elem = mElements [i];
      if (elem instanceof String) {
	buf.append ((String) elem);
      }
      else if (elem instanceof Expression) {
	Object val = 
	  ((Expression) elem).evaluate (pContext,
					pResolver,
					functions,
					defaultPrefix,
					pLogger);
	if (val != null) {
	  buf.append (val.toString ());
	}
      }
    }
    return buf.toString ();
  }

  //-------------------------------------
  /**
   *
   * Returns the expression in the expression language syntax
   **/
  public String getExpressionString ()
  {
    StringBuffer buf = new StringBuffer ();
    for (int i = 0; i < mElements.length; i++) {
      Object elem = mElements [i];
      if (elem instanceof String) {
	buf.append ((String) elem);
      }
      else if (elem instanceof Expression) {
	buf.append ("${");
	buf.append (((Expression) elem).getExpressionString ());
	buf.append ("}");
      }
    }
    return buf.toString ();
  }

  //-------------------------------------
}
