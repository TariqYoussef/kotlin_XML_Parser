package xmlparser.gui.action.popupmenu

import xmlparser.core.element.XmlElement
import xmlparser.gui.action.IAction
import xmlparser.gui.action.AddChildAction
import xmlparser.gui.view.ElementView
import javax.swing.JOptionPane

class AddChildPopupMenuAction : IActionPopupMenu
{
    override val displayName: String = "Add child"
    override fun getAction(elementView: ElementView): IAction {
        val text = JOptionPane.showInputDialog("text")
        return AddChildAction(elementView.xmlElement, XmlElement(text))
    }

}