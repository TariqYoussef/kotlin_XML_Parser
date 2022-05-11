package xmlparser.gui.controllers

import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.asObservable
import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.actions.ActionStack
import xmlparser.gui.actions.RemoveAttributeXmlEntityAction
import xmlparser.gui.actions.RemoveXmlEntityAction
import xmlparser.gui.views.EditElementView
import xmlparser.gui.views.MainView

class EditElementController : Controller() {

    private var xmlElement: XmlElement? = null
    private var attributes: ObservableList<XmlElementAttribute>? = null

    fun attributes() = attributes
    fun element() = xmlElement

    fun setContext(xmlElement: XmlElement?)
    {
        this.xmlElement = xmlElement
        this.attributes = xmlElement?.attributes()?.asObservable()
        this.find(EditElementView::class).setContext()
    }

    fun removeAttribute(xmlElementAttribute: XmlElementAttribute) {
        val removeAttributeXmlEntityAction = RemoveAttributeXmlEntityAction(xmlElementAttribute, attributes!!)
        ActionStack.doAction(removeAttributeXmlEntityAction)
    }

    fun addAttribute(name: String, value: String) {
        val xmlElementAttribute = XmlElementAttribute(name, value)
        attributes?.add(xmlElementAttribute)
    }

    fun updateEntity(name: String, value: String) {
        updateEntityName(name)
        updateEntityValue(value)
        this@EditElementController.find(MainView::class).populateTreeView()
    }

    private fun updateEntityName(name: String) {
        xmlElement?.name = name
    }

    private fun updateEntityValue(value: String) {
        xmlElement?.value = value
    }
}
