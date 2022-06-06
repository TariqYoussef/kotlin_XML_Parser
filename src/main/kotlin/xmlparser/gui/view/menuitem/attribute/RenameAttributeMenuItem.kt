package xmlparser.gui.view.menuitem.attribute

import xmlparser.gui.ActionStack
import xmlparser.gui.action.RenameAttributeAction
import xmlparser.gui.view.AttributeView
import xmlparser.gui.view.menuitem.IMenuItem
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