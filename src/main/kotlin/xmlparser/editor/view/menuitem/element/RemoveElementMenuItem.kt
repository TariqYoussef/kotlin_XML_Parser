package xmlparser.editor.view.menuitem.element

import xmlparser.editor.controller.ActionStack
import xmlparser.editor.controller.action.RemoveElementAction
import xmlparser.editor.view.ElementView
import xmlparser.editor.view.menuitem.IMenuItem
import javax.swing.JMenuItem

class RemoveElementMenuItem : IMenuItem<ElementView> {
    override fun menuItem(view: ElementView): JMenuItem {
        val jMenuItem = JMenuItem("Remove")
        jMenuItem.addActionListener {
            ActionStack.doAction(RemoveElementAction(view.xmlElement, view.xmlElement.father!!))
        }
        return jMenuItem
    }

    override fun accept(view: ElementView): Boolean = view.xmlElement.hasFather()
}