package xmlparser.gui.view

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.ActionStack
import xmlparser.gui.Application
import xmlparser.gui.action.EditAttributeAction
import xmlparser.gui.action.RemoveAttributeAction
import java.awt.GridLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

class AttributeView(private val application: Application,
                    val xmlElement: XmlElement,
                    val xmlElementAttribute: XmlElementAttribute) : JPanel() {

    init {
        layout = GridLayout(1,2)
        xmlElementAttribute.addObserver {
            print("d")
            removeAll()
            createPopupMenu()
            makeElementUI()
            updateUI()
            revalidate()
            repaint()
        }
        makeElementUI()
    }

    private fun makeElementUI()
    {
        add(JLabel(xmlElementAttribute.name))
        val textField = JTextField(xmlElementAttribute.value)
        textField.addActionListener{
            ActionStack.doAction(EditAttributeAction(xmlElementAttribute, textField.text))
        }
        add(textField)
    }

    private fun createPopupMenu()
    {
        val popupmenu = JPopupMenu("Actions")
        val jMenuItem = JMenuItem("Remove")
        jMenuItem.addActionListener {
            ActionStack.doAction(RemoveAttributeAction(xmlElement, xmlElementAttribute))
        }
        popupmenu.add(jMenuItem)
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@AttributeView, e.x, e.y)
            }
        })
    }
}