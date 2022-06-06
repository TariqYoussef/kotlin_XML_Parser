package xmlparser.plugins.musiclibrary.menuitem

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.editor.ActionStack
import xmlparser.editor.action.AddChildAction
import xmlparser.editor.view.ElementView
import xmlparser.editor.view.menuitem.IMenuItem
import javax.swing.*

class AddSongMenuItem : IMenuItem<ElementView> {

    override fun accept(view: ElementView): Boolean = view.xmlElement.name == "Album"

    override fun menuItem(view: ElementView): JMenuItem {
        val jMenuItem = JMenuItem("Add Song")
        jMenuItem.addActionListener {
            val nameField = JTextField(5)
            val lengthField = JTextField(5)

            val jPanel = JPanel()

            jPanel.add(JLabel("Name:"))
            jPanel.add(nameField)
            jPanel.add(Box.createHorizontalStrut(15))

            jPanel.add(JLabel("Length:"))
            jPanel.add(lengthField)
            val result = JOptionPane.showConfirmDialog(
                null, jPanel,
                "Please Enter Album Information", JOptionPane.OK_CANCEL_OPTION
            )
            if (result == JOptionPane.OK_OPTION) {
                val xmlElement = XmlElement("Song")
                xmlElement.addAttribute(XmlElementAttribute("name", nameField.text))
                xmlElement.addAttribute(XmlElementAttribute("Length", lengthField.text))
                ActionStack.doAction(AddChildAction(view.xmlElement, xmlElement))
            }
        }
        return jMenuItem
    }
}