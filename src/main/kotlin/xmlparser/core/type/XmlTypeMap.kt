package xmlparser.core.type

import xmlparser.core.XmlElement

fun Map<Any, Any>.createXmlElement(xmlElement: XmlElement)
{
    this.forEach{
        val xmlElementItem = XmlElement("item")
        xmlElementItem.addChild(XmlElement(it.key, "key"))
        xmlElementItem.addChild(XmlElement(it.value, "value"))
        xmlElement.addChild(xmlElementItem)
    }
}