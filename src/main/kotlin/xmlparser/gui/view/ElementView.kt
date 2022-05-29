package xmlparser.gui.view

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.ActionStack
import xmlparser.gui.action.*
import java.awt.Color
import java.awt.FlowLayout
import java.awt.Font
import java.awt.Graphics
import java.awt.GridLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.border.CompoundBorder

class ElementView(private val xmlElement: XmlElement) : JPanel() {

    private val panel = JPanel()
    init {
        layout = GridLayout(2, 1)
        border = CompoundBorder(
            BorderFactory.createEmptyBorder(30, 10, 10, 10),
            BorderFactory.createLineBorder(Color.BLACK, 2, true)
        )
        xmlElement.addObserver {
            removeAll()
            makeElementUI()
            updateUI()
            revalidate()
            repaint()
        }
        createPopupMenu()
        makeElementUI()
    }

    private fun makeElementUI()
    {
        panel.removeAll()
        panel.layout = GridLayout(xmlElement.attributes.size + 1,1)

        xmlElement.attributes.forEach {
            val panel = JPanel()
            panel.layout = GridLayout(1,3)
            panel.add(JLabel(it.name))
            val textField = JTextField(it.value)
            textField.addActionListener{ _ ->
                ActionStack.doAction(EditAttributeAction(xmlElement, it, textField.text))
            }
            panel.add(textField)
            val button = JButton("Remove")
            button.addActionListener{ _ ->
                ActionStack.doAction(RemoveAttributeAction(xmlElement, it))
            }
            panel.add(button)
            this.panel.add(panel)
        }

        val textField = JTextField(xmlElement.value.toString())
        textField.addActionListener{
            ActionStack.doAction(EditElementValueAction(xmlElement, textField.text))
        }
        panel.add(textField)
        add(panel)

        xmlElement.children.forEach {
            add(ElementView(it))
        }
    }

    private fun createPopupMenu() {
        val popupmenu = JPopupMenu("Actions")
        val a = JMenuItem("Add child")
        a.addActionListener {
            val text = JOptionPane.showInputDialog("Name")
            ActionStack.doAction(AddChildAction(xmlElement, XmlElement(text)))
        }
        popupmenu.add(a)

        val b = JMenuItem("Rename")
        b.addActionListener {
            val text = JOptionPane.showInputDialog("text")
            ActionStack.doAction(RenameElementAction(xmlElement, text))
        }
        popupmenu.add(b)

        val c = JMenuItem("Add attribute")
        c.addActionListener {
            val text = JOptionPane.showInputDialog("text")
            ActionStack.doAction(AddAttributeAction(xmlElement, XmlElementAttribute(text)))
        }
        popupmenu.add(c)

        if(xmlElement.hasFather())
        {
            val d = JMenuItem("Remove")
            d.addActionListener {
                ActionStack.doAction(RemoveElementAction(xmlElement, xmlElement.father!!))
            }
            popupmenu.add(d)
        }

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@ElementView, e.x, e.y)
            }
        })
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Arial", Font.BOLD, 16)
        g.drawString(xmlElement.name, 10, 20)
    }
}