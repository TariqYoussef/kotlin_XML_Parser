package xmlparser.plugins.calendar.component

import xmlparser.gui.ActionStack
import xmlparser.gui.action.EditAttributeValueAction
import xmlparser.gui.view.AttributeView
import xmlparser.gui.view.component.AttributePanelComponent
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.SwingConstants

class MandatoryAttributePanelComponent: AttributePanelComponent()
{

    override fun accept(attributeView: AttributeView): Boolean {
        return attributeView.xmlElementAttribute.name == "Mandatory" &&
                attributeView.xmlElement.name == "Event"
    }

    override fun getComponent(attributeView: AttributeView) {
        val label = JLabel(attributeView.xmlElementAttribute.name)
        label.horizontalAlignment = SwingConstants.RIGHT
        attributeView.add(label)

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
        attributeView.add(jCheckBox)
    }

    private fun convertToBool(boolString: String): Boolean = boolString == "true"

}