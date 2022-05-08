package xmlparser.core.type

import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement

fun Map<Any, Any>.createXmlElement(xmlContext: XmlContext, elementName: String = "map"): XmlElement
{
    val xmlElement = XmlElement(elementName)
    this.forEach{
        val xmlElementItem = XmlElement("item")
        xmlContext.addXmlElementChild(xmlElementItem, it.key, "key")
        xmlContext.addXmlElementChild(xmlElementItem, it.value, "value")
        xmlElement.addChild(xmlElementItem)
    }

    return xmlElement
}