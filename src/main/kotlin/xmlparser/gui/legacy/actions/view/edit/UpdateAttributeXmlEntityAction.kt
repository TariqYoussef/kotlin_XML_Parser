package xmlparser.gui.legacy.actions.view.edit

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.IAction

class UpdateAttributeXmlEntityAction(private val xmlElementAttribute: XmlElementAttribute,
                                     private val oldXmlElementAttribute: XmlElementAttribute,
                                     private val xmlElement: XmlElement,
                                     override val name: String = "Update Attribute Entity"
) : IAction {
    override val displayName: String = ""
    private val newName: String = xmlElementAttribute.name
    private val newValue: String = xmlElementAttribute.value

    override fun execute() {
        xmlElement.updateAttribute(xmlElementAttribute, newName, newValue)
    }

    override fun undo() {
        xmlElement.updateAttribute(xmlElementAttribute, oldXmlElementAttribute.name, oldXmlElementAttribute.value)
    }
}