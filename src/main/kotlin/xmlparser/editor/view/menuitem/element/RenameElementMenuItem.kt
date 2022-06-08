package xmlparser.editor.view.menuitem.element

import xmlparser.editor.controller.ActionStack
import xmlparser.editor.action.RenameElementAction
import xmlparser.editor.view.ElementView
import xmlparser.editor.view.menuitem.IMenuItem
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