package xmlparser.core.type

import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement

fun Iterable<Any>.createXmlElement(xmlContext: XmlContext, elementName: String = "iterable"): XmlElement
{
    val xmlElement = XmlElement(elementName)
    this.forEach{
        xmlContext.addXmlElementChild(xmlElement, it, "item")
    }
    return xmlElement
}