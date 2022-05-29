package xmlparser.gui.action

import xmlparser.core.element.XmlElement
import xmlparser.gui.IAction

class EditElementValueAction(private val xmlElement: XmlElement, private val newValue: String) : IAction {
    override val name: String = ""

    private val oldValue: String = xmlElement.value as String

    override fun execute() {
        xmlElement.value = newValue
    }

    override fun undo() {
        xmlElement.value = oldValue
    }

}