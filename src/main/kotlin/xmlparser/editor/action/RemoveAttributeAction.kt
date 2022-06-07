package xmlparser.editor.action

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlAttribute

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