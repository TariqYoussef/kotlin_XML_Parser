package xmlparser.gui.action.popupmenu

import xmlparser.gui.action.IAction
import xmlparser.gui.action.RenameElementAction
import xmlparser.gui.view.ElementView
import javax.swing.JOptionPane

class RenameElementPopupMenuAction : IActionPopupMenu
{
    override val displayName: String = "Rename"
    override fun getAction(elementView: ElementView): IAction {
        val text = JOptionPane.showInputDialog("text")
        return RenameElementAction(elementView.xmlElement, text)
    }
}