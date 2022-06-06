package xmlparser.gui.view.menuitem.element

import xmlparser.gui.ActionStack
import xmlparser.gui.action.RenameElementAction
import xmlparser.gui.view.ElementView
import xmlparser.gui.view.menuitem.IMenuItem
import javax.swing.JMenuItem
import javax.swing.JOptionPane

class RenameElementMenuItem : IMenuItem<ElementView> {
    override fun menuItem(view: ElementView): JMenuItem {
        val jMenuItem = JMenuItem("Rename")
        jMenuItem.addActionListener {
            val text = JOptionPane.showInputDialog("text")
            ActionStack.doAction(RenameElementAction(view.xmlElement, text))
        }
        return jMenuItem
    }
}