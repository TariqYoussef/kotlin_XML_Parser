package xmlparser.gui.views

import javafx.scene.control.Alert
import javafx.scene.control.TextField
import tornadofx.*
import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.controllers.EditElementController

class EditElementView(xmlElement: XmlElement) : View() {

    private val controller: EditElementController = EditElementController(xmlElement)

    private var newAttributeName: TextField by singleAssign()
    private var newAttributeValue: TextField by singleAssign()

    private var newElementName: TextField by singleAssign()
    private var newElementValue: TextField by singleAssign()

    private var selectedAttribute: XmlElementAttribute? = null

    override val root = vbox {

        menubar {
            menu("Edit") {
                item("Undo").action {

                }
                item("Redo").action {

                }
            }
        }

        form {
            fieldset("Edit Element") {
                field("Element Name") {
                    newElementName = textfield(xmlElement.name)
                    {
                        promptText = "Element Name"
                    }
                }
                field("Element Value") {
                    newElementValue = textfield(xmlElement.value as String)
                    {
                        promptText = "Element Value"
                    }
                }
            }
        }

        tableview(controller.attributes()) {
            isEditable = true
            column("Name", XmlElementAttribute::name).makeEditable()
            column("Value", XmlElementAttribute::value).makeEditable()

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
                button("Save") {
                    action {
                        if(newElementName.text == "")
                        {
                            alert(Alert.AlertType.ERROR, "An element must have a name.")
                            return@action
                        }
                        controller.updateEntity(newElementName.text, newElementValue.text)
                        close()
                    }
                }
                button("Cancel") {
                    action {
                        close()
                    }
                }
                button("Apply") {
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
    }
}