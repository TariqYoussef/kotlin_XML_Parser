package xmlparser.gui.view.component

import xmlparser.gui.ActionStack
import xmlparser.gui.action.EditAttributeValueAction
import xmlparser.gui.view.AttributeView
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.SwingConstants

class BasicAttributeComponent : IComponent {

    override fun component(attributeView: AttributeView): JPanel {
        val panel = JPanel()
        panel.layout = GridLayout(0,2)
        val label = JLabel(attributeView.xmlElementAttribute.name)
        label.horizontalAlignment = SwingConstants.RIGHT
        panel.add(label)
        val textField = JTextField(attributeView.xmlElementAttribute.value)
        textField.addActionListener{
            ActionStack.doAction(EditAttributeValueAction(attributeView.xmlElementAttribute, textField.text))
        }
        panel.add(textField)
        return panel
    }

}