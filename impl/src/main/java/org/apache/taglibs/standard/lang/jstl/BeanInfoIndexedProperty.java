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



import java.beans.IndexedPropertyDescriptor;

import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;



/**

 *

 * <p>This contains the information for one indexed property in a

 * BeanInfo - IndexedPropertyDescriptor, read method, and write

 * method.  This class is necessary because the read/write methods in

 * the IndexedPropertyDescriptor may not be accessible if the bean

 * given to the introspector is not a public class.  In this case, a

 * publicly accessible version of the method must be found by

 * searching for a public superclass/interface that declares the

 * method (this searching is done by the BeanInfoManager).

 * 

 * @author Nathan Abramson - Art Technology Group

 * @version $Change: 181181 $$DateTime: 2001/06/26 09:55:09 $$Author: kchung $

 **/



public class BeanInfoIndexedProperty

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



  IndexedPropertyDescriptor mIndexedPropertyDescriptor;

  public IndexedPropertyDescriptor getIndexedPropertyDescriptor ()

  { return mIndexedPropertyDescriptor; }



  //-------------------------------------

  /**

   *

   * Constructor

   **/

  public BeanInfoIndexedProperty 

    (Method pReadMethod,

     Method pWriteMethod,

     IndexedPropertyDescriptor pIndexedPropertyDescriptor)

  {

    mReadMethod = pReadMethod;

    mWriteMethod = pWriteMethod;

    mIndexedPropertyDescriptor = pIndexedPropertyDescriptor;

  }



  //-------------------------------------

}

