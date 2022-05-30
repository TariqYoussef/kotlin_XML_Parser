package xmlparser.gui.view.component

import xmlparser.gui.ActionStack
import xmlparser.gui.action.EditAttributeValueAction
import xmlparser.gui.view.AttributeView
import javax.swing.JLabel
import javax.swing.JTextField

class BasicAttributeComponent : IAttributeComponent {

    override fun draw(attributeView: AttributeView) {
        attributeView.add(JLabel(attributeView.xmlElementAttribute.name))
        val textField = JTextField(attributeView.xmlElementAttribute.value)
        textField.addActionListener{
            ActionStack.doAction(EditAttributeValueAction(attributeView.xmlElementAttribute, textField.text))
        }
        attributeView.add(textField)
    }

}