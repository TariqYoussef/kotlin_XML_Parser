package xmlparser.gui.views

import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.text.Font
import tornadofx.*
import xmlparser.core.element.XmlElement
import xmlparser.gui.controllers.MainController

class MainView : View() {
    private val controller: MainController by inject()

    private var treeView: TreeView<XmlElement> by singleAssign()

    override val root = vbox {

        menubar {
            menu("File") {
                item("Load").action {

                }
                item("Save").action {
                    println(controller.context())
                }
                item("Quit").action {
                    close()
                }
            }

            menu("Edit") {
                item("Undo").action {

                }
                item("Redo").action {

                }
            }
        }

        treeView = treeview {
            isShowRoot = false
            root = TreeItem()
            cellFormat {
                text = if(it.value != "") it.name + ": " + it.value
                        else it.name
            }

            val childFactory: (TreeItem<XmlElement>) -> Iterable<XmlElement>? = {
                if(controller.context().principalXmlElement() == null) listOf()
                else if (it == root) listOf(controller.context().principalXmlElement()!!)
                else it.value.children()
            }

            populate(childFactory = childFactory)

            contextmenu {
                item("Edit").action {
                    val editElementView = EditElementView(treeView.selectionModel.selectedItem?.value!!)
                    editElementView.openModal()
                    populate(childFactory = childFactory)
                }
                item("Add Element").action{
                    val addElementView = AddElementView(treeView.selectionModel.selectedItem?.value!!)
                    addElementView.openModal()
                    populate(childFactory = childFactory)
                }
                item("Remove").action{
                    controller.removeElement(treeView.selectionModel.selectedItem?.value!!)
                    treeView.selectionModel.selectedItem?.value = null
                    populate(childFactory = childFactory)
                }
            }
        }
    }
}