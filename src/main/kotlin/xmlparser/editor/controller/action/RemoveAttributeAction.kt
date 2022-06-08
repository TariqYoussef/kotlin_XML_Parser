package xmlparser.editor.controller.action

import xmlparser.core.XmlElement
import xmlparser.core.XmlAttribute

class RemoveAttributeAction(private val xmlElement: XmlElement, private val xmlAttribute: XmlAttribute) :
    IAction {
    override val name: String = "Remove Attribute ${xmlAttribute.name} from ${xmlElement.name}"

    override fun execute() {
        xmlElement.removeAttribute(xmlAttribute)
    }

    override fun undo() {
        xmlElement.addAttribute(xmlAttribute)
    }
}