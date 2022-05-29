package xmlparser.gui.legacy.actions.view.edit

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.IAction

class RemoveAttributeXmlEntityAction(private val attribute: XmlElementAttribute,
                                     private val xmlElement: XmlElement,
                                     override val name: String = "Remove Attribute Entity"
) : IAction {

    override fun execute() {
        xmlElement.removeAttribute(attribute)
    }

    override fun undo() {
        xmlElement.addAttribute(attribute)
    }
}