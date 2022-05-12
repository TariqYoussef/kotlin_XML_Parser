package xmlparser.gui.controllers

import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.asObservable
import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.ActionStack
import xmlparser.gui.actions.*
import xmlparser.gui.views.EditElementView
import xmlparser.gui.views.MainView

class EditElementController : Controller() {

    private var xmlElement: XmlElement? = null
    private var attributes: ObservableList<XmlElementAttribute>? = null

    fun attributes() = attributes
    fun element() = xmlElement

    fun setContext(xmlElement: XmlElement)
    {
        this.xmlElement = xmlElement
        this.attributes = xmlElement.attributes().asObservable()
        this.find(EditElementView::class).setContext()
    }

    fun removeAttribute(xmlElementAttribute: XmlElementAttribute) {
        val removeAttributeXmlEntityAction = RemoveAttributeXmlEntityAction(xmlElementAttribute, xmlElement!!)
        ActionStack.doAction(removeAttributeXmlEntityAction)
    }

    fun addAttribute(name: String, value: String) {
        val xmlElementAttribute = XmlElementAttribute(name, value)
        val addAttributeXmlEntityAction = AddAttributeXmlEntityAction(xmlElementAttribute,  xmlElement!!)
        ActionStack.doAction(addAttributeXmlEntityAction)
    }

    fun registerAttributeUpdate(xmlElementAttribute: XmlElementAttribute, oldXmlElementAttribute: XmlElementAttribute)
    {
        val updateAttributeXmlEntityAction = UpdateAttributeXmlEntityAction(xmlElementAttribute,
            oldXmlElementAttribute,  xmlElement!!)
        ActionStack.doAction(updateAttributeXmlEntityAction)
    }

    fun updateEntity(name: String, value: String) {
        val updateXmlEntityAction = UpdateXmlEntityAction(xmlElement!!, name, value)
        ActionStack.doAction(updateXmlEntityAction)
    }

    fun undo() = this@EditElementController.find(MainController::class).undo()

    fun onClose()
    {
        xmlElement = null
        attributes = null
    }
}
