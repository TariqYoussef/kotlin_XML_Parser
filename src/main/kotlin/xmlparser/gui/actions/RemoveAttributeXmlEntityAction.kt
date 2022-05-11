package xmlparser.gui.actions

import javafx.collections.ObservableList
import xmlparser.core.element.XmlElementAttribute

class RemoveAttributeXmlEntityAction(private val attribute: XmlElementAttribute,
                                     private val  attributes: ObservableList<XmlElementAttribute>) : IAction {

    override fun execute() {
        attributes.remove(attribute)
    }

    override fun undo() {
        attributes.add(attribute)
    }
}