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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 *
 * <p>This contains the information for one property in a BeanInfo -
 * PropertyDescriptor, read method, and write method.  This class is
 * necessary because the read/write methods in the PropertyDescriptor
 * may not be accessible if the bean given to the introspector is not
 * a public class.  In this case, a publicly accessible version of the
 * method must be found by searching for a public superclass/interface
 * that declares the method (this searching is done by the
 * BeanInfoManager).
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181181 $$DateTime: 2001/06/26 09:55:09 $$Author: kchung $
 **/

public class BeanInfoProperty
{
  //-------------------------------------
  // Properties
  //-------------------------------------
  // property readMethod

  Method mReadMethod;
  public Method getReadMethod ()
  { return mReadMethod; }

  //-------------------------------------
  // property writeMethod

  Method mWriteMethod;
  public Method getWriteMethod ()
  { return mWriteMethod; }

  //-------------------------------------
  // property propertyDescriptor

  PropertyDescriptor mPropertyDescriptor;
  public PropertyDescriptor getPropertyDescriptor ()
  { return mPropertyDescriptor; }

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public BeanInfoProperty (Method pReadMethod,
			   Method pWriteMethod,
			   PropertyDescriptor pPropertyDescriptor)
  {
    mReadMethod = pReadMethod;
    mWriteMethod = pWriteMethod;
    mPropertyDescriptor = pPropertyDescriptor;
  }

  //-------------------------------------
}
