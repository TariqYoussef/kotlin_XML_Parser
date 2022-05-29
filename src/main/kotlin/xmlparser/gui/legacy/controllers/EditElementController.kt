package xmlparser.gui.legacy.controllers

import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.asObservable
import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.legacy.ActionStack
import xmlparser.gui.legacy.actions.view.edit.AddAttributeXmlEntityAction
import xmlparser.gui.legacy.actions.view.edit.RemoveAttributeXmlEntityAction
import xmlparser.gui.legacy.actions.view.edit.UpdateAttributeXmlEntityAction
import xmlparser.gui.legacy.actions.view.edit.UpdateXmlEntityAction
import xmlparser.gui.legacy.views.EditElementView

class EditElementController : Controller() {

    var xmlElement: XmlElement? = null
    private set
    var attributes: ObservableList<XmlElementAttribute>? = null
    private set

    fun setContext(xmlElement: XmlElement)
    {
        this.xmlElement = xmlElement
        this.attributes = xmlElement.attributes.asObservable()
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
    fun redo() = this@EditElementController.find(MainController::class).redo()

    fun onClose()
    {
        xmlElement = null
        attributes = null
    }
}
