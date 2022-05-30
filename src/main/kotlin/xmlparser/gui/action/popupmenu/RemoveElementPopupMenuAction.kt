package xmlparser.gui.action.popupmenu

import xmlparser.gui.action.IAction
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