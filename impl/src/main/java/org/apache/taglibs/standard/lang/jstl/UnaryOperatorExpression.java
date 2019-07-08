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

import java.util.List;
import java.util.Map;

/**
 *
 * <p>An expression representing one or more unary operators on a
 * value
 * 
 * @author Nathan Abramson - Art Technology Group
 * @author Shawn Bayern
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public class UnaryOperatorExpression
  extends Expression
{
  //-------------------------------------
  // Properties
  //-------------------------------------
  // property operator

  UnaryOperator mOperator;
  public UnaryOperator getOperator ()
  { return mOperator; }
  public void setOperator (UnaryOperator pOperator)
  { mOperator = pOperator; }

  //-------------------------------------
  // property operators

  List mOperators;
  public List getOperators ()
  { return mOperators; }
  public void setOperators (List pOperators)
  { mOperators = pOperators; }

  //-------------------------------------
  // property expression

  Expression mExpression;
  public Expression getExpression ()
  { return mExpression; }
  public void setExpression (Expression pExpression)
  { mExpression = pExpression; }

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public UnaryOperatorExpression (UnaryOperator pOperator,
				  List pOperators,
				  Expression pExpression)
  {
    mOperator = pOperator;
    mOperators = pOperators;
    mExpression = pExpression;
  }

  //-------------------------------------
  // Expression methods
  //-------------------------------------
  /**
   *
   * Returns the expression in the expression language syntax
   **/
  public String getExpressionString ()
  {
    StringBuffer buf = new StringBuffer ();
    buf.append ("(");
    if (mOperator != null) {
      buf.append (mOperator.getOperatorSymbol ());
      buf.append (" ");
    }
    else {
      for (int i = 0; i < mOperators.size (); i++) {
	UnaryOperator operator = (UnaryOperator) mOperators.get (i);
	buf.append (operator.getOperatorSymbol ());
	buf.append (" ");
      }
    }
    buf.append (mExpression.getExpressionString ());
    buf.append (")");
    return buf.toString ();
  }

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
    Object value = mExpression.evaluate (pContext, pResolver, functions,
					 defaultPrefix, pLogger);
    if (mOperator != null) {
      value = mOperator.apply (value, pContext, pLogger);
    }
    else {
      for (int i = mOperators.size () - 1; i >= 0; i--) {
	UnaryOperator operator = (UnaryOperator) mOperators.get (i);
	value = operator.apply (value, pContext, pLogger);
      }
    }
    return value;
  }

  //-------------------------------------
}
