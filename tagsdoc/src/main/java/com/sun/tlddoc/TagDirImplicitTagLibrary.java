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
import javax.xml.parsers.DocumentBuilder;
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
 * Implicit Tag Library for a directory of tag files.
 *
 * @author  mroth
 */
public class TagDirImplicitTagLibrary 
    extends TagLibrary 
{
    /** The directory containing the tag files */
    private File dir;
    
    /** Creates a new instance of TagDirImplicitTagLibrary */
    public TagDirImplicitTagLibrary( File dir ) {
        this.dir = dir;
    }
    
    /** 
     * Returns a String that the user would recognize as a location for this
     * tag library.
     */
    public String getPathDescription() {
        return dir.getAbsolutePath();
    }
    
    /** 
     * Returns an input stream for the given resource, or null if the
     * resource could not be found.
     */
    public InputStream getResource(String path) 
        throws IOException 
    {
        InputStream result = null;
        
        // Start from the tag directory and backtrack,
        // using the path as a relative path.
        //   For example:
        //      TLD:  /home/mroth/test/sample/WEB-INF/tags/mytags
        //      path: /WEB-INF/tags/mytags/tag1.tag
        
        File dir = this.dir;
        if( path.startsWith( "/" ) ) {
            path = path.substring( 1 );
        }
        File look = null;
        while( (dir != null) && !(look = new File( dir, path )).exists() ) {
            dir = dir.getParentFile();
        }
        
        if( (look != null) && look.exists() ) {
            // Found it:
            result = new FileInputStream( look );
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
        
        // Determine path from root of web application (this is somewhat of
        // a guess):
        String path = dir.getAbsolutePath().replace( File.separatorChar, '/' );
        int index = path.indexOf( "/WEB-INF/" );
        if( index != -1 ) {
            path = path.substring( index );
        }
        else {
            path = "unknown";
        }
        if( !path.endsWith( "/" ) ) path += "/";
        
        // Create root taglib node:
        Element taglibElement = createRootTaglibNode( result, path );
        
        // According to the JSP 2.0 specification:
        // A <tag-file> element is considered to exist for each tag file in 
        // this directory, with the following sub-elements:
        //    - The <name> for each is the filename of the tag file, 
        //      without the .tag extension.   
        //    - The <path> for each is the path of the tag file, relative 
        //      to the root of the web application.        
        File[] files = this.dir.listFiles();
        for( int i = 0; (files != null) && (i < files.length); i++ ) {
            if( !files[i].isDirectory() && 
                ( files[i].getName().toLowerCase().endsWith( ".tag" ) ||
                  files[i].getName().toLowerCase().endsWith( ".tagx" ) ) ) 
            {
                String tagName = files[i].getName().substring( 0, 
                    files[i].getName().lastIndexOf( '.' ) );
                String tagPath = path + files[i].getName();
                
                Element tagFileElement = result.createElement( "tag-file" );
                Element nameElement = result.createElement( "name" );
                nameElement.appendChild( result.createTextNode( tagName ) );
                tagFileElement.appendChild( nameElement );
                Element pathElement = result.createElement( "path" );
                pathElement.appendChild( result.createTextNode( tagPath ) );
                tagFileElement.appendChild( pathElement );
                taglibElement.appendChild( tagFileElement );
                files[i].getName();
            }
        }
        
        // Output implicit tag library, as a test.
        StringWriter buffer = new StringWriter();
        Transformer transformer = 
            TransformerFactory.newInstance().newTransformer();
        transformer.transform( new DOMSource( result ), 
            new StreamResult( buffer ) ); 
        result = documentBuilder.parse( new InputSource( new StringReader( 
            buffer.toString() ) ) );
        
        return result;
        
    }
    
    /**
     * Creates an implicit tag library root node, with default values.
     * Shared by WARTagDirImplicitTagLibrary.
     */
    protected static Element createRootTaglibNode( Document result,
        String path ) 
    {
        Element taglibElement = result.createElementNS(
            Constants.NS_JAVAEE, "taglib" );
        // JDK 1.4 does not add xmlns for some reason - add it manually:
        taglibElement.setAttributeNS( "http://www.w3.org/2000/xmlns/", 
            "xmlns", Constants.NS_JAVAEE );
        taglibElement.setAttributeNS( "http://www.w3.org/2000/xmlns/", 
            "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance" );
        taglibElement.setAttributeNS( 
            "http://www.w3.org/2001/XMLSchema-instance",
            "xsi:schemaLocation", 
            Constants.NS_JAVAEE + 
            " http://java.sun.com/xml/ns/javaee/web-jsptaglibrary_2_1.xsd" );
        taglibElement.setAttribute( "version", "2.1" );
        result.appendChild( taglibElement );
        
        // Add <description>
        Element descriptionElement = result.createElement( "description" );
        descriptionElement.appendChild( result.createTextNode( 
            "Implicit tag library for tag file directory " + path ) );
        taglibElement.appendChild( descriptionElement );
        
        // Add <tlib-version> of 1.0
        Element tlibVersionElement = result.createElement( "tlib-version" );
        tlibVersionElement.appendChild( result.createTextNode( "1.0" ) );
        taglibElement.appendChild( tlibVersionElement );
        
        // According to the JSP 2.0 specification, <short-name> is derived
        // from the directory name.  If the directory is /WEB-INF/tags/, the
        // short name is simply tags.  Otherwise, the full directory path 
        // (relative to the web application) is taken, minus the 
        // /WEB-INF/tags/ prefix. Then, all / characters are replaced 
        // with -, which yields the short name. Note that short names are 
        // not guaranteed to be unique.
        String shortName;
        if( path.equals( "unknown" ) ) {
            shortName = path;
        }
        else if( path.equals( "/WEB-INF/tags" ) || 
            path.equals( "/WEB-INF/tags/" ) ) 
        {
            shortName = "tags";
        }
        else {
            shortName = path;
            if( shortName.startsWith( "/WEB-INF/tags" ) ) {
                shortName = shortName.substring( "/WEB-INF/tags".length() );
            }
            if( shortName.startsWith( "/" ) ) {
                shortName = shortName.substring( 1 );
            }
            if( shortName.endsWith( "/" ) ) {
                shortName = shortName.substring( 0, shortName.length() - 1 );
            }
            shortName = shortName.replace( File.separatorChar, '/' );
            shortName = shortName.replace( '/', '-' );
        }
        Element shortNameElement = result.createElement( "short-name" );
        shortNameElement.appendChild( result.createTextNode( shortName ) );
        taglibElement.appendChild( shortNameElement );
        
        Element uriElement = result.createElement( "uri" );
        uriElement.appendChild( result.createTextNode( path ) );
        taglibElement.appendChild( uriElement );
        
        return taglibElement;
    }
    
}
