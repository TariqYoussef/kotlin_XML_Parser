package xmlparser.gui.view

import xmlparser.core.element.XmlElement
import xmlparser.gui.Application
import xmlparser.gui.view.component.BasicElementValueComponent
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.GridLayout
import javax.swing.*
import javax.swing.border.CompoundBorder

class ElementView(private val application: Application, val xmlElement: XmlElement) : AbstractContextView<ElementView>() {

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
        xmlElement.attributes.forEach {
            add(AttributeView(application, xmlElement, it))
        }

        var componentAdded = false
        application.elementValueViewPluginComponents.forEach {
            if(it.accept(this))
            {
                val component = it.component(this)
                if(component != null)
                    add(component)
                componentAdded = true
                return@forEach
            }
        }

        if(!componentAdded) add(BasicElementValueComponent().component(this))

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