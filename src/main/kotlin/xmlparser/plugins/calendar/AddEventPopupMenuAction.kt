package xmlparser.plugins.calendar

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.action.AddChildAction
import xmlparser.gui.action.IAction
import xmlparser.gui.action.popupmenu.IActionPopupMenu
import xmlparser.gui.view.ElementView
import javax.swing.*

class AddEventPopupMenuAction : IActionPopupMenu<ElementView> {
    override val displayName: String = "Add Event"

    override fun accept(view: ElementView): Boolean = view.xmlElement.name == "Calendar"

    override fun action(view: ElementView): IAction? {
        val textFieldDescription = JTextField(5)
        val jCheckBox = JCheckBox("Mandatory")
        val jPanel = JPanel()

        jPanel.add(JLabel("Description:"))
        jPanel.add(textFieldDescription)
        jPanel.add(Box.createHorizontalStrut(15))

        jPanel.add(jCheckBox)
        val result = JOptionPane.showConfirmDialog(
            null, jPanel,
            "Please Enter Event Information", JOptionPane.OK_CANCEL_OPTION
        )
        return if (result == JOptionPane.OK_OPTION) {
            val xmlElement = XmlElement("Event")
            xmlElement.addAttribute(XmlElementAttribute("Mandatory", jCheckBox.isSelected.toString()))
            xmlElement.addAttribute(XmlElementAttribute("Description", textFieldDescription.text))
            AddChildAction(view.xmlElement, xmlElement)
        }else
            null
    }

}
