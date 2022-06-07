package xmlparser.core.type

import xmlparser.core.XmlElement

fun Iterable<Any>.populateXmlElement(xmlElement: XmlElement)
{
    this.forEach{
        val child = XmlElement(it, "item")
        xmlElement.addChild(child)
    }
}