package xmlparser.gui.action

import xmlparser.core.element.XmlElement
import xmlparser.gui.IAction

class AddChildAction(private val xmlElementFather: XmlElement, private val xmlElementChild: XmlElement) : IAction {
    override val name: String = "Add child ${xmlElementChild.name}  on ${xmlElementFather.name}"

    override fun execute() {
        xmlElementFather.addChild(xmlElementChild)
    }

    override fun undo() {
        xmlElementFather.removeChild(xmlElementChild)
    }
}