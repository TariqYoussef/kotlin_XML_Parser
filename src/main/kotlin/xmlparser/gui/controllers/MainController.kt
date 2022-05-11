package xmlparser.gui.controllers

import javafx.scene.control.Alert
import javafx.stage.FileChooser
import tornadofx.Controller
import tornadofx.alert
import xmlparser.core.*
import xmlparser.core.element.XmlElement
import xmlparser.gui.ActionStack
import xmlparser.gui.actions.RemoveXmlEntityAction
import xmlparser.gui.views.MainView
import java.io.PrintWriter

private data class Entity(private val id: Int, val name: String)
private data class Point(val x: Int, val y: Int)

@XmlElementName("ComplexEntity")
private class Complex()
{
    @XmlElementContent
    val data: String = "Data Example"
    @XmlElementIgnore
    val ignore: String = "Element to ignore"
    @XmlElementAttribute
    val attribute1: String = "Attribute content"
    @XmlElementAttribute
    @XmlElementName("SpecialAttribute")
    val attribute2: String = "Attribute content"

    private val entity: Entity = Entity(1, "1")
    private val point: Point = Point(1,1)
    private val id: Int = 1
    @XmlElementName("maps")
    val map: Map<Int, Point> = mapOf(Pair(0, Point(0, 0)), Pair(1, Point(1, 1)))
}

class MainController : Controller() {
    private val xmlContext: XmlContext = XmlContext()

    private val fileChooser: FileChooser = FileChooser()
    init {
        val complex = Complex()
        xmlContext.setPrincipalXmlElement(complex)
        fileChooser.title = "Save"
        fileChooser.extensionFilters.addAll(FileChooser.ExtensionFilter("Xml", "*.xml"))
    }

    fun context() = xmlContext

    fun removeElement(xmlElement: XmlElement) {
        val removeXmlEntityAction = RemoveXmlEntityAction(this.xmlContext, xmlElement)
        ActionStack.doAction(removeXmlEntityAction)
    }

    fun undo()
    {
        ActionStack.undoAction()
    }

    fun save(){
        val file = fileChooser.showSaveDialog(primaryStage)
        if(file == null)
        {
            alert(Alert.AlertType.ERROR, "Must select a file.")
            return
        }
        val printWriter = PrintWriter(file)
        printWriter.println(context())
        printWriter.close()
    }
}