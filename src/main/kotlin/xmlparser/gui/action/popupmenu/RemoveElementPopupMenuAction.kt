package xmlparser.gui.action.popupmenu

import xmlparser.core.element.XmlElement
import xmlparser.gui.IAction
import xmlparser.gui.IActionPopupMenu
import xmlparser.gui.action.RemoveElementAction
import xmlparser.gui.view.ElementView

class RemoveElementPopupMenuAction : IActionPopupMenu
{
    override val displayName: String = "Remove"
    override fun getAction(elementView: ElementView): IAction {
        return RemoveElementAction(elementView.xmlElement, elementView.xmlElement.father!!)
    }

    override fun accept(elementView: ElementView): Boolean = elementView.xmlElement.hasFather()

}