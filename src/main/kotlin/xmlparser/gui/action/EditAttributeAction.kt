package xmlparser.gui.action

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.IAction

class EditAttributeAction(private val xmlElement: XmlElement,
                          private val xmlElementAttribute: XmlElementAttribute,
                          private val newValue: String) : IAction {

    private val oldValue: String = xmlElementAttribute.value
    override val name: String = "Attribute value from $oldValue to $newValue"
    override val displayName: String = ""

    override fun execute() {
        xmlElement.updateAttribute(xmlElementAttribute, xmlElementAttribute.name, newValue)
    }

    override fun undo() {
        xmlElement.updateAttribute(xmlElementAttribute, xmlElementAttribute.name, oldValue)
    }
}