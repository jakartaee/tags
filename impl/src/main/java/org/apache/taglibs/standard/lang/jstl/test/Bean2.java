/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.lang.jstl.test;

/**
 *
 * <p>This is a test bean that holds a single String
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public class Bean2
{
  //-------------------------------------
  // Properties
  //-------------------------------------
  // property value

  String mValue;
  public String getValue ()
  { return mValue; }
  public void setValue (String pValue)
  { mValue = pValue; }

  //-------------------------------------
  // Member variables
  //-------------------------------------

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public Bean2 (String pValue)
  {
    mValue = pValue;
  }

  //-------------------------------------
  public String toString ()
  {
    return ("Bean2[" + mValue + "]");
  }

  //-------------------------------------

}
