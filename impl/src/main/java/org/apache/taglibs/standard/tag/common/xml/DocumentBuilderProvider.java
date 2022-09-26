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

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Provides preconfigured {@link DocumentBuilder} instances.
 *
 * @author David Matejcek
 */
public class DocumentBuilderProvider {

    private static final DocumentBuilderFactory DBF;
    private static final DocumentBuilderFactory DBF_SECURE;
    static {
        DBF = DocumentBuilderFactory.newInstance();
        DBF.setNamespaceAware(true);
        DBF.setValidating(false);

        DBF_SECURE = DocumentBuilderFactory.newInstance();
        DBF_SECURE.setNamespaceAware(true);
        DBF_SECURE.setValidating(false);
        try {
            DBF_SECURE.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        } catch (ParserConfigurationException e) {
            throw new Error("Parser does not support secure processing");
        }
    }

    /**
     * Creates a namespace-aware {@link DocumentBuilder} with disabled validation.
     * <p>
     * Note that {@link DocumentBuilder} instances are not thread-safe and their implementation can
     * be chosen as described in {@link DocumentBuilderFactory} documentation.
     *
     * @return new {@link DocumentBuilder} instance.
     */
    public static DocumentBuilder createDocumentBuilder() {
        try {
            return DBF.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new Error("Could not initialize the DocumentBuilder!", e);
        }
    }


    /**
     * Creates a namespace-aware {@link DocumentBuilder} with disabled validation and enabled
     * {@link XMLConstants#FEATURE_SECURE_PROCESSING}.
     * <p>
     * Note that {@link DocumentBuilder} instances are not thread-safe and their implementation can
     * be chosen as described in {@link DocumentBuilderFactory} documentation.
     *
     * @return new {@link DocumentBuilder} instance.
     */
    public static DocumentBuilder createSecureDocumentBuilder() {
        try {
            return DBF_SECURE.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new Error("Could not initialize the DocumentBuilder!", e);
        }
    }
}
