package xmlparser.gui.actions.view.edit

import javafx.collections.ObservableList
import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.IAction

class AddAttributeXmlEntityAction(private val attribute: XmlElementAttribute,
                                  private val xmlElement: XmlElement) : IAction {
    override fun execute() {
        xmlElement.addAttribute(attribute)
    }

    override fun undo() {
        xmlElement.removeAttribute(attribute)
    }
}