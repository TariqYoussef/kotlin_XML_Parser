package xmlparser.gui.legacy.view.add

import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.IAction
import xmlparser.gui.legacy.views.AddElementView

class UpdateAttributeXmlEntityAction(private val xmlElementAttribute: XmlElementAttribute,
                                     private val oldXmlElementAttribute: XmlElementAttribute,
                                     private val addElementView: AddElementView,
                                     override val name: String = "Update Attribute Entity"
) : IAction {

    private val newName: String = xmlElementAttribute.name
    private val newValue: String = xmlElementAttribute.value

    override fun execute() {
        xmlElementAttribute.name = newName
        xmlElementAttribute.value = newValue
        addElementView.setContext()
    }

    override fun undo() {
        xmlElementAttribute.name = oldXmlElementAttribute.name
        xmlElementAttribute.value = oldXmlElementAttribute.value
        addElementView.setContext()
    }
}