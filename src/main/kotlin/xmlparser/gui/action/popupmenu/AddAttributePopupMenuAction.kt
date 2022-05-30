package xmlparser.gui.action.popupmenu

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.IAction
import xmlparser.gui.IActionPopupMenu
import xmlparser.gui.view.ElementView
import javax.swing.JOptionPane

class AddAttributePopupMenuAction : IActionPopupMenu
{
    override val displayName: String = "Add attribute"
    override fun getAction(elementView: ElementView): IAction {
        val text = JOptionPane.showInputDialog("text")
        return AddAttributeAction(elementView.xmlElement, XmlElementAttribute(text))
    }

    class AddAttributeAction(private val xmlElement: XmlElement, private val xmlElementAttribute: XmlElementAttribute) : IAction {
        override val name: String = "Add Attribute ${xmlElementAttribute.name} to ${xmlElement.name}"

        override fun execute() {
            xmlElement.addAttribute(xmlElementAttribute)
        }

        override fun undo() {
            xmlElement.removeAttribute(xmlElementAttribute)
        }

    }
}

