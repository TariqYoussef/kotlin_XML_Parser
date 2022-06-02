package xmlparser.gui.view

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.Application
import xmlparser.gui.view.component.BasicAttributePanelComponent
import java.awt.GridLayout

class AttributeView(private val application: Application,
                    val xmlElement: XmlElement,
                    val xmlElementAttribute: XmlElementAttribute) : ContextView<AttributeView>() {

    override val popupMenuName: String = "Actions"

    init {
        layout = GridLayout(1,2)
        xmlElementAttribute.addObserver {
            removeAll()
            createPopupMenu(this,
                application.attributeViewPopupMenuActions,
                application.attributeViewPluginPopupMenuActions)
            createView()
            updateUI()
            revalidate()
            repaint()
        }
        createPopupMenu(this,
            application.attributeViewPopupMenuActions,
            application.attributeViewPluginPopupMenuActions)
        createView()
    }

    override fun createView()
    {
        application.attributePanelPluginComponents.forEach {
            if(it.accept(this))
            {
                it.getComponent(this)
                return
            }
        }

        val basicAttributeComponent = BasicAttributePanelComponent()
        basicAttributeComponent.getComponent(this)
    }

}