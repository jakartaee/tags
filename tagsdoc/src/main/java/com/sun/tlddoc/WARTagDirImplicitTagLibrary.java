/*
 * <license>
 * Copyright (c) 2003-2004, Sun Microsystems, Inc.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright 
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Sun Microsystems, Inc. nor the names of its 
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A 
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * ROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * </license>
 */

package com.sun.tlddoc;

import com.sun.tlddoc.GeneratorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Implicit Tag Library for a directory of tag files that is encapsulated
 * in a WAR file.
 *
 * @author  mroth
 */
public class WARTagDirImplicitTagLibrary 
    extends TagLibrary 
{
    /** The WAR file that contains this tag library */
    private File war;
    
    /** The directory containing the tag files */
    private String dir;
    
    /** Creates a new instance of TagDirImplicitTagLibrary */
    public WARTagDirImplicitTagLibrary(File war, String dir) {
        this.war = war;
        this.dir = dir;
    }
    
    /** 
     * Returns a String that the user would recognize as a location for this
     * tag library.
     */
    public String getPathDescription() {
        return war.getAbsolutePath() + "!" + dir;
    }
    
    /** 
     * Returns an input stream for the given resource, or null if the
     * resource could not be found.
     */
    public InputStream getResource(String path) 
        throws IOException 
    {
        InputStream result = null;
        if( path.startsWith( "/" ) ) path = path.substring( 1 );
        JarFile warFile = new JarFile( this.war );
        JarEntry warEntry = warFile.getJarEntry( path );
        if( warEntry != null ) {
            result = warFile.getInputStream( warEntry );
        }
        
        return result;
    }
    
    /** 
     * Returns a Document of the effective tag library descriptor for this
     * tag library.  This might come from a file or be implicitly generated.
     */
    public Document getTLDDocument(DocumentBuilder documentBuilder) 
        throws IOException, SAXException, ParserConfigurationException, 
            TransformerConfigurationException, TransformerException, 
            GeneratorException 
    {
        Document result = documentBuilder.newDocument();
        
        // Determine path from root of web application:
        String path = dir;
        if( !path.endsWith( "/" ) ) path += "/";
        
        Element taglibElement = 
            TagDirImplicitTagLibrary.createRootTaglibNode( result, "/" + path );
        
        // According to the JSP 2.0 specification:
        // A <tag-file> element is considered to exist for each tag file in 
        // this directory, with the following sub-elements:
        //    - The <name> for each is the filename of the tag file, 
        //      without the .tag extension.   
        //    - The <path> for each is the path of the tag file, relative 
        //      to the root of the web application.        
        JarFile warFile = new JarFile( this.war );
        Enumeration entries = warFile.entries();
        while( entries.hasMoreElements() ) {
            JarEntry warEntry = (JarEntry)entries.nextElement();
            String entryName = warEntry.getName();
            if( !warEntry.isDirectory() &&
                entryName.startsWith( path ) )
            {
                String relativeName = entryName.replace( File.separatorChar, 
                    '/' );
                relativeName = relativeName.substring( path.length() );
                if( relativeName.indexOf( '/' ) == -1 ) {
                    // We're not in a subdirectory.
                    if( relativeName.toLowerCase().endsWith( ".tag" ) ||
                        relativeName.toLowerCase().endsWith( ".tagx" ) ) 
                    {
                        String tagName = relativeName.substring( 0, 
                            relativeName.lastIndexOf( '.' ) );
                        String tagPath = "/" + entryName;

                        Element tagFileElement = result.createElement( 
                            "tag-file" );
                        Element nameElement = result.createElement( "name" );
                        nameElement.appendChild( result.createTextNode( 
                            tagName ) );
                        tagFileElement.appendChild( nameElement );
                        Element pathElement = result.createElement( "path" );
                        pathElement.appendChild( result.createTextNode( 
                            tagPath ) );
                        tagFileElement.appendChild( pathElement );
                        taglibElement.appendChild( tagFileElement );
                    }
                }
            }
        }
        warFile.close();
        
        // JDK 1.4 does not correctly import the node into the tree, so
        // simulate reading this entry from a file.  There might be a
        // better / more efficient way to do this, but this works.
        StringWriter buffer = new StringWriter();
        Transformer transformer = 
            TransformerFactory.newInstance().newTransformer();
        transformer.transform( new DOMSource( result ), 
            new StreamResult( buffer ) ); 
        result = documentBuilder.parse( new InputSource( new StringReader( 
            buffer.toString() ) ) );
        
        return result;
        
    }
    
}
