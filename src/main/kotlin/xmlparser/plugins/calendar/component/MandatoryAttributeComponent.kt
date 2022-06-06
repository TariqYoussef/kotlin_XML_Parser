package xmlparser.plugins.calendar.component

import xmlparser.editor.ActionStack
import xmlparser.editor.action.EditAttributeValueAction
import xmlparser.editor.view.AttributeView
import xmlparser.editor.view.component.IComponent
import java.awt.GridLayout
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants

class MandatoryAttributeComponent: IComponent<AttributeView>
{

    override fun accept(view: AttributeView): Boolean {
        return view.xmlElementAttribute.name == "Mandatory" &&
                view.xmlElement.name == "Event"
    }

    override fun component(view: AttributeView): JPanel {
        val panel = JPanel()
        panel.layout = GridLayout(0,2)
        val label = JLabel(view.xmlElementAttribute.name)
        label.horizontalAlignment = SwingConstants.RIGHT
        panel.add(label)

        val jCheckBox = JCheckBox()
        jCheckBox.isSelected = convertToBool(view.xmlElementAttribute.value)
        jCheckBox.addActionListener {
            val value = jCheckBox.isSelected.toString()
            ActionStack.doAction(
                EditAttributeValueAction(
                    view.xmlElementAttribute,
                    value
                )
            )
        }
        panel.add(jCheckBox)
        return panel
    }

    private fun convertToBool(boolString: String): Boolean = boolString == "true"

}