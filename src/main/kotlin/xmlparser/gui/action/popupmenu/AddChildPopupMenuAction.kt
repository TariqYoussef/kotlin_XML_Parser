package xmlparser.gui.action.popupmenu

import xmlparser.core.element.XmlElement
import xmlparser.gui.IAction
import xmlparser.gui.IActionPopupMenu
import xmlparser.gui.view.ElementView
import javax.swing.JOptionPane

class AddChildPopupMenuAction : IActionPopupMenu
{
    override val displayName: String = "Add child"
    override fun getAction(elementView: ElementView): IAction {
        val text = JOptionPane.showInputDialog("text")
        return AddChildAction(elementView.xmlElement, XmlElement(text))
    }

    class AddChildAction(private val xmlElementFather: XmlElement, private val xmlElementChild: XmlElement) : IAction {
        override val name: String = "Add child ${xmlElementChild.name}  on ${xmlElementFather.name}"

        override fun execute() {
            xmlElementFather.addChild(xmlElementChild)
        }

        override fun undo() {
            xmlElementFather.removeChild(xmlElementChild)
        }

    }
}