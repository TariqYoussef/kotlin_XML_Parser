package xmlparser.gui.action

import xmlparser.core.element.XmlElement
import xmlparser.gui.IAction

class EditElementValueAction(private val xmlElement: XmlElement, private val newValue: String) : IAction {

    private val oldValue: String = xmlElement.value as String

    override val name: String = "Edit Element Value to $newValue from $oldValue"
    override val displayName: String = ""

    override fun execute() {
        xmlElement.value = newValue
    }

    override fun undo() {
        xmlElement.value = oldValue
    }

}