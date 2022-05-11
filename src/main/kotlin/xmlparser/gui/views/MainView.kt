package xmlparser.gui.views

import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableView
import tornadofx.*
import xmlparser.core.element.XmlElement
import xmlparser.gui.controllers.AddElementController
import xmlparser.gui.controllers.EditElementController
import xmlparser.gui.controllers.MainController

class MainView : View() {
    private val controller: MainController by inject()

    private var treeTableView: TreeTableView<XmlElement> by singleAssign()

    var populateTreeView: () -> Unit by singleAssign()

    override val root = vbox {

        menubar {
            menu("File") {
                item("Save").action {
                    controller.save()
                }
                item("Quit").action {
                    close()
                }
            }

            menu("Edit") {
                item("Undo").action {
                    controller.undo()
                }
                item("Redo").action {

                }
            }
        }

        treeTableView = treetableview {
            isShowRoot = false
            root = TreeItem()
            column("Name", XmlElement::name)
            column("Value", XmlElement::value)

            val childFactory: (TreeItem<XmlElement>) -> Iterable<XmlElement>? = {
                if(controller.context().principalXmlElement() == null) listOf()
                else if (it == root) listOf(controller.context().principalXmlElement()!!)
                else it.value.children()
            }

            populate(childFactory = childFactory)
            populateTreeView = { populate(childFactory = childFactory) }

            contextmenu {
                item("Edit").action {
                    this@MainView.find(EditElementController::class).setContext(treeTableView.selectionModel.selectedItem?.value)
                    this@MainView.find(EditElementView::class).openWindow()
                }
                item("Add Element").action{
                    this@MainView.find(AddElementController::class).setContext(treeTableView.selectionModel.selectedItem?.value)
                    this@MainView.find(AddElementView::class).openWindow()
                }
                item("Remove").action{
                    controller.removeElement(treeTableView.selectionModel.selectedItem?.value!!)
                    treeTableView.selectionModel.selectedItem?.value = null
                    populate(childFactory = childFactory)
                }
            }
        }
    }

}