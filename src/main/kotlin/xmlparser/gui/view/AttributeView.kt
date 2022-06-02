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
        layout = GridLayout(0,1)
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
        application.attributeViewPluginComponents.forEach {
            if(it.accept(this))
            {
                add(it.component(this))
                return
            }
        }

        add(BasicAttributeComponent().component(this))
    }

}