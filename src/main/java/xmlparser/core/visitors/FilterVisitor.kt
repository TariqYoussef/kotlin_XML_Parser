package xmlparser.core.visitors

import xmlparser.core.Visitor
import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement

class FilterVisitor(private val accept: (xmlElement: XmlElement) -> Boolean) : Visitor {

    var xmlContext: XmlContext? = null
    var currentXmlElement: XmlElement? = null

    override fun visit(xmlContext: XmlContext): Boolean {
        this.xmlContext = XmlContext(xmlContext.xmlHeader.version,
                                    xmlContext.xmlHeader.encoding,
                                    xmlContext.xmlHeader.standalone)
        return super.visit(xmlContext)
    }

    override fun endVisit(xmlContext: XmlContext) {
        this.xmlContext!!.principalXmlElement = currentXmlElement
        super.endVisit(xmlContext)
    }

    override fun visit(xmlElement: XmlElement): Boolean {
        if(this.xmlContext == null)
            return false

        val withoutChildrenXmlElement: XmlElement = xmlElement.cloneWithoutChildren()
        return if(accept(xmlElement))
        {
            if(currentXmlElement != null)
                currentXmlElement!!.addChild(withoutChildrenXmlElement)

            currentXmlElement = withoutChildrenXmlElement

            super.visit(xmlElement)
        } else
            false
    }


    override fun endVisit(xmlElement: XmlElement) {
        if(currentXmlElement != null && currentXmlElement!!.hasFather())
            currentXmlElement = currentXmlElement!!.father()
        super.endVisit(xmlElement)
    }
}