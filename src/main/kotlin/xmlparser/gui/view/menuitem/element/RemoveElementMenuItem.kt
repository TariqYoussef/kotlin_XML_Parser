package xmlparser.gui.view.menuitem.element

import xmlparser.gui.ActionStack
import xmlparser.gui.action.RemoveElementAction
import xmlparser.gui.view.ElementView
import xmlparser.gui.view.menuitem.IMenuItem
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