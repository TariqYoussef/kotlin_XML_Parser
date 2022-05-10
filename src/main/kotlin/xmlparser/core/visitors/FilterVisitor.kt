package xmlparser.core.visitors

import xmlparser.core.IVisitor
import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement

class FilterVisitor(private val accept: (xmlElement: XmlElement) -> Boolean) : IVisitor {

    private var xmlContext: XmlContext? = null
    private var currentXmlElement: XmlElement? = null

    /**
     * Gets XmlContext.
     */
    fun xmlContext() = xmlContext

    override fun visit(xmlContext: XmlContext): Boolean {
        this.xmlContext = XmlContext(xmlContext.xmlHeader().version,
                                    xmlContext.xmlHeader().encoding,
                                    xmlContext.xmlHeader().standalone)
        return super.visit(xmlContext)
    }

    override fun endVisit(xmlContext: XmlContext) {
        this.xmlContext!!.setPrincipalXmlElement(currentXmlElement!!)
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

            true
        } else
            false
    }

    override fun endVisit(xmlElement: XmlElement) {
        if(accept(xmlElement) && currentXmlElement != null && currentXmlElement!!.hasFather())
            currentXmlElement = currentXmlElement!!.father()
        super.endVisit(xmlElement)
    }
}