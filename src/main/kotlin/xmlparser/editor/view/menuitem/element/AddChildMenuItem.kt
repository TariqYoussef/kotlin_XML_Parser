package xmlparser.editor.view.menuitem.element

import xmlparser.core.element.XmlElement
import xmlparser.editor.ActionStack
import xmlparser.editor.action.AddChildAction
import xmlparser.editor.view.ElementView
import xmlparser.editor.view.menuitem.IMenuItem
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