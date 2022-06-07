package xmlparser.editor.action

import xmlparser.core.element.XmlAttribute

class RenameAttributeAction(private val xmlAttribute: XmlAttribute,
                            private val newName: String) : IAction{

    private val oldName: String = xmlAttribute.name
    override val name: String = "Rename attribute $oldName to $newName"

    override fun execute() {
        xmlAttribute.name = newName
    }

    override fun undo() {
        xmlAttribute.name = oldName
    }
}