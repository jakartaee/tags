/*
* Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Distribution License v. 1.0, which is available at
* http://www.eclipse.org/org/documents/edl-v10.php.
*
* SPDX-License-Identifier: BSD-3-Clause
*/

package org.apache.taglibs.standard.extra.spath;

public interface SPathParserConstants {

  int EOF = 0;
  int LITERAL = 1;
  int QNAME = 2;
  int NCNAME = 3;
  int NSWILDCARD = 4;
  int NCNAMECHAR = 5;
  int LETTER = 6;
  int DIGIT = 7;
  int COMBINING_CHAR = 8;
  int EXTENDER = 9;
  int UNDERSCORE = 10;
  int DOT = 11;
  int DASH = 12;
  int SLASH = 13;
  int STAR = 14;
  int COLON = 15;
  int START_BRACKET = 16;
  int END_BRACKET = 17;
  int AT = 18;
  int EQUALS = 19;

  int DEFAULT = 0;

  String[] tokenImage = {
    "<EOF>",
    "<LITERAL>",
    "<QNAME>",
    "<NCNAME>",
    "<NSWILDCARD>",
    "<NCNAMECHAR>",
    "<LETTER>",
    "<DIGIT>",
    "<COMBINING_CHAR>",
    "<EXTENDER>",
    "\"_\"",
    "\".\"",
    "\"-\"",
    "\"/\"",
    "\"*\"",
    "\":\"",
    "\"[\"",
    "\"]\"",
    "\"@\"",
    "\"=\"",
  };

}
