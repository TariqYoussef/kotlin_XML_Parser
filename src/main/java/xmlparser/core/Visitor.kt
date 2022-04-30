package xmlparser.core

import xmlparser.core.element.XmlElement

interface Visitor {
    fun visit(xmlContext: XmlContext): Boolean = true
    fun visit(xmlElement: XmlElement): Boolean = true
    fun endVisit(xmlContext: XmlContext){}
    fun endVisit(xmlElement: XmlElement){}
}