package xmlparser.examples.plugins.musiclibrary.menuitem

import xmlparser.core.XmlElement
import xmlparser.core.XmlAttribute
import xmlparser.editor.controller.ActionStack
import xmlparser.editor.controller.action.AddChildAction
import xmlparser.editor.view.ElementView
import xmlparser.editor.view.menuitem.IMenuItem
import java.awt.GridLayout
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter


class AddAlbumMenuItem : IMenuItem<ElementView> {

    override fun accept(view: ElementView): Boolean = view.xmlElement.name == "MusicLibrary"

    override fun menuItem(view: ElementView): JMenuItem {
        val jMenuItem = JMenuItem("Add Album")
        jMenuItem.addActionListener {
            val nameField = JTextField(5)
            val artistField = JTextField(5)
            val fileButton = JButton("Choose Cover")
            val filePathLabel = JLabel()
            fileButton.addActionListener {
                val fileChooser = JFileChooser()
                val filter = FileNameExtensionFilter("Photo", "png", "jpeg")
                fileChooser.fileFilter = filter
                val option = fileChooser.showSaveDialog(view)
                if (option == JFileChooser.APPROVE_OPTION) {
                    filePathLabel.text = fileChooser.selectedFile.path
                }
            }

            val jPanel = JPanel()
            jPanel.layout = GridLayout(0,2)

            jPanel.add(JLabel("Name:"))
            jPanel.add(nameField)

            jPanel.add(JLabel("Artist:"))
            jPanel.add(artistField)

            jPanel.add(filePathLabel)
            jPanel.add(fileButton)
            val result = JOptionPane.showConfirmDialog(
                null, jPanel,
                "Please Enter Album Information", JOptionPane.OK_CANCEL_OPTION
            )
            if (result == JOptionPane.OK_OPTION) {
                val xmlElement = XmlElement("Album")
                xmlElement.addAttribute(XmlAttribute("name", nameField.text))
                xmlElement.addAttribute(XmlAttribute("artist", artistField.text))
                xmlElement.value = filePathLabel.text
                ActionStack.doAction(AddChildAction(view.xmlElement, xmlElement))
            }
        }
        return jMenuItem
    }
}