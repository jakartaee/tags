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

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 *
 * <p>The implementation of the empty operator
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public class EmptyOperator
  extends UnaryOperator
{
  //-------------------------------------
  // Singleton
  //-------------------------------------

  public static final EmptyOperator SINGLETON =
    new EmptyOperator ();

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public EmptyOperator ()
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
    return "empty";
  }

  //-------------------------------------
  /**
   *
   * Applies the operator to the given value
   **/
  public Object apply (Object pValue,
		       Object pContext,
		       Logger pLogger)
    throws ELException
  {
    // See if the value is null
    if (pValue == null) {
      return PrimitiveObjects.getBoolean (true);
    }

    // See if the value is a zero-length String
    else if ("".equals (pValue)) {
      return PrimitiveObjects.getBoolean (true);
    }

    // See if the value is a zero-length array
    else if (pValue.getClass ().isArray () &&
	     Array.getLength (pValue) == 0) {
      return PrimitiveObjects.getBoolean (true);
    }

    // See if the value is an empty List
    else if (pValue instanceof List &&
	     ((List) pValue).isEmpty ()) {
      return PrimitiveObjects.getBoolean (true);
    }

    // See if the value is an empty Map
    else if (pValue instanceof Map &&
	     ((Map) pValue).isEmpty ()) {
      return PrimitiveObjects.getBoolean (true);
    }

    // Otherwise, not empty
    else {
      return PrimitiveObjects.getBoolean (false);
    }
  }

  //-------------------------------------
}
