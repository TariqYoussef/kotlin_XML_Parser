package xmlparser.core.visitor

import xmlparser.core.IVisitor
import xmlparser.core.element.XmlElement

class FindVisitor(private val accept: (xmlElement: XmlElement) -> Boolean) : IVisitor {

    private val filteredXmlElements: MutableList<XmlElement> = mutableListOf()

    fun filteredXmlElements() = filteredXmlElements

    override fun visit(xmlElement: XmlElement): Boolean {
        if(accept(xmlElement))
        {
            filteredXmlElements.add(xmlElement.cloneWithoutChildren())
        }
        return super.visit(xmlElement)
    }
}