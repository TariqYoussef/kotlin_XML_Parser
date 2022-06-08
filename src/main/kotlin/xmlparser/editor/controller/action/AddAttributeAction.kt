package xmlparser.editor.controller.action

import xmlparser.core.XmlElement
import xmlparser.core.XmlAttribute

class AddAttributeAction(private val xmlElement: XmlElement, private val xmlAttribute: XmlAttribute) :
    IAction {
    override val name: String = "Add Attribute ${xmlAttribute.name} to ${xmlElement.name}"

    override fun execute() {
        xmlElement.addAttribute(xmlAttribute)
    }

    override fun undo() {
        xmlElement.removeAttribute(xmlAttribute)
    }
}