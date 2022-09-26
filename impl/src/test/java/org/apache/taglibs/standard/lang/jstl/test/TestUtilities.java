/*
 * Copyright (c) 2022 Eclipse Foundation and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.apache.taglibs.standard.lang.jstl.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author David Matejcek
 */
class TestUtilities {

    static BufferedReader openFileForReading(File file) throws FileNotFoundException {
        return openFileForReading(new FileInputStream(file));
    }


    static BufferedReader openFileForReading(InputStream file) {
        return new BufferedReader(new InputStreamReader(file));
    }


    /**
     * Performs a line-by-line comparison of the two files, returning
     * true if the files are different, false if not.
     **/
    static boolean assertEqualContent(BufferedReader expected, BufferedReader generated) throws IOException {
        int line = 1;
        while (true) {
            String expectedLine = expected.readLine();
            String generatedLine = generated.readLine();
            if (expectedLine == null && generatedLine == null) {
                return false;
            }
            assertEquals(expectedLine, generatedLine, "Line: " + line);
            line++;
        }
    }

}
