package xmlparser.editor.view.menuitem.attribute

import xmlparser.editor.ActionStack
import xmlparser.editor.action.RenameAttributeAction
import xmlparser.editor.view.AttributeView
import xmlparser.editor.view.menuitem.IMenuItem
import javax.swing.JMenuItem
import javax.swing.JOptionPane

class RenameAttributeMenuItem : IMenuItem<AttributeView> {
    override fun menuItem(view: AttributeView): JMenuItem {
        val jMenuItem = JMenuItem("Rename Attribute")
        jMenuItem.addActionListener {
            val text = JOptionPane.showInputDialog("text")
            ActionStack.doAction(RenameAttributeAction(view.xmlElementAttribute, text))
        }
        return jMenuItem
    }
}