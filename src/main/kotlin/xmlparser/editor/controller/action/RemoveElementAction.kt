package xmlparser.editor.controller.action

import xmlparser.core.XmlElement

class RemoveElementAction(private val xmlElementChild: XmlElement, private val xmlElementFather: XmlElement) : IAction {
    override val name: String = "Remove child ${xmlElementChild.name} on ${xmlElementFather.name}"

    override fun execute() {
        xmlElementFather.removeChild(xmlElementChild)
    }

    override fun undo() {
        xmlElementFather.addChild(xmlElementChild)
    }

}