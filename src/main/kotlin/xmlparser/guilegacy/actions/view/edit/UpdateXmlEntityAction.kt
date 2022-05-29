package xmlparser.guilegacy.actions.view.edit

import xmlparser.core.element.XmlElement
import xmlparser.guilegacy.IAction

class UpdateXmlEntityAction(private val xmlElement: XmlElement,
                            private val newName: String,
                            private val newValue: String,
                            override val name: String = "Update Entity"
) : IAction {
    override val displayName: String = ""
    private val oldName = xmlElement.name
    private val oldValue: String = xmlElement.value.toString()

    override fun execute() {
        xmlElement.updateEntity(newName, newValue)
    }

    override fun undo() {
        xmlElement.updateEntity(oldName, oldValue)
    }
}