package xmlparser.gui.view

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.Application
import xmlparser.gui.view.component.BasicAttributeComponent
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
        application.attributePluginComponents.forEach {
            if(it.accept(this))
            {
                it.draw(this)
                return
            }
        }

        val basicAttributeComponent = BasicAttributeComponent()
        basicAttributeComponent.draw(this)
    }

}