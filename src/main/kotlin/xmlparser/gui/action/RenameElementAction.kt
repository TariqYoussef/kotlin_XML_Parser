package xmlparser.gui.action

import xmlparser.core.element.XmlElement

class RenameElementAction(private val xmlElement: XmlElement, private val newName: String) : IAction {

    private val oldName: String = xmlElement.name
    override val name: String = "Rename Element $oldName to $newName"

    override fun execute() {
        xmlElement.name = newName
    }

    override fun undo() {
        xmlElement.name = oldName
    }
}