package testbed

import xml.element.XmlElementAttribute
import xml.XmlContext
import xml.element.XmlElement


fun main()
{
    val xmlContext = XmlContext()

    val xmlElement = XmlElement("test", 1)
    xmlElement.addAttribute(XmlElementAttribute("attribute", "attribute"))
    xmlContext.addXmlElement(xmlElement)

    val entity = Entity(1, "test")
    xmlContext.addXmlElement(entity)

    val point2 = Point2(Point(0,0), Point(5,5))
    xmlContext.addXmlElement(point2)

    println(xmlContext.dump(4))
}

