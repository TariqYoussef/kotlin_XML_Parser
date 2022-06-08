package xmlparser.examples.plugins.musiclibrary.menuitem

import xmlparser.core.XmlElement
import xmlparser.core.XmlAttribute
import xmlparser.editor.controller.ActionStack
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
                xmlElement.addAttribute(XmlAttribute("name", nameField.text))
                xmlElement.addAttribute(XmlAttribute("Length", lengthField.text))
                ActionStack.doAction(AddChildAction(view.xmlElement, xmlElement))
            }
        }
        return jMenuItem
    }
}