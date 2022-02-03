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

package com.sun.tlddoc.tagfileparser;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Encapsulates a directive in a tag file.
 *
 * @author  mroth
 */
public class Directive {
  
    /** The name of this directive. */
    private String directiveName;
    
    /** The attributes for this directive. */
    private ArrayList attributes = new ArrayList();
    
    /** Creates a new instance of Directive */
    public Directive() {
    }
    
    /** 
     * Getter for property directiveName.
     * @return Value of property directiveName.
     */
    public java.lang.String getDirectiveName() {
        return directiveName;
    }
    
    /** 
     * Setter for property directiveName.
     * @param directiveName New value of property directiveName.
     */
    public void setDirectiveName(java.lang.String directiveName) {
        this.directiveName = directiveName;
    }

    /**
     * Adds an attribute to the list of attributes for this directive.
     */
    public void addAttribute( Attribute attribute ) {
        this.attributes.add( attribute );
    }
    
    /**
     * Returns an iterator through the set of attributes in this directive.
     */
    public Iterator getAttributes() {
        return this.attributes.iterator();
    }
    
    /**
     * Returns a string representation of this directive.
     */
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append( "[Directive;name=" );
        result.append( directiveName );
        result.append( ",attributes={" );
        for( int i = 0; i < attributes.size(); i++ ) {
            Attribute attr = (Attribute)attributes.get( i );
            result.append( attr.toString() );
            if( i < (attributes.size()-1) ) {
                result.append( ", " );
            }
        }
        result.append( "}]" );
        return result.toString();
    }
}
