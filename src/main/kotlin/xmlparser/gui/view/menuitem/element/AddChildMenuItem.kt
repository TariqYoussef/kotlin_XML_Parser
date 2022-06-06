package xmlparser.gui.view.menuitem.element

import xmlparser.core.element.XmlElement
import xmlparser.gui.ActionStack
import xmlparser.gui.action.AddChildAction
import xmlparser.gui.view.ElementView
import xmlparser.gui.view.menuitem.IMenuItem
import javax.swing.JMenuItem
import javax.swing.JOptionPane

class AddChildMenuItem : IMenuItem<ElementView> {
    override fun menuItem(view: ElementView): JMenuItem {
        val jMenuItem = JMenuItem("Add child")
        jMenuItem.addActionListener {
            val text = JOptionPane.showInputDialog("text")
            ActionStack.doAction(AddChildAction(view.xmlElement, XmlElement(text)))
        }
        return jMenuItem
    }
}