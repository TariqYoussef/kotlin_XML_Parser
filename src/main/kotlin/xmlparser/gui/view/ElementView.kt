package xmlparser.gui.view

import xmlparser.core.element.XmlElement
import xmlparser.gui.ActionStack
import xmlparser.gui.Application
import xmlparser.gui.action.*
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.GridLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.border.CompoundBorder

class ElementView(private val application: Application, val xmlElement: XmlElement) : IContextView() {

    override val popupMenuName: String = "Actions"

    private val panel = JPanel()

    init {
        layout = GridLayout(2, 1)
        border = CompoundBorder(
            BorderFactory.createEmptyBorder(30, 10, 10, 10),
            BorderFactory.createLineBorder(Color.BLACK, 2, true)
        )
        xmlElement.addObserver {
            removeAll()
            createPopupMenu()
            createView()
            updateUI()
            revalidate()
            repaint()
        }
        createPopupMenu()
        createView()
    }

    override fun createView()
    {
        panel.removeAll()
        panel.layout = GridLayout(xmlElement.attributes.size + 1,1)

        xmlElement.attributes.forEach {
            this.panel.add(AttributeView(application, xmlElement, it))
        }

        val textField = JTextField(xmlElement.value.toString())
        textField.addActionListener{
            ActionStack.doAction(EditElementValueAction(xmlElement, textField.text))
        }
        panel.add(textField)
        add(panel)

        xmlElement.children.forEach {
            add(ElementView(application, it))
        }
    }

    override fun populatePopupMenu(popupMenu: JPopupMenu) {
        application.elementViewPopupMenuActions.forEach {
            if(it.accept(this))
            {
                val jMenuItem = JMenuItem(it.displayName)
                jMenuItem.addActionListener {_ ->
                    val action = it.action(this)
                    if(action != null)
                        ActionStack.doAction(action)
                }
                popupMenu.add(jMenuItem)
            }
        }
        popupMenu.addSeparator()
        application.elementViewPopupMenuPluginActions.forEach {
            if(it.accept(this))
            {
                val jMenuItem = JMenuItem(it.displayName)
                jMenuItem.addActionListener {_ ->
                    val action = it.action(this)
                    if(action != null)
                        ActionStack.doAction(action)
                }
                popupMenu.add(jMenuItem)
            }
        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Arial", Font.BOLD, 16)
        g.drawString(xmlElement.name, 10, 20)
    }
}