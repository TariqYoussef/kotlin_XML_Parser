package xmlparser.guilegacy.actions.view.add

import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement
import xmlparser.guilegacy.IAction

class AddXmlEntityAction(private val context: XmlContext,
                         private val treeTableViewObserver: ((XmlElement) -> Unit),
                         private val xmlElementFather: XmlElement?,
                         private val xmlElement: XmlElement, override val name: String = "Add Entity"
) : IAction {
    override val displayName: String = ""
    override fun execute() {
        xmlElement.addObserverToAllChildren(treeTableViewObserver)
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