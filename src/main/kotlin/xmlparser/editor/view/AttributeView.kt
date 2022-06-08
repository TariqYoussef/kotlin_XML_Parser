package xmlparser.editor.view

import xmlparser.core.XmlElement
import xmlparser.core.XmlAttribute
import xmlparser.editor.controller.MainController
import xmlparser.editor.view.component.attribute.BasicAttributeComponent
import java.awt.GridLayout

class AttributeView(private val mainController: MainController,
                    val xmlElement: XmlElement,
                    val xmlAttribute: XmlAttribute
) : AbstractContextView<AttributeView>() {

    override val popupMenuName: String = "Actions"

    init {
        layout = GridLayout(0,1)
        xmlAttribute.addObserver { _, _ ->
            removeAll()
            createPopupMenu(this,
                mainController.attributeViewMenuItems,
                mainController.attributeViewPluginMenuItems)
            createView()
            updateUI()
            revalidate()
            repaint()
        }
        createPopupMenu(this,
            mainController.attributeViewMenuItems,
            mainController.attributeViewPluginMenuItems)
        createView()
    }

    override fun createView()
    {
        mainController.attributeViewPluginComponents.forEach {
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