package xmlparser.gui.actions.view.add

import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement
import xmlparser.gui.IAction

class AddXmlEntityAction(private val context: XmlContext,
                         private val xmlElementFather: XmlElement?,
                         private val xmlElement: XmlElement) : IAction {

    override fun execute() {
        if(xmlElementFather != null)
            xmlElementFather.addChild(xmlElement)
        else
            context.setRootXmlElement(xmlElement)
    }

    override fun undo() {
        if(xmlElementFather != null)
            xmlElementFather.removeChild(xmlElement)
        else
            context.clearXmlElements()
    }
}