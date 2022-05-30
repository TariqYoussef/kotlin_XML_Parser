package xmlparser.gui.action.popupmenu

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.IAction
import xmlparser.gui.IActionPopupMenu
import xmlparser.gui.action.AddAttributeAction
import xmlparser.gui.view.ElementView
import javax.swing.JOptionPane

class AddAttributePopupMenuAction : IActionPopupMenu
{
    override val displayName: String = "Add attribute"
    override fun getAction(elementView: ElementView): IAction {
        val text = JOptionPane.showInputDialog("text")
        return AddAttributeAction(elementView.xmlElement, XmlElementAttribute(text))
    }
}

