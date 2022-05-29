package xmlparser.guilegacy.controllers

import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.stage.FileChooser
import tornadofx.Controller
import tornadofx.alert
import tornadofx.singleAssign
import xmlparser.core.*
import xmlparser.core.element.XmlElement
import xmlparser.gui.ActionStack
import xmlparser.guilegacy.actions.view.main.RemoveXmlEntityAction
import xmlparser.guilegacy.views.LocalHistoryView
import java.io.PrintWriter


private data class Entity(private val id: Int, val name: String)
private data class Point(val x: Int, val y: Int)

@XmlElementName("ComplexEntity")
private class Complex {
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
    val xmlContext: XmlContext = XmlContext()

    private val fileChooser: FileChooser = FileChooser()

    var updateTreeView: () -> Unit by singleAssign()

    var treeTableViewXmlElementObserver: ((XmlElement) -> Unit) = {
        updateTreeView()
        this.find(EditElementController::class).setContext(it)
        this.find(AddElementController::class).setContext(it)
        this.find(LocalHistoryView::class).updateHistoryList()
    }

    var treeTableViewXmlContextObserver: ((XmlContext) -> Unit) = {
        updateTreeView()
        this.find(LocalHistoryView::class).updateHistoryList()
    }

    init {
        val complex = Complex()
        xmlContext.setRootXmlElement(complex)
        fileChooser.title = "Save"
        fileChooser.extensionFilters.addAll(FileChooser.ExtensionFilter("Xml", "*.xml"))
    }

    fun removeElement(xmlElement: XmlElement) {
        val removeXmlEntityAction = RemoveXmlEntityAction(xmlContext, treeTableViewXmlElementObserver, xmlElement)
        ActionStack.doAction(removeXmlEntityAction)
    }

    fun undo()
    {
        if(!ActionStack.isUndoStackEmpty())
            alert(Alert.AlertType.CONFIRMATION,
                "Do you wish to undo ${ActionStack.getPeekActionUndo().name} ?",
                "", buttons = arrayOf(ButtonType.YES, ButtonType.NO)){
                when(it)
                {
                    ButtonType.YES -> ActionStack.undoAction()
                }
            }
        else
            alert(Alert.AlertType.ERROR, "There are no actions to undo.")
    }

    fun redo()
    {
        if(!ActionStack.isRedoStackEmpty())
            alert(Alert.AlertType.CONFIRMATION,
                "Do you wish to redo ${ActionStack.getPeekActionRedo().name} ?",
                "", buttons = arrayOf(ButtonType.YES, ButtonType.NO)){
                when(it)
                {
                    ButtonType.YES -> ActionStack.redoAction()
                }
            }
        else
            alert(Alert.AlertType.ERROR, "There are no actions to redo.")
    }

    fun save(){
        val file = fileChooser.showSaveDialog(primaryStage)
        if(file == null)
        {
            alert(Alert.AlertType.ERROR, "Must select a file.")
            return
        }
        val printWriter = PrintWriter(file)
        printWriter.println(xmlContext)
        printWriter.close()
    }
}