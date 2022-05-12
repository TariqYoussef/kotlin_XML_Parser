package xmlparser.gui.actions.view.add

import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.IAction
import xmlparser.gui.views.AddElementView

class UpdateAttributeXmlEntityAction(private val xmlElementAttribute: XmlElementAttribute,
                                     private val oldXmlElementAttribute: XmlElementAttribute,
                                     private val addElementView: AddElementView
) : IAction {

    override fun execute() {
        xmlElementAttribute.name = xmlElementAttribute.name
        xmlElementAttribute.value = xmlElementAttribute.value
        addElementView.setContext()
    }

    override fun undo() {
        xmlElementAttribute.name = oldXmlElementAttribute.name
        xmlElementAttribute.value = oldXmlElementAttribute.value
        addElementView.setContext()
    }
}