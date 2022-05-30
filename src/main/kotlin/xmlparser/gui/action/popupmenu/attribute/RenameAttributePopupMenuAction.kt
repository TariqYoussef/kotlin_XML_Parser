package xmlparser.gui.action.popupmenu.attribute

import xmlparser.gui.action.IAction
import xmlparser.gui.action.RenameAttributeAction
import xmlparser.gui.action.popupmenu.IActionPopupMenu
import xmlparser.gui.view.AttributeView
import javax.swing.JOptionPane

class RenameAttributePopupMenuAction : IActionPopupMenu<AttributeView> {
    override val displayName: String = "Rename Attribute"

    override fun action(view: AttributeView): IAction {
        val text = JOptionPane.showInputDialog("text")
        return RenameAttributeAction(view.xmlElementAttribute, text)
    }
}