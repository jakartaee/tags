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

package org.apache.taglibs.standard.tag.common.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author David Matejcek
 */
public class DocumentBuilderProvider {

    /**
     * Creates a namespace-aware {@link DocumentBuilder} with disabled validation.
     * <p>
     * Note that {@link DocumentBuilder} instances are not thread-safe and their implementation can
     * be chosen as described in {@link DocumentBuilderFactory} documentation.
     *
     * @return new {@link DocumentBuilder} instance.
     */
    static DocumentBuilder createDocumentBuilder() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            dbf.setValidating(false);
            return dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // this should never happen with a well-behaving JAXP implementation.
            throw new Error(e);
        }
    }
}
