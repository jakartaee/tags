/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation
 * Copyright (c) 1997-2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2020 Payara Services Ltd.
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
import jakarta.servlet.jsp.PageContext;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.taglibs.standard.lang.jstl.Evaluator;
import org.apache.taglibs.standard.lang.jstl.test.beans.Factory;
import org.apache.taglibs.standard.lang.jstl.test.mock.PageContextImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.apache.taglibs.standard.lang.jstl.test.TestUtilities.assertEqualContent;
import static org.apache.taglibs.standard.lang.jstl.test.TestUtilities.openFileForReading;

/**
 * <p>
 * This runs a series of tests specifically for the evaluator. It
 * parses and evaluates various expressions in the context of a test
 * PageContext containing preset data, and prints out the results of
 * the evaluations.
 * <p>
 * The expressions are stored in an input text file, where one line
 * contains the expression and the next line contains the expected
 * type. Blank lines and lines that start with # are ignored. The
 * results are written to an output file (blank lines and # lines are
 * included in the output file). The output file may be compared
 * against an existing output file to do regression testing.
 *
 * @author Nathan Abramson - Art Technology Group 2001
 * @author David Matejcek 2022
 **/

public class EvaluationTest {

    private static BufferedReader pIn;
    private static PrintStream pOut;
    private static File outputFile;

    @BeforeAll
    public static void init() throws Exception {
        InputStream fin = EvaluationTest.class.getResourceAsStream("/evaluationTests.txt");
        pIn = openFileForReading(fin);

        outputFile = File.createTempFile(EvaluationTest.class.getName(), ".out");
        FileOutputStream fout = new FileOutputStream(outputFile);
        BufferedOutputStream bout = new BufferedOutputStream(fout);
        pOut = new PrintStream(bout);
    }


    @AfterAll
    public static void close() throws Exception {
        if (pIn != null) {
            pIn.close();
        }
        if (pOut != null) {
            pOut.close();
        }
    }


    /**
     * Runs the tests, reading expressions from pIn and writing the
     * results to pOut.
     */
    @Test
    public void generateAndCompare() throws Exception {
        PageContext context = createTestContext();
        while (true) {
            String str = pIn.readLine();
            if (str == null) {
                break;
            }
            if (str.startsWith("#") || "".equals(str.trim())) {
                pOut.println(str);
            } else {
                String typeStr = pIn.readLine();
                pOut.println("Expression: " + str);

                try {
                    Class<?> cl = parseClassName(typeStr);
                    pOut.println("ExpectedType: " + cl);
                    Evaluator e = new Evaluator();
                    Object val = e.evaluate("test", str, cl, null, context);
                    pOut.println("Evaluates to: " + val);
                    if (val != null) {
                        pOut.println("With type: " + val.getClass().getName());
                    }
                    pOut.println();
                } catch (JspException exc) {
                    pOut.println("Causes an error: " + exc);
                    pOut.println();
                } catch (ClassNotFoundException exc) {
                    pOut.println("Causes an error: " + exc);
                    pOut.println();
                }
            }
        }
        pOut.flush();

        BufferedReader expected = openFileForReading(EvaluationTest.class.getResourceAsStream("/evaluationTestsOutput.txt"));
        BufferedReader generated = openFileForReading(outputFile);
        assertEqualContent(expected, generated);
    }


    /**
     * Finds the class for a class name, including primitive names
     **/
    private static Class<?> parseClassName(String pClassName) throws ClassNotFoundException {
        String c = pClassName.trim();
        if ("boolean".equals(c)) {
            return Boolean.TYPE;
        } else if ("byte".equals(c)) {
            return Byte.TYPE;
        } else if ("char".equals(c)) {
            return Character.TYPE;
        } else if ("short".equals(c)) {
            return Short.TYPE;
        } else if ("int".equals(c)) {
            return Integer.TYPE;
        } else if ("long".equals(c)) {
            return Long.TYPE;
        } else if ("float".equals(c)) {
            return Float.TYPE;
        } else if ("double".equals(c)) {
            return Double.TYPE;
        } else {
            return Class.forName(pClassName);
        }
    }


    /**
     * Creates and returns the test PageContext that will be used for the tests.
     **/
    private static PageContext createTestContext() {
        PageContext ret = new PageContextImpl();

        // Create some basic values for lookups
        ret.setAttribute("val1a", "page-scoped1", PageContext.PAGE_SCOPE);
        ret.setAttribute("val1b", "request-scoped1", PageContext.REQUEST_SCOPE);
        ret.setAttribute("val1c", "session-scoped1", PageContext.SESSION_SCOPE);
        ret.setAttribute("val1d", "app-scoped1", PageContext.APPLICATION_SCOPE);

        // Create a bean
        {
            Bean1 b1 = new Bean1();
            b1.setBoolean1(true);
            b1.setByte1((byte) 12);
            b1.setShort1((short) -124);
            b1.setChar1('b');
            b1.setInt1(4);
            b1.setLong1(222423);
            b1.setFloat1((float) 12.4);
            b1.setDouble1(89.224);
            b1.setString1("hello");
            b1.setStringArray1(new String[] {"string1", "string2", "string3", "string4"});
            {
                List<Object> l = new ArrayList<>();
                l.add(Integer.valueOf(14));
                l.add("another value");
                l.add(b1.getStringArray1());
                b1.setList1(l);
            }
            {
                Map<Object, Object> m = new HashMap<>();
                m.put("key1", "value1");
                m.put(Integer.valueOf(14), "value2");
                m.put(Long.valueOf(14), "value3");
                m.put("recurse", b1);
                b1.setMap1(m);
            }
            ret.setAttribute("bean1a", b1);

            Bean1 b2 = new Bean1();
            b2.setInt2(Integer.valueOf(-224));
            b2.setString2("bean2's string");
            b1.setBean1(b2);

            Bean1 b3 = new Bean1();
            b3.setDouble1(1422.332);
            b3.setString2("bean3's string");
            b2.setBean2(b3);
        }

        // Create the public/private beans
        {
            ret.setAttribute("pbean1", Factory.createBean1());
            ret.setAttribute("pbean2", Factory.createBean2());
            ret.setAttribute("pbean3", Factory.createBean3());
            ret.setAttribute("pbean4", Factory.createBean4());
            ret.setAttribute("pbean5", Factory.createBean5());
            ret.setAttribute("pbean6", Factory.createBean6());
            ret.setAttribute("pbean7", Factory.createBean7());
        }

        // Create the empty tests
        {
            Map<String, Object> m = new HashMap<>();
            m.put("emptyArray", new Object[0]);
            m.put("nonemptyArray", new Object[] {"abc"});
            m.put("emptyList", new ArrayList<>());
            {
                List<String> l = new ArrayList<>();
                l.add("hello");
                m.put("nonemptyList", l);
            }
            m.put("emptyMap", new HashMap<>());
            {
                Map<String, String> m2 = new HashMap<>();
                m2.put("a", "a");
                m.put("nonemptyMap", m2);
            }
            m.put("emptySet", new HashSet<>());
            {
                Set<String> s = new HashSet<>();
                s.add("hello");
                m.put("nonemptySet", s);
            }
            ret.setAttribute("emptyTests", m);
        }

        return ret;
    }
}
