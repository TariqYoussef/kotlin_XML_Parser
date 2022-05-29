package xmlparser.gui.legacy.actions.view.add

import javafx.collections.ObservableList
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.IAction

class AddAttributeXmlEntityAction(private val attribute: XmlElementAttribute,
                                  private val  attributes: ObservableList<XmlElementAttribute>,
                                  override val name: String = "Add Attribute Entity"
) : IAction {


    override fun execute() {
        attributes.add(attribute)
    }

    override fun undo() {
        attributes.remove(attribute)
    }
}