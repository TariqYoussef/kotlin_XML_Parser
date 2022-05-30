package xmlparser.gui.action

import xmlparser.core.element.XmlElementAttribute

class RenameAttributeAction(private val xmlElementAttribute: XmlElementAttribute,
                            private val newName: String) : IAction{

    private val oldName: String = xmlElementAttribute.name
    override val name: String = "Rename attribute $oldName to $newName"

    override fun execute() {
        xmlElementAttribute.name = newName
    }

    override fun undo() {
        xmlElementAttribute.name = oldName
    }
}