package xmlparser.gui.view

import xmlparser.core.element.XmlElement
import xmlparser.gui.ActionStack
import xmlparser.gui.action.AddChildAction
import xmlparser.gui.action.RenameElementAction
import java.awt.Color
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
        //panel.layout = GridLayout(2,1)
        //add(panel)
        //panel.add(JTextField())
        //panel.add(JTextArea())

        xmlElement.children.forEach {
            add(ElementView(it))
        }
    }

    private fun createPopupMenu() {
        val popupmenu = JPopupMenu("Actions")
        val a = JMenuItem("Add child")
        a.addActionListener {
            val text = JOptionPane.showInputDialog("Name")
            ActionStack.doAction(AddChildAction(this.xmlElement, XmlElement(text)))
        }
        popupmenu.add(a)

        val b = JMenuItem("Rename")
        b.addActionListener {
            val text = JOptionPane.showInputDialog("text")
            ActionStack.doAction(RenameElementAction(xmlElement, text))
        }
        popupmenu.add(b)

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