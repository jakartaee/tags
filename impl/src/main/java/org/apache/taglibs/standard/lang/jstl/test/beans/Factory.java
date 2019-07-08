/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.lang.jstl.test.beans;

/**
 *
 * <p>A factory for generating the various beans
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181181 $$DateTime: 2001/06/26 09:55:09 $$Author: kchung $
 **/

public class Factory
{
  public static PublicBean1 createBean1 () 
  {
    return new PublicBean1 (); 
  }

  public static PublicBean1 createBean2 () 
  {
    return new PrivateBean1a ();
  }

  public static PublicBean1 createBean3 () 
  {
    return new PublicBean1b ();
  }

  public static PublicInterface2 createBean4 () 
  {
    return new PublicBean2a ();
  }

  public static PublicInterface2 createBean5 () 
  {
    return new PrivateBean2b ();
  }

  public static PublicInterface2 createBean6 () 
  {
    return new PrivateBean2c ();
  }

  public static PublicInterface2 createBean7 () 
  {
    return new PrivateBean2d ();
  }
}
