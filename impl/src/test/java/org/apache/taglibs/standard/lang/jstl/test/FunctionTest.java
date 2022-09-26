/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation
 * Copyright (c) 2020 Payara Services Ltd.
 * Copyright (c) 1997-2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2004 The Apache Software Foundation
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

import jakarta.servlet.jsp.JspException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.apache.taglibs.standard.lang.jstl.Evaluator;
import org.apache.taglibs.standard.lang.jstl.parser.ELParserConstants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains some test functions.
 *
 * @author Shawn Bayern
 * @author David Matejcek
 */
public class FunctionTest {

    private static Map<String, Method> functions;
    private static Evaluator evaluator;


    @BeforeAll
    public static void initSystemProperty() throws Exception {
        System.setProperty(ELParserConstants.SYSTEM_PROPERTY_ALLOW_FUNCTIONS, "true");
        functions = getSampleMethodMap();
        evaluator = new Evaluator();
    }


    @AfterAll
    public static void resetSystemProperty() {
        System.clearProperty(ELParserConstants.SYSTEM_PROPERTY_ALLOW_FUNCTIONS);
    }


    @Test
    public void functionsAsStaticMethods() throws Exception{
        assertAll(
            () -> assertEquals(4, evaluate("4")),
            () -> assertEquals(4, evaluate("${4}")),
            () -> assertEquals(4, evaluate("${2+2}")),
            () -> assertEquals(5, evaluate("${foo:add(2, 3)}")),
            () -> assertEquals(5, evaluate("${add(2, 3)}")),
            () -> assertEquals(6, evaluate("${multiply(2, 3)}")),
            () -> assertEquals(10, evaluate("${add(2, 3) + 5}")),
            () -> assertEquals(5, evaluate("${getInt(getInteger(getInt(5)))}")),
            () -> assertEquals(5, evaluate("${getInteger(getInt(getInteger(5)))}")),
            () -> assertEquals(5, evaluate("${getInt(getInt(getInt(5)))}")),
            () -> assertEquals(5, evaluate("${getInteger(getInteger(getInteger(5)))}"))
        );
    }

    private static Map<String, Method> getSampleMethodMap() throws Exception {
        Map<String, Method> m = new HashMap<>();
        Class<?> c = TestedClass.class;
        m.put("foo:add", c.getMethod("add", new Class<?>[] { Integer.TYPE, Integer.TYPE }));
        m.put("foo:multiply", c.getMethod("multiply", new Class<?>[] { Integer.TYPE, Integer.TYPE }));
        m.put("foo:getInt", c.getMethod("getInt", new Class<?>[] { Integer.class }));
        m.put("foo:getInteger", c.getMethod("getInteger", new Class<?>[] { Integer.TYPE }));
        return m;
    }


    private static Object evaluate(String value) throws JspException {
        return evaluator.evaluate("", value, Integer.class, null, null, functions, "foo");
    }


    public static class TestedClass {
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
    }
}
