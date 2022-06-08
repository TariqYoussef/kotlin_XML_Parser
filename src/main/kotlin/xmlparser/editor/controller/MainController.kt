package xmlparser.editor.controller

import xmlparser.core.XmlContext
import xmlparser.editor.view.AttributeView
import xmlparser.editor.view.ElementView
import xmlparser.editor.view.Frame
import xmlparser.editor.view.component.IComponent
import xmlparser.editor.view.menuitem.IMenuItem
import xmlparser.editor.view.menuitem.attribute.RemoveAttributeMenuItem
import xmlparser.editor.view.menuitem.attribute.RenameAttributeMenuItem
import xmlparser.editor.view.menuitem.element.AddAttributeMenuItem
import xmlparser.editor.view.menuitem.element.AddChildMenuItem
import xmlparser.editor.view.menuitem.element.RemoveElementMenuItem
import xmlparser.editor.view.menuitem.element.RenameElementMenuItem
import java.awt.Component

class MainController {

    @Inject
    lateinit var context: XmlContext

    @InjectAdd
    val elementViewPluginMenuItems = mutableListOf<IMenuItem<ElementView>>()
    @InjectAdd
    val attributeViewPluginMenuItems = mutableListOf<IMenuItem<AttributeView>>()
    @InjectAdd
    val attributeViewPluginComponents = mutableListOf<IComponent<AttributeView, Component>>()
    @InjectAdd
    val elementValueViewPluginComponents = mutableListOf<IComponent<ElementView, Component>>()

    val elementViewMenuItems: List<IMenuItem<ElementView>> = listOf(
        AddChildMenuItem(),
        RenameElementMenuItem(),
        AddAttributeMenuItem(),
        RemoveElementMenuItem()
    )
    val attributeViewMenuItems: List<IMenuItem<AttributeView>> = listOf(
        RemoveAttributeMenuItem(),
        RenameAttributeMenuItem()
    )

    fun isContextInitialized(): Boolean = this::context.isInitialized
}

fun main() {
    val mainController = Injector.create(MainController::class) as MainController
    val frame = Frame(mainController)
    frame.open()
}