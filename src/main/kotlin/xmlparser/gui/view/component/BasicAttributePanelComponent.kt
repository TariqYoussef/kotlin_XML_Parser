package xmlparser.gui.view.component

import xmlparser.gui.ActionStack
import xmlparser.gui.action.EditAttributeValueAction
import xmlparser.gui.view.AttributeView
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.SwingConstants

class BasicAttributePanelComponent : AttributePanelComponent() {

    override fun getComponent(attributeView: AttributeView) {
        val label = JLabel(attributeView.xmlElementAttribute.name)
        label.horizontalAlignment = SwingConstants.RIGHT
        attributeView.add(label)
        val textField = JTextField(attributeView.xmlElementAttribute.value)
        textField.addActionListener{
            ActionStack.doAction(EditAttributeValueAction(attributeView.xmlElementAttribute, textField.text))
        }
        attributeView.add(textField)
    }

}