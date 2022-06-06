package xmlparser.editor.action

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute

class AddAttributeAction(private val xmlElement: XmlElement, private val xmlElementAttribute: XmlElementAttribute) :
    IAction {
    override val name: String = "Add Attribute ${xmlElementAttribute.name} to ${xmlElement.name}"

    override fun execute() {
        xmlElement.addAttribute(xmlElementAttribute)
    }

    override fun undo() {
        xmlElement.removeAttribute(xmlElementAttribute)
    }
}