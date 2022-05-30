package xmlparser.gui.action.popupmenu

import xmlparser.core.element.XmlElement
import xmlparser.gui.IAction

class RemoveElementAction(private val xmlElementChild: XmlElement, private val xmlElementFather: XmlElement) : IAction {
    override val name: String = "Remove child ${xmlElementChild.name} on ${xmlElementFather.name}"

    override fun execute() {
        xmlElementFather.removeChild(xmlElementChild)
    }

    override fun undo() {
        xmlElementFather.addChild(xmlElementChild)
    }

}