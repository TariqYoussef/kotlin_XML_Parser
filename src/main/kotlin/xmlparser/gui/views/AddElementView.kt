package xmlparser.gui.views

import javafx.scene.control.Alert
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import tornadofx.*
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.controllers.AddElementController

class AddElementView: View() {
    private val controller: AddElementController by inject()

    private var newAttributeName: TextField by singleAssign()
    private var newAttributeValue: TextField by singleAssign()

    private var newElementName: TextField by singleAssign()
    private var newElementValue: TextField by singleAssign()

    private var selectedAttribute: XmlElementAttribute? = null

    private var tableview: TableView<XmlElementAttribute> by singleAssign()

    fun setContext()
    {
        tableview.items = controller.attributes()
        tableview.refresh()
        newAttributeName.text = ""
        newAttributeValue.text = ""
        newElementName.text = ""
        newElementValue.text = ""
        selectedAttribute = null
    }

    override val root = vbox {

        menubar {
            menu("Edit") {
                item("Undo").action {
                    controller.undo()
                }
                item("Redo").action {

                }
            }
        }

        form {
            fieldset("Add Element") {
                field("Element Name") {
                    newElementName = textfield()
                    {
                        promptText = "Element name"
                    }
                }
                field("Element Value") {
                    newElementValue = textfield()
                    {
                        promptText = "Element value"
                    }
                }
            }
        }

        tableview = tableview(controller.attributes()) {
            isEditable = true
            column("Name", XmlElementAttribute::name).makeEditable()
            column("Value", XmlElementAttribute::value).makeEditable()

            var oldAttribute: XmlElementAttribute? = null

            onEditCommit {
                controller.registerAttributeUpdate(it, oldAttribute!!)
            }

            onEditStart {
                oldAttribute = it.copy()
            }

            contextmenu {
                item("Remove").action{
                    if(selectedAttribute == null)
                    {
                        alert(Alert.AlertType.ERROR, "Must select an attribute.")
                        return@action
                    }
                    controller.removeAttribute(selectedAttribute!!)
                }
            }

            onSelectionChange { selectedAttribute = it }
        }

        hbox {
            newAttributeName = textfield()
            {
                promptText = "Attribute Name"
            }
            newAttributeValue = textfield()
            {
                promptText = "Attribute Value"
            }
            button("Add Attribute") {
                action {
                    if(newAttributeName.text == "")
                    {
                        alert(Alert.AlertType.ERROR, "An attribute must have a name.")
                        return@action
                    }
                    controller.addAttribute(newAttributeName.text, newAttributeValue.text)
                    newAttributeName.text = ""
                    newAttributeValue.text = ""
                }
            }
        }

        borderpane {
            paddingAll = 5
            right = hbox {
                spacing = 5.0
                button("Create") {
                    action {
                        if(newElementName.text == "")
                        {
                            alert(Alert.AlertType.ERROR, "An element must have a name.")
                            return@action
                        }
                        controller.createChild(newElementName.text, newElementValue.text)
                        controller.onClose()
                        close()
                    }
                }
                button("Cancel") {
                    action {
                        controller.onClose()
                        close()
                    }
                }
            }
        }

    }
}