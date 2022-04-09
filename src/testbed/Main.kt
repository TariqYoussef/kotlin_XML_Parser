package testbed

import xml.XmlContext


fun main()
{
    val entity: Entity = Entity(1, "test")

    val xmlContext: XmlContext = XmlContext()
    xmlContext.addXmlElement(entity)
    xmlContext.addXmlElement(entity)
    xmlContext.addXmlElement(entity)
    println(xmlContext.dump(4))
}

