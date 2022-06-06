package xmlparser.plugins.musiclibrary.menuitem

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.editor.ActionStack
import xmlparser.editor.action.AddChildAction
import xmlparser.editor.view.ElementView
import xmlparser.editor.view.menuitem.IMenuItem
import javax.swing.*

class AddAlbumMenuItem : IMenuItem<ElementView> {

    override fun accept(view: ElementView): Boolean = view.xmlElement.name == "Albums"

    override fun menuItem(view: ElementView): JMenuItem {
        val jMenuItem = JMenuItem("Add Album")
        jMenuItem.addActionListener {
            val nameField = JTextField(5)
            val artistField = JTextField(5)

            val jPanel = JPanel()

            jPanel.add(JLabel("Name:"))
            jPanel.add(nameField)
            jPanel.add(Box.createHorizontalStrut(15))

            jPanel.add(JLabel("Artist:"))
            jPanel.add(artistField)
            val result = JOptionPane.showConfirmDialog(
                null, jPanel,
                "Please Enter Album Information", JOptionPane.OK_CANCEL_OPTION
            )
            if (result == JOptionPane.OK_OPTION) {
                val xmlElement = XmlElement("Album")
                xmlElement.addAttribute(XmlElementAttribute("name", nameField.text))
                xmlElement.addAttribute(XmlElementAttribute("artist", artistField.text))
                ActionStack.doAction(AddChildAction(view.xmlElement, xmlElement))
            }
        }
        return jMenuItem
    }
}