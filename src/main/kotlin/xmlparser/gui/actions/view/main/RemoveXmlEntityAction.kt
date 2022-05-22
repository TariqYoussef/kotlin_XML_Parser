package xmlparser.gui.actions.view.main

import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement
import xmlparser.gui.IAction

class RemoveXmlEntityAction(private val context: XmlContext,
                            private val treeTableViewObserver: ((XmlElement) -> Unit),
                            private val populateTreeView: () -> Unit,
                            private val xmlElement: XmlElement) : IAction {

    private var fatherXmlElement: XmlElement? = null
    override fun execute() {
        if(xmlElement.father() != null)
        {
            fatherXmlElement = xmlElement.father()
            xmlElement.father()!!.removeChild(xmlElement)
        }
        else
        {
            context.clearXmlElements()
            populateTreeView()
        }
    }

    override fun undo() {
        xmlElement.addObserverToAllChildren( treeTableViewObserver )
        if(fatherXmlElement != null)
            fatherXmlElement!!.addChild(xmlElement)
        else
            context.setRootXmlElement(xmlElement)
    }
}