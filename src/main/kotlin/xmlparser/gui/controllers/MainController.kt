package xmlparser.gui.controllers

import tornadofx.Controller
import xmlparser.core.*
import xmlparser.core.element.XmlElement

private data class Entity(private val id: Int, val name: String)
private data class Point(val x: Int, val y: Int)

@XmlElementName("ComplexEntity")
private class Complex()
{
    @XmlElementContent
    val data: String = "Data Example"
    @XmlElementIgnore
    val ignore: String = "Element to ignore"
    @XmlElementAttribute
    val attribute1: String = "Attribute content"
    @XmlElementAttribute
    @XmlElementName("SpecialAttribute")
    val attribute2: String = "Attribute content"

    private val entity: Entity = Entity(1, "1")
    private val point: Point = Point(1,1)
    private val id: Int = 1
    @XmlElementName("maps")
    val map: Map<Int, Point> = mapOf(Pair(0, Point(0, 0)), Pair(1, Point(1, 1)))
}

class MainController : Controller() {
    private val xmlContext: XmlContext = XmlContext()

    init {
        val complex = Complex()
        xmlContext.setPrincipalXmlElement(complex)
    }

    fun context() = xmlContext

    fun removeElement(xmlElement: XmlElement) {
        if(xmlElement.father() != null)
            xmlElement.father()!!.removeChild(xmlElement)
        else
            xmlContext.clearXmlElements()
    }

}