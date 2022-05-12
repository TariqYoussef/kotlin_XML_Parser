package xmlparser.gui.controllers

import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.observableListOf
import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.ActionStack
import xmlparser.gui.IAction
import xmlparser.gui.actions.view.add.AddXmlEntityAction
import xmlparser.gui.views.AddElementView

class AddElementController : Controller() {

    private var xmlElementFather: XmlElement? = null
    private var attributes: ObservableList<XmlElementAttribute>? = null

    private var actions = mutableListOf<IAction>()

    fun attributes() = attributes

    fun setContext(xmlElementFather: XmlElement?)
    {
        this.xmlElementFather = xmlElementFather
        this.attributes = observableListOf()
        this.find(AddElementView::class).setContext()
    }

    fun removeAttribute(xmlElementAttribute: XmlElementAttribute) {
        //val removeAttributeXmlEntityAction = RemoveAttributeXmlEntityAction(xmlElementAttribute, attributes!!)
        //actions.add(removeAttributeXmlEntityAction)
        //ActionStack.doAction(removeAttributeXmlEntityAction)
    }

    fun addAttribute(name: String, value: String) {
        val xmlElementAttribute = XmlElementAttribute(name, value)
        //val addAttributeXmlEntityAction = AddAttributeXmlEntityAction(xmlElementAttribute, attributes!!)
        //actions.add(addAttributeXmlEntityAction)
        //ActionStack.doAction(addAttributeXmlEntityAction)
    }

    fun registerAttributeUpdate(xmlElementAttribute: XmlElementAttribute, oldXmlElementAttribute: XmlElementAttribute)
    {
        //val updateAttributeXmlEntityAction = UpdateAttributeXmlEntityAction(xmlElementAttribute, oldXmlElementAttribute, attributes!!)
        //actions.add(updateAttributeXmlEntityAction)
        //ActionStack.doAction(updateAttributeXmlEntityAction)
    }

    fun createChild(name: String, value: String)
    {
        val xmlElementChild = XmlElement(name, value)
        attributes?.forEach {
            xmlElementChild.addAttribute(it)
        }
        val addXmlEntityAction = AddXmlEntityAction(this@AddElementController.find(MainController::class).context(),
            xmlElementFather,xmlElementChild)
        ActionStack.doAction(addXmlEntityAction)
    }

    fun undo() = this@AddElementController.find(MainController::class).undo()

    fun onClose()
    {
        xmlElementFather = null
        attributes = null
        actions.forEach{
            ActionStack.removeAction(it)
        }
        actions.clear()
    }
}