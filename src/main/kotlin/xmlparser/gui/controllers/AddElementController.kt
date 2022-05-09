package xmlparser.gui.controllers

import tornadofx.Controller
import tornadofx.observableListOf
import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute

class AddElementController : Controller() {

    private var xmlElementFather: XmlElement? = null
    private val attributes = observableListOf<XmlElementAttribute>()

    fun attributes() = attributes

    fun setElementFather(xmlElementFather: XmlElement?)
    {
        this.xmlElementFather = xmlElementFather
    }


    fun removeAttribute(xmlElementAttribute: XmlElementAttribute) {
        attributes.remove(xmlElementAttribute)
    }

    fun addAttribute(name: String, value: String) {
        val xmlElementAttribute = XmlElementAttribute(name, value)
        attributes.add(xmlElementAttribute)
    }

    fun createChild(name: String, value: String)
    {
        val xmlElementChild = XmlElement(name, value)
        attributes.forEach {
            xmlElementChild.addAttribute(it)
        }
        xmlElementFather!!.addChild(xmlElementChild)
    }
}