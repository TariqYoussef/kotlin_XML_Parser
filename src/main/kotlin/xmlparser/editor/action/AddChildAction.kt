package xmlparser.editor.action

import xmlparser.core.XmlElement

class AddChildAction(private val xmlElementFather: XmlElement, private val xmlElementChild: XmlElement) : IAction {
    override val name: String = "Add child ${xmlElementChild.name}  on ${xmlElementFather.name}"

    override fun execute() {
        xmlElementFather.addChild(xmlElementChild)
    }

    override fun undo() {
        xmlElementFather.removeChild(xmlElementChild)
    }
}