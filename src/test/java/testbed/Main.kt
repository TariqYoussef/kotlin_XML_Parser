package testbed

import xmlparser.core.element.XmlElementAttribute
import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement


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

    val filterVisitor = FilterVisitor(){true}
    xmlContext.accept(filterVisitor)

    filterVisitor.filteredXmlElements.forEach{
        println(it)
    }
}

