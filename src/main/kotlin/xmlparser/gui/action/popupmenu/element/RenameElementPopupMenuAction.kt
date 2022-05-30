package xmlparser.gui.action.popupmenu.element

import xmlparser.gui.action.IAction
import xmlparser.gui.action.RenameElementAction
import xmlparser.gui.action.popupmenu.IActionPopupMenu
import xmlparser.gui.view.ElementView
import javax.swing.JOptionPane

class RenameElementPopupMenuAction : IActionPopupMenu<ElementView>
{
    override val displayName: String = "Rename"
    override fun action(view: ElementView): IAction {
        val text = JOptionPane.showInputDialog("text")
        return RenameElementAction(view.xmlElement, text)
    }
}