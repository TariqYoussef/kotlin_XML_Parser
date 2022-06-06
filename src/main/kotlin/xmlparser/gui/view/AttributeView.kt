package xmlparser.gui.view

import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.Application
import xmlparser.gui.view.component.attribute.BasicAttributeComponent
import java.awt.GridLayout

class AttributeView(private val application: Application,
                    val xmlElement: XmlElement,
                    val xmlElementAttribute: XmlElementAttribute) : AbstractContextView<AttributeView>() {

    override val popupMenuName: String = "Actions"

    init {
        layout = GridLayout(0,1)
        xmlElementAttribute.addObserver {
            removeAll()
            createPopupMenu(this,
                application.attributeViewMenuItems,
                application.attributeViewPluginMenuItems)
            createView()
            updateUI()
            revalidate()
            repaint()
        }
        createPopupMenu(this,
            application.attributeViewMenuItems,
            application.attributeViewPluginMenuItems)
        createView()
    }

    override fun createView()
    {
        application.attributeViewPluginComponents.forEach {
            if(it.accept(this))
            {
                val component = it.component(this)
                if(component != null)
                    add(component)
                return
            }
        }

        add(BasicAttributeComponent().component(this))
    }

}