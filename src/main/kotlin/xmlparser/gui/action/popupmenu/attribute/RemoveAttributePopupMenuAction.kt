package xmlparser.gui.action.popupmenu.attribute

import xmlparser.gui.action.IAction
import xmlparser.gui.action.RemoveAttributeAction
import xmlparser.gui.action.popupmenu.IActionPopupMenu
import xmlparser.gui.view.AttributeView

class RemoveAttributePopupMenuAction : IActionPopupMenu<AttributeView> {
    override val displayName: String = "Remove Attribute"

    override fun action(view: AttributeView): IAction {
        return RemoveAttributeAction( view.xmlElement, view.xmlElementAttribute)
    }

}