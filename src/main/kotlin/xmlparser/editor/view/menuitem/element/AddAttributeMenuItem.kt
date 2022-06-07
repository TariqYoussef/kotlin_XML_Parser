package xmlparser.editor.view.menuitem.element

import xmlparser.core.XmlAttribute
import xmlparser.editor.ActionStack
import xmlparser.editor.action.AddAttributeAction
import xmlparser.editor.view.ElementView
import xmlparser.editor.view.menuitem.IMenuItem
import javax.swing.JMenuItem
import javax.swing.JOptionPane

class AddAttributeMenuItem : IMenuItem<ElementView> {
    override fun menuItem(view: ElementView): JMenuItem {
        val jMenuItem = JMenuItem("Add attribute")
        jMenuItem.addActionListener {
            val text = JOptionPane.showInputDialog("text")
            ActionStack.doAction(AddAttributeAction(view.xmlElement, XmlAttribute(text)))
        }
        return jMenuItem
    }
}