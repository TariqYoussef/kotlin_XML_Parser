package xmlparser.core.type

import xmlparser.core.element.XmlElement

fun Iterable<Any>.createXmlElement(xmlElement: XmlElement)
{
    this.forEach{
        val child = XmlElement(it, "item")
        xmlElement.addChild(child)
    }
}