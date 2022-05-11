package xmlparser.gui.actions

import javafx.collections.ObservableList
import xmlparser.core.element.XmlElementAttribute

class AddAttributeXmlEntityAction(private val attribute: XmlElementAttribute,
                                  private val  attributes: ObservableList<XmlElementAttribute>) : IAction {
    override fun execute() {
        attributes.add(attribute)
    }

    override fun undo() {
        attributes.remove(attribute)
    }
}