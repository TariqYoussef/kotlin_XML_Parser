package xmlparser.gui.view.menuitem.element

import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.ActionStack
import xmlparser.gui.action.AddAttributeAction
import xmlparser.gui.view.ElementView
import xmlparser.gui.view.menuitem.IMenuItem
import javax.swing.JMenuItem
import javax.swing.JOptionPane

class AddAttributeMenuItem : IMenuItem<ElementView> {
    override fun menuItem(view: ElementView): JMenuItem {
        val jMenuItem = JMenuItem("Add attribute")
        jMenuItem.addActionListener {
            val text = JOptionPane.showInputDialog("text")
            ActionStack.doAction(AddAttributeAction(view.xmlElement, XmlElementAttribute(text)))
        }
        return jMenuItem
    }
}