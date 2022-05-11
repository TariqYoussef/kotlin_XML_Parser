package xmlparser.gui.actions

import javafx.collections.ObservableList
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.IAction

class UpdateAttributeXmlEntityAction(private val xmlElementAttribute: XmlElementAttribute,
                                     private val oldXmlElementAttribute: XmlElementAttribute,
                                     private val  attributes: ObservableList<XmlElementAttribute>) : IAction {

    override fun execute() {
        if(attributes.contains(oldXmlElementAttribute))
            attributes.remove(oldXmlElementAttribute)
        if(!attributes.contains(xmlElementAttribute))
            attributes.add(xmlElementAttribute)
    }

    override fun undo() {
        if(attributes.contains(xmlElementAttribute))
            attributes.remove(xmlElementAttribute)
        if(!attributes.contains(oldXmlElementAttribute))
            attributes.add(oldXmlElementAttribute)
    }
}