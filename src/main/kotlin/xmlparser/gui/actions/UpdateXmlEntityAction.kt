package xmlparser.gui.actions

import xmlparser.core.element.XmlElement
import xmlparser.gui.IAction
import xmlparser.gui.views.EditElementView

class UpdateXmlEntityAction(private val xmlElement: XmlElement,
                            private val newName: String,
                            private val newValue: String,
                            private val editElementView: EditElementView) : IAction {

    private val oldName = xmlElement.name
    private val oldValue = xmlElement.value

    override fun execute() {
        xmlElement.name = newName
        xmlElement.value = newValue
        editElementView.setContext()
    }

    override fun undo() {
        xmlElement.name = oldName
        xmlElement.value = oldValue
        editElementView.setContext()
    }
}