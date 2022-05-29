package xmlparser.gui.legacy.actions.view.add

import javafx.collections.ObservableList
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.legacy.IAction

class RemoveAttributeXmlEntityAction(private val attribute: XmlElementAttribute,
                                     private val  attributes: ObservableList<XmlElementAttribute>,
                                     override val name: String = "Remove Attribute Entity"
) : IAction {
    override val displayName: String = ""

    override fun execute() {
        attributes.remove(attribute)
    }

    override fun undo() {
        attributes.add(attribute)
    }
}