package xmlparser.editor.view.component.attribute

import xmlparser.editor.controller.ActionStack
import xmlparser.editor.action.EditAttributeValueAction
import xmlparser.editor.view.AttributeView
import xmlparser.editor.view.component.IComponent
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingConstants

class BasicAttributeComponent : IComponent<AttributeView, JPanel> {

    override fun component(view: AttributeView): JPanel {
        val panel = JPanel()
        panel.layout = GridLayout(0,2)
        val label = JLabel(view.xmlAttribute.name)
        label.horizontalAlignment = SwingConstants.RIGHT
        panel.add(label)
        val textField = JTextField(view.xmlAttribute.value)
        textField.addActionListener{
            ActionStack.doAction(EditAttributeValueAction(view.xmlAttribute, textField.text))
        }
        panel.add(textField)
        return panel
    }

}