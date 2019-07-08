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
 * Represents any of the exception conditions that arise during the
 * operation evaluation of the evaluator.
 * 
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: kchung $
 **/

public class ELException
  extends Exception
{
  //-------------------------------------
  // Member variables
  //-------------------------------------

  Throwable mRootCause;

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public ELException ()
  {
    super ();
  }

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public ELException (String pMessage)
  {
    super (pMessage);
  }

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public ELException (Throwable pRootCause)
  {
    mRootCause = pRootCause;
  }

  //-------------------------------------
  /**
   *
   * Constructor
   **/
  public ELException (String pMessage,
		      Throwable pRootCause)
  {
    super (pMessage);
    mRootCause = pRootCause;
  }

  //-------------------------------------
  /**
   *
   * Returns the root cause
   **/
  public Throwable getRootCause ()
  {
    return mRootCause;
  }

  //-------------------------------------
  /**
   *
   * String representation
   **/
  public String toString ()
  {
    if (getMessage () == null) {
      return mRootCause.toString ();
    }
    else if (mRootCause == null) {
      return getMessage ();
    }
    else {
      return getMessage () + ": " + mRootCause;
    }
  }

  //-------------------------------------
}
