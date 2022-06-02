package xmlparser.plugins.calendar.component

import xmlparser.gui.ActionStack
import xmlparser.gui.action.EditAttributeValueAction
import xmlparser.gui.view.AttributeView
import xmlparser.gui.view.component.IAttributeComponent
import java.awt.GridLayout
import java.text.SimpleDateFormat
import java.util.*
import javax.swing.*

class DateAttributeComponent: IAttributeComponent
{

    override fun accept(attributeView: AttributeView): Boolean {
        return attributeView.xmlElementAttribute.name == "Date" &&
                attributeView.xmlElement.name == "Event"
    }

    override fun component(attributeView: AttributeView): JPanel {
        val panel = JPanel()
        panel.layout = GridLayout(0,2)
        val label = JLabel(attributeView.xmlElementAttribute.name)
        label.horizontalAlignment = SwingConstants.RIGHT
        panel.add(label)

        val date = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(attributeView.xmlElementAttribute.value)
        val jSpinner = JSpinner(SpinnerDateModel(date,
            null, null, Calendar.MINUTE))
        jSpinner.addChangeListener {
            val value = jSpinner.value.toString()
            ActionStack.doAction(
                EditAttributeValueAction(
                    attributeView.xmlElementAttribute,
                    value
                )
            )
        }
        panel.add(jSpinner)
        return panel
    }

}