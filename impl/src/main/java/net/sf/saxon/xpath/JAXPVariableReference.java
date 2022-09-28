////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017 Saxonica Limited.
// This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
// If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
// This Source Code Form is "Incompatible With Secondary Licenses", as defined by the Mozilla Public License, v. 2.0.
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package net.sf.saxon.xpath;

import net.sf.saxon.Configuration;
import net.sf.saxon.expr.*;
import net.sf.saxon.expr.parser.RebindingMap;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.trace.ExpressionPresenter;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.type.AnyItemType;
import net.sf.saxon.type.ItemType;
import net.sf.saxon.value.EmptySequence;

import javax.xml.xpath.XPathVariableResolver;


/**
 * This class represents a variable in an XPath expression compiled using the JAXP XPath API.
 * Although the class name suggests otherwise, the expression is not in fact a VariableReference;
 * it's a custom expression which, on evaluation, calls the JAXP XPathVariableResolver to get the
 * value of the variable.
 * 
 * <strong>Overlayed class based of the same class in Saxon 11.4 implementation. </strong>  This overlay was created following the Saxon discussion in bug 5687
 * where by running the JSTL TCK it was discovered a behavior difference between Xalan and Saxon while parsing unexisting variables.
 * According to the JSTL specification <a href=https://jakarta.ee/specifications/tags/2.0/jakarta-tags-spec-2.0.html#java-to-xpath-type-mappings>section 11.1.3</a>
 * <strong>Following the XPath specification (section 3.1), a variable name that is not bound to any value results in an exception.</strong>
 * 
 * This overlay is a simple one line change from returning an empty sequence when the variable resolver returns a null value to throwing an error due to a missing variable.
 * 
 */

// See bug 2554 which motivated a redesign for Saxon 9.7.0.2

public class JAXPVariableReference extends Expression implements Callable {

    private StructuredQName name;
    private XPathVariableResolver resolver;


    /**
     * Create the expression
     */

    public JAXPVariableReference(StructuredQName name, XPathVariableResolver resolver) {
        this.name = name;
        this.resolver = resolver;
    }

    /**
     * Get a name identifying the kind of expression, in terms meaningful to a user.
     *
     * @return a name identifying the kind of expression, in terms meaningful to a user.
     * The name will always be in the form of a lexical XML QName, and should match the name used
     * in explain() output displaying the expression.
     */

    public String getExpressionName() {
        return "$" + name.getDisplayName();
    }

    /**
     * Create a clone copy of this expression
     *
     * @return a copy of this expression
     * @param rebindings
     */

    /*@NotNull*/
    public Expression copy(RebindingMap rebindings) {
        return new JAXPVariableReference(name, resolver);
    }

    /**
     * Determine the item type
     */

    /*@NotNull*/
    public ItemType getItemType() {
        return AnyItemType.getInstance();
    }

    /**
     * Get the static cardinality
     */

    public int computeCardinality() {
        return StaticProperty.ALLOWS_ZERO_OR_MORE;
    }

    /**
     * Determine the special properties of this expression
     *
     * @return the value {@link StaticProperty#NO_NODES_NEWLY_CREATED}
     */

    public int computeSpecialProperties() {
        return StaticProperty.NO_NODES_NEWLY_CREATED;
    }

    @Override
    public int getImplementationMethod() {
        return ITERATE_METHOD;
    }

    /**
     * Is this expression the same as another expression?
     */

    public boolean equals(Object other) {
        return other instanceof JAXPVariableReference &&
                ((JAXPVariableReference)other).name.equals(name) &&
                ((JAXPVariableReference)other).resolver == resolver;
    }

    /**
     * get HashCode for comparing two expressions
    */
    protected int computeHashCode() {
        return this.name.hashCode();
    }

    @Override
    public Sequence call(XPathContext context, Sequence[] arguments) throws XPathException {
        Configuration config = context.getConfiguration();
        Object value = resolver.resolveVariable(name.toJaxpQName());
        if (value == null) {
            // Should throw Exception according to spec and not return an empty sequence
            // NEW:
            throw new XPathException("Got null value for variable: " + getExpressionName());
            // OLD: 
            //return EmptySequence.getInstance();
        }
        JPConverter converter = JPConverter.allocate(value.getClass(), null, config);
        return converter.convert(value, context);
    }

    /**
     * Iterate over the value of the expression
     */

    /*@NotNull*/
    public SequenceIterator iterate(XPathContext context) throws XPathException {
        return call(context, null).iterate();
    }

    /**
     * The toString() method for an expression attempts to give a representation of the expression
     * in an XPath-like form, but there is no guarantee that the syntax will actually be true XPath.
     * In the case of XSLT instructions, the toString() method gives an abstracted view of the syntax
     */

    public String toString() {
        return getExpressionName();
    }

    /**
     * Diagnostic print of expression structure. The abstract expression tree
     * is written to the supplied output destination.
     */

    public void export(ExpressionPresenter destination) throws XPathException {
        destination.startElement("jaxpVar", this);
        destination.emitAttribute("name", name.getEQName());
        destination.endElement();
    }

}
