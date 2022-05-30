package xmlparser.gui.action.popupmenu

import xmlparser.gui.action.IAction
import xmlparser.gui.action.RemoveElementAction
import xmlparser.gui.view.ElementView

class RemoveElementPopupMenuAction : IActionPopupMenu<ElementView>
{
    override val displayName: String = "Remove"
    override fun getAction(view: ElementView): IAction {
        return RemoveElementAction(view.xmlElement, view.xmlElement.father!!)
    }

    override fun accept(view: ElementView): Boolean = view.xmlElement.hasFather()

}