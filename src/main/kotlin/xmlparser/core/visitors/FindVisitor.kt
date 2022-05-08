package xmlparser.core.visitors

import xmlparser.core.Visitor
import xmlparser.core.element.XmlElement

class FindVisitor(private val accept: (xmlElement: XmlElement) -> Boolean) : Visitor {

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