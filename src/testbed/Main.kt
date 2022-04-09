package testbed

import xml.XmlContext
import xml.XmlElement


fun main()
{
    val entity = Entity(1, "test")

    val xmlContext = XmlContext()

    val xmlElement = XmlElement("test", 1)
    //xmlElement.addAttribute("attribute1", "attribute")

    xmlContext.addXmlElement(xmlElement)

    xmlContext.addXmlElement(entity)
    xmlContext.addXmlElement(entity)
    xmlContext.addXmlElement(entity)
    println(xmlContext.dump(4))
}

