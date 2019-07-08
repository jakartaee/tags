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
 * <p>An expression representing a String literal value.
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public class StringLiteral
  extends Literal
{
  //-------------------------------------
  /**
   *
   * Constructor
   **/
  StringLiteral (Object pValue)
  {
    super (pValue);
  }

  //-------------------------------------
  /**
   *
   * Returns a StringLiteral parsed from the given token (enclosed by
   * single or double quotes)
   **/
  public static StringLiteral fromToken (String pToken)
  {
    return new StringLiteral (getValueFromToken (pToken));
  }

  //-------------------------------------
  /**
   *
   * Returns a StringLiteral with the given string value
   **/
  public static StringLiteral fromLiteralValue (String pValue)
  {
    return new StringLiteral (pValue);
  }

  //-------------------------------------
  /**
   *
   * Parses the given token into the literal value
   **/
  public static String getValueFromToken (String pToken)
  {
    StringBuffer buf = new StringBuffer ();
    int len = pToken.length () - 1;
    boolean escaping = false;
    for (int i = 1; i < len; i++) {
      char ch = pToken.charAt (i);
      if (escaping) {
	buf.append (ch);
	escaping = false;
      }
      else if (ch == '\\') {
	escaping = true;
      }
      else {
	buf.append (ch);
      }
    }
    return buf.toString ();
  }

  //-------------------------------------
  /**
   *
   * Converts the specified value to a String token, using " as the
   * enclosing quotes and escaping any characters that need escaping.
   **/
  public static String toStringToken (String pValue)
  {
    // See if any escaping is needed
    if (pValue.indexOf ('\"') < 0 &&
	pValue.indexOf ('\\') < 0) {
      return "\"" + pValue + "\"";
    }

    // Escaping is needed
    else {
      StringBuffer buf = new StringBuffer ();
      buf.append ('\"');
      int len = pValue.length ();
      for (int i = 0; i < len; i++) {
	char ch = pValue.charAt (i);
	if (ch == '\\') {
	  buf.append ('\\');
	  buf.append ('\\');
	}
	else if (ch == '\"') {
	  buf.append ('\\');
	  buf.append ('\"');
	}
	else {
	  buf.append (ch);
	}
      }
      buf.append ('\"');
      return buf.toString ();
    }
  }

  //-------------------------------------
  /**
   *
   * Converts the specified value to an identifier token, escaping it
   * as a string literal if necessary.
   **/
  public static String toIdentifierToken (String pValue)
  {
    // See if it's a valid java identifier
    if (isJavaIdentifier (pValue)) {
      return pValue;
    }

    // Return as a String literal
    else {
      return toStringToken (pValue);
    }
  }

  //-------------------------------------
  /**
   *
   * Returns true if the specified value is a legal java identifier
   **/
  static boolean isJavaIdentifier (String pValue)
  {
    int len = pValue.length ();
    if (len == 0) {
      return false;
    }
    else {
      if (!Character.isJavaIdentifierStart (pValue.charAt (0))) {
	return false;
      }
      else {
	for (int i = 1; i < len; i++) {
	  if (!Character.isJavaIdentifierPart (pValue.charAt (i))) {
	    return false;
	  }
	}
	return true;
      }
    }
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
    return toStringToken ((String) getValue ());
  }

  //-------------------------------------
}
