package xmlparser.core.visitors

import xmlparser.core.Visitor
import xmlparser.core.element.XmlElement

class FindVisitor(private val accept: (xmlElement: XmlElement) -> Boolean) : Visitor {

    val filteredXmlElements: MutableList<XmlElement> = mutableListOf()

    override fun visit(xmlElement: XmlElement): Boolean {
        if(accept(xmlElement))
        {
            filteredXmlElements.add(xmlElement)
        }
        return super.visit(xmlElement)
    }
}