package testbed

import xml.element.XmlElementAttribute
import xml.XmlContext
import xml.element.XmlElement


fun main()
{
    val entity = Entity(1, "test")

    val xmlContext = XmlContext()

    val xmlElement = XmlElement("test", 1)
    xmlElement.addAttribute(XmlElementAttribute("attribute", "attribute"))

    xmlContext.addXmlElement(xmlElement)

    xmlContext.addXmlElement(entity)
    xmlContext.addXmlElement(entity)
    xmlContext.addXmlElement(entity)
    println(xmlContext.dump(4))
}

