/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation
 * Copyright (c) 1997-2020 Oracle and/or its affiliates. All rights reserved.
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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.taglibs.standard.lang.jstl.Evaluator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.apache.taglibs.standard.lang.jstl.test.TestUtilities.assertEqualContent;
import static org.apache.taglibs.standard.lang.jstl.test.TestUtilities.openFileForReading;

/**
 * <p>This runs a series of tests specifically for the parser.  It
 * parses various expressions and prints out the canonical
 * representation of those parsed expressions.
 *
 * <p>The expressions are stored in an input text file, with one line
 * per expression.  Blank lines and lines that start with # are
 * ignored.  The results are written to an output file (blank lines
 * and # lines are included in the output file).  The output file may
 * be compared against an existing output file to do regression
 * testing.
 *
 * @author Nathan Abramson - Art Technology Group 2001
 * @author David Matejcek 2022
 */
public class ParserTest {
    private static BufferedReader pIn;
    private static PrintStream pOut;
    private static File outputFile;

    @BeforeAll
    public static void init() throws Exception {
        InputStream fin = ParserTest.class.getResourceAsStream("/parserTests.txt");
        pIn = openFileForReading(fin);

        outputFile = File.createTempFile(ParserTest.class.getName(), ".out");
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
     **/
    @Test
    public void runTests() throws IOException {
        while (true) {
            String str = pIn.readLine();
            if (str == null) {
                break;
            }
            if (str.startsWith("#") || "".equals(str.trim())) {
                pOut.println(str);
            } else {
                // For testing non-ASCII values, the string @@non-ascii gets
                // converted internally to '\u1111'
                if ("@@non-ascii".equals(str)) {
                    str = "\u1111";
                }

                pOut.println("Attribute value: " + str);
                try {
                    String result = Evaluator.parseAndRender(str);
                    pOut.println("Parses to: " + result);
                } catch (JspException exc) {
                    pOut.println("Causes an error: " + exc.getMessage());
                }
            }
        }
        pOut.flush();

        BufferedReader expected = openFileForReading(EvaluationTest.class.getResourceAsStream("/parserTestsOutput.txt"));
        BufferedReader generated = openFileForReading(outputFile);
        assertEqualContent(expected, generated);
    }
}
