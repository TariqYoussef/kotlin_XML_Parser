package xmlparser.gui.action.popupmenu

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.IAction

class AddAttributeAction(private val xmlElement: XmlElement, private val xmlElementAttribute: XmlElementAttribute) : IAction {
    override val name: String = "Add Attribute ${xmlElementAttribute.name} to ${xmlElement.name}"

    override fun execute() {
        xmlElement.addAttribute(xmlElementAttribute)
    }

    override fun undo() {
        xmlElement.removeAttribute(xmlElementAttribute)
    }

}