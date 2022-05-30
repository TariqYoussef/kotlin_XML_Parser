package xmlparser.gui.action.popupmenu

import xmlparser.core.element.XmlElement
import xmlparser.gui.IAction
import xmlparser.gui.IActionPopupMenu
import xmlparser.gui.view.ElementView

class RemoveElementPopupMenuAction : IActionPopupMenu
{
    override val displayName: String = "Remove"
    override fun getAction(elementView: ElementView): IAction {
        return RemoveElementAction(elementView.xmlElement, elementView.xmlElement.father!!)
    }

    override fun accept(elementView: ElementView): Boolean = elementView.xmlElement.hasFather()

    class RemoveElementAction(private val xmlElementChild: XmlElement, private val xmlElementFather: XmlElement) : IAction {
        override val name: String = "Remove child ${xmlElementChild.name} on ${xmlElementFather.name}"

        override fun execute() {
            xmlElementFather.removeChild(xmlElementChild)
        }

        override fun undo() {
            xmlElementFather.addChild(xmlElementChild)
        }

    }
}