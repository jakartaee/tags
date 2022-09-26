/*
 * Copyright (c) 1997-2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 * Copyright (c) 2020 Payara Services Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.taglibs.standard.lang.jstl.test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.taglibs.standard.lang.jstl.Evaluator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * <p>This class contains some test functions.</p>
 * 
 * @author Shawn Bayern
 */

public class StaticFunctionTests {

  public static void main(String args[]) throws Exception {
    Map<String, Method> m = getSampleMethodMap();
    Evaluator e = new Evaluator();
    Object o;

    o = e.evaluate("", "4", Integer.class, null, null, m, "foo");
    System.out.println(o);
    o = e.evaluate("", "${4}", Integer.class, null, null, m, "foo");
    System.out.println(o);
    o = e.evaluate("", "${2+2}", Integer.class, null, null, m, "foo");
    System.out.println(o);
    o = e.evaluate("", "${foo:add(2, 3)}", Integer.class, null, null, m, "foo");
    System.out.println(o);
    o = e.evaluate("", "${foo:multiply(2, 3)}", Integer.class, null, null, m, "foo");
    System.out.println(o);
    o = e.evaluate("", "${add(2, 3)}", Integer.class, null, null, m, "foo");
    System.out.println(o);
    o = e.evaluate("", "${multiply(2, 3)}", Integer.class, null, null, m, "foo");
    System.out.println(o);
    o = e.evaluate("", "${add(2, 3) + 5}", Integer.class, null, null, m, "foo");
    System.out.println(o);

    System.out.println("---");
    o = e.evaluate("", "${getInt(getInteger(getInt(5)))}", Integer.class, null, null, m, "foo");
    System.out.println(o);
    o = e.evaluate("", "${getInteger(getInt(getInteger(5)))}", Integer.class, null, null, m, "foo");
    System.out.println(o);
    o = e.evaluate("", "${getInt(getInt(getInt(5)))}", Integer.class, null, null, m, "foo");
    System.out.println(o);
    o = e.evaluate("", "${getInteger(getInteger(getInteger(5)))}", Integer.class, null, null, m, "foo");
    System.out.println(o);

  }

  

  public static int add(int a, int b) {
    return a + b;
  }

  public static int multiply(int a, int b) {
    return a * b;
  }

  public static int getInt(Integer i) {
    return i.intValue();
  }

  public static Integer getInteger(int i) {
    return Integer.valueOf(i);
  }

  @Test
  // Method for running static tests and comparing expected with actual
  public void staticTest() throws Exception{
    // Enable jakarta.servlet.jsp.functions.allowed for functions to be evaluated
    Properties props = System.getProperties();
    props.setProperty("jakarta.servlet.jsp.functions.allowed", "true");

    Map<String, Method> m = getSampleMethodMap();
    Evaluator e = new Evaluator();

    // Setup expected values from test
    Object[] expected = new Object[]{
      4, // Expected for attributeValue 4
      4, // Expected for attributeValue ${4}
      4, // Expected for attributeValue ${2+2}
      5, // Expected for attributeValue ${foo:add(2, 3)}
      6, // Expected for attributeValue ${foo:multiply(2, 3)}
      5, // Expected for attributeValue ${add(2, 3)}
      6, // Expected for attributeValue ${multiply(2, 3)}
      10, // Expected for attributeValue ${add(2, 3) + 5}
      5, // Expected for attributeValue ${getInt(getInteger(getInt(5)))}
      5, // Expected for attributeValue ${getInteger(getInt(getInteger(5)))}
      5, // Expected for attributeValue ${getInt(getInt(getInt(5)))}
      5 // Expected for attributeValue ${getInteger(getInteger(getInteger(5)))}
    };

    // Evaluate statements
    Object[] actual = new Object[12];
    actual[0] = e.evaluate("", "4", Integer.class, null, null, m, "foo");
    actual[1] = e.evaluate("", "${4}", Integer.class, null, null, m, "foo");
    actual[2] = e.evaluate("", "${2+2}", Integer.class, null, null, m, "foo");
    actual[3] = e.evaluate("", "${foo:add(2, 3)}", Integer.class, null, null, m, "foo");
    actual[4] = e.evaluate("", "${foo:multiply(2, 3)}", Integer.class, null, null, m, "foo");
    actual[5] = e.evaluate("", "${add(2, 3)}", Integer.class, null, null, m, "foo");
    actual[6] = e.evaluate("", "${multiply(2, 3)}", Integer.class, null, null, m, "foo");
    actual[7] = e.evaluate("", "${add(2, 3) + 5}", Integer.class, null, null, m, "foo");
    actual[8] = e.evaluate("", "${getInt(getInteger(getInt(5)))}", Integer.class, null, null, m, "foo");
    actual[9] = e.evaluate("", "${getInteger(getInt(getInteger(5)))}", Integer.class, null, null, m, "foo");
    actual[10] = e.evaluate("", "${getInt(getInt(getInt(5)))}", Integer.class, null, null, m, "foo");
    actual[11] = e.evaluate("", "${getInteger(getInteger(getInteger(5)))}", Integer.class, null, null, m, "foo");
    
    // Verify values
    Assertions.assertArrayEquals(expected, actual, "Arrays did not match! Expected: " + Arrays.toString(expected) + " but got: " + Arrays.toString(actual));
  }

  public static Map<String, Method> getSampleMethodMap() throws Exception {
    Map<String, Method> m = new HashMap<>();
    Class<?> c = StaticFunctionTests.class;
    m.put("foo:add", c.getMethod("add", new Class<?>[] { Integer.TYPE, Integer.TYPE }));
    m.put("foo:multiply", c.getMethod("multiply", new Class<?>[] { Integer.TYPE, Integer.TYPE }));
    m.put("foo:getInt", c.getMethod("getInt", new Class<?>[] { Integer.class }));
    m.put("foo:getInteger", c.getMethod("getInteger", new Class<?>[] { Integer.TYPE }));
    return m;
  }

}
