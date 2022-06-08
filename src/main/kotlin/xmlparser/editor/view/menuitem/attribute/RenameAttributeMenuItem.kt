package xmlparser.editor.view.menuitem.attribute

import xmlparser.editor.controller.ActionStack
import xmlparser.editor.controller.action.RenameAttributeAction
import xmlparser.editor.view.AttributeView
import xmlparser.editor.view.menuitem.IMenuItem
import javax.swing.JMenuItem
import javax.swing.JOptionPane

class RenameAttributeMenuItem : IMenuItem<AttributeView> {
    override fun menuItem(view: AttributeView): JMenuItem {
        val jMenuItem = JMenuItem("Rename Attribute")
        jMenuItem.addActionListener {
            val text = JOptionPane.showInputDialog("text")
            ActionStack.doAction(RenameAttributeAction(view.xmlAttribute, text))
        }
        return jMenuItem
    }
}