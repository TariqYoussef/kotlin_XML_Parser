package xmlparser.editor.action

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlAttribute

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