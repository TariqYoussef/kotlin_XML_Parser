package xmlparser.core

import xmlparser.core.element.XmlElement

interface IVisitor {
    /**
     * Visit.
     */
    fun visit(xmlContext: XmlContext): Boolean = true
    /**
     * Visit.
     */
    fun visit(xmlElement: XmlElement): Boolean = true
    /**
     * End Visit.
     */
    fun endVisit(xmlContext: XmlContext){}
    /**
     * End Visit.
     */
    fun endVisit(xmlElement: XmlElement){}
}