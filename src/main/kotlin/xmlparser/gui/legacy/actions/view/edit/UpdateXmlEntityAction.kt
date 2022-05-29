package xmlparser.gui.legacy.actions.view.edit

import xmlparser.core.element.XmlElement
import xmlparser.gui.IAction

class UpdateXmlEntityAction(private val xmlElement: XmlElement,
                            private val newName: String,
                            private val newValue: String,
                            override val name: String = "Update Entity"
) : IAction {

    private val oldName = xmlElement.name
    private val oldValue: String = xmlElement.value.toString()

    override fun execute() {
        xmlElement.updateEntity(newName, newValue)
    }

    override fun undo() {
        xmlElement.updateEntity(oldName, oldValue)
    }
}