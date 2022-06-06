package xmlparser.gui.view.menuitem.attribute

import xmlparser.gui.ActionStack
import xmlparser.gui.action.RemoveAttributeAction
import xmlparser.gui.view.AttributeView
import xmlparser.gui.view.menuitem.IMenuItem
import javax.swing.JMenuItem

class RemoveAttributeMenuItem : IMenuItem<AttributeView> {
    override fun menuItem(view: AttributeView): JMenuItem {
        val jMenuItem = JMenuItem("Remove Attribute")
        jMenuItem.addActionListener {
            ActionStack.doAction(RemoveAttributeAction( view.xmlElement, view.xmlElementAttribute))
        }
        return jMenuItem
    }
}