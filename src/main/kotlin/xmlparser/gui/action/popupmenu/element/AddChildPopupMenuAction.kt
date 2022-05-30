package xmlparser.gui.action.popupmenu.element

import xmlparser.core.element.XmlElement
import xmlparser.gui.action.IAction
import xmlparser.gui.action.AddChildAction
import xmlparser.gui.action.popupmenu.IActionPopupMenu
import xmlparser.gui.view.ElementView
import javax.swing.JOptionPane

class AddChildPopupMenuAction : IActionPopupMenu<ElementView>
{
    override val displayName: String = "Add child"
    override fun getAction(view: ElementView): IAction {
        val text = JOptionPane.showInputDialog("text")
        return AddChildAction(view.xmlElement, XmlElement(text))
    }

}