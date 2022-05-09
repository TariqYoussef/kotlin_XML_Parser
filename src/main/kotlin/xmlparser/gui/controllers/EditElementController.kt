package xmlparser.gui.controllers

import tornadofx.Controller
import tornadofx.asObservable
import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute

class EditElementController(private val xmlElement: XmlElement) : Controller() {
    private val attributes = xmlElement.attributes().asObservable()

    fun attributes() = attributes

    fun removeAttribute(xmlElementAttribute: XmlElementAttribute) {
        attributes.remove(xmlElementAttribute)
    }

    fun addAttribute(name: String, value: String) {
        val xmlElementAttribute = XmlElementAttribute(name, value)
        attributes.add(xmlElementAttribute)
    }

    fun updateEntity(name: String, value: String) {
        updateEntityName(name)
        updateEntityValue(value)
    }

    private fun updateEntityName(name: String) {
        xmlElement.name = name
    }

    private fun updateEntityValue(value: String) {
        xmlElement.value = value
    }
}
