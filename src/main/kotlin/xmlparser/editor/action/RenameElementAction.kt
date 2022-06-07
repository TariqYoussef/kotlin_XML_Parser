package xmlparser.editor.action

import xmlparser.core.XmlElement

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