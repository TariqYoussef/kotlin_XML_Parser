package xmlparser.gui.legacy.actions.view.edit

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.IAction

class AddAttributeXmlEntityAction(private val attribute: XmlElementAttribute,
                                  private val xmlElement: XmlElement,
                                  override val name: String = "Add Attribute Entity"
) : IAction {
    override val displayName: String = ""
    override fun execute() {
        xmlElement.addAttribute(attribute)
    }

    override fun undo() {
        xmlElement.removeAttribute(attribute)
    }
}