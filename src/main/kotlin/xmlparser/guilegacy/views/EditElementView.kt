package xmlparser.guilegacy.views

import javafx.scene.control.Alert
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import tornadofx.*
import xmlparser.core.element.XmlElementAttribute
import xmlparser.guilegacy.controllers.EditElementController

class EditElementView : View() {

    private val controller: EditElementController by inject()

    private var newAttributeName: TextField by singleAssign()
    private var newAttributeValue: TextField by singleAssign()

    private var newElementName: TextField by singleAssign()
    private var newElementValue: TextField by singleAssign()

    private var selectedAttribute: XmlElementAttribute? = null

    private var tableview: TableView<XmlElementAttribute> by singleAssign()

    fun setContext()
    {
        tableview.items = controller.attributes
        tableview.refresh()
        newAttributeName.text = ""
        newAttributeValue.text = ""
        newElementName.text = controller.xmlElement?.name
        newElementValue.text = controller.xmlElement?.value.toString()
        selectedAttribute = null
    }

    override val root = vbox {

        menubar {
            menu("Edit") {
                item("Undo").action {
                    controller.undo()
                }
                item("Redo").action {
                    controller.redo()
                }
            }
        }

        form {
            fieldset("Edit Element") {
                field("Element Name") {
                    newElementName = textfield(controller.xmlElement?.name)
                    {
                        promptText = "Element Name"
                    }
                }
                field("Element Value") {
                    newElementValue = textfield(controller.xmlElement?.value.toString())
                    {
                        promptText = "Element Value"
                    }
                }
                button("Save") {
                    action {
                        if(newElementName.text == "")
                        {
                            alert(Alert.AlertType.ERROR, "An element must have a name.")
                            return@action
                        }
                        controller.updateEntity(newElementName.text, newElementValue.text)
                    }
                }
            }
        }

        tableview = tableview(controller.attributes) {
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
                promptText = "Attribute name"
            }
            newAttributeValue = textfield()
            {
                promptText = "Attribute value"
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
                button("Close") {
                    action {
                        controller.onClose()
                        close()
                    }
                }
            }
        }
    }
}