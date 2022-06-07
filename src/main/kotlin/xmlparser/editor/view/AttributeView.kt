package xmlparser.editor.view

import xmlparser.core.XmlElement
import xmlparser.core.XmlAttribute
import xmlparser.editor.Application
import xmlparser.editor.view.component.attribute.BasicAttributeComponent
import java.awt.GridLayout

class AttributeView(private val application: Application,
                    val xmlElement: XmlElement,
                    val xmlAttribute: XmlAttribute
) : AbstractContextView<AttributeView>() {

    override val popupMenuName: String = "Actions"

    init {
        layout = GridLayout(0,1)
        xmlAttribute.addObserver {
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