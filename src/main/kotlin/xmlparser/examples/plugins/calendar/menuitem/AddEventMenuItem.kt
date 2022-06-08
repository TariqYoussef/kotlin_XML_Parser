package xmlparser.examples.plugins.calendar.menuitem

import xmlparser.core.XmlElement
import xmlparser.core.XmlAttribute
import xmlparser.editor.ActionStack
import xmlparser.editor.action.AddChildAction
import xmlparser.editor.view.ElementView
import xmlparser.editor.view.menuitem.IMenuItem
import java.awt.GridLayout
import java.util.*
import javax.swing.*


class AddEventMenuItem : IMenuItem<ElementView> {

    override fun accept(view: ElementView): Boolean = view.xmlElement.name == "Calendar"

    override fun menuItem(view: ElementView): JMenuItem {
        val jMenuItem = JMenuItem("Add Event")
        jMenuItem.addActionListener {
            val textFieldDescription = JTextField(5)
            val date = JSpinner(SpinnerDateModel(Date(), null, null, Calendar.DATE))
            val jCheckBox = JCheckBox("Mandatory")

            val principalPanel = JPanel()
            principalPanel.layout = GridLayout(0,1)

            val descriptionPanel = JPanel()
            descriptionPanel.layout = GridLayout(0, 2)
            descriptionPanel.add(JLabel("Description:"))
            descriptionPanel.add(textFieldDescription)
            principalPanel.add(descriptionPanel)

            val datePanel = JPanel()
            datePanel.layout = GridLayout(0, 2)
            datePanel.add(JLabel("Date:"))
            datePanel.add(date)
            principalPanel.add(datePanel)

            principalPanel.add(jCheckBox)
            val result = JOptionPane.showConfirmDialog(
                null, principalPanel,
                "Please Enter Event Information", JOptionPane.OK_CANCEL_OPTION
            )
            if (result == JOptionPane.OK_OPTION) {
                val xmlElement = XmlElement("Event")
                xmlElement.addAttribute(XmlAttribute("Description", textFieldDescription.text))
                xmlElement.addAttribute(XmlAttribute("Date", (date.value as Date).toString()))
                xmlElement.addAttribute(XmlAttribute("Mandatory", jCheckBox.isSelected.toString()))
                ActionStack.doAction(AddChildAction(view.xmlElement, xmlElement))
            }
        }
        return jMenuItem
    }

}
