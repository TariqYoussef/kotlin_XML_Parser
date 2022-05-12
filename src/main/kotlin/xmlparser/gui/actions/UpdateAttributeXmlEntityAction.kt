package xmlparser.gui.actions

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.IAction

class UpdateAttributeXmlEntityAction(private val xmlElementAttribute: XmlElementAttribute,
                                     private val oldXmlElementAttribute: XmlElementAttribute,
                                     private val xmlElement: XmlElement) : IAction {

    override fun execute() {
        xmlElement.updateAttribute(xmlElementAttribute, xmlElementAttribute.name, xmlElementAttribute.value)
    }

    override fun undo() {
        xmlElement.updateAttribute(xmlElementAttribute, oldXmlElementAttribute.name, oldXmlElementAttribute.value)
    }
}