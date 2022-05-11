package xmlparser.gui.actions

import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement

class RemoveXmlEntityAction(private val context: XmlContext, private val xmlElement: XmlElement) : IAction {

    override fun execute() {
        if(xmlElement.father() != null)
            xmlElement.father()!!.removeChild(xmlElement)
        else
            context.clearXmlElements()
    }

    override fun undo() {
        if(xmlElement.father() != null)
            xmlElement.father()!!.addChild(xmlElement)
        else
            context.setPrincipalXmlElement(xmlElement)
    }
}