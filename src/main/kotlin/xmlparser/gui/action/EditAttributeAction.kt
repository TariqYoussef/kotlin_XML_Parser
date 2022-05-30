package xmlparser.gui.action

import xmlparser.core.element.XmlElementAttribute

class EditAttributeAction(private val xmlElementAttribute: XmlElementAttribute,
                          private val newValue: String) : IAction {

    private val oldValue: String = xmlElementAttribute.value
    override val name: String = "Attribute value from $oldValue to $newValue"

    override fun execute() {
        xmlElementAttribute.value = newValue
    }

    override fun undo() {
        xmlElementAttribute.value = oldValue
    }
}