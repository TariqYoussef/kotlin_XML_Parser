package xmlparser.gui.controllers

import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.asObservable
import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.actions.*
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
        val addAttributeXmlEntityAction = AddAttributeXmlEntityAction(xmlElementAttribute, attributes!!)
        ActionStack.doAction(addAttributeXmlEntityAction)
    }

    fun updateEntity(name: String, value: String) {
        val updateXmlEntityAction = UpdateXmlEntityAction(xmlElement!!,
            name, value, this@EditElementController.find(EditElementView::class))
        ActionStack.doAction(updateXmlEntityAction)
        this@EditElementController.find(MainView::class).populateTreeView()
    }

    fun undo() = this@EditElementController.find(MainController::class).undo()
}
