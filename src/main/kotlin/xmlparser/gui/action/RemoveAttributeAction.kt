package xmlparser.gui.action

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute

class RemoveAttributeAction(private val xmlElement: XmlElement, private val xmlElementAttribute: XmlElementAttribute) :
    IAction {
    override val name: String = "Remove Attribute ${xmlElementAttribute.name} from ${xmlElement.name}"

    override fun execute() {
        xmlElement.removeAttribute(xmlElementAttribute)
    }

    override fun undo() {
        xmlElement.addAttribute(xmlElementAttribute)
    }
}