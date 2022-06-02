package xmlparser.gui.view

import xmlparser.core.element.XmlElement
import xmlparser.gui.ActionStack
import xmlparser.gui.Application
import xmlparser.gui.action.*
import xmlparser.gui.view.component.BasicElementValueComponent
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.GridLayout
import javax.swing.*
import javax.swing.border.CompoundBorder

class ElementView(private val application: Application, val xmlElement: XmlElement) : ContextView<ElementView>() {

    override val popupMenuName: String = "Actions"

    init {
        layout = GridLayout(0, 1)
        border = CompoundBorder(
            BorderFactory.createEmptyBorder(30, 10, 10, 10),
            BorderFactory.createLineBorder(Color.BLACK, 2, true)
        )
        xmlElement.addObserver {
            removeAll()
            createPopupMenu(this,
                application.elementViewPopupMenuActions,
                application.elementViewPluginPopupMenuActions)
            createView()
            updateUI()
            revalidate()
            repaint()
        }
        createPopupMenu(this,
            application.elementViewPopupMenuActions,
            application.elementViewPluginPopupMenuActions)
        createView()
    }

    override fun createView()
    {
        val panel = JPanel()
        panel.layout = GridLayout(0,1)

        xmlElement.attributes.forEach {
            panel.add(AttributeView(application, xmlElement, it))
        }

        panel.add(BasicElementValueComponent().component(this))
        add(panel)

        xmlElement.children.forEach {
            add(ElementView(application, it))
        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Arial", Font.BOLD, 16)
        g.drawString(xmlElement.name, 10, 20)
    }
}