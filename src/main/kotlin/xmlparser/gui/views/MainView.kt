package xmlparser.gui.views

import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import tornadofx.*
import xmlparser.core.element.XmlElement
import xmlparser.gui.controllers.AddElementController
import xmlparser.gui.controllers.EditElementController
import xmlparser.gui.controllers.MainController

class MainView : View() {
    private val controller: MainController by inject()

    private var treeView: TreeView<XmlElement> by singleAssign()

    var populateTreeView: () -> Unit by singleAssign()

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

            menus.addAll(this@MainView.find(EditView::class).root.menus)
        }

        treeView = treeview {
            isShowRoot = false
            root = TreeItem()

            cellFormat {
                text =  if(it.value != "") it.name + ": " + it.value
                        else it.name
            }

            val childFactory: (TreeItem<XmlElement>) -> Iterable<XmlElement>? = {
                if(controller.context().principalXmlElement() == null) listOf()
                else if (it == root) listOf(controller.context().principalXmlElement()!!)
                else it.value.children()
            }

            populate(childFactory = childFactory)
            populateTreeView = { populate(childFactory = childFactory) }

            contextmenu {
                item("Edit").action {
                    this@MainView.find(EditElementController::class).setContext(treeView.selectionModel.selectedItem?.value)
                    this@MainView.find(EditElementView::class).openWindow()
                }
                item("Add Element").action{
                    this@MainView.find(AddElementController::class).setContext(treeView.selectionModel.selectedItem?.value)
                    this@MainView.find(AddElementView::class).openWindow()
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