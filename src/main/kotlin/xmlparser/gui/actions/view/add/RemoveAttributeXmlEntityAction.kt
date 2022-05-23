package xmlparser.gui.actions.view.add

import javafx.collections.ObservableList
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.IAction

class RemoveAttributeXmlEntityAction(private val attribute: XmlElementAttribute,
                                     private val  attributes: ObservableList<XmlElementAttribute>,
                                     override val name: String = "Remove Attribute Entity"
) : IAction {

    override fun execute() {
        attributes.remove(attribute)
    }

    override fun undo() {
        attributes.add(attribute)
    }
}