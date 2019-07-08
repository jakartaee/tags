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

import java.beans.PropertyEditorSupport;

/**
 *
 * PropertyEditor for parsing Bean2
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public class Bean2Editor
  extends PropertyEditorSupport
{
  //-------------------------------------
  public void setAsText (String pText)
    throws IllegalArgumentException
  {
    if ("badvalue".equals (pText)) {
      throw new IllegalArgumentException ("Bad value " + pText);
    }
    else {
      setValue (new Bean2 (pText));
    }
  }

  //-------------------------------------
}
