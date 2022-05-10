package xmlparser.gui.controllers

import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.observableListOf
import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.views.AddElementView

class AddElementController : Controller() {

    private var xmlElementFather: XmlElement? = null
    private var attributes: ObservableList<XmlElementAttribute>? = null

    fun attributes() = attributes

    fun setContext(xmlElementFather: XmlElement?)
    {
        this.xmlElementFather = xmlElementFather
        this.attributes = observableListOf()
        this.find(AddElementView::class).setContext()
    }

    fun removeAttribute(xmlElementAttribute: XmlElementAttribute) {
        attributes?.remove(xmlElementAttribute)
    }

    fun addAttribute(name: String, value: String) {
        val xmlElementAttribute = XmlElementAttribute(name, value)
        attributes?.add(xmlElementAttribute)
    }

    fun createChild(name: String, value: String)
    {
        val xmlElementChild = XmlElement(name, value)
        attributes?.forEach {
            xmlElementChild.addAttribute(it)
        }
        xmlElementFather!!.addChild(xmlElementChild)
    }
}