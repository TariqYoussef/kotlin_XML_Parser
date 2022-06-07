package xmlparser.editor.action

import xmlparser.core.XmlElement

class EditElementValueAction(private val xmlElement: XmlElement, private val newValue: String) : IAction {

    private val oldValue: String = xmlElement.value as String

    override val name: String = "Edit Element Value to $newValue from $oldValue"

    override fun execute() {
        xmlElement.value = newValue
    }

    override fun undo() {
        xmlElement.value = oldValue
    }

}