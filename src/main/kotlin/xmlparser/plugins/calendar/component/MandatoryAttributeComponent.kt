package xmlparser.plugins.calendar.component

import xmlparser.gui.ActionStack
import xmlparser.gui.action.EditAttributeValueAction
import xmlparser.gui.view.AttributeView
import xmlparser.gui.view.component.IAttributeComponent
import java.awt.GridLayout
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants

class MandatoryAttributeComponent: IAttributeComponent
{

    override fun accept(attributeView: AttributeView): Boolean {
        return attributeView.xmlElementAttribute.name == "Mandatory" &&
                attributeView.xmlElement.name == "Event"
    }

    override fun component(attributeView: AttributeView): JPanel {
        val panel = JPanel()
        panel.layout = GridLayout(0,2)
        val label = JLabel(attributeView.xmlElementAttribute.name)
        label.horizontalAlignment = SwingConstants.RIGHT
        panel.add(label)

        val jCheckBox = JCheckBox()
        jCheckBox.isSelected = convertToBool(attributeView.xmlElementAttribute.value)
        jCheckBox.addActionListener {
            val value = jCheckBox.isSelected.toString()
            ActionStack.doAction(
                EditAttributeValueAction(
                    attributeView.xmlElementAttribute,
                    value
                )
            )
        }
        panel.add(jCheckBox)
        return panel
    }

    private fun convertToBool(boolString: String): Boolean = boolString == "true"

}