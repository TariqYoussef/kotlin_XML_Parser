package xmlparser.gui.view

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.ActionStack
import xmlparser.gui.Application
import xmlparser.gui.action.EditAttributeValueAction
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
            removeAll()
            createPopupMenu()
            createView()
            updateUI()
            revalidate()
            repaint()
        }
        createView()
    }

    private fun createView()
    {
        add(JLabel(xmlElementAttribute.name))
        val textField = JTextField(xmlElementAttribute.value)
        textField.addActionListener{
            ActionStack.doAction(EditAttributeValueAction(xmlElementAttribute, textField.text))
        }
        add(textField)
    }

    private fun createPopupMenu()
    {
        val popupmenu = JPopupMenu("Actions")

        application.attributeViewPopupMenuActions.forEach {
            if(it.accept(this))
            {
                val jMenuItem = JMenuItem(it.displayName)
                jMenuItem.addActionListener {_ ->
                    val action = it.action(this)
                    if(action != null)
                        ActionStack.doAction(action)
                }
                popupmenu.add(jMenuItem)
            }
        }

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@AttributeView, e.x, e.y)
            }
        })
    }
}