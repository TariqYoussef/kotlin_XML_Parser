package xmlparser.core.visitors

import xmlparser.core.Visitor
import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement

class FilterVisitor(private val accept: (xmlElement: XmlElement) -> Boolean) : Visitor {

    var xmlContext: XmlContext? = null
    val xmlElements: MutableList<XmlElement> = mutableListOf()
    var currentXmlElement: XmlElement? = null
    var depth: Int = 0
    override fun visit(xmlContext: XmlContext): Boolean {
        this.xmlContext = XmlContext(xmlContext.xmlHeader.version,
                                    xmlContext.xmlHeader.encoding,
                                    xmlContext.xmlHeader.standalone)
        return super.visit(xmlContext)
    }

    override fun endVisit(xmlContext: XmlContext) {
        if(currentXmlElement!=null)
            this.xmlContext!!.xmlElements.add(currentXmlElement!!)
        super.endVisit(xmlContext)
    }

    override fun visit(xmlElement: XmlElement): Boolean {
        if(this.xmlContext == null)
            return false

        if(currentXmlElement == null)
        {
            currentXmlElement = xmlElement.cloneWithoutChildren()
        }

        depth++
        if(accept(xmlElement))
        {
            if(currentXmlElement != null)
                currentXmlElement!!.addChild(xmlElement)

            xmlElements.add(xmlElement.cloneWithoutChildren())
        }

        if(xmlElement.children.isNotEmpty())
            currentXmlElement = xmlElement.cloneWithoutChildren()

        return super.visit(xmlElement)
    }

    override fun endVisit(xmlElement: XmlElement) {
        depth--
        super.endVisit(xmlElement)
    }
}