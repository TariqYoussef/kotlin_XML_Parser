package xmlparser.gui.views

import javafx.scene.control.Alert
import javafx.scene.control.TableView
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeTableView
import tornadofx.*
import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.controllers.AddElementController
import xmlparser.gui.controllers.EditElementController
import xmlparser.gui.controllers.MainController

class MainView : View() {
    private val controller: MainController by inject()

    private var treeTableView: TreeTableView<XmlElement> by singleAssign()

    override val root = vbox {

        menubar {
            menu("File") {
                item("Save").action {
                    controller.save()
                }
                item("History").action {
                    this@MainView.find(LocalHistoryView::class).openWindow()
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
                    controller.redo()
                }
            }
        }

        treeTableView = treetableview {
            isShowRoot = false
            root = TreeItem()

            column("Name", XmlElement::name)
            column("Value", XmlElement::value)

            val childFactory: (TreeItem<XmlElement>) -> Iterable<XmlElement>? = {
                if(controller.context().root() == null) listOf()
                else if (it == root) listOf(controller.context().root()!!)
                else it.value.children()
            }

            populate(childFactory = childFactory)

            controller.updateTreeView = {
                treeTableView.selectionModel.selectedItem?.value = null
                populate(childFactory = childFactory)
                this@treetableview.refresh()
            }

            controller.context().addObserverToAllXmlElements(controller.treeTableViewXmlElementObserver)
            controller.context().addObserver(controller.treeTableViewXmlContextObserver)

            contextmenu {
                item("Edit").action {
                    if(treeTableView.selectionModel.selectedItem?.value == null)
                    {
                        alert(Alert.AlertType.ERROR, "An element must be selected.")
                        return@action
                    }
                    this@MainView.find(EditElementController::class).setContext(treeTableView.selectionModel.selectedItem?.value!!)
                    this@MainView.find(EditElementView::class).openWindow()
                }
                item("Add Element").action {
                    if (treeTableView.selectionModel.selectedItem?.value == null && controller.context().root() != null)
                    {
                        alert(Alert.AlertType.ERROR, "A xml Document can't have 2 roots. Please select an element or delete the root.")
                        return@action
                    }
                    this@MainView.find(AddElementController::class).setContext(treeTableView.selectionModel.selectedItem?.value)
                    this@MainView.find(AddElementView::class).openWindow()
                }
                item("Remove").action{
                    if(treeTableView.selectionModel.selectedItem?.value == null)
                    {
                        alert(Alert.AlertType.ERROR, "An element must be selected.")
                        return@action
                    }
                    controller.removeElement(treeTableView.selectionModel.selectedItem?.value!!)
                    treeTableView.selectionModel.selectedItem?.value = null
                }
            }
        }
    }

}