package xmlparser.gui.view

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.ActionStack
import xmlparser.gui.Application
import xmlparser.gui.view.component.BasicAttributeComponent
import java.awt.GridLayout
import javax.swing.*

class AttributeView(private val application: Application,
                    val xmlElement: XmlElement,
                    val xmlElementAttribute: XmlElementAttribute) : IContextView() {

    override val popupMenuName: String = "Actions"

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
        createPopupMenu()
        createView()
    }

    override fun createView()
    {
        application.attributePluginComponents.forEach {
            if(it.accept(this))
            {
                it.draw(this)
                return@forEach
            }
        }

        val basicAttributeComponent = BasicAttributeComponent()
        basicAttributeComponent.draw(this)
    }

    override fun populatePopupMenu(popupMenu: JPopupMenu) {
        application.attributeViewPopupMenuActions.forEach {
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
        application.attributeViewPluginPopupMenuActions.forEach {
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

}