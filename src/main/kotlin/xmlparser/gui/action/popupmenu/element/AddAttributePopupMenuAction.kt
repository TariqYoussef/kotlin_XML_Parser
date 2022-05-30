package xmlparser.gui.action.popupmenu.element

import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.action.IAction
import xmlparser.gui.action.AddAttributeAction
import xmlparser.gui.action.popupmenu.IActionPopupMenu
import xmlparser.gui.view.ElementView
import javax.swing.JOptionPane

class AddAttributePopupMenuAction : IActionPopupMenu<ElementView>
{
    override val displayName: String = "Add attribute"
    override fun getAction(view: ElementView): IAction {
        val text = JOptionPane.showInputDialog("text")
        return AddAttributeAction(view.xmlElement, XmlElementAttribute(text))
    }
}

