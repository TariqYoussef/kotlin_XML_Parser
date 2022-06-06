package xmlparser.editor.view

import xmlparser.core.element.XmlElement
import xmlparser.editor.Application
import xmlparser.editor.view.component.element.BasicElementValueComponent
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.GridLayout
import javax.swing.*
import javax.swing.border.CompoundBorder

class ElementView(private val application: Application, val xmlElement: XmlElement) : AbstractContextView<ElementView>() {

    override val popupMenuName: String = "Actions"

    init {
        layout = BorderLayout()
        border = CompoundBorder(
            BorderFactory.createEmptyBorder(30, 10, 10, 10),
            BorderFactory.createLineBorder(Color.BLACK, 2, true)
        )
        xmlElement.addObserver {
            removeAll()
            createPopupMenu(this,
                application.elementViewMenuItems,
                application.elementViewPluginMenuItems)
            createView()
            updateUI()
            revalidate()
            repaint()
        }
        createPopupMenu(this,
            application.elementViewMenuItems,
            application.elementViewPluginMenuItems)
        createView()
    }

    override fun createView()
    {
        val northPanel = JPanel()
        northPanel.layout = GridLayout(0, 1)
        xmlElement.attributes.forEach {
            northPanel.add(AttributeView(application, xmlElement, it))
        }
        add(northPanel, BorderLayout.NORTH)

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

        if(!componentAdded) add(BasicElementValueComponent().component(this), BorderLayout.CENTER)

        val southPanel = JPanel()
        southPanel.layout = GridLayout(0, 1)
        xmlElement.children.forEach {
            add(ElementView(application, it))
        }
        add(southPanel, BorderLayout.SOUTH)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Arial", Font.BOLD, 16)
        g.drawString(xmlElement.name, 10, 20)
    }
}