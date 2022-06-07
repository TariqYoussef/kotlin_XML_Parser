package xmlparser.editor.view.menuitem.attribute

import xmlparser.editor.ActionStack
import xmlparser.editor.action.RemoveAttributeAction
import xmlparser.editor.view.AttributeView
import xmlparser.editor.view.menuitem.IMenuItem
import javax.swing.JMenuItem

class RemoveAttributeMenuItem : IMenuItem<AttributeView> {
    override fun menuItem(view: AttributeView): JMenuItem {
        val jMenuItem = JMenuItem("Remove Attribute")
        jMenuItem.addActionListener {
            ActionStack.doAction(RemoveAttributeAction( view.xmlElement, view.xmlAttribute))
        }
        return jMenuItem
    }
}